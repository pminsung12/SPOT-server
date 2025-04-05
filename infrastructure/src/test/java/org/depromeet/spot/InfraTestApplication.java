package org.depromeet.spot;

import org.depromeet.spot.infrastructure.jpa.oauth.config.OauthProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = "org.depromeet.spot")
@EnableConfigurationProperties(OauthProperties.class)
public class InfraTestApplication {}
