package Preproject28.server.Question.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class QuestionResponseDto {
    private Long questionId;


    private String title;


    private String problemBody;


    private String expectingBody;


    private LocalDateTime createdAt;


    private LocalDateTime modifiedAt;


    private long viewCount;


    private long voteCount;


    private long memberId;


    private String answers;
}
