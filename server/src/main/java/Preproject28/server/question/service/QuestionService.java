package Preproject28.server.question.service;


import Preproject28.server.error.exception.BusinessLogicException;
import Preproject28.server.error.exception.ExceptionCode;
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
        Optional.ofNullable(question.getAnswers())
                .ifPresent(answers -> findQuestion.setAnswers(answers));
        Optional.ofNullable(question.getTitle())
                .ifPresent(title -> findQuestion.setTitle(title));
        Optional.ofNullable(question.getProblemBody())
                .ifPresent(problemBody -> findQuestion.setProblemBody(problemBody));
        Optional.ofNullable(question.getExpectingBody())
                .ifPresent(expectingBody -> findQuestion.setExpectingBody(expectingBody));
        Optional.of(question.getViewCount())
                .ifPresent(viewCount -> findQuestion.setViewCount(viewCount));
        Optional.of(question.getVoteCount())
                .ifPresent(voteCount ->findQuestion.setVoteCount(voteCount));
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
