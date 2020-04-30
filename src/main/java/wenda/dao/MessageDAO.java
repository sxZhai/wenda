package wenda.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import wenda.model.Message;

import java.util.List;

@Repository
@Mapper
public interface MessageDAO {
    String TABLE_NAME = " message ";
    String INSERT_FIELDS = " fromId, toId, content, hasRead, conversationId, createdDate ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{fromId},#{toId},#{content},#{hasRead},#{conversationId},#{createdDate})"})
    int addMessage(Message message);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where conversationId=#{conversationId} order by createdDate desc limit #{offset}, #{limit}"})
    List<Message> getConversationDetail(String conversationId, int offset, int limit);

    // 注意这里把count(id)的值设到了id字段
    @Select({"select ", INSERT_FIELDS, " , count(id) as id from ( select * from ", TABLE_NAME,
            " where fromId=#{userId} or toId=#{userId} order by createdDate desc) tt group by conversationId order by createdDate desc limit #{offset}, #{limit}"})
    List<Message> getConversationList(int userId, int offset, int limit);

    @Select({"select count(id) from ", TABLE_NAME, " where hasRead=0 and toId=#{userId} and conversationId=#{conversationId}"})
    int getConversationUnreadCount(int userId, String conversationId);
}