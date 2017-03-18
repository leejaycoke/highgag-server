package com.highgag.web.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.highgag.web.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.abstractj.kalium.crypto.Random;
import org.abstractj.kalium.crypto.SecretBox;
import org.abstractj.kalium.crypto.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Base64;

@Slf4j
@Component
public class TokenService<T> {

    private final static Charset utf8 = Charset.forName("utf-8");

    private Random random = new Random();

    private SecretBox secretBox;

    private ObjectMapper objectMapper;

    public TokenService(AppConfig config) {
        byte[] secretKey = Base64.getDecoder().decode(config.getSecretKey());
        secretBox = new SecretBox(secretKey);
    }

    public Token issue(T object) throws IOException {
        byte[] nonce = random.randomBytes(24); // nonce
        byte[] message = objectMapper.writeValueAsBytes(object); // message

        byte[] cipher = secretBox.encrypt(nonce, message); // enc(message)
        String value = Base64.getEncoder().encodeToString(Util.merge(nonce, cipher)); // b64(nonce + enc(message))

        Token token = new Token(value, null);
        return token;
    }

    public T decrypt(String encrypted, Class<T> clazz) throws IOException {
        byte[] value = Base64.getDecoder().decode(encrypted);
        byte[] nonce = Arrays.copyOfRange(value, 0, 24);
        byte[] cipher = Arrays.copyOfRange(value, 24, value.length);

        byte[] message = secretBox.decrypt(nonce, cipher);

        T token = objectMapper.readValue(message, clazz);
        return token;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

}
