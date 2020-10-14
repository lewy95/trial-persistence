package cn.xzxy.lewy.redisson.controller;

import cn.xzxy.lewy.redisson.common.model.JsonResponseEntity;
import cn.xzxy.lewy.redisson.dto.CountryDetailReq;
import cn.xzxy.lewy.redisson.pojo.Country;
import cn.xzxy.lewy.redisson.service.CountryService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author lewy95
 */
@RestController
@RequestMapping("/rest/country")
@Slf4j
public class CountryController {

    @Resource
    private CountryService countryService;

    @Autowired
    RedissonClient redissonClient;

    @PostMapping("/detail")
    public JsonResponseEntity getCountry(@RequestBody CountryDetailReq countryDetailReq) {

        String jsonStr = null;
        RBucket<String> rCache = redissonClient.getBucket("LEWY_COUNTRY_" + countryDetailReq.getCountryId());
        if (rCache.isExists()) {
            log.info("============读取服务详情缓存成功，国家编号{}============", countryDetailReq.getCountryId());
            jsonStr = rCache.get();
        } else {
            // 不存在则查询数据库，并且放入redis
            log.info("============缓存中没有该服务详情数据，进行数据库查询============");
            Country country = countryService.getCountry(countryDetailReq.getCountryId());
            jsonStr = JSONObject.toJSONString(country);
            // 写入缓存，过期时间为1h
            rCache.set(jsonStr, 1, TimeUnit.HOURS);
            log.info("============写入服务详情缓存成功，国家编号{}============", countryDetailReq.getCountryId());
        }
        return JsonResponseEntity.buildOK(JSONObject.parse(jsonStr));
    }
}
