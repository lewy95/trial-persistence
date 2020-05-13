package cn.xzxy.lewy.redis.service.impl;

import cn.xzxy.lewy.redis.dao.PlayerDao;
import cn.xzxy.lewy.redis.pojo.Player;
import cn.xzxy.lewy.redis.service.PlayerService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lewy
 */
@Service
@CacheConfig(cacheNames = "player")
public class PlayerServiceImpl implements PlayerService {

    @Resource
    PlayerDao playerDao;

    /**
     * 基于注解形式
     */
    @Override
    @Cacheable(value = "player_info", key = "'player-id-' + #id", unless="#result == null")
    //@Cacheable(value = "player_info", key = "'player-id-' + #p0", unless="#result == null")
    //上述两种key的效果相同：player_info::player-id-1，因为这里的SpEL是基于方法参数而言的
    //@CachePut(value = "player_info", key = "'player-id-' + #p0", unless="#result == null")
    //@CacheEvict(value = "player_info", key = "'player-id-' + #p0", allEntries = true)
    //@CacheEvict(value = "player_info", allEntries = true)
    //@CacheEvict(beforeInvocation = true)
    public Player findByIdAnnotation(Integer id) {
        System.out.println("是否执行");
        // 标注了@Cacheable之后：
        // 第一次，该查询方法会执行并将查询结果缓存添加到redis中；
        // 之后的只要Key没有过期，都会直接走缓存，不会再去执行这个查询方法

        // 标注了@CachePut之后：
        // 不管Key是否存在于redis中，每次都会执行这个查询方法，并将查询结果进行重新缓存

        // 标注了@CacheEvict之后：
        // 每次都会执行这个查询方法，并且调用结束时会将缓存清除
        // allEntries属性：
        // 如果配置了allEntries = true，则所有缓存(依据是value属性，value下的所有缓存)都会被清除
        // 即若@CacheEvict(value = "player_info",allEntries = true)会删除player_info下的所有缓存
        // 如果只有@CacheEvict(allEntries = true)则只删除自己，作用和@CacheEvict一致
        // 也就是说allEntries属性必须和value属性一起配置才起作用
        // beforeInvocation属性：
        // 是否在方法执行前就清空
        return playerDao.findById(id);
    }

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

    @Override
    public Player update(Player player) {
        return playerDao.save(player);
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
