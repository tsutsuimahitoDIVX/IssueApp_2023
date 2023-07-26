package in.techcamp.issueapp.repository;

import in.techcamp.issueapp.entity.IssueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<IssueEntity, Integer> {
    List<IssueEntity> findByUser_Id(Integer userId);
}

//@Mapper
//public interface IssueRepository {
//    @Select("select * from issues")
//    List<IssueEntity> findAll();
//    @Insert("insert into issues (title, content, period, importance) values (#{title}, #{content}, #{period}, #{importance})")
//    void insert(String title, String content, String period, Character importance);
//    @Select("select * from issues where id = #{id}")
//    IssueEntity findById(long id);
//    @Update("UPDATE issues SET title = #{title}, content = #{content}, period = #{period}, importance = #{importance} WHERE id =#{id}")
//    void update(long id, String title, String content, String period, Character importance);
//    @Delete("delete from issues where id=#{id}")
//    void deleteById(Long id);
//}