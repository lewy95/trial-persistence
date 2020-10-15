package cn.xzxy.lewy.redisson.controller;

import cn.xzxy.lewy.redisson.common.model.JsonResponseEntity;
import cn.xzxy.lewy.redisson.dto.RedPacketReq;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author lewy95
 */
@RestController
@RequestMapping("/rest/redPacket")
@Slf4j
public class RedPacketController {

    @Resource
    RedissonClient redissonClient;

    @PostMapping("/setNumber")
    public JsonResponseEntity setRedPacketNumber(@RequestBody @Valid RedPacketReq redPacketReq) {

        // 逻辑：创建一个红包后，需要设置红包的数量（为了并发控制，需要使用分布式锁）
        try {
            RBucket<Integer> rpCache = redissonClient.getBucket("RP_" + redPacketReq.getRedPacketId());
            if (!rpCache.isExists()) {
                rpCache.set(redPacketReq.getRedPacketNumber(), 12, TimeUnit.HOURS);
            }
        } catch (Exception e) {
            log.error("Redis处理异常，异常原因:{}", e.getMessage());
            return JsonResponseEntity.buildBusinessError("Redis处理异常");
        }

        return JsonResponseEntity.buildOK(redPacketReq);
    }

    @PostMapping("/getNumber")
    public JsonResponseEntity getRedPacketNumber(@RequestBody @Valid RedPacketReq redPacketReq) {

        String redPacketKey = "RP_" + redPacketReq.getRedPacketId();
        Integer redPacketNumber;
        RLock redissonLock = redissonClient.getLock("redPacket");
        log.info("{} 进入 {} 分布式锁", Thread.currentThread().getName(), redPacketKey);
        try {
            // 30s过期时间，如果程序正常执行完就会释放锁不用等30s，出现异常时需要等到30s
            redissonLock.lock(30, TimeUnit.SECONDS);
            RBucket<Integer> rpCache = redissonClient.getBucket(redPacketKey);

            











            if (rpCache.isExists()) {
                redPacketNumber = rpCache.get();
                if (redPacketNumber > 0) {
                    rpCache.set(--redPacketNumber);
                    log.info("抢到红包，剩余数量 {}", redPacketNumber);
                } else {
                    log.warn("红包已抢完, 剩余数量{}", redPacketNumber);
                }
            } else {
                // 红包已过期
                redPacketNumber = -1;
                log.warn("红包{}已过期", redPacketKey);
            }
        } finally {
            log.info("{} 释放 {} 分布式锁", Thread.currentThread().getName(), redPacketKey);
            redissonLock.unlock();
        }
        return JsonResponseEntity.buildOK(redPacketNumber);
    }
}
