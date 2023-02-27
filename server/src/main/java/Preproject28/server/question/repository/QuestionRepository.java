package Preproject28.server.question.repository;


import Preproject28.server.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    // 쿼리메서드로 조건달아서 필터 & 검색기능 추가해야할듯

//    @Query(value = "SELECT p FROM Post p WHERE (p.secretStatus='PUBLIC' OR p.member.memberId = :memberId) AND NOT p.questionStatus = 'QUESTION_DELETE'")
//    Page<Post> findAllByMemberId(long memberId, Pageable pageable);
//    @Query(value = "SELECT p FROM Post p WHERE NOT p.questionStatus = 'QUESTION_DELETE'")
//    Page<Post> findAllFromAdmin(Pageable pageable);
}
