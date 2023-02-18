package Preproject28.server.member;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberMapper mapper;
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity postMember(@RequestBody MemberDto.Post requestBody) {
        Member member = mapper.memberPostDtoToMember(requestBody);
        Member createdMember = memberService.createMember(member);
        MemberDto.Response response = mapper.memberToMemberResponse(createdMember);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public String getMember(){
        return "member로 들어왔음";
    }
}
