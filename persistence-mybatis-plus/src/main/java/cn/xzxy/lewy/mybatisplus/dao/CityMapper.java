package cn.xzxy.lewy.mybatisplus.dao;

import cn.xzxy.lewy.mybatisplus.pojo.City;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface CityMapper extends BaseMapper<City> {

    /**
     * 自定义生成的mapper会继承BaseMapper<T>接口
     * Mapper 继承该接口后，无需编写 mapper.xml 文件，即可获得CRUD功能
     */
}
