package Preproject28.server.answer.dto;


import Preproject28.server.member.entity.Member;
import Preproject28.server.question.entity.Question;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerResponseDto {
    private Long answerId;

    private List<String> content;

    private int voteCount;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private boolean adoptStatus;

//    private long questionId;

    private long memberId;


}
