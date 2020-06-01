package cn.xzxy.lewy.mybatisplus.service;

import cn.xzxy.lewy.mybatisplus.pojo.Country;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface CountryService extends IService<Country> {

    Country getCountry(int id);

    IPage<Country> selectListByPage();

    Integer selectCount();

    List<Map<String, Object>> selectMaps();
}
