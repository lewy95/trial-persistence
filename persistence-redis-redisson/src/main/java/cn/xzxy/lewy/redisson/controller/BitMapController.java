package cn.xzxy.lewy.redisson.controller;

import cn.hutool.core.date.DateUtil;
import cn.xzxy.lewy.redisson.common.model.JsonResponseEntity;
import cn.xzxy.lewy.redisson.dto.UserSignInReq;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBitSet;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * bitmap case
 *
 * @author lewy95
 */
@RestController
@RequestMapping("/rest/bitmap")
@Slf4j
public class BitMapController {

    @Resource
    RedissonClient redissonClient;

    @PostMapping("/signIn")
    public JsonResponseEntity signIn(@RequestBody @Valid UserSignInReq userSignInReq) {

        // sign in start day (fixed)
        String startDate = "2020-10-01";

        long startTime = DateUtil.parse(startDate).getTime();
        long todayTime = DateUtil.parse(userSignInReq.getToday()).getTime();
        long offset = (long) Math.floor((todayTime - startTime) / (60 * 60 * 24 * 1000));

        String cacheKey = "SIGN_" + userSignInReq.getUserId();

        RBitSet bitSet = redissonClient.getBitSet(cacheKey);
        try {
            bitSet.set(offset);
            long signDays = bitSet.cardinality();
            log.info("today is the {} th day, you totally signed in {} days.", offset, signDays);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("sign in error: {} ", e.getMessage());
            return JsonResponseEntity.buildBusinessError("sign in error");
        }

        return JsonResponseEntity.buildOK("sign in ok");
    }
}
