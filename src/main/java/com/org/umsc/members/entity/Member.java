package com.org.umsc.members.entity;

import com.org.umsc.auth.enums.RoleType;
import com.org.umsc.common.entity.CommonEntity;
import com.org.umsc.members.enums.MemberAuthProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String gender;

    @Column(nullable = false)
    private String socialId;

    @Column
    @Enumerated(EnumType.STRING)
    private MemberAuthProvider memberAuthProvider;

    @Column
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Column
    private String profileImagePath;
}
