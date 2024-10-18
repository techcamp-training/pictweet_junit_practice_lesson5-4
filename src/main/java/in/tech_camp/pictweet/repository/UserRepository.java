package in.tech_camp.pictweet.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.pictweet.entity.UserEntity;

@Mapper
public interface UserRepository {
    @Select("SELECT * FROM users WHERE id = #{id}")
    @Results(value = {
    @Result(property = "id", column = "id"),
    @Result(property = "tweets", column = "id",
            many = @Many(select = "in.tech_camp.pictweet.repository.TweetRepository.findByUserId")),
    @Result(property = "comments", column = "id",
            many = @Many(select = "in.tech_camp.pictweet.repository.CommentRepository.findByUserId"))
    })
    UserEntity findById(Integer id);

    @Select("SELECT * FROM users WHERE email = #{email}")
    UserEntity findByEmail(String email);

    @Select("SELECT * FROM users")
    List<UserEntity> findAll();

    @Select("SELECT EXISTS(SELECT 1 FROM users WHERE email = #{email})")
    boolean existsByEmail(String email);

    @Insert("INSERT INTO users (nickname, email, password) VALUES (#{nickname}, #{email}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(UserEntity user);
}