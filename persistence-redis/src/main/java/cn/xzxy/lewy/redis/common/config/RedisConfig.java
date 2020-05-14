package cn.xzxy.lewy.redis.common.config;

import cn.xzxy.lewy.redis.common.util.FastJson2JsonRedisSerializer;
import cn.xzxy.lewy.redis.common.util.RedisTemplate;
import cn.xzxy.lewy.redis.common.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author lewy
 */
@Configuration
@PropertySource("classpath:redis-single.properties")
@Slf4j
public class RedisConfig {

    @Value("${redis.hosts}")
    private String hostName;

    @Value("${redis.port}")
    private Integer port;

    // 集群模式（非哨兵）
    //@Value("${redis.cluster.nodes}")
    //private String clusterHost;

    // 集群模式（哨兵）
    //@Value("${redis.sentinel.master}")
    //private String sentinelMaster;

    //@Value("${redis.sentinel.nodes}")
    //private String sentinelHosts;

    @Value("${redis.password}")
    private String password;

    @Value("${redis.maxIdle}")
    private Integer maxIdle;

    @Value("${redis.timeout}")
    private Integer timeout;

    @Value("${redis.maxTotal}")
    private Integer maxTotal;

    @Value("${redis.maxWaitMillis}")
    private Integer maxWaitMillis;

    @Value("${redis.minEvictableIdleTimeMillis}")
    private Integer minEvictableIdleTimeMillis;

    @Value("${redis.numTestsPerEvictionRun}")
    private Integer numTestsPerEvictionRun;

    @Value("${redis.timeBetweenEvictionRunsMillis}")
    private long timeBetweenEvictionRunsMillis;

    @Value("${redis.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${redis.testWhileIdle}")
    private boolean testWhileIdle;

    /**
     * Jedis 初始化
     * 在springboot2.0以后，redis默认的底层连接池已经由Jedis更换为Lettuce
     * 本项目底层连接依然使用的jedis，需要进行以下初始化
     * 注意：注入什么参数，说明采用说明形式：
     * 有三种参数：
     * 1.RedisStandaloneConfiguration 单机模式
     * 2.RedisClusterConfiguration    集群（非哨兵）模式
     * 3.RedisSentinelConfiguration   集群（哨兵）模式
     */
    @Bean
    public JedisConnectionFactory JedisConnectionFactory(@Autowired RedisStandaloneConfiguration redisStandaloneConfiguration) {

        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
        jedisClientConfiguration.connectTimeout(Duration.ofMillis(timeout));
        JedisConnectionFactory factory = new JedisConnectionFactory(redisStandaloneConfiguration,
                jedisClientConfiguration.build());
        return factory;
    }

    /**
     * 配置模式一：standalone（单机）模式
     *
     * @return redisStandaloneConfiguration
     */
    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(hostName);
        redisStandaloneConfiguration.setPort(port);
        //由于我们使用了动态配置库,所以此处省略
        //redisStandaloneConfiguration.setDatabase(database);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        return redisStandaloneConfiguration;
    }

    /**
     * 配置模式二：集群（非哨兵）模式
     * @return redisClusterConfiguration
     */
//    @Bean
//    public RedisClusterConfiguration redisClusterConfiguration() {
//        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
//        String [] hosts = clusterHost.split(",");
//        // hosts 为 ip:port, 继续拆分
//        Set<RedisNode> nodeList = new HashSet<RedisNode>();
//        for (String hostAndPort : hosts){
//            String [] hostOrPort = hostAndPort.split(":");
//            nodeList.add(new RedisNode(hostOrPort[0],Integer.parseInt(hostOrPort[1])));
//        }
//        redisClusterConfiguration.setClusterNodes(nodeList);
//        //集群模式下，集群最大转发的数量
//        redisClusterConfiguration.setMaxRedirects(18);
//        redisClusterConfiguration.setPassword(RedisPassword.of(password));
//        return redisClusterConfiguration;
//    }

    /**
     * 配置模式三：集群（哨兵）模式
     * @return redisSentinelConfiguration
     */
