package com.org.umsc.auth.service;

import com.org.umsc.auth.dto.AuthResponse;
import com.org.umsc.auth.jwt.AuthToken;
import com.org.umsc.auth.jwt.AuthTokenProvider;
import com.org.umsc.members.entity.Member;
import com.org.umsc.members.repository.MemberQuerydslRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthTokenProvider authTokenProvider;
    private final MemberQuerydslRepository memberQuerydslRepository;

    public AuthResponse updateToken(AuthToken authToken){
        Claims claims = authToken.getTokenClaims();
        if(claims==null){
            return null;
        }

        String socialId = claims.getSubject();
        AuthToken newToken = authTokenProvider.createUserAppToken(socialId);

        return AuthResponse.builder()
                .appToken(newToken.getToken())
                .build();
    }

    public Long getMemberId(String token){
        AuthToken authToken = authTokenProvider.convertAuthToken(token);

        Claims claims = authToken.getTokenClaims();
        if(claims==null){
            return null;
        }

        try {
            Member member = memberQuerydslRepository.findBySocialId(claims.getSubject());
            return member.getId();
        } catch (NullPointerException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자가 존재하지 않습니다.");
        }
    }
}
