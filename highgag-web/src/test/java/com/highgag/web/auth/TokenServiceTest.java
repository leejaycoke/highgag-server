package com.highgag.web.auth;

import com.highgag.core.domain.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.highgag.web.Constatns.VALID_ACCESS_TOKEN;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenServiceTest {

    @Autowired
    private TokenService tokenService;

    @Test
    public void 토큰_인코딩_성공() throws IOException {
        Session session = new Session(1L, "account", Role.MEMBER);
        Token token = tokenService.issue(session);

        Assert.assertNotNull(token);
        Assert.assertNotNull(token.getValue());
        Assert.assertNotNull(token.getExpiresAt());
        Assert.assertTrue(token.getValue().startsWith("Bearer "));
        Assert.assertTrue(token.getExpiresAt().isAfter(LocalDateTime.now()));
        log.debug("token={}", token.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void 토큰_디코딩_실패_빈값() throws IOException {
        tokenService.decrypt("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void 토큰_디코딩_실패_옳바르지않은_Bearer() throws IOException {
        tokenService.decrypt("Bearer");
    }

    @Test(expected = RuntimeException.class)
    public void 토큰_디코딩_실패_옳바르지않은_값() throws IOException {
        tokenService.decrypt("Bearer ERBcYnrGs5KG3C1iUdz9yxOFYY7V7wjabvDzkN0xu/6ThuAcd+6mSHWHhf1");
    }

    @Test
    public void 토큰_디코딩_성공() throws IOException {
        Session session = tokenService.decrypt(VALID_ACCESS_TOKEN);

        Assert.assertNotNull(session);
        Assert.assertNotNull(session.getId());
        Assert.assertNotNull(session.getAccount());
        Assert.assertNotNull(session.getRole());
        Assert.assertNotNull(session.getExpiresAt());
        Assert.assertEquals((long) session.getId(), 1L);
        Assert.assertEquals(session.getAccount(), "account");
    }

}