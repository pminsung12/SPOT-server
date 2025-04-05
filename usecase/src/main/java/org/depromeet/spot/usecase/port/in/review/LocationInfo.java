package org.depromeet.spot.usecase.port.in.review;

import java.io.Serializable;

import lombok.Builder;

@Builder
public record LocationInfo(String stadiumName, String sectionName, String blockCode)
        implements Serializable {}
