package org.depromeet.spot.usecase.port.in.review;

import java.io.Serializable;
import java.util.List;

import org.depromeet.spot.domain.review.Review;

import lombok.Builder;

@Builder
public record BlockReviewListResult(
        LocationInfo location,
        List<Review> reviews,
        List<BlockKeywordInfo> topKeywords,
        List<Review> topReviewImages,
        Long totalElements,
        String nextCursor,
        boolean hasNext)
        implements Serializable {}
