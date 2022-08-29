package com.org.umsc.auth.service;

import com.org.umsc.auth.client.KakaoClient;
import com.org.umsc.auth.dto.AuthRequest;
import com.org.umsc.auth.dto.AuthResponse;
import com.org.umsc.auth.jwt.AuthToken;
import com.org.umsc.auth.jwt.AuthTokenProvider;
import com.org.umsc.members.entity.Member;
import com.org.umsc.members.repository.MemberQuerydslRepository;
import com.org.umsc.members.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {
    private final KakaoClient kakaoClient;
    private final AuthTokenProvider authTokenProvider;
    private final MemberRepository memberRepository;
    private final MemberQuerydslRepository memberQuerydslRepository;

    @Transactional
    public AuthResponse login(AuthRequest authRequest){
        Member kakaoMember = kakaoClient.getUserData(authRequest.getAccessToken());
        String socialId = kakaoMember.getSocialId();
        Member member = memberQuerydslRepository.findBySocialId(socialId);

        AuthToken authToken = authTokenProvider.createUserAppToken(socialId);
        if(member==null){
            memberRepository.save(kakaoMember);
            return AuthResponse.builder()
                    .appToken(authToken.getToken())
                    .isNewMember(Boolean.TRUE)
                    .build();
        }

        return AuthResponse.builder()
                .appToken(authToken.getToken())
                .isNewMember(Boolean.FALSE)
                .build();
    }
}
