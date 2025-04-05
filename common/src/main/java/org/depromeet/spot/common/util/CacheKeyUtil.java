package org.depromeet.spot.common.util;

public class CacheKeyUtil {
    public static String generateBlockReviewKey(
            Long stadiumId,
            String blockCode,
            Integer rowNumber,
            Integer seatNumber,
            Integer year,
            Integer month,
            String sortBy,
            String cursor) {
        StringBuilder key = new StringBuilder("block:");

        key.append(stadiumId).append(":").append(blockCode).append(":").append(sortBy);

        if (cursor != null) key.append(":cursor=").append(cursor);
        if (rowNumber != null) key.append(":row=").append(rowNumber);
        if (seatNumber != null) key.append(":seat=").append(seatNumber);
        if (year != null) key.append(":year=").append(year);
        if (month != null) key.append(":month=").append(month);

        return key.toString();
    }
}
