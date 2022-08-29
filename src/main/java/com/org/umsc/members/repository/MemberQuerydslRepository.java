package com.org.umsc.members.repository;

import com.org.umsc.members.dto.UserInfoResponse;
import com.org.umsc.members.entity.Member;
import com.org.umsc.members.entity.QMember;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberQuerydslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Transactional(readOnly = true)
    public Member findBySocialId(String socialId) {
        return jpaQueryFactory
                .selectFrom(QMember.member)
                .where(QMember.member.socialId.eq(socialId))
                .fetchOne();
    }

    public UserInfoResponse findByMemberId(Long memberId) {
        return jpaQueryFactory
                .select(Projections.fields(UserInfoResponse.class,
                        QMember.member.profileImagePath.as("profileImage"),
                        QMember.member.name.as("nickName"),
                        QMember.member.email))
                .from(QMember.member)
                .where(QMember.member.id.eq(memberId))
                .fetchOne();
    }
}
