package cn.xzxy.lewy.mongodb.controller;

import cn.xzxy.lewy.mongodb.dto.PlayerDto;
import cn.xzxy.lewy.mongodb.service.PlayerMongoService;
import cn.xzxy.lewy.mongodb.utils.MongodbHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    // 单个文档保存，需要指定集合名称
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

    // 单个文档保存，根据实体类上的@Document注解自动映射集合
    @PostMapping(value = "saveAuto")
    public Object saveAuto() {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setPlayerId("990005");
        playerDto.setPlayerName("alaba");
        playerDto.setPlayerNumber(27);
        playerDto.setPlayerAge(27);
        playerDto.setCreateDate("2020-05-14 09:12:34");
        playerDto.setUpdateDate("2020-05-14 09:12:34");

        return mongodbHelper.save(playerDto, COLLECTION);
    }

    // 多个文档保存
    @PostMapping(value = "insertMulti")
    public String insertMulti() {
        PlayerDto playerDto1 = new PlayerDto();
        playerDto1.setPlayerId("990008");
        playerDto1.setPlayerName("leno");
        playerDto1.setPlayerNumber(18);
        playerDto1.setPlayerAge(24);
        playerDto1.setCreateDate("2020-05-16 09:12:34");
        playerDto1.setUpdateDate("2020-05-16 09:12:34");

        PlayerDto playerDto2 = new PlayerDto();
        playerDto2.setPlayerId("990007");
        playerDto2.setPlayerName("thiago");
        playerDto2.setPlayerNumber(6);
        playerDto2.setPlayerAge(29);
        playerDto2.setCreateDate("2020-05-17 09:12:34");
        playerDto2.setUpdateDate("2020-05-17 09:12:34");

        List<PlayerDto> list = new ArrayList<>();
        list.add(playerDto1);
        list.add(playerDto2);

        mongodbHelper.insertMulti(list, COLLECTION);

        return "SUCCESS";
    }

    // 根据指定条件删除
    @PostMapping(value = "removeByKey")
    public String removeByKey() {
        String[] removeKey = {"createDate", "playerName"};
        Object[] removeValue = {"2020-05-16 09:12:34", "leno"};
        mongodbHelper.removeByKey(removeKey, removeValue, COLLECTION);
        return "SUCCESS";
    }

    // 根据ID删除文档
    @PostMapping(value = "removeById")
    public String removeById() {
        mongodbHelper.removeById("5eb9514cc59722271fb691e4", COLLECTION);
        return "SUCCESS";
    }

    // 删除集合中所有文档
    @PostMapping(value = "removeAll")
    public String removeAll() {
        mongodbHelper.removeAll(COLLECTION);
        return "SUCCESS";
    }


}
