package cn.xzxy.lewy.mybatis.controller;

import cn.xzxy.lewy.mybatis.common.model.JsonResponseEntity;
import cn.xzxy.lewy.mybatis.dto.CountryDetailReq;
import cn.xzxy.lewy.mybatis.pojo.Country;
import cn.xzxy.lewy.mybatis.service.CountryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "城市管理相关接口")
@RestController
@RequestMapping("/rest/country")
public class CountryController {

    @Resource
    private CountryService countryService;

    @PostMapping("/detail1")
    @ApiOperation(value = "根据ID查询城市", notes = "根据ID查询城市")
    public JsonResponseEntity<Country> getCountry(@RequestBody CountryDetailReq countryDetailReq) {
        return JsonResponseEntity.buildOK(countryService.getCountry(countryDetailReq.getCountryId()));
    }

    @PostMapping("/detail2")
    @ApiOperation(value = "根据ID查询城市", notes = "根据ID查询城市")
    public JsonResponseEntity<Country> getCountry2(@RequestBody CountryDetailReq countryDetailReq) {
        return JsonResponseEntity.buildOK(countryService.getCountry(countryDetailReq.getCountryId()));
    }
}