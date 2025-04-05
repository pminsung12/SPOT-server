package org.depromeet.spot.usecase.port.in.review;

import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.review.ReviewCount;

import lombok.Builder;

@Builder
public record MemberInfoOnMyReviewResult(
        Long userId,
        String profileImageUrl,
        Integer level,
        String levelTitle,
        String nickname,
        Long reviewCount,
        Long totalLikes,
        Long teamId,
        String teamName) {

    public static MemberInfoOnMyReviewResult of(Member member, ReviewCount reviewCount) {
        return MemberInfoOnMyReviewResult.builder()
                .userId(member.getId())
                .profileImageUrl(member.getProfileImage())
                .level(member.getLevel().getValue())
                .levelTitle(member.getLevel().getTitle())
                .nickname(member.getNickname())
                .reviewCount(reviewCount.reviewCount())
                .totalLikes(reviewCount.totalLikes())
                .teamId(null)
                .teamName(null)
                .build();
    }

    public static MemberInfoOnMyReviewResult of(
            Member member, ReviewCount reviewCount, String teamName) {
        return MemberInfoOnMyReviewResult.builder()
                .userId(member.getId())
                .profileImageUrl(member.getProfileImage())
                .level(member.getLevel().getValue())
                .levelTitle(member.getLevel().getTitle())
                .nickname(member.getNickname())
                .reviewCount(reviewCount.reviewCount())
                .totalLikes(reviewCount.totalLikes())
                .teamId(member.getTeamId())
                .teamName(teamName)
                .build();
    }
}
