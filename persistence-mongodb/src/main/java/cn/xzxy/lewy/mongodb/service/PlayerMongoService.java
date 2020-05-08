package cn.xzxy.lewy.mongodb.service;

import cn.xzxy.lewy.mongodb.pojo.PlayerMongo;

import java.util.List;

/**
 * @author lewy
 */
public interface PlayerMongoService {

    PlayerMongo addPlayer(PlayerMongo player);

    void deletePlayer(String id);

    PlayerMongo updatePlayer(PlayerMongo player);

    PlayerMongo findPlayerById(String id);

    List<PlayerMongo> findAllPlayer();
}
