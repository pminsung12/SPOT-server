package org.depromeet.spot.domain.stadium;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Stadium {

    private final Long id;
    private final String name;
    private final String mainImage;
    private final String seatingChartImage;
    private final String labeledSeatingChartImage;
    private final boolean isActive;

    @JsonCreator
    public Stadium(
            @JsonProperty("id") Long id,
            @JsonProperty("name") String name,
            @JsonProperty("mainImage") String mainImage,
            @JsonProperty("seatingChartImage") String seatingChartImage,
            @JsonProperty("labeledSeatingChartImage") String labeledSeatingChartImage,
            @JsonProperty("active") boolean isActive) {
        this.id = id;
        this.name = name;
        this.mainImage = mainImage;
        this.seatingChartImage = seatingChartImage;
        this.labeledSeatingChartImage = labeledSeatingChartImage;
        this.isActive = isActive;
    }
}
