package org.depromeet.spot.domain.member;

import java.time.LocalDateTime;

import org.depromeet.spot.domain.member.enums.MemberRole;
import org.depromeet.spot.domain.member.enums.SnsProvider;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Member {

    private final Long id;
    private final String email;
    private final String name;
    private final String nickname;
    private final String phoneNumber;
    private final Level level;
    private final String profileImage;
    private final SnsProvider snsProvider;
    private final String idToken;
    private final Long teamId;
    private final MemberRole role;
    private final LocalDateTime createdAt;
    private final LocalDateTime deletedAt;
    private final LocalDateTime updatedAt;

    @JsonCreator
    public Member(
            @JsonProperty("id") Long id,
            @JsonProperty("email") String email,
            @JsonProperty("name") String name,
            @JsonProperty("nickname") String nickname,
            @JsonProperty("phoneNumber") String phoneNumber,
            @JsonProperty("level") Level level,
            @JsonProperty("profileImage") String profileImage,
            @JsonProperty("snsProvider") SnsProvider snsProvider,
            @JsonProperty("idToken") String idToken,
            @JsonProperty("teamId") Long teamId,
            @JsonProperty("role") MemberRole role,
            @JsonProperty("createdAt") LocalDateTime createdAt,
            @JsonProperty("deletedAt") LocalDateTime deletedAt,
            @JsonProperty("updatedAt") LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.level = level;
        this.profileImage = profileImage;
        this.snsProvider = snsProvider;
        this.idToken = idToken;
        this.teamId = teamId;
        this.role = role;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.updatedAt = updatedAt;
    }

    public Member updateLevel(Level newLevel) {
        return Member.builder()
                .id(id)
                .email(email)
                .name(name)
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .level(newLevel)
                .profileImage(profileImage)
                .snsProvider(snsProvider)
                .idToken(idToken)
                .teamId(teamId)
                .role(role)
                .createdAt(createdAt)
                .deletedAt(deletedAt)
                .updatedAt(updatedAt)
                .build();
    }

    public Member updateProfile(String newProfileImage, String newNickname, Long newTeamId) {
        return Member.builder()
                .id(id)
                .email(email)
                .name(name)
                .nickname(newNickname)
                .phoneNumber(phoneNumber)
                .level(level)
                .profileImage(newProfileImage)
                .snsProvider(snsProvider)
                .idToken(idToken)
                .teamId(newTeamId)
                .role(role)
                .createdAt(createdAt)
                .deletedAt(deletedAt)
                .updatedAt(updatedAt)
                .build();
    }
}
