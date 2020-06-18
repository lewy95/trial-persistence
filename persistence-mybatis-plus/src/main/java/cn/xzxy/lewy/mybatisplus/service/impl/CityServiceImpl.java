package cn.xzxy.lewy.mybatisplus.service.impl;

import cn.xzxy.lewy.mybatisplus.pojo.City;
import cn.xzxy.lewy.mybatisplus.dao.CityMapper;
import cn.xzxy.lewy.mybatisplus.service.CityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class CityServiceImpl extends ServiceImpl<CityMapper, City> implements CityService {
}
