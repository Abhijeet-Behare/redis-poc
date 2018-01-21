package com.abhijeet.poc.controller;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@RestController
public class RedisController {

	static JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost", 6379);
	final Logger logger = LoggerFactory.getLogger(RedisController.class);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@CrossOrigin

	@ApiOperation(value = "/savestring", nickname = "/savestring")
	@RequestMapping(method = RequestMethod.GET, path = "/savestring")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 400, message = "BadRequest"),
			@ApiResponse(code = 500, message = "Internal Server Error")

	})
	public ResponseEntity saveStringInRedis(@RequestHeader String key, @RequestHeader String value) {

		Jedis jedis = null;

		try {
			jedis = pool.getResource();
			jedis.set(key, value);
			logger.info("data for key " + key + " is " + jedis.get(key));

			return new ResponseEntity("Data Saved to redis for key " + key, HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Exception in setStringAndGetString " + e);
			return new ResponseEntity("Data not Saved to redis for key " + key, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}

	}

	// get all keys present in redis db

	@CrossOrigin
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "/getkeys", nickname = "/getkeys")
	@RequestMapping(method = RequestMethod.GET, path = "/getkeys")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 400, message = "BadRequest"),
			@ApiResponse(code = 500, message = "Internal Server Error")

	})

	public ResponseEntity getKeys() {

		Jedis jedis = null;

		try {

			jedis = pool.getResource();

			Set<String> setOfKeys = jedis.keys("*");
			if (!setOfKeys.isEmpty()) {
				return new ResponseEntity(setOfKeys, HttpStatus.OK);
			} else {
				return new ResponseEntity("NO Keys Found", HttpStatus.OK);
			}

		} catch (Exception e) {
			logger.error(" Exception in get keys " + e);
			return new ResponseEntity("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		finally {

			if (jedis != null) {
				jedis.close();
			}

		}

	}

}
