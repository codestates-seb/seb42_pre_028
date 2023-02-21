package Preproject28.server.questionVote.repository;

import Preproject28.server.questionVote.entity.QuestionVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionVoteRepository extends JpaRepository<QuestionVote,Long> {

}
