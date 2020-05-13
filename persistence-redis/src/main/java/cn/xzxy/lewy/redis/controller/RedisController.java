package cn.xzxy.lewy.redis.controller;

import cn.xzxy.lewy.redis.common.controller.BaseController;
import cn.xzxy.lewy.redis.common.util.RedisConstants;
import cn.xzxy.lewy.redis.common.util.RedisUtil;
import cn.xzxy.lewy.redis.common.util.StateParameter;
import cn.xzxy.lewy.redis.pojo.Player;
import cn.xzxy.lewy.redis.service.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 测试类：基于对RedisTemplate封装的RedisUtil操作
 * @author lewy
 */
@Controller
@RequestMapping(value = "/redis")
public class RedisController extends BaseController {

    @Resource
    RedisUtil redisUtil;

    @Resource
    PlayerService playerService;

    /**
     * 测试redis存储&读取
     */
    @RequestMapping(value = "/test")
    @ResponseBody
    public ModelMap test() {
        try {
            redisUtil.set("redisTemplate", "这是一条测试数据", RedisConstants.DATEBASE2);
            String value = redisUtil.get("redisTemplate", RedisConstants.DATEBASE2).toString();
            logger.info("redisValue=" + value);
            logger.info("读取redis成功");
            return getModelMap(StateParameter.SUCCESS, value, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return getModelMap(StateParameter.FAULT, null, "操作失败");
        }
    }

    /**
     * 测试redis存储&读取，保存对象
     */
    @GetMapping(value = "/setPlayer")
    @ResponseBody
    public ModelMap setPlayer() {
        try {
            Player player = new Player();
            player.setId(1);
            player.setName("lewy");
            player.setAge(28);
            player.setNumber(9);
            redisUtil.set("player:lewy", player, RedisConstants.DATEBASE1);
            redisUtil.expire("player:lewy", 300, RedisConstants.DATEBASE1);
            // 获取过期时间
            System.out.println(redisUtil.getExpire("player:lewy"));
            // 读取一个对象
            Player res = (Player) redisUtil.get("player:lewy", RedisConstants.DATEBASE1);
            logger.info("res=" + res.toString());
            logger.info("读取redis成功");
            return getModelMap(StateParameter.SUCCESS, res, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return getModelMap(StateParameter.FAULT, null, "操作失败");
        }
    }

    /**
     * 测试redis存储&读取，从数据库中读取对象并保存对象
     */
    @GetMapping(value = "/setPlayer/{id}")
    @ResponseBody
    public ModelMap setPlayerFromDb(@PathVariable Integer id) {
        try {
            Player player = playerService.findById(id);
            String key = "player-" + id;
            redisUtil.set(key, player, RedisConstants.DATEBASE2);
            redisUtil.expire(key, 300, RedisConstants.DATEBASE2);
            // 读取一个对象
            Player res = (Player) redisUtil.get(key, RedisConstants.DATEBASE2);
            logger.info("res=" + res.toString());
            logger.info("读取redis成功");
            return getModelMap(StateParameter.SUCCESS, res, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return getModelMap(StateParameter.FAULT, null, "操作失败");
        }
    }

}
