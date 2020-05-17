package wenda.async;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wenda.utils.JedisAdapter;
import wenda.utils.RedisKeyUtil;

@Service
public class EventProducer {
    @Autowired
    JedisAdapter jedisAdapter;

    // 把事件推到队列里面去
    public boolean fireEvent(EventModel eventModel) {
        try {
            String json = JSONObject.toJSONString(eventModel);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key, json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
