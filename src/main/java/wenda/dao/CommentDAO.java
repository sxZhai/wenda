package wenda.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import wenda.model.Comment;

import java.util.List;

@Repository
@Mapper
public interface CommentDAO {
    String TABLE_NAME = " comment ";
    String INSERT_FIELDS = " userId, content, createdDate, entityId, entityType, status ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{userId},#{content},#{createdDate},#{entityId},#{entityType},#{status})"})
    int addComment(Comment comment);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    Comment getCommentById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where entityId=#{entityId} and entityType=#{entityType} order by createdDate desc"})
    List<Comment> selectCommentByEntity(int entityId, int entityType);

    @Select({"select count(id) from ", TABLE_NAME, " where entityId=#{entityId} and entityType=#{entityType}"})
    int getCommentCount(int entityId, int entityType);

    @Update({"update comment set status=#{status} where id=#{id}"})
    int updateStatus(int id, int status);

    @Select({"select count(id) from ", TABLE_NAME, " where userId=#{userId}"})
    int getUserCommentCount(int userId);
}
