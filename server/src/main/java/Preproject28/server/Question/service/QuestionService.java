package Preproject28.server.Question.service;


import Preproject28.server.Question.entity.Question;
import Preproject28.server.Question.repository.QuestionRepository;
import Preproject28.server.exception.BusinessLogicException;
import Preproject28.server.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question createQuestion(Question question){
        return questionRepository.save(question);
    }

    public Question updateQuestion(Question question){
        Question findQuestion = findQuestion(question.getQuestionId());
        Optional.ofNullable(question.getAnswers()).ifPresent(Answers -> findQuestion.setAnswers(Answers));
        Optional.ofNullable(question.getTitle()).ifPresent(Title -> findQuestion.setTitle(Title));
        Optional.ofNullable(question.getProblemBody()).ifPresent(PB -> findQuestion.setProblemBody(PB));
        Optional.ofNullable(question.getExpectingBody()).ifPresent(EB -> findQuestion.setExpectingBody(EB));
        Optional.ofNullable(question.getViewCount()).ifPresent(ViewCount -> findQuestion.setViewCount(ViewCount));
        Optional.ofNullable(question.getVoteCount()).ifPresent(VoteCount -> findQuestion.setViewCount(VoteCount));
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
