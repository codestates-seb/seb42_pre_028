package Preproject28.server.answerVote.entity;

import Preproject28.server.answer.entity.Answer;
import Preproject28.server.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class AnswerVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerVoteId;
    private VoteStatus voteStatus = VoteStatus.NONE;
    @ManyToOne
    @JoinColumn(name = "answer_answer_id")
    private Answer answer;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    enum VoteStatus{
        UP,
        NONE,
        DOWN;
    }
}
