package cn.xzxy.lewy.mongodb.service;

import cn.xzxy.lewy.mongodb.pojo.Player;

import java.util.List;

/**
 * @author lewy
 */
public interface PlayerService {

    Player save(Player player);

    Player findById(Integer id);

    Player findByIdAnnotation(Integer id);

    void delete(Player player);

    List<Player> findAll();
}
