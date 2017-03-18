package com.highgag.web.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.highgag.web.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.util.Assert;

import java.io.IOException;

@Slf4j
public class TokenServiceTest {

    private TokenService<Session> tokenService;

    @Before
    public void init() {
        AppConfig appConfig = new AppConfig();
        appConfig.setSecretKey("bS3q2SgPA3Xkn9DCt9j/wFyF09xm+yOjTdjz9DoImeM=");

        tokenService = Mockito.spy(new TokenService<>(appConfig));
        tokenService.setObjectMapper(new ObjectMapper());
    }

    @Test
    public void issue() throws IOException {
        Session session = new Session();
        session.setId(1L);
        session.setAccount("account");
        Token token = tokenService.issue(session);

        log.info("token.getValue()={}", token.getValue());
        Assert.notNull(token, "token=null");
    }

    @Test
    public void decrypt() throws IOException {
        Session session = tokenService.decrypt("aGDA+Ud5rvyqNhmQXZJ8/gNlHFlYd7VK7ZAN60/e5TBMFkLvqT1z5EncSQTweSvvw6OgwqMp1sOzWtUC+uaAZ4ojMwE=", Session.class);

        Assert.isTrue(session.getId() == 1L, "not 1L");
        Assert.isTrue(session.getAccount().equals("account"), "not 'account'");
    }

}