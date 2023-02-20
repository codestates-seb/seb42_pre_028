package Preproject28.server.member.service;

import Preproject28.server.exception.BusinessLogicException;
import Preproject28.server.exception.ExceptionCode;
import Preproject28.server.member.entity.Member;
import Preproject28.server.member.repository.MemberRepository;
import Preproject28.server.security.auths.utils.CustomAuthorityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;

    public Member createMember(Member member) {
        member.setPassword(passwordEncoding(member.getPassword()));
        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);
        return memberRepository.save(member);
    }

    public Member updateMember(Member member) {
        Member findMember = findverifiedMember(member.getMemberId());
        Optional.ofNullable(member.getDisplayName()).ifPresent(findMember::setDisplayName);
        Optional.ofNullable(member.getPassword()).ifPresent(password-> findMember.setPassword(passwordEncoding(password)));
        Optional.ofNullable(member.getIconImageUrl()).ifPresent(findMember::setIconImageUrl);

        return findMember;
    }

    public Member findMember(long memberId) {
        return findverifiedMember(memberId);
    }

    public void deleteMember(long memberId) {
        //회원삭제시 본인인지 검증할 코드가 필요함
        memberRepository.deleteById(memberId);
    }

    private Member findverifiedMember(long memberId) {
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        return memberOptional.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }
    private String passwordEncoding(String password) {
        return passwordEncoder.encode(password);
    }
}
