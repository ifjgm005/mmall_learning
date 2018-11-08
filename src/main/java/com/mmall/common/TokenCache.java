package com.mmall.common;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author : axes
 * create at:  2018/11/7  11:15 PM
 * @description: Token 缓存
 */
public class TokenCache {
    private static final Logger logger = LoggerFactory.getLogger(TokenCache.class);
    private static final String TOKENPREFIX = "token_prefix";
    // initialCapacity 初始化缓存容量
    // maximumSize 缓存的最大容量， 达到最大容量后采用 LRU 算法
    // expireAfterAccess 缓存周期
    // 利用假 "null" 为之后的判断做伏笔
    private static LoadingCache<String, String> localCache =
            CacheBuilder
                    .newBuilder()
                    .initialCapacity(1000)
                    .maximumSize(10000)
                    .expireAfterAccess(12, TimeUnit.HOURS)
                    .build(new CacheLoader<String, String>() {
                        //默认数据加载实现，当调用 get 取值的时候，没有与key 相对应的值的时候便会调用这个方法。
                        @Override
                        public String load(String s) throws Exception {
                            return null;
                        }
                    });

    /**
     * create by axes at 2018/11/7 11:39 PM
     * description: 设置缓存
     *
     * @param key   键值
     * @param value 值
     */
    public static void setTokenCache(String key, String value) {
        localCache.put(key, value);
    }

    /**
     * create by axes at 2018/11/7 11:49 PM
     * description:
     *
     * @param key key 值
     * @return String 返回和key 对应的String 类型的值
     */
    public static String getCacheToken(String key) {
        String value = null;
        try {
            value = localCache.get(key);
            //感觉不是很有必要
//            if (value.equals("null")){
//                return null;
//            }
        } catch (ExecutionException e) {
            e.printStackTrace();
            logger.error("localcache get error", e);
        }
        return value;
    }

    /**
    * create by axes at 2018/11/8 10:37 PM
    * description: 用固定前缀和客户姓名拼接 key 用户缓存 token
    * @return 返回拼接后的token 缓存的key
    * @param userName 客户姓名
    */
    public static String joinTokenWithUser(String userName) {
        return TOKENPREFIX+userName;
    }
}
