package Preproject28.server.question.repository;


import Preproject28.server.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query(value = "SELECT q FROM Question q WHERE q.member.memberId = :memberId")
    Page<Question> findByMemberId(long memberId, Pageable pageable);

    @Query("SELECT q FROM Question q WHERE q.title = name AND WHERE LOWER(q.title) LIKE CONCAT('%', LOWER(:name), '%') ")
    List<Question> findByTitle(String name);
// 쿼리메서드로 조건달아서 필터 & 검색기능 추가해야할듯

    //다빈님

//    @Query(value = "SELECT p FROM Post p WHERE (p.secretStatus='PUBLIC' OR p.member.memberId = :memberId) AND NOT p.questionStatus = 'QUESTION_DELETE'")
//    Page<Post> findAllByMemberId(long memberId, Pageable pageable);
//    @Query(value = "SELECT p FROM Post p WHERE NOT p.questionStatus = 'QUESTION_DELETE'")
//    Page<Post> findAllFromAdmin(Pageable pageable);
}
