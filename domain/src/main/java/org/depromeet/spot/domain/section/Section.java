package org.depromeet.spot.domain.section;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class Section {

    private final Long id;
    private final Long stadiumId;
    private final String name;
    private final String alias;

    @JsonCreator
    public Section(
            @JsonProperty("id") Long id,
            @JsonProperty("stadiumId") Long stadiumId,
            @JsonProperty("name") String name,
            @JsonProperty("alias") String alias) {
        this.id = id;
        this.stadiumId = stadiumId;
        this.name = name;
        this.alias = alias;
    }
}
