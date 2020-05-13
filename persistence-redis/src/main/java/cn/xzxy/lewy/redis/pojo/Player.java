package cn.xzxy.lewy.redis.pojo;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author lewy
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "player")
public class Player extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "number")
    private int number;
}
