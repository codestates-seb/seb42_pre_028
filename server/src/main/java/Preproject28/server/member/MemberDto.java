package Preproject28.server.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class MemberDto {

    @Getter
    @AllArgsConstructor
    public static class Post{
        private Long memberId;
        private String name;
        private String email;
        private String password;

    }

    @Getter
    @AllArgsConstructor
    public static class Response{
        private Long memberId;
        private String name;
        private String email;
    }

}
