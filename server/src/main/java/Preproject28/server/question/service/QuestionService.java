package Preproject28.server.question.service;


import Preproject28.server.answer.entity.Answer;
import Preproject28.server.answer.service.AnswerService;
import Preproject28.server.error.exception.BusinessLogicException;
import Preproject28.server.error.exception.ExceptionCode;
import Preproject28.server.member.entity.Member;
import Preproject28.server.question.entity.Question;
import Preproject28.server.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerService answerService;

    public Question createQuestion(Question question) {
        //if 토큰에서 멤버 아이디 받아오기

        return questionRepository.save(question);
    }

    public Question updateQuestion(Question question) {
        Question findQuestion = findQuestion(question.getQuestionId());
        Optional.ofNullable(question.getAnswers()).ifPresent(findQuestion::setAnswers);
        Optional.ofNullable(question.getTitle()).ifPresent(findQuestion::setTitle);
        Optional.ofNullable(question.getProblemBody()).ifPresent(findQuestion::setProblemBody);
        Optional.ofNullable(question.getExpectingBody()).ifPresent(findQuestion::setExpectingBody);
        return questionRepository.save(findQuestion);
    }

    public boolean deleteQuestion(long questionId, Member member) {
        //내가쓴 질문글중에 지워야하는 게시글 id가 있으면 삭제
        List<Question> questions = member.getQuestions();
        for (Question question : questions) {
            long findQuestionId = question.getQuestionId();
            if (findQuestionId == questionId) questionRepository.deleteById(findQuestionId);
        }
        //질문글 검색해서 값이 없으면 성공
        Optional<Question> deleteQuestion = questionRepository.findById(questionId);
        return deleteQuestion.isEmpty();
    }

    public Page<Question> findQuestions(int page, int size) {
        return questionRepository.findAll(PageRequest.of(page, size, Sort.by("questionId").descending()));

    }

    public Question findQuestion(long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);

        return optionalQuestion.orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
    }

    public void setViewCount(Question question) {
        question.setViewCount(question.getViewCount() + 1);
        questionRepository.save(question);
    }

    public void adoptAnswer(long questionId, Answer answer, Member member) {

        Question question = checkTheAuthorOfTheQuestion(member, questionId);         //질문글 등록한사람 본인이 답변채택 할수있어야함.
        if (question.getAdoptAnswerId() == 0L) { //만약 채택되있는게 없으면 신청한걸로 채택
            question.setAdoptAnswerId(answer.getAnswerId());
            answer.setAdoptStatus(Answer.AdoptStatus.TRUE);
            log.info("처음 채택시 그냥 채택");
        } else if (question.getAdoptAnswerId() != answer.getAnswerId()) { // 다른것이 채택되어있으면, 기존꺼 취소후 신청한걸로 채택
            Answer oldAdoptAnswer = answerService.findAnswer(question.getAdoptAnswerId()); // 기존꺼 답변 채택취소로변경
            oldAdoptAnswer.setAdoptStatus(Answer.AdoptStatus.FALSE); // 기존꺼 답변 채택취소로변경
            question.setAdoptAnswerId(answer.getAnswerId()); //새로운거 채택
            answer.setAdoptStatus(Answer.AdoptStatus.TRUE); //새로운거 채택
            log.info("다른거 채택되있을시 기존값 변경후 채택");
        } else if (question.getAdoptAnswerId() == answer.getAnswerId()) { // 채택된것을 다시 채택을 누르면 채택취소되게
            question.setAdoptAnswerId(0L); // 질문에는 아무것도 없는상태로
            answer.setAdoptStatus(Answer.AdoptStatus.FALSE); //답변글을  취소로 변경
            log.info("채택 두번시 채택취소");
        }
        questionRepository.save(question);
    }

    private Question checkTheAuthorOfTheQuestion(Member member, long questionId) {
        List<Question> questions = member.getQuestions();

        for (Question question : questions) {
            long findQuestionId = question.getQuestionId();
            if (findQuestionId == questionId) return question;
        }
        log.warn("게시글 쓴사람이 아닌데 채택요청함");
        throw new BusinessLogicException(ExceptionCode.NOT_IMPLEMENTATION);
    }
}
