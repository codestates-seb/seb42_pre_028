package Preproject28.server.question.repository;


import Preproject28.server.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
// 쿼리메서드로 조건달아서 필터 & 검색기능 추가해야할듯

    @Query("SELECT q FROM Question q WHERE LOWER(q.title) LIKE CONCAT('%', LOWER(:name), '%') " +
            "AND (q.secretStatus = 'PUBLIC' OR q.member.memberId = :memberId) AND NOT q.questionStatus = 'QUESTION_DELETE'")
    List<Question> findByTitle(String name,Long memberId);

//    JOIN p.tag
    //다빈님

//    @Query(value = "SELECT p FROM Post p WHERE (p.secretStatus='PUBLIC' OR p.member.memberId = :memberId) AND NOT p.questionStatus = 'QUESTION_DELETE'")
//    Page<Post> findAllByMemberId(long memberId, Pageable pageable);
//    @Query(value = "SELECT p FROM Post p WHERE NOT p.questionStatus = 'QUESTION_DELETE'")
//    Page<Post> findAllFromAdmin(Pageable pageable);
}
