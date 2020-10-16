package cn.xzxy.lewy.redisson.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author lewy95
 */
@Data
public class ViewBlogReq {

    @NotBlank(message = "blogId can not be empty")
    private String blogId;
    @NotBlank(message = "userIpAddr can not be empty")
    private String userIpAddr;
}
