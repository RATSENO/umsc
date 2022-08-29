package com.org.umsc.members.repository;

import com.org.umsc.members.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findMemberById(Long memberId);
}
