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
        Answer findAnswer = findAnswer(answer.getAnswerId());
        Optional.ofNullable(answer.getContent())
                .ifPresent(content -> findAnswer.setContent(content));
        Optional.ofNullable(answer.getVoteCount())
                .ifPresent(voteCount -> answer.setVoteCount(voteCount));
        Optional.ofNullable(answer.getMember())
                .ifPresent(member -> answer.setMember(member));
        Optional.ofNullable(answer.getContent())
                .ifPresent(content -> findAnswer.setContent(content));
        Optional.ofNullable(answer.getModifiedAt())
                .ifPresent(Modified -> findAnswer.setModifiedAt(Modified));
;



        return findAnswer;
    }
    public void deleteAnswer(long QId){
        Answer answer = findAnswer(QId);
        answerRepository.deleteById(QId);

    }
    public Page<Answer> findAnswers(int page, int size){
        return answerRepository.findAll(PageRequest.of(page, size, Sort.by("questionId").descending()));

    }
    public Answer findAnswer(long QId){
        Optional<Answer> optionalQuestion = answerRepository.findById(QId);
        Answer findAnswer = optionalQuestion.orElseThrow(()-> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));

        return findAnswer;
    }


}