//    @Bean
//    public RedisSentinelConfiguration redisSentinelConfiguration() {
//        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
//        redisSentinelConfiguration.setMaster(sentinelMaster);
//        String[] sentinels = sentinelHosts.split(",");
//        for (int i = 0; i < sentinels.length; i++) {
//            String[] hostAndPort = sentinels[i].split(":");
//            RedisNode redisServer = new RedisServer(hostAndPort[0], Integer.parseInt(hostAndPort[1]));
//            redisSentinelConfiguration.sentinel(redisServer);
//        }
//        redisSentinelConfiguration.setPassword(RedisPassword.of(password));
//        return redisSentinelConfiguration;
//    }

    /**
     * 实例化自定义的 RedisTemplate 组件
     * <p>
     * 注意：这里的RedisConnectionFactory指明了连接池的类型
     * 它有两个常用子类：LettuceConnectionFactory 和 JedisConnectionFactory
     * 这里实际上就是注入的就是RedisConnectionFactory的某一个子类，需要自己决定
     */
    @Bean
    public RedisTemplate initRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        log.info("RedisTemplate 开始初始化！");
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        // 配置序列化
        // key序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // value序列化
        redisTemplate.setValueSerializer(fastJson2JsonRedisSerializer());
        // hash小键的序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // hash值的序列化
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        // 开启事务
        redisTemplate.setEnableTransactionSupport(true);
        // 注入
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        log.info("RedisTemplate 初始化成功！");

        return redisTemplate;
    }

    /**
     * 引入自定义序列化
     */
    @Bean
    public RedisSerializer fastJson2JsonRedisSerializer() {
        return new FastJson2JsonRedisSerializer<Object>(Object.class);
    }

    /**
     * 使用Lettuce作为redis的底层连接池，只需要使用LettuceConnectionFactory就行，spring已完成自动配置
     * 本项目暂时不用
     */
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory) {
//        // 配置redisTemplate
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(connectionFactory);
//        // key序列化
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        // value序列化
//        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        // hash小键的序列化
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        // hash值的序列化
//        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
//        // 开启事务
//        redisTemplate.setEnableTransactionSupport(true);
//        // 注入
//        redisTemplate.setConnectionFactory(connectionFactory);
//        return redisTemplate;
//    }

    /**
     * 配置redisUtil工具类
     * 注入封装的RedisTemplate
     */
    @Bean(name = "redisUtil")
    public RedisUtil redisUtil(RedisTemplate<String, Object> redisTemplate) {
        log.info("RedisUtil 初始化成功！");
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.setRedisTemplate(redisTemplate);
        return redisUtil;
    }

    /**
     * 配置基于SpringCache注解的自定义效果生效(主要是序列化等)
     * 注入封装的redisTemplate
     */
    @Bean
    public RedisCacheManager redisCacheManager(RedisTemplate<String, Object> redisTemplate) {

        RedisCacheWriter redisCacheWriter =
                RedisCacheWriter.nonLockingRedisCacheWriter(Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                // 配置序列化
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer()));
        return new RedisCacheManager(redisCacheWriter,
                redisCacheConfiguration,
                this.getRedisCacheTtlMap(redisCacheConfiguration));
    }

    /**
     * 配置一个使注解生效的map
     */
    private Map<String, RedisCacheConfiguration> getRedisCacheTtlMap(RedisCacheConfiguration redisCacheConfiguration) {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        // 根据具体来自定义设置缓存时间
        redisCacheConfigurationMap.put("businessCache", redisCacheConfiguration.entryTtl(Duration.ofSeconds(30*60)));
        redisCacheConfigurationMap.put("userCache", redisCacheConfiguration.entryTtl(Duration.ofSeconds(60)));
        // 这里做一个测试: player_info对应@Cacheable的value属性
        redisCacheConfigurationMap.put("player_info", redisCacheConfiguration.entryTtl(Duration.ofSeconds(60)));

        return redisCacheConfigurationMap;
    }

}
