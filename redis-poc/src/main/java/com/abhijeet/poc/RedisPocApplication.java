package com.abhijeet.poc;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableWebSecurity
@EnableSwagger2
public class RedisPocApplication {

	RedisPocApplication() {

		//
	}

	public static void main(String[] args) {
		SpringApplication.run(RedisPocApplication.class, args);
		RedisOperations redisOperations = new RedisOperations();
		// method to set and get string on the basis of key name in 0th db of
		// redis server
		redisOperations.setStringAndGetString("name", "Abhijeet");

		// method to set and get HASh Data on the basis of key name in 0th db of
		// redis server
		// users:uniqueId in this form data will be stored in redis "users" will
		// be a namespace
		// hgetAll will gives to all data present on that hash key
		Map<String, String> map = new HashMap<>();
		map.put("name", "Abhijeet Behare");
		map.put("age", "23");
		map.put("uniqueId", "1");
		redisOperations.setAndGetHashData("users:" + map.get("uniqueId"), map);

		// to update hashdata for example i want to update age
		Map<String, String> updateMap = new HashMap<>();
		updateMap.put("age", "24");
		redisOperations.setAndGetHashData("users:" + 1, updateMap);

		for (int counter = 0; counter < 3; counter++) {
			// to set and get redis SET data
			redisOperations.setGetSETData("usersset", "ABhijeet" + counter);
			// to set sorted set data
			redisOperations.setGetSETDataOfSortedSET("REDIS_POC", counter * 10, "AB" + counter);
		}

		redisOperations.setGetListData("REDIS:REDIS_LIST", "ABHIJEET");

	}
}
