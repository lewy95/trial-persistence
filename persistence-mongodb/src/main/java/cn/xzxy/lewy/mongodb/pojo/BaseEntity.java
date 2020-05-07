package cn.xzxy.lewy.mongodb.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author lewy
 */
@Data
@MappedSuperclass
public class BaseEntity {

    @Column(name= "create_date") //创建时间
    private Date createDate;

    @Column(name= "update_date")//修改时间
    private Date updateDate;
}
