package com.abhijeet.poc;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class RedisOperations {

	final Logger logger = LoggerFactory.getLogger(RedisOperations.class);
	static JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost", 6379, 10000);

	public void setStringAndGetString(String key, String value) {
		Jedis jedis = null;

		try {
			jedis = pool.getResource();
			jedis.set(key, value);
			logger.info("data for key " + key + " is " + jedis.get(key));

		} catch (Exception e) {
			logger.error("Exception in setStringAndGetString " + e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}

	}

	public void setAndGetHashData(String key, Map<String, String> map) {
		Jedis jedis = null;

		try {
			jedis = pool.getResource();
			jedis.hmset(key, map);
			logger.info("To get All hash data for key " + key + " is " + jedis.hgetAll(key));
			logger.info("To get only name data for key " + key + " is " + jedis.hget(key, "name"));
			logger.info("To get only age data for key " + key + " is " + jedis.hget(key, "age"));

		} catch (Exception e) {
			logger.error("Exception in setAndGetHashData " + e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void setGetSETData(String key, String members) {
		Jedis jedis = null;

		try {
			jedis = pool.getResource();
			jedis.sadd(key, members);
			logger.info("To get SET data for key " + key + " is " + jedis.smembers(key));
			// below line will remove ABhijeet1 from the redis SET For given key
			// if it exits
			jedis.srem(key, "ABhijeet1");
			logger.info("after removing ABhijeet1 To get SET data for key " + key + " is " + jedis.smembers(key));

		} catch (Exception e) {
			logger.error("Exception in setAndGetHashData " + e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void setGetSETDataOfSortedSET(String key, double score, String members) {
		Jedis jedis = null;

		try {
			jedis = pool.getResource();

			jedis.zadd(key, score, members);
			// here will are trying to get data by range of Score between 20 to
			// 1000
			Set<String> rangeofData = jedis.zrangeByScore(key, 20, 1000);
			for (String setData : rangeofData) {
				logger.info("sorted set data as per range " + setData);
			}
			// here range is give 0 ,1 it will give first two record from the
			// redis Sorted SET
			Set<String> dataAsPerIndex = jedis.zrange(key, 0, 1);

			for (String setData : dataAsPerIndex) {
				logger.info("sorted set data as per dataAsPerIndex " + setData);
			}

		} catch (Exception e) {
			logger.error("Exception in setGetSETDataOfSortedSET " + e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void setGetListData(String key, String members) {
		Jedis jedis = null;

		try {
			jedis = pool.getResource();
			// list will allow to insert same data again and again in list
			for (int count = 0; count < 4; count++) {
				jedis.lpush(key, members + count);
			}
			// last inserted data will be pop from the list using below line
			String listData = jedis.lpop(key);
			logger.info("list pop deleted data from list is  - > " + listData);
			// below line will return data from list as per index from 0 to 2
			logger.info("list from as per index  0 to 10 " + jedis.lrange(key, 0, 10));
			// below line will remove "ABHIJEET0" 5 occurrence from the list
			jedis.lrem(key, 5, "ABHIJEET0");
			// update record of 2nd index with QWERTY

			logger.info("update record of 2nd index with QWERT STATUS  " + jedis.lset(key, 1, "QWERTY"));

		} catch (Exception e) {
			logger.error("Exception in setGetListData " + e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

}
