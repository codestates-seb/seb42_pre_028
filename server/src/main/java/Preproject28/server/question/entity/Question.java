package Preproject28.server.question.entity;

import Preproject28.server.answer.entity.Answer;
import Preproject28.server.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    private String title;

    private String problemBody;

    private String expectingBody;
    //리스트로 변경.

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    private long viewCount;

    private long voteCount;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;

    @ManyToOne
    @JoinColumn(name = "answer_id")
    @JsonBackReference
    private Answer answers;

    //문자열 배열로 태그 구현

//    @OneToMany(mappedBy = "question")
//    @JsonManagedReference
//    private List<Answer> answers = new ArrayList<>();




}
