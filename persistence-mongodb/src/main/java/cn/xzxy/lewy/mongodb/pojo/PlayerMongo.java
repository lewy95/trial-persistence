package cn.xzxy.lewy.mongodb.pojo;


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
@Table(name = "player_mongo")
public class PlayerMongo extends BaseEntity implements Serializable {

    @Id
    @Column(name = "player_id")
    private String playerId;

    @Column(name = "player_name")
    private String playerName;

    @Column(name = "player_age")
    private int playerAge;

    @Column(name = "player_number")
    private int playerNumber;
}
