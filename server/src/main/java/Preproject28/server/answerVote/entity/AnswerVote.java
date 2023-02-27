package Preproject28.server.answerVote.entity;

import Preproject28.server.answer.entity.Answer;
import Preproject28.server.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class AnswerVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerVoteId;
    @ManyToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;
    @ManyToOne
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;

    public AnswerVote(Answer answer, Member member, VoteStatus voteStatus) {
        this.answer = answer;
        this.member = member;
        this.voteStatus = voteStatus;
    }

    @Enumerated(EnumType.STRING)
    private VoteStatus voteStatus;

    public enum VoteStatus{
        UP,
        NONE,
        DOWN;

    }
}
