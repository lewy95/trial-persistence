package cn.xzxy.lewy.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PersistenceMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersistenceMybatisApplication.class, args);
    }

}
