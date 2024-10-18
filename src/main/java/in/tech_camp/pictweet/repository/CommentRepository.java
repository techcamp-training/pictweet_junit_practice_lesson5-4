package in.tech_camp.pictweet.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.pictweet.entity.CommentEntity;


@Mapper
public interface CommentRepository {
    @Select("SELECT c.*, u.id AS user_id, u.nickname AS user_nickname FROM comments c JOIN users u ON c.user_id = u.id WHERE c.tweet_id = #{tweetId}")
    @Results(value = {
      @Result(property = "user.id", column = "user_id"),
      @Result(property = "user.nickname", column = "user_nickname"),
      @Result(property = "tweet", column = "tweet_id",
              one = @One(select = "in.tech_camp.pictweet.repository.TweetRepository.findById"))
    })
    List<CommentEntity> findByTweetId(Integer tweetId);

    @Select("SELECT * FROM comments WHERE user_id = #{userId}")
    @Results(value = {
      @Result(property = "user", column = "user_id",
              one = @One(select = "in.tech_camp.pictweet.repository.UserRepository.findById")),
      @Result(property = "tweet", column = "tweet_id",
              one = @One(select = "in.tech_camp.pictweet.repository.TweetRepository.findById"))
    })
    List<CommentEntity> findByUserId(Integer userId);

    @Insert("INSERT INTO comments (text, user_id, tweet_id) VALUES (#{text}, #{user.id}, #{tweet.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(CommentEntity comment);
}