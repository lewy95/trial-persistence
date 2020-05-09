package cn.xzxy.lewy.mongodb.service.impl;

import cn.xzxy.lewy.mongodb.dao.PlayerMongoDao;
import cn.xzxy.lewy.mongodb.pojo.PlayerMongo;
import cn.xzxy.lewy.mongodb.service.PlayerMongoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author lewy
 */
@Service("playerMongoService")
public class PlayerMongoServiceImpl implements PlayerMongoService {

    @Resource
    PlayerMongoDao playerMongoDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PlayerMongo addPlayer(PlayerMongo player) {
        return playerMongoDao.save(player);
    }

    @Override
    public void deletePlayer(String id) {
        playerMongoDao.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PlayerMongo updatePlayer(PlayerMongo player) {
        PlayerMongo originalPlayer = this.findPlayerById(player.getPlayerId());
        if (originalPlayer != null) {
            originalPlayer.setPlayerName(player.getPlayerName());
            originalPlayer.setPlayerAge(player.getPlayerAge());
            originalPlayer.setPlayerNumber(player.getPlayerNumber());
            originalPlayer.setCreateDate(player.getCreateDate());
            originalPlayer.setUpdateDate(new Date());

            return playerMongoDao.save(originalPlayer);
        } else {
            return null;
        }
    }

    @Override
    public PlayerMongo findPlayerById(String id) {
        return playerMongoDao.findById(id).get();
    }

    @Override
    public List<PlayerMongo> findAllPlayer() {
        return playerMongoDao.findAll();
    }
}
