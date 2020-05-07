package cn.xzxy.lewy.mybatis.service.impl;

import cn.xzxy.lewy.mybatis.mapper.CountryMapper;
import cn.xzxy.lewy.mybatis.pojo.Country;
import cn.xzxy.lewy.mybatis.service.CountryService;
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
