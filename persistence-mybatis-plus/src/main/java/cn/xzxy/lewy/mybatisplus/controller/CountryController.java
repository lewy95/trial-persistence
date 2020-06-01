package cn.xzxy.lewy.mybatisplus.controller;


import cn.xzxy.lewy.mybatisplus.pojo.Country;
import cn.xzxy.lewy.mybatisplus.service.CountryService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/country")
public class CountryController {

    @Resource
    private CountryService countryService;

    @GetMapping("detail/{id}")
    public String getCountry(@PathVariable int id) {
        return countryService.getCountry(id).toString();
    }

    /**
     * 分页查询
     */
    @PostMapping("queryListByPage")
    public List<Country> queryListByPage() {

        IPage<Country> countries  = countryService.selectListByPage();
        return countries.getRecords();
    }

    /**
     * 查询总数
     */
    @PostMapping("queryCount")
    public Integer queryCount() {
        return countryService.selectCount();
    }

    /**
     * 查询Maps
     */
    @PostMapping("queryMap")
    public List<Map<String, Object>> queryMaps() {
        return countryService.selectMaps();
    }


}
