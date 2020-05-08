package cn.xzxy.lewy.mongodb.controller;

import cn.xzxy.lewy.mongodb.pojo.PlayerMongo;
import cn.xzxy.lewy.mongodb.service.PlayerMongoService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lewy
 */
@RestController
@RequestMapping("/rest/playerMongo")
public class PlayerController {

    @Resource
    PlayerMongoService playerMongoService;

    @RequestMapping("addPlayerMongo")
    public PlayerMongo add(@RequestBody PlayerMongo player) {
        return playerMongoService.addPlayer(player);
    }

}
