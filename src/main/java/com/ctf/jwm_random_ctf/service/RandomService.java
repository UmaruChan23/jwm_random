package com.ctf.jwm_random_ctf.service;

import com.ctf.jwm_random_ctf.dto.MyRandom;
import com.ctf.jwm_random_ctf.dto.Step;
import com.google.common.cache.LoadingCache;
import lombok.Getter;
import org.springframework.stereotype.Service;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

import javax.servlet.http.Cookie;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
@Getter
public class RandomService {

    //private final Step rnd = new Step(new Random());
    private final CacheLoader<String, Step> loader = new CacheLoader<>() {
        @Override
        public Step load(String key) {
            return new Step(new MyRandom());
        }
    };

    private LoadingCache<String, Step> randoms = CacheBuilder.newBuilder()
            .expireAfterAccess(15, TimeUnit.MINUTES)
            .build(loader);

    public long generateNewPassword(Cookie cookie) throws ExecutionException {

        var random = randoms.get(cookie.getValue());

        return random.getRandom().nextNumber();
    }

}
