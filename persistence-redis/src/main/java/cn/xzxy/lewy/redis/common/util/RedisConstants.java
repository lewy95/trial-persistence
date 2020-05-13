package cn.xzxy.lewy.redis.common.util;

/**
 * 定义了一些常量
 * 明确划分了每个库的用途
 * @author lewy
 */
public class RedisConstants {

    public static final String spilt = ":";

    /**
     * redis库1  保存档案树
     */
    public static final Integer DATEBASE1 = 1;

    /**
     * redis库2
     * 1. 保存档案表格
     * 2. 保存分页码
     */
    public static final Integer DATEBASE2 = 2;

    /**
     * redis库3 保存档案image url
     */
    public static final Integer DATEBASE3 = 3;

    /**
     * redis库4 保存手机验证码
     */
    public static final Integer DATEBASE4 = 4;

    /**
     * redis库5 保存身份认证信息
     */
    public static final Integer DATEBASE5 = 5;

    /**
     * redis库6 记录身份认证次数
     */
    public static final Integer DATEBASE6 = 6;

    /**
     * redis库7 记录重发次数
     */
    public static final Integer DATEBASE7 = 7;

    /**
     * redis库8 记录任务参数
     */
    public static final Integer DATEBASE8 = 8;


    public RedisConstants() {

    }
}
