package org.depromeet.spot.domain.section;

import org.depromeet.spot.domain.common.HexCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
@AllArgsConstructor
public class Section {

    private final Long id;
    private final Long stadiumId;
    private final String name;
    private final String alias;
    private final HexCode labelColor;
}
