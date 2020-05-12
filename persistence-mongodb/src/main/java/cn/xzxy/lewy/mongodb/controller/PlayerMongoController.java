package cn.xzxy.lewy.mongodb.controller;

import cn.xzxy.lewy.mongodb.dto.PlayerDto;
import cn.xzxy.lewy.mongodb.service.PlayerMongoService;
import cn.xzxy.lewy.mongodb.utils.MongodbHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author lewy
 */
@RestController
@RequestMapping("/rest/playerMongo")
@Slf4j
public class PlayerMongoController {

    private static final String COLLECTION = "player";

    @Resource
    PlayerMongoService playerMongoService;

    @Resource
    MongodbHelper mongodbHelper;

    // 查询集合中所有索引
    @PostMapping(value = "listIndexes")
    public List listIndexes() {
        return mongodbHelper.getAllIndexes(COLLECTION);
    }

    // 查询所有
    @PostMapping(value = "findAll")
    public Object findAll() {

        return mongodbHelper.findAll(new PlayerDto(), COLLECTION);
    }

    // 根据匹配查询所有
    @PostMapping(value = "find")
    public List find() {

        String[] findKeys = {"playerAge"};
        Object[] findValues = {20};

        return mongodbHelper.find(new PlayerDto(), findKeys, findValues, COLLECTION);
    }

    // 根据匹配查询并排序
    @PostMapping(value = "findBySort")
    public List findBySort() {

        String[] findKeys = {"playerAge"};
        Object[] findValues = {20};
        String sortField = "playerNumber";

        return mongodbHelper.findBySort(new PlayerDto(), findKeys, findValues, COLLECTION, sortField, 1);
    }

    // 根据匹配查询出符合的第一条数据
    @PostMapping(value = "findOne")
    public Object findOne() {
        String[] findKeys = {"playerAge"};
        Object[] findValues = {20};

        return mongodbHelper.findOne(new PlayerDto(), findKeys, findValues, COLLECTION);
    }


    // 查询并更新
    @PostMapping(value = "findAndModify")
    public String findAndModify() {

        PlayerDto playerDto = new PlayerDto();
        String collectionName = "player";
        String[] findKeys = {"playerName", "playerNumber"};
        Object[] findValues = {"muller", 25};
        String[] targetKeys = {"playerAge"};
        Object[] targetValues = {30};

        // Object res = xxx  返回的这个object是更新之前的
        mongodbHelper.findAndModify(playerDto, findKeys, findValues, targetKeys, targetValues, collectionName);

        return "SUCCESS";
    }

    // 只更新匹配的第一条
    @PostMapping(value = "updateFirst")
    public String updateFirst() {

        String accordingKey = "playerAge";
        Object accordingValue = 20;
        String[] updateKeys = {"createDate", "updateDate"};
        String[] updateValues = {"2020-05-14 10:13:11", "2020-05-14 10:13:11"};
        mongodbHelper.updateFirst(accordingKey, accordingValue, updateKeys, updateValues, COLLECTION);

        return "SUCCESS";
    }

    // 更新多条
    @PostMapping(value = "updateMulti")
    public String updateMulti() {

        String accordingKey = "playerName";
        String accordingValue = "muller";
        String[] updateKeys = {"createDate", "updateDate"};
        String[] updateValues = {"2020-05-14 09:12:34", "2020-05-14 09:12:36"};
        mongodbHelper.updateMulti(accordingKey, accordingValue, updateKeys, updateValues, COLLECTION);

        return "SUCCESS";
    }

    @PostMapping(value = "save")
    public Object save() {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setPlayerId("990005");
        playerDto.setPlayerName("alaba");
        playerDto.setPlayerNumber(27);
        playerDto.setPlayerAge(27);
        playerDto.setCreateDate("2020-05-14 09:12:34");
        playerDto.setUpdateDate("2020-05-14 09:12:34");

        return mongodbHelper.save(playerDto, COLLECTION);
    }

    @PostMapping(value = "saveWithoutDocument")
    public Object saveWithoutDocument() {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setPlayerId("990006");
        playerDto.setPlayerName("kimmich");
        playerDto.setPlayerNumber(32);
        playerDto.setPlayerAge(24);
        playerDto.setCreateDate("2020-05-16 09:12:34");
        playerDto.setUpdateDate("2020-05-16 09:12:34");

        return mongodbHelper.save(playerDto);
    }




}
