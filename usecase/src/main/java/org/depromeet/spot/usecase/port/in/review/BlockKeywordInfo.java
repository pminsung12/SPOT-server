package org.depromeet.spot.usecase.port.in.review;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Builder;

@JsonSerialize
@JsonDeserialize
@Builder
public record BlockKeywordInfo(String content, Long count, Boolean isPositive)
        implements Serializable {}
