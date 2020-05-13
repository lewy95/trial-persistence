package cn.xzxy.lewy.redis.service;

import cn.xzxy.lewy.redis.pojo.Player;

import java.util.List;

/**
 * @author lewy
 */
public interface PlayerService {

    Player save(Player player);

    Player update(Player player);

    Player findById(Integer id);

    Player findByIdAnnotation(Integer id);

    void delete(Player player);

    List<Player> findAll();
}
