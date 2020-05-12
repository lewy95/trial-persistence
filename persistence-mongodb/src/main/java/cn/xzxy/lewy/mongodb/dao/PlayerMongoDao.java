package cn.xzxy.lewy.mongodb.dao;

import cn.xzxy.lewy.mongodb.pojo.PlayerMongo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 和 JPA 一样，SpringBoot 同样为开发者准备了一套 Repository ，
 * 只需要继承 MongoRepository 传入实体类型以及主键类型即可。
 *
 * 但这里没有使用mongoRepository，mongodb的持久化交给mongoTemplate实现
 * mongoRepository
 * 好处：在于使用便捷，基本不需要在dao层编码
 * 缺点：是只适用于对mongodb进行简单的操作，比如说只提供了save方法实现更新，没有提供update方法，
 *      需要更丰富的方法可以使用mongoTemplate自己进行封装
 *
 * @author lewy
 */
@Repository
public interface PlayerMongoDao extends PagingAndSortingRepository<PlayerMongo, String>, JpaSpecificationExecutor<PlayerMongo>, JpaRepository<PlayerMongo, String> {

}
