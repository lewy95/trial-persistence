package cn.xzxy.lewy.mongodb.service.impl;

import cn.xzxy.lewy.mongodb.dao.PlayerDao;
import cn.xzxy.lewy.mongodb.pojo.Player;
import cn.xzxy.lewy.mongodb.service.PlayerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lewy
 */
@Service
public class PlayerServiceImpl implements PlayerService {

    @Resource
    PlayerDao playerDao;

    @Override
    public Player save(Player player) {
        return playerDao.save(player);
    }

    /**
     * 非注解形式
     */
    @Override
    public Player findById(Integer id) {
        return playerDao.findById(id);
    }

    /**
     * 基于注解形式
     */
    @Override
    //@Cacheable(value = "user_details", key = "#id", unless="#result == null")
    //@Cacheable
    public Player findByIdAnnotation(Integer id) {
        return playerDao.findById(id);
    }

    @Override
    public void delete(Player player) {
        playerDao.delete(player);
    }

    @Override
    public List<Player> findAll() {
        return playerDao.findAll();
    }
}
