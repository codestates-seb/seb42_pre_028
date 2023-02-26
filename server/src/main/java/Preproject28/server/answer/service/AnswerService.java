package Preproject28.server.answer.service;


import Preproject28.server.answer.entity.Answer;
import Preproject28.server.answer.repository.AnswerRepository;
import Preproject28.server.error.exception.BusinessLogicException;
import Preproject28.server.error.exception.ExceptionCode;
import Preproject28.server.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
                .ifPresent(findAnswer::setContent);
        return answerRepository.save(findAnswer);
    }
    public boolean deleteAnswer(long answerId, Member member) {
        //내가쓴 답변글중 지워야하는 게시글 id가 있으면 삭제
        List<Answer> answers = member.getAnswers();
        for(Answer answer: answers) {
            long findAnswerId = answer.getAnswerId();
            if(findAnswerId == answerId) answerRepository.deleteById(findAnswerId);
        }
        //답변글 검색해서 값이 없으면 성공
        Optional<Answer> deleteQuestion = answerRepository.findById(answerId);
        return deleteQuestion.isEmpty();
    }
    public Page<Answer> findAnswers(int page, int size) {
        return answerRepository.findAll(PageRequest.of(page, size, Sort.by("questionId").descending()));

    }
    public Answer findAnswer(long answerId) {
        Optional<Answer> optionalQuestion = answerRepository.findById(answerId);

        return optionalQuestion.orElseThrow(()-> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
    }

    public void adoptAnswer(long answerId) {
        Answer answer = findAnswer(answerId);

    }

}
