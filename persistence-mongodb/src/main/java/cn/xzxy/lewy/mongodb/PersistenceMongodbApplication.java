package cn.xzxy.lewy.mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//@EnableMongoRepositories(basePackages = {"cn.xzxy.lewy.mongodb.dao"})
@SpringBootApplication
public class PersistenceMongodbApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersistenceMongodbApplication.class, args);
    }

}
