package Preproject28.server.questionVote.repository;

import Preproject28.server.answer.entity.Answer;
import Preproject28.server.answerVote.entity.AnswerVote;
import Preproject28.server.member.entity.Member;
import Preproject28.server.question.entity.Question;
import Preproject28.server.questionVote.entity.QuestionVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionVoteRepository extends JpaRepository<QuestionVote,Long> {
    Optional<QuestionVote> findByQuestionAndMember(Question question, Member member);

}
