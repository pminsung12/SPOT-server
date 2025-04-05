package org.depromeet.spot.domain.seat;

import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.block.BlockRow;
import org.depromeet.spot.domain.section.Section;
import org.depromeet.spot.domain.stadium.Stadium;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Seat {

    private final Long id;
    private final Stadium stadium;
    private final Section section;
    private final Block block;
    private final BlockRow row;
    private final Integer seatNumber;

    @JsonCreator
    public Seat(
            @JsonProperty("id") Long id,
            @JsonProperty("stadium") Stadium stadium,
            @JsonProperty("section") Section section,
            @JsonProperty("block") Block block,
            @JsonProperty("row") BlockRow row,
            @JsonProperty("seatNumber") Integer seatNumber) {
        this.id = id;
        this.stadium = stadium;
        this.section = section;
        this.block = block;
        this.row = row;
        this.seatNumber = seatNumber;
    }
}
