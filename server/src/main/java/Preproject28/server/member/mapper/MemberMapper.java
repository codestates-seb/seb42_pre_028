package Preproject28.server.member.mapper;

import Preproject28.server.answer.entity.Answer;
import Preproject28.server.member.dto.*;
import Preproject28.server.member.dto.response.LoginMemberVoteInfo;
import Preproject28.server.member.dto.response.MemberInfoResponseDto;
import Preproject28.server.member.entity.Member;
import Preproject28.server.question.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {

    Member memberPostDtoToMember(MemberPostDto memberPostDto);
    Member memberPatchDtoToMember(MemberPatchDto memberPatchDto);
    @Mapping(source = "questions", target = "myQuestionCount", qualifiedByName = "countQuestions")
    @Mapping(source = "answers", target = "myAnswerCount", qualifiedByName = "countAnswers")
    MemberInfoResponseDto memberToMemberInfoResponse(Member member);
    @Named("countQuestions")
    default long countQuestions(List<Question> questions) { return questions.size(); }
    @Named("countAnswers")
    default long countAnswers(List<Answer> answers) { return answers.size(); }

    LoginMemberVoteInfo memberToLogInUserInfoResponseDto(Member member);

}
