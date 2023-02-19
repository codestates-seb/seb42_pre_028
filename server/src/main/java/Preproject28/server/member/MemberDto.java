package Preproject28.server.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class MemberDto {

    @Getter
    @AllArgsConstructor
    public static class Post{
        private String displayName;
        private String email;
        private String password;

    }

    @Getter @Setter
    @AllArgsConstructor
    public static class Response{
        private Long memberId;
        private String displayName;
        private String email;
        private String password;

        public Response() {
        }
    }

}
