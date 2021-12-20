package com.jiangfendou.share;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *  @author jiangmh
 * @MapperScan("com.jiangfendou.share.mapper")    如果使用这个注解的话mapper注解就不需要
 * */
@SpringBootApplication
public class VehicleApplication {
    public static void main(String[] args) {
        SpringApplication.run(VehicleApplication.class);
    }
}
