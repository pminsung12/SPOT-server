package org.depromeet.spot.domain.block;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Block {

    private final Long id;
    private final Long stadiumId;
    private final Long sectionId;
    private final String code;
    private final Integer maxRows;

    public static final int BLOCK_SEAT_START_NUM = 1;

    @JsonCreator
    public Block(
            @JsonProperty("id") Long id,
            @JsonProperty("stadiumId") Long stadiumId,
            @JsonProperty("sectionId") Long sectionId,
            @JsonProperty("code") String code,
            @JsonProperty("maxRows") Integer maxRows) {
        this.id = id;
        this.stadiumId = stadiumId;
        this.sectionId = sectionId;
        this.code = code;
        this.maxRows = maxRows;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Block block = (Block) o;
        return Objects.equals(id, block.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
