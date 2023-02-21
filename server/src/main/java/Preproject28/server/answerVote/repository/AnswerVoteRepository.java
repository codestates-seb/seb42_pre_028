package Preproject28.server.answerVote.repository;

import Preproject28.server.answer.entity.Answer;
import Preproject28.server.answerVote.entity.AnswerVote;
import Preproject28.server.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerVoteRepository extends JpaRepository<AnswerVote, Long> {
    Optional<AnswerVote> findByAnswerAndMember(Answer answer, Member member);
}
