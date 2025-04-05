package org.depromeet.spot.domain.block;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BlockRow {

    private final Long id;
    private final Block block;
    private final Integer number;
    private final Integer maxSeats;

    @JsonCreator
    public BlockRow(
            @JsonProperty("id") Long id,
            @JsonProperty("block") Block block,
            @JsonProperty("number") Integer number,
            @JsonProperty("maxSeats") Integer maxSeats) {
        this.id = id;
        this.block = block;
        this.number = number;
        this.maxSeats = maxSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BlockRow row = (BlockRow) o;
        return Objects.equals(id, row.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
