package cn.xzxy.lewy.redisson.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CountryDetailReq {

    @NotNull(message = "城市ID不能为空")
    private int countryId;
}
