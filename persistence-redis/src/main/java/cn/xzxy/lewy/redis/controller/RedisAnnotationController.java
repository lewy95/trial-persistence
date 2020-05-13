package cn.xzxy.lewy.redis.controller;

import cn.xzxy.lewy.redis.common.controller.BaseController;
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
 * 测试类：Redis整合SpringCache注解 @Cacheable / @CachePut / @CacheEvict
 *        注解在service中使用
 * @author lewy
 */
@Controller
@RequestMapping(value = "/redisAnnotation")
public class RedisAnnotationController extends BaseController {

    @Resource
    RedisUtil redisUtil;

    @Resource
    PlayerService playerService;

    @GetMapping(value = "/setPlayer/{id}")
    @ResponseBody
    public ModelMap setPlayerFromDb(@PathVariable Integer id) {
        try {
            Player player = playerService.findByIdAnnotation(id);
            return getModelMap(StateParameter.SUCCESS, player, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return getModelMap(StateParameter.FAULT, null, "操作失败");
        }
    }

    // 测试读取一个对象
    @GetMapping(value = "/getPlayer")
    @ResponseBody
    public ModelMap getPlayerFromRedis() {
        try {
            Player res = (Player) redisUtil.get("player_info::player-id-1", 0);
            logger.info("res=" + res.toString());
            logger.info("读取redis成功");
            return getModelMap(StateParameter.SUCCESS, res, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return getModelMap(StateParameter.FAULT, null, "操作失败");
        }
    }
}
