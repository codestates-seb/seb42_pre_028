package Preproject28.server.answer.mapper;

import Preproject28.server.answer.dto.AnswerInfoResponseDto;
import Preproject28.server.answer.dto.AnswerPatchDto;
import Preproject28.server.answer.dto.AnswerPostDto;
import Preproject28.server.answer.dto.AnswerResponseDto;
import Preproject28.server.answer.entity.Answer;
import Preproject28.server.member.dto.response.MemberInfoResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnswerMapper {
    Answer answerPostDtoToAnswer(AnswerPostDto answerPostDto);
    Answer answerPatchDtoToAnswer(AnswerPatchDto answerPatchDto);
    AnswerResponseDto answerToAnswerResponseDto(Answer answer);
    List<AnswerResponseDto> answerToAnswerResponseDtos(List<Answer> answers);
    List<AnswerInfoResponseDto> answerToAnswerInfoResponseDtos(List<Answer> answers);

    //회원&질문 아이디값만 보내게끔 수동으로 추가
    default AnswerInfoResponseDto answerToAnswerInfoResponseDto(Answer answer) {
        if ( answer == null ) {
            return null;
        }
        AnswerInfoResponseDto.AnswerInfoResponseDtoBuilder answerInfoResponseDto = AnswerInfoResponseDto.builder();

        answerInfoResponseDto.answerId( answer.getAnswerId() );
        List<String> list = answer.getContent();
        if ( list != null ) {
            answerInfoResponseDto.content( new ArrayList<>( list ) );
        }

        MemberInfoResponseDto memberInfoResponseDto = new MemberInfoResponseDto();

        memberInfoResponseDto.setMemberId( answer.getMember().getMemberId() );
        memberInfoResponseDto.setDisplayName( answer.getMember().getDisplayName() );
        memberInfoResponseDto.setEmail( answer.getMember().getEmail() );
        memberInfoResponseDto.setCreatedAt( answer.getMember().getCreatedAt() );

        answerInfoResponseDto.questionId( answer.getQuestion().getQuestionId() ); // 수동추가
        answerInfoResponseDto.member( memberInfoResponseDto ); // 수동추가
        answerInfoResponseDto.voteCount( (int) answer.getVoteCount() );
        answerInfoResponseDto.createdAt( answer.getCreatedAt() );
        answerInfoResponseDto.modifiedAt( answer.getModifiedAt() );
        answerInfoResponseDto.adoptStatus( answer.getAdoptStatus() );

        return answerInfoResponseDto.build();
    }

}
