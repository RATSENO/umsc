package com.org.umsc.auth.client;

import com.org.umsc.members.entity.Member;

public interface Client {
    Member getUserData(String accessToken);
}
