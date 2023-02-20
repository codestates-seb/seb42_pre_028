package Preproject28.server.question.service;


import Preproject28.server.exception.BusinessLogicException;
import Preproject28.server.exception.ExceptionCode;
import Preproject28.server.question.entity.Question;
import Preproject28.server.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Question createQuestion(Question question){
        return questionRepository.save(question);
    }

    public Question updateQuestion(Question question){
        Question findQuestion = findQuestion(question.getQuestionId());
        Optional.ofNullable(question.getAnswers()).ifPresent(findQuestion::setAnswers);
        Optional.ofNullable(question.getTitle()).ifPresent(findQuestion::setTitle);
        Optional.ofNullable(question.getProblemBody()).ifPresent(findQuestion::setProblemBody);
        Optional.ofNullable(question.getExpectingBody()).ifPresent(findQuestion::setExpectingBody);
        Optional.of(question.getViewCount()).ifPresent(findQuestion::setViewCount);
        Optional.of(question.getVoteCount()).ifPresent(findQuestion::setViewCount);
        return findQuestion;
    }
    public void deleteQuestion(long QId){
        Question question = findQuestion(QId);
        questionRepository.deleteById(QId);

    }
    public Page<Question> findQuestions(int page, int size){
        return questionRepository.findAll(PageRequest.of(page, size, Sort.by("questionId").descending()));

    }
    public Question findQuestion(long QId){
        Optional<Question> optionalQuestion = questionRepository.findById(QId);
        Question findQuestion = optionalQuestion.orElseThrow(()-> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));

        return findQuestion;
    }


}
