package org.depromeet.spot.common.util;

import org.depromeet.spot.domain.review.Review.SortCriteria;

public class CacheKeyUtil {

    public static String generateBlockReviewKey(
            Long stadiumId,
            String blockCode,
            Integer rowNumber,
            Integer seatNumber,
            Integer year,
            Integer month,
            SortCriteria sortBy) {
        return String.format(
                "stadium:%d:block:%s:row:%s:seat:%s:year:%s:month:%s:sort:%s",
                stadiumId,
                blockCode,
                rowNumber != null ? rowNumber : "any",
                seatNumber != null ? seatNumber : "any",
                year != null ? year : "any",
                month != null ? month : "any",
                sortBy != null ? sortBy.name() : "DATE_TIME");
    }
}
