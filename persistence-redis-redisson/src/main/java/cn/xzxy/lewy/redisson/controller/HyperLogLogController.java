package cn.xzxy.lewy.redisson.controller;

import cn.xzxy.lewy.redisson.common.model.JsonResponseEntity;
import cn.xzxy.lewy.redisson.dto.UserSignInReq;
import cn.xzxy.lewy.redisson.dto.ViewBlogReq;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RHyperLogLog;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * hyperLogLog case
 *
 * @author lewy95
 */
@RestController
@RequestMapping("/rest/hyperloglog")
@Slf4j
public class HyperLogLogController {

    @Resource
    RedissonClient redissonClient;

    @PostMapping("/view")
    public JsonResponseEntity signIn(@RequestBody @Valid ViewBlogReq viewBlogReq) {

        // query the blog basic info form mysql first

        // add the view number of blog
        String cacheKey = "BLOG_" + viewBlogReq.getBlogId();

        RHyperLogLog<String> rHyperLogLog = redissonClient.getHyperLogLog(cacheKey);

        try {
            rHyperLogLog.add(viewBlogReq.getUserIpAddr());
            log.info("user from {} viewed the blog:{}", viewBlogReq.getUserIpAddr(), viewBlogReq.getBlogId());

            long count = rHyperLogLog.count();
            log.info("the view number blog:{} is {} now.", viewBlogReq.getBlogId(), count);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("view counts error: {} ", e.getMessage());
            return JsonResponseEntity.buildBusinessError("view counts error");
        }

        return JsonResponseEntity.buildOK("view ok");
    }

}
