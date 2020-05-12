package cn.xzxy.lewy.mongodb.dto;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author lewy
 */
@Data
@Document(collection = "player") // BSON 指定对象所在Mongodb的集合
public class PlayerDto {

    String playerId;
    String playerName;
    Integer playerAge;
    Integer playerNumber;
    String createDate;
    String updateDate;
}
