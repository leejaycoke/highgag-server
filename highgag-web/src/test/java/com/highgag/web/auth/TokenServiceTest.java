package com.highgag.web.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.highgag.core.domain.Role;
import com.highgag.web.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

@Slf4j
public class TokenServiceTest {

    private TokenService<Session> tokenService;

    private final static String PRE_GENERATED_TOKEN = "Bearer ERBcYnrGs5KG3C1iUdz9yxOFYY7V7wjabvDzkN0xu/6ThuAcd+6mSHWHhf1" +
            "bMVrQHYLvjWYkHgsZoq0dcBrMogdZoXKZ1WVNeKLGD7ZkVzqvsMIX";

    @Before
    public void init() {
        AppConfig appConfig = new AppConfig();
        appConfig.setSecretKey("bS3q2SgPA3Xkn9DCt9j/wFyF09xm+yOjTdjz9DoImeM=");

        tokenService = Mockito.spy(new TokenService<>(appConfig, new ObjectMapper()));
    }

    @Test
    public void 토큰_인코딩_성공() throws IOException {
        Session session = new Session(1L, "account", Role.MEMBER);
        Token token = tokenService.issue(session);

        Assert.assertNotNull(token);
        Assert.assertNotNull(token.getValue());
        Assert.assertTrue(token.getValue().startsWith("Bearer "));
        log.debug("token.value={}", token.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void 토큰_디코딩_실패_빈값() throws IOException {
        tokenService.decrypt("", Session.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void 토큰_디코딩_실패_옳바르지않은_Bearer() throws IOException {
        tokenService.decrypt("Bearer", Session.class);
    }

    @Test(expected = RuntimeException.class)
    public void 토큰_디코딩_실패_옳바르지않은_값() throws IOException {
        tokenService.decrypt("Bearer ERBcYnrGs5KG3C1iUdz9yxOFYY7V7wjabvDzkN0xu/6ThuAcd+6mSHWHhf1", Session.class);
    }

    @Test
    public void 토큰_디코딩_성공() throws IOException {
        Session session = tokenService.decrypt(PRE_GENERATED_TOKEN, Session.class);

        Assert.assertNotNull(session);
        Assert.assertNotNull(session.getId());
        Assert.assertNotNull(session.getAccount());
        Assert.assertNotNull(session.getRole());
        Assert.assertEquals((long) session.getId(), 1L);
        Assert.assertEquals(session.getAccount(), "account");
    }

}