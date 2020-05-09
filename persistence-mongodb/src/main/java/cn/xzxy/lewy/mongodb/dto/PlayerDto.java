package cn.xzxy.lewy.mongodb.dto;

import lombok.Data;

/**
 * @author lewy
 */
@Data
public class PlayerDto {

    String playerId;
    String playerName;
    Integer playerAge;
    Integer playerNumber;
    String createDate;
    String updateDate;
}
