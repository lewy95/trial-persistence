package cn.xzxy.lewy.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("cn.xzxy.lewy.mybatis.mapper") //扫描的mapper
@SpringBootApplication
public class PersistenceMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersistenceMybatisApplication.class, args);
    }

}
