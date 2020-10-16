package cn.xzxy.lewy.redisson.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author lewy95
 */
@Data
public class UserSignInReq {

    @NotBlank(message = "userId can not be empty")
    private String userId;
    @NotBlank(message = "today can not be empty")
    private String today;

}
