package cn.xzxy.lewy.redisson.controller;

import cn.xzxy.lewy.redisson.common.model.JsonResponseEntity;
import cn.xzxy.lewy.redisson.dto.CountryDetailReq;
import cn.xzxy.lewy.redisson.dto.UserSignInReq;
import cn.xzxy.lewy.redisson.pojo.Country;
import cn.xzxy.lewy.redisson.service.CountryService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.concurrent.CountDownLatch;

/**
 * rateLimiter case
 *
 * @author lewy95
 */
@RestController
@RequestMapping("/rest/rateLimiter")
@Slf4j
public class RateLimiterController {

    @Resource
    RedissonClient redissonClient;

    @Resource
    private CountryService countryService;

    @PostMapping("/getCountry")
    public JsonResponseEntity signIn(@RequestBody @Valid CountryDetailReq countryDetailReq) {
        // generate rateLimiter
        RRateLimiter rateLimiter = redissonClient.getRateLimiter("rl9");
        // set rate : 1 token per 1 second
        rateLimiter.trySetRate(RateType.OVERALL, 1, 1, RateIntervalUnit.SECONDS);

        int allThreadNum = 10; // set thread nums
        // if without rateLimiter, 10 threads get executed instantly
        // if with rateLimiter, only 1 thread get executed per second
        CountDownLatch latch = new CountDownLatch(allThreadNum);

        long startTime = 0;
        try {
            startTime = System.currentTimeMillis();
            for (int i = 0; i < allThreadNum; i++) {
                new Thread(() -> {
                    rateLimiter.acquire(1);
                    Country country = countryService.getCountry(countryDetailReq.getCountryId());
                    log.info("get Thread: {}, Country: {}", Thread.currentThread().getName(), country.getCountry());
                    latch.countDown();
                }).start();
            }
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("Elapsed {}", (System.currentTimeMillis() - startTime));

        return JsonResponseEntity.buildOK("test rateLimiter ok");
    }
}
