package cn.xzxy.lewy.redis.dao;

import cn.xzxy.lewy.redis.pojo.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lewy
 */
@Transactional
public interface PlayerDao extends PagingAndSortingRepository<Player, Long>, JpaSpecificationExecutor<Player>, JpaRepository<Player, Long> {

    /**
     * 根据id查找
     * @param id id
     * @return player
     */
    Player findById(Integer id);
}
