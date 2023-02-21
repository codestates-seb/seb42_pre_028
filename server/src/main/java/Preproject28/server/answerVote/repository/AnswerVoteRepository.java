package Preproject28.server.answerVote.repository;

import Preproject28.server.answerVote.entity.AnswerVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerVoteRepository extends JpaRepository<AnswerVote, Long> {
}
