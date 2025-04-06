package org.depromeet.spot.infrastructure.redis;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.depromeet.spot.domain.review.Review.SortCriteria;
import org.depromeet.spot.usecase.port.in.review.BlockReviewListResult;
import org.depromeet.spot.usecase.port.in.review.LocationInfo;
import org.depromeet.spot.usecase.service.review.DeleteReviewService;
import org.depromeet.spot.usecase.service.review.ReadReviewService;
import org.depromeet.spot.usecase.service.review.ReviewMetadataService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SqlGroup({
    @Sql(
            value = "/sql/delete-data-after-review-like.sql",
            executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
    @Sql(
            value = "/sql/review-like-service-data.sql",
            executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
})
@ExtendWith(OutputCaptureExtension.class)
public class ReadReviewCacheIntegrationTest {

    @Autowired private ReadReviewService readReviewService;

    @Autowired private DeleteReviewService deleteReviewService;

    @Autowired private RedisTemplate<String, Object> redisTemplate;

    @Autowired private ReviewMetadataService reviewMetadataService;

    @Container
    static GenericContainer<?> redisContainer =
            new GenericContainer<>("redis:6.2").withExposedPorts(6379);

    @DynamicPropertySource
    static void overrideRedisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.redis.host", redisContainer::getHost);
        registry.add("spring.redis.port", () -> redisContainer.getMappedPort(6379));
    }

    private final Long stadiumId = 1L;
    private final String blockCode = "207";

    @Test
    @Transactional
    void 캐시_자동_생성_및_HIT_MISS_TTL_검증(CapturedOutput output) throws InterruptedException {
        // 1. 첫 호출 → CACHE MISS 로그가 찍히고 캐시 저장
        BlockReviewListResult firstCall =
                readReviewService.findReviewsByStadiumIdAndBlockCode(
                        1L,
                        stadiumId,
                        blockCode,
                        null,
                        null,
                        null,
                        null,
                        null,
                        org.depromeet.spot.domain.review.Review.SortCriteria.DATE_TIME,
                        10);

        assertThat(firstCall).isNotNull();

        // 방법 1: 로그에 CACHE MISS 한 번만 있는지 확인
        long missCount =
                output.getOut().lines().filter(line -> line.contains("CACHE MISS")).count();
        assertThat(missCount).isGreaterThan(1);
        System.out.println("[TEST] CACHE MISS 로그 확인 완료");

        // 방법 2: Redis에 자동 생성된 캐시 키가 있는지 확인
        Set<String> keys = redisTemplate.keys("blockReviews*");
        assertThat(keys).isNotEmpty(); // 빈 Set이면 캐싱이 안된 것
        String cacheKey = keys.iterator().next();
        Long ttl = redisTemplate.getExpire(cacheKey);
        assertThat(ttl).isGreaterThan(0);
        System.out.println("[TEST] 생성된 캐시 키: " + cacheKey + ", TTL: " + ttl);

        // 2. 두 번째 호출 → 캐시 HIT (DB 접근 없음)
        BlockReviewListResult secondCall =
                readReviewService.findReviewsByStadiumIdAndBlockCode(
                        1L,
                        stadiumId,
                        blockCode,
                        null,
                        null,
                        null,
                        null,
                        null,
                        org.depromeet.spot.domain.review.Review.SortCriteria.DATE_TIME,
                        10);
        assertThat(secondCall).usingRecursiveComparison().isEqualTo(firstCall);
        System.out.println("[TEST] 캐시 HIT 확인 완료");

        // 3. TTL 지나고 → 캐시가 만료됐는지 확인
        Thread.sleep(6000); // TTL은 5초라고 가정
        Long expired = redisTemplate.getExpire(cacheKey);
        assertThat(expired).isEqualTo(-2); // -2는 key 존재하지 않음
        System.out.println("[TEST] TTL 만료 확인 완료");

        // 4. 세 번째 호출 → 다시 CACHE MISS 발생
        BlockReviewListResult thirdCall =
                readReviewService.findReviewsByStadiumIdAndBlockCode(
                        1L,
                        stadiumId,
                        blockCode,
                        null,
                        null,
                        null,
                        null,
                        null,
                        org.depromeet.spot.domain.review.Review.SortCriteria.DATE_TIME,
                        10);

        assertThat(thirdCall).isNotEqualTo(secondCall); // 캐시가 만료됐기 때문에 새로 조회
        System.out.println("[TEST] TTL 만료 이후 CACHE MISS 확인 완료");
    }

    @Transactional
    @Test
    void 리뷰_삭제_시_캐시가_삭제되는지_확인() {
        // 1. 첫 호출로 캐시 생성
        BlockReviewListResult cached =
                readReviewService.findReviewsByStadiumIdAndBlockCode(
                        1L,
                        stadiumId,
                        blockCode,
                        null,
                        null,
                        null,
                        null,
                        null,
                        SortCriteria.DATE_TIME,
                        10);
        assertThat(cached).isNotNull();
        System.out.println("[TEST] 캐시 최초 생성 완료");

        // 2. Redis에 캐시 키 존재 확인
        Set<String> keysBefore = redisTemplate.keys("blockReviews*");
        assertThat(keysBefore).isNotEmpty();
        System.out.println("[TEST] 삭제 전 캐시 키 존재: " + keysBefore);

        // 3. 리뷰 삭제 실행
        deleteReviewService.deleteReview(1L, 1L);

        // 4. 캐시 키가 제거되었는지 확인
        Set<String> keysAfter = redisTemplate.keys("blockReviews*");
        assertThat(keysAfter).isEmpty();
        System.out.println("[TEST] 리뷰 삭제 후 캐시 삭제 확인 완료");
    }

    //    @Test
    //    void topKeywords_캐시_생성_TTL_확인() throws InterruptedException {
    //        // 1. 호출 → 캐시 생성
    //        List<BlockKeywordInfo> result = reviewMetadataService.getTopKeywords(stadiumId,
    // blockCode);
    //        assertThat(result).isNotEmpty();
    //
    //        // 2. 캐시 키 확인
    //        Set<String> keys = redisTemplate.keys("topKeywords*");
    //        assertThat(keys).isNotEmpty();
    //        String key = keys.iterator().next();
    //        Long ttl = redisTemplate.getExpire(key);
    //        assertThat(ttl).isGreaterThan(0);
    //
    //        System.out.println("TTL for topKeywords: " + ttl);
    //    }

    @Test
    void locationInfo_캐시_생성_TTL_확인() {
        LocationInfo locationInfo = reviewMetadataService.getLocationInfo(stadiumId, blockCode);
        assertThat(locationInfo).isNotNull();

        Set<String> keys = redisTemplate.keys("locationInfo*");
        assertThat(keys).isNotEmpty();
    }

    //    @Test
    //    void topReviewImages_캐시_생성_TTL_확인() {
    //        List<Review> images = reviewMetadataService.getTopReviewImages(stadiumId, blockCode);
    //        assertThat(images).isNotEmpty();
    //
    //        Set<String> keys = redisTemplate.keys("topReviewImages*");
    //        assertThat(keys).isNotEmpty();
    //    }
}
