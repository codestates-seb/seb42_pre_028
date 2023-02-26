package Preproject28.server.question.dto;


import Preproject28.server.member.dto.MemberInfoResponseDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class QuestionInfoResponseDto {
    private Long questionId;
    private String title;
    private List<String> problemBody;
    private List<String> expectingBody;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int viewCount;
    private int voteCount;
    private MemberInfoResponseDto member;
    private List<String> tag;
}
