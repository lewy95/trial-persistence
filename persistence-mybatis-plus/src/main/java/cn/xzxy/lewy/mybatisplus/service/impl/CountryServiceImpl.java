package cn.xzxy.lewy.mybatisplus.service.impl;

import cn.xzxy.lewy.mybatisplus.pojo.Country;
import cn.xzxy.lewy.mybatisplus.dao.CountryMapper;
import cn.xzxy.lewy.mybatisplus.service.CountryService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class CountryServiceImpl extends ServiceImpl<CountryMapper, Country> implements CountryService {

    @Resource
    CountryMapper countryMapper;

    @Override
    public Country getCountry(int id) {
        return countryMapper.selectById(id);
    }

    @Override
    public IPage<Country> selectListByPage() {
        Page<Country> page = new Page(1,5);
        QueryWrapper<Country> qw = new QueryWrapper<>();
        // 查找ID在21-30之间的数据
        qw.between("country_id", 21, 30);
        return countryMapper.selectPage(page, qw);
    }

    @Override
    public Integer selectCount() {
        return countryMapper.selectCount(new QueryWrapper<>());
    }

    @Override
    public List<Map<String, Object>> selectMaps() {
        QueryWrapper<Country> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("country_id", 21, 30);
        return countryMapper.selectMaps(queryWrapper);
    }
}
