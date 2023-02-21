package Preproject28.server.answer.service;


import Preproject28.server.answer.entity.Answer;
import Preproject28.server.answer.repository.AnswerRepository;
import Preproject28.server.exception.BusinessLogicException;
import Preproject28.server.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public Answer createAnswer(Answer answer){
        return answerRepository.save(answer);
    }

    public Answer updateAnswer(Answer answer){
        Answer findAnswer = findAnswer (answer.getAnswerId());
        Optional.ofNullable(answer.getContent()).ifPresent(findAnswer::setContent);
        Optional.ofNullable(answer.getMember()).ifPresent(findAnswer::setMember);
        Optional.of(answer.getVoteCount()).ifPresent(findAnswer::setVoteCount);
        return findAnswer;
    }
    public void deleteAnswer(long answerId){
        Answer answer = findAnswer(answerId);
        answerRepository.deleteById(answerId);

    }
    public Page<Answer> findAnswers(int page, int size){
        return answerRepository.findAll(PageRequest.of(page, size, Sort.by("questionId").descending()));

    }
    public Answer findAnswer(long answerId){
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        Answer findAnswer = optionalAnswer.orElseThrow(()-> new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));

        return findAnswer;
    }


}
