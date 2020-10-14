package cn.xzxy.lewy.redisson.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
// 指定要扫描的Mapper类的包的路径
@MapperScan("cn.xzxy.lewy.redisson.mapper")
public class MybatisConfig {

}
