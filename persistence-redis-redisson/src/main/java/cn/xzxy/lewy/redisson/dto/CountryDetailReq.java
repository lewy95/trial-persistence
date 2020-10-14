package cn.xzxy.lewy.redisson.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CountryDetailReq {

    @NotBlank(message = "城市ID不能为空")
    @Size(max = 40,message = "城市ID最大长度为5")
    private int countryId;
}
