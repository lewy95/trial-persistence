package cn.xzxy.lewy.redisson.service.impl;

import cn.xzxy.lewy.redisson.mapper.CountryMapper;
import cn.xzxy.lewy.redisson.pojo.Country;
import cn.xzxy.lewy.redisson.service.CountryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("countryService")
public class CountryServiceImpl implements CountryService {

    @Resource
    CountryMapper countryMapper;

    @Override
    public Country getCountry(int id) {
        return countryMapper.selectByPrimaryKey(id);
    }
}
