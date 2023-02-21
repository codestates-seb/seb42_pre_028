package Preproject28.server.questionVote.entity;

import Preproject28.server.member.entity.Member;
import Preproject28.server.question.entity.Question;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class QuestionVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionVoteId;
    private VoteStatus voteStatus = VoteStatus.NONE;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    enum VoteStatus{
        UP,
        NONE,
        DOWN;
    }
}
