package org.depromeet.spot.infrastructure;

import org.depromeet.spot.infrastructure.jpa.oauth.config.OauthProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@EnableConfigurationProperties(OauthProperties.class)
@ConfigurationPropertiesScan(basePackages = {"org.depromeet.spot.infrastructure"})
@ComponentScan(basePackages = {"org.depromeet.spot.infrastructure"})
public class InfrastructureConfig {}
