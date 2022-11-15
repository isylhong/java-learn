package redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class JedisDemo {

    public static Jedis getJedis() {
        String hostname = "192.168.80.128";
        int port = 6380;
        Jedis jedis = new Jedis(hostname, port);
        return jedis;
    }

    public static void main(String[] args) {
        Jedis jedis = getJedis();

        String value = jedis.ping();
        System.out.println(value);
        jedis.close();
    }

    // 操作 zset 类型
    @Test
    public void demo05() {
        Jedis jedis = getJedis();

        // score值越下，越靠前
        jedis.zadd("score",100D,"s-100");
        jedis.zadd("score",10D,"s-10");
        jedis.zadd("score",60D,"s-60");

        Set<String> values = jedis.zrange("score",0,-1);
        System.out.println(values);

        System.out.println("------zset删除-------");
        Long zrem = jedis.zrem("score", "s-100");
        System.out.println(zrem);
        jedis.close();
    }

    // 操作 set 类型
    @Test
    public void demo04() {
        Jedis jedis = getJedis();

        jedis.sadd("names","zhangsan1");
        jedis.sadd("names","zhangsan2");

        Set<String> names = jedis.smembers("names");
        System.out.println(names);

        System.out.println("------set删除-------");
        Long srem = jedis.srem("names", "zhangsan1");
        System.out.println(srem);
        jedis.close();
    }


    // 操作 list 类型
    @Test
    public void demo03() {
        Jedis jedis = getJedis();
        jedis.lpush("names", "r-Value","tom3", "tom2", "tom1","l-value");

        String lpop =  jedis.lpop("names");
        System.out.println(lpop);

        String rpop = jedis.rpop("names");
        System.out.println(rpop);

        // lrange 下标从0开始，-1表示最后一个
        List<String> values = jedis.lrange("names", 0, -1);
        System.out.println(values);
        jedis.close();

    }

    // 操作 hash 类型
    @Test
    public void demo02() {
        Jedis jedis = getJedis();
        jedis.hset("user","age","20");
        String hget =jedis.hget("user","age");
        System.out.println(hget);

        Long hdel = jedis.hdel("user", "age");
        System.out.println(hdel);


        jedis.close();
    }



    // 操作 string 类型
    @Test
    public void demo01() {
        Jedis jedis = getJedis();

        //添加、获取单个值
        jedis.set("name", "zhangsan");
        System.out.println("------get--------");
        String name = jedis.get("name");
        System.out.println(name);

        //设置/获取 多个key-value
        System.out.println("--------设置多个key-value----------");
        String[] keyValues = {"k1", "v1", "k2", "v2"};
        jedis.mset(keyValues);

        String[] keys = new String[]{"k1", "k2"};
        List<String> mget = jedis.mget(keys);
        System.out.println(mget);

        // 获取所有keys
        System.out.println("------获取所以keys-------");
        Set<String> keyAll = jedis.keys("*");
        Iterator<String> iterator = keyAll.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            System.out.print(key + ",");
        }
        jedis.close();

    }
}
