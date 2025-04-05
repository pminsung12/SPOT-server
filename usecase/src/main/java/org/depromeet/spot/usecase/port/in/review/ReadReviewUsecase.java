package org.depromeet.spot.usecase.port.in.review;

import java.util.List;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.Review.ReviewType;
import org.depromeet.spot.domain.review.Review.SortCriteria;
import org.depromeet.spot.domain.review.ReviewYearMonth;

public interface ReadReviewUsecase {

    BlockReviewListResult findReviewsByStadiumIdAndBlockCode(
            Long memberId,
            Long stadiumId,
            String blockCode,
            Integer rowNumber,
            Integer seatNumber,
            Integer year,
            Integer month,
            String cursor,
            SortCriteria sortBy,
            Integer size);

    MyReviewListResult findMyReviewsByUserId(
            Long userId,
            Integer year,
            Integer month,
            String cursor,
            SortCriteria sortBy,
            Integer size,
            ReviewType reviewType);

    MemberInfoOnMyReviewResult findMemberInfoOnMyReview(Long memberId);

    List<ReviewYearMonth> findReviewMonths(Long memberId, ReviewType reviewType);

    MyRecentReviewResult findLastReviewByMemberId(Long memberId);

    ReadReviewResult findReviewById(Long reviewId, Long memberId);

    long countByIdByMemberId(Long memberId);

    long countByMember(Long memberId);

    Review findById(long reviewId);
}
