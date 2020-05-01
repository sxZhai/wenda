package wenda;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import redis.clients.jedis.Jedis;

public class JedisAdapter {
    public static void print(int index,Object obj){
        System.out.println(String.format("%d,%s",index,obj.toString()));
    }

    public static void main(String[] arg){
        Jedis jedis=new Jedis("redis://localhost:6379/9");
        jedis.flushDB();

        //get set
        jedis.set("hello","world");
        print(1,jedis.get("hello"));
        jedis.rename("hello","newhello");
        print(1,jedis.get("newhello"));
        jedis.setex("hello2",15,"world");

        //
        jedis.set("pv","100");
        jedis.incr("pv");
        print(2,jedis.get("pv"));
        print(3,jedis.keys("*"));


        //list
        String listName="List";
        jedis.del(listName);
        for (int i=0;i<10;i++){
            jedis.lpush(listName,"a"+String.valueOf(i));
        }
        print(4,jedis.lrange(listName,0,12));

        //hash
        String userKey="userxx";
        jedis.hset(userKey,"name","jim");
        jedis.hset(userKey,"age","12");
        jedis.hset(userKey,"phone","15651853879");
        print(5,jedis.hget(userKey,"name"));
        print(6,jedis.hgetAll(userKey));

        //set
        String likeKey1="commentLike1";
        String likeKey2="commentLike2";
        for (int i=0;i<10;i++){
            jedis.sadd(likeKey1,String.valueOf(i));
            jedis.sadd(likeKey2,String.valueOf(i*i));
        }
        print(7,jedis.smembers(likeKey1));
        print(8,jedis.smembers(likeKey2));
        print(9,jedis.sunion(likeKey1,likeKey2));
        print(10,jedis.sdiff(likeKey1,likeKey2));
        print(11,jedis.sinter(likeKey1,likeKey2));


    }
}
