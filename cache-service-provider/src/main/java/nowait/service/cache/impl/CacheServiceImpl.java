package nowait.service.cache.impl;

import nowait.service.cache.api.CacheService;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

/**
 * Created by yangjunming on 3/23/16.
 * author: yangjunming@huijiame.com
 */
@Service("cacheService")
public class CacheServiceImpl implements CacheService.Iface {

    private final String KEY_SPLIT_CHAR = ":";
    private final String KEYS_STRING = "STRING";
//    private static final String KEYS_SET = "SET";
//    private static final String KEYS_LIST = "LIST";
//    private static final String KEYS_HASH = "HASH";
//    private static final String KEYS_ZSET = "ZSET";

    @Autowired
    JedisCluster jc;

    @Override
    public String ping() throws TException {
        return "pong";
    }

    @Override
    public String setCache(String key, String value) throws TException {
        return set(key, value);
    }

    @Override
    public String getCache(String key) throws TException {
        return jc.get(key);
    }

    @Override
    public String setCacheWithClientId(String clientId, String key, String value) throws TException {
        return set(clientId + KEY_SPLIT_CHAR + key, value);
    }

    @Override
    public String getCacheWithClientId(String clientId, String key) throws TException {
        return jc.get(clientId + KEY_SPLIT_CHAR + key);
    }


    private void addKey(final String container, final String key) {
        if (!jc.exists(container)) {
            jc.sadd(container, key);
        } else {
            if (!jc.smembers(container).contains(key)) {
                jc.sadd(container, key);
            }
        }
    }


    /**
     * 写入字符串缓存
     *
     * @param key
     * @param value
     * @return
     */
    private String set(final String key, final String value) {
        String result = jc.set(key, value);
        //addKey(KEYS_STRING, key);
        return result;
    }
}
