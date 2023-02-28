package Preproject28.server.answer.repository;


import Preproject28.server.answer.entity.Answer;
import Preproject28.server.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query(value = "SELECT a FROM Answer a WHERE a.member.memberId = :memberId")
    Page<Answer> findByMemberId(long memberId, Pageable pageable);
}
