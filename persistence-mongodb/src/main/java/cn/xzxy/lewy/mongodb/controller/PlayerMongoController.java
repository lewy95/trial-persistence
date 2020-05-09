package cn.xzxy.lewy.mongodb.controller;

import cn.xzxy.lewy.mongodb.dto.PlayerDto;
import cn.xzxy.lewy.mongodb.service.PlayerMongoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lewy
 */
@RestController
@RequestMapping("/rest/playerMongo")
@Slf4j
public class PlayerMongoController {

    @Resource
    PlayerMongoService playerMongoService;

    @Resource
    MongoTemplate mongoTemplate;

    @PostMapping(value = "convert")
    public void mongo2Mysql() {

        Query query = new Query();
        Criteria criteria = Criteria.where("player_name").is("lewy");
        //criteria.and("age").in(31);
        query.addCriteria(criteria);
        PlayerDto player = mongoTemplate.findOne(query, PlayerDto.class, "player");
        if (player != null) {
            log.info("读取数据成功{}", player.toString());
        } else {
            log.info("读取数据失败");
        }
    }

    @PostMapping(value = "queryList")
    public List<PlayerDto> playerMongoList() {

        return mongoTemplate.findAll(PlayerDto.class, "player");
    }

}
