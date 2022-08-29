package com.org.umsc.auth.client;

import com.org.umsc.auth.exception.TokenValidFailedException;
import com.org.umsc.auth.dto.KakaoUserResponse;
import com.org.umsc.auth.enums.RoleType;
import com.org.umsc.members.entity.Member;
import com.org.umsc.members.enums.MemberAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class KakaoClient implements Client{
    private final WebClient webClient;

    @Override
    public Member getUserData(String accessToken) {
        KakaoUserResponse kakaoUserResponse = webClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(accessToken))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new TokenValidFailedException("Social Access Token is unauthorized")))
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new TokenValidFailedException("Internal Server Error")))
                .bodyToMono(KakaoUserResponse.class)
                .block();

        return Member.builder()
                .socialId(String.valueOf(kakaoUserResponse.getId()))
                .name(kakaoUserResponse.getProperties().getNickName())
                .email(kakaoUserResponse.getKakaoAccount().getEmail())
                .gender(kakaoUserResponse.getKakaoAccount().getGender())
                .memberAuthProvider(MemberAuthProvider.KAKAO)
                .roleType(RoleType.USER)
                .profileImagePath(kakaoUserResponse.getProperties().getProfileImage()!=null?kakaoUserResponse.getProperties().getProfileImage():"")
                .build();
    }
}
