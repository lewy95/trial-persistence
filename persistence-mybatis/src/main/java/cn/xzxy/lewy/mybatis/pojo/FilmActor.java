package cn.xzxy.lewy.mybatis.pojo;

import java.util.Date;

public class FilmActor extends FilmActorKey {
    private Date lastUpdate;

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}