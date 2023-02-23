package Preproject28.server.error.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, ",Member not found"),
    MEMBER_EXISTS(409, "Member exists"),
    QUESTION_NOT_FOUND(404, "Question not found"),
   QUESTION_EXISTS(404, "Question exists"),
    ANSWER_NOT_FOUND(404, "Answer not found"),
    ANSWER_CHANGE_ORDER(403, "Answer exists"),
    NOT_IMPLEMENTATION(501, "No Implementation"),
    INVALID_MEMBER_STATUS(400, "Invalid member status");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
