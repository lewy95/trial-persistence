package cn.xzxy.lewy.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("cn.xzxy.lewy.mybatisplus.dao") //扫描的mapper
@SpringBootApplication
public class PersistenceMybatisPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersistenceMybatisPlusApplication.class, args);
    }

}
