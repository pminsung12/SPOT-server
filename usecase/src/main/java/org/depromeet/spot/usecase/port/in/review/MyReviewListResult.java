package org.depromeet.spot.usecase.port.in.review;

import java.util.List;

import org.depromeet.spot.domain.review.Review;

import lombok.Builder;

@Builder
public record MyReviewListResult(
        MemberInfoOnMyReviewResult memberInfoOnMyReviewResult,
        List<Review> reviews,
        String nextCursor,
        boolean hasNext) {}
