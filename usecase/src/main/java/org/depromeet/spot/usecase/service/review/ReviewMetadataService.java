package org.depromeet.spot.usecase.service.review;

import java.util.List;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.usecase.port.in.review.BlockKeywordInfo;
import org.depromeet.spot.usecase.port.in.review.LocationInfo;
import org.depromeet.spot.usecase.port.out.review.BlockTopKeywordRepository;
import org.depromeet.spot.usecase.port.out.review.ReviewImageRepository;
import org.depromeet.spot.usecase.port.out.review.ReviewRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ReviewMetadataService {

    private final BlockTopKeywordRepository blockTopKeywordRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final ReviewRepository reviewRepository;

    @Cacheable(
            value = "topKeywords",
            key = "'stadium:' + #stadiumId + ':block:' + #blockCode",
            unless = "#result == null")
    public List<BlockKeywordInfo> getTopKeywords(Long stadiumId, String blockCode) {
        log.info("[CACHE MISS] getTopKeywords - stadiumId={}, blockCode={}", stadiumId, blockCode);
        return blockTopKeywordRepository.findTopKeywordsByStadiumIdAndBlockCode(
                stadiumId, blockCode, 5);
    }

    @Cacheable(
            value = "topReviewImages",
            key = "'stadium:' + #stadiumId + ':block:' + #blockCode",
            unless = "#result == null")
    public List<Review> getTopReviewImages(Long stadiumId, String blockCode) {
        log.info(
                "[CACHE MISS] getTopReviewImages - stadiumId={}, blockCode={}",
                stadiumId,
                blockCode);
        return reviewImageRepository.findTopReviewImagesByStadiumIdAndBlockCode(
                stadiumId, blockCode, 5);
    }

    @Cacheable(
            value = "locationInfo",
            key = "'stadium:' + #stadiumId + ':block:' + #blockCode",
            unless = "#result == null")
    public LocationInfo getLocationInfo(Long stadiumId, String blockCode) {
        log.info("[CACHE MISS] getLocationInfo - stadiumId={}, blockCode={}", stadiumId, blockCode);
        return reviewRepository.findLocationInfoByStadiumIdAndBlockCode(stadiumId, blockCode);
    }
}
