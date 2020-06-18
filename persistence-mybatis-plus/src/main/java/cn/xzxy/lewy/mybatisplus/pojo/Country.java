package cn.xzxy.lewy.mybatisplus.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "country_id", type = IdType.AUTO)
    private Integer countryId;

    private String country;

    private LocalDateTime lastUpdate;


}
