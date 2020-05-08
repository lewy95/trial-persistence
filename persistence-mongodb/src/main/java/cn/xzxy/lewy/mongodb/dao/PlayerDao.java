package cn.xzxy.lewy.mongodb.dao;

import cn.xzxy.lewy.mongodb.pojo.Player;
import cn.xzxy.lewy.mongodb.pojo.PlayerMongo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lewy
 */
@Transactional
public interface PlayerDao extends PagingAndSortingRepository<Player, Long>, JpaSpecificationExecutor<PlayerMongo>, JpaRepository<Player, Long> {

    /**
     * 根据id查找
     * @param id id
     * @return player
     */
    Player findById(Integer id);
}
