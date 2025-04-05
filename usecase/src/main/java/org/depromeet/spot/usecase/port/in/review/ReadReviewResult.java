package org.depromeet.spot.usecase.port.in.review;

import org.depromeet.spot.domain.review.Review;

import lombok.Builder;

@Builder
public record ReadReviewResult(Review review) {}
