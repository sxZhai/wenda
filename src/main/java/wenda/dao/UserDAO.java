package wenda.dao;

import org.apache.ibatis.annotations.*;
import wenda.model.User;

@Mapper
public interface UserDAO {
    String TABLE_NAME="user";
    String INSERT_FIELDS="name, password, salt, headUrl";
    String SELECT_FIELDS="id, "+INSERT_FIELDS;
    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS, ") values(#{name},#{password},#{salt},#{headUrl})"})
    int addUser(User user);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    User selectById(int id);

    @Update({"update ", TABLE_NAME, " set password=#{password} where id=#{id}"})
    void updatePassword(User user);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where name=#{name}"})
    User selectByName(String name);

    @Delete({"delete from ", TABLE_NAME, " where id=#{id}"})
    void deleteById(int id);
}
