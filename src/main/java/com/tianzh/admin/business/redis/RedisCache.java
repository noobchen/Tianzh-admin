package com.tianzh.admin.business.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by pig on 2015-09-16.
 */
public class RedisCache {

    private final String DEFAULT_HOST = "120.24.1.156";
    private final int DEFAULT_PORT = 6379;
    private JedisPool pool;
    private String host = DEFAULT_HOST;
    private int port = DEFAULT_PORT;
    private int timeout = 5000;
    private int maxActive = 100;
    private int maxIdle = 20;
    private int maxWait = 1000;
    private boolean testOnBorrow = false;

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public void init() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxActive(maxActive);
        config.setMaxIdle(maxIdle);
        config.setMaxWait(maxWait);
        config.setTestOnBorrow(testOnBorrow);
        pool = new JedisPool(config, host, port, timeout,"Sztz12345");
    }


    public void destroy() {
        pool.destroy();
    }


    public String query(String key) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();

            return jedis.get(key);
        } catch (Exception e) {
            return "";
        } finally {
            pool.returnResource(jedis);
        }
    }


    public boolean insert(String key, String json) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            String response = jedis.set(key, json);

            if (response.equals("OK")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        } finally {
            pool.returnResource(jedis);
        }
    }


    public boolean del(String key) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            long delNum = jedis.del(key);

            if (delNum > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        } finally {
            pool.returnResource(jedis);
        }
    }
}
