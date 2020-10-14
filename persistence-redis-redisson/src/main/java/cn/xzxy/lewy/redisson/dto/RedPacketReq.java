package cn.xzxy.lewy.redisson.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author lewy95
 */
@Data
public class RedPacketReq {

    @NotBlank(message = "redPacket id can not be empty")
    private String redPacketId;
    @NotNull(message = "redPacket id can not be empty")
    @Min(value = 0, message = "redPacket number can not be less than zero")
    private Integer redPacketNumber;
    private Date createTime;
}
