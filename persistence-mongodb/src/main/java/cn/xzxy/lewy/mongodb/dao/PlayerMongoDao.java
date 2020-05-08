package cn.xzxy.lewy.mongodb.dao;

import cn.xzxy.lewy.mongodb.pojo.PlayerMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author lewy
 */
public interface PlayerMongoDao extends MongoRepository<PlayerMongo, String> {

    /**
     * 和 JPA 一样，SpringBoot 同样为开发者准备了一套 Repository ，
     * 只需要继承 MongoRepository 传入实体类型以及主键类型即可。
     */
}
