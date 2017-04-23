package com.highgag.web.auth;

import com.highgag.web.config.AppConfig;
import com.lambdaworks.crypto.SCryptUtil;
import org.springframework.stereotype.Component;

/**
 * Created by leejuhyun on 2017. 3. 21..
 */
@Component
public class ScryptService {

    private final int N;

    private final int R;

    private final int P;

    public ScryptService(AppConfig appConfig) {
        this.N = appConfig.getScrypt().getN();
        this.R = appConfig.getScrypt().getR();
        this.P = appConfig.getScrypt().getP();
    }

    public String encrypt(String password) {
        return SCryptUtil.scrypt(password, N, R, P);
    }

    public boolean check(String password, String hashed) {
        return SCryptUtil.check(password, hashed);
    }
}
