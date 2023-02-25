package Preproject28.server.question.service;


import Preproject28.server.error.exception.BusinessLogicException;
import Preproject28.server.error.exception.ExceptionCode;
import Preproject28.server.member.entity.Member;
import Preproject28.server.question.entity.Question;
import Preproject28.server.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Question createQuestion(Question question){
        //if 토큰에서 멤버 아이디 받아오기

        return questionRepository.save(question);
    }

    public Question updateQuestion(Question question){
        Question findQuestion = findQuestion(question.getQuestionId());
        Optional.ofNullable(question.getAnswers())
                .ifPresent(findQuestion::setAnswers);
        Optional.ofNullable(question.getTitle())
                .ifPresent(findQuestion::setTitle);
        Optional.ofNullable(question.getProblemBody())
                .ifPresent(findQuestion::setProblemBody);
        Optional.ofNullable(question.getExpectingBody())
                .ifPresent(findQuestion::setExpectingBody);
        return findQuestion;
    }
    public boolean deleteQuestion(long questionId, Member member){
        //내가쓴 질문글중에 지워야하는 게시글 id가 있으면 삭제
        List<Question> questions = member.getQuestions();
        for(Question question: questions) {
            long findQuestionId = question.getQuestionId();
            if(findQuestionId == questionId) questionRepository.deleteById(findQuestionId);
        }
        //질문글 검색해서 값이 없으면 성공
        Optional<Question> deleteQuestion = questionRepository.findById(questionId);
        return deleteQuestion.isEmpty();
    }
    public Page<Question> findQuestions(int page, int size){
        return questionRepository.findAll(PageRequest.of(page, size, Sort.by("questionId").descending()));

    }
    public Question findQuestion(long questionId){
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);

        return optionalQuestion.orElseThrow(()-> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
    }


}
