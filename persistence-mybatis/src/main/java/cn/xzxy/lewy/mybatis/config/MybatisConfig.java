package cn.xzxy.lewy.mybatis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
// 指定要扫描的Mapper类的包的路径
@MapperScan("cn.xzxy.lewy.mybatis.mapper")
public class MybatisConfig {

}
