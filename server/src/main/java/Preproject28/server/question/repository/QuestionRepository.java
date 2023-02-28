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



    //검색해서 찾기 성공!  nueweset 순으로 정렬 동일한 -> 포함된 CONCAT('%', LOWER(q.title), '%') // 대소문자
    @Query(value = "SELECT q FROM Question q WHERE q.title LIKE %:title%")
    Page<Question> findByTitle(String title, Pageable pageable);

    Page<Question> findByTitleContainingIgnoreCase(String title, Pageable pageable);
//    @Query(value = "SELECT p FROM Post p WHERE (p.secretStatus='PUBLIC' OR p.member.memberId = :memberId) AND NOT p.questionStatus = 'QUESTION_DELETE'")
//    Page<Post> findAllByMemberId(long memberId, Pageable pageable);
//    @Query(value = "SELECT p FROM Post p WHERE NOT p.questionStatus = 'QUESTION_DELETE'")
//    Page<Post> findAllFromAdmin(Pageable pageable);
}
