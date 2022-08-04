package server.rest.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import server.rest.api.domain.Member;
import server.rest.api.exception.UserNotFoundException;
import server.rest.api.repository.MemberRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jpa")
public class MemberJpaController {

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/members")
    public List<Member> retrieveAllMembers() {
        return memberRepository.findAll();
    }

    @GetMapping("/members/{id}")
    public Member findMemberById(@PathVariable Long id) {
        Optional<Member> member = memberRepository.findById(id);

        if (member.isEmpty()) {
            throw new UserNotFoundException("id-" + id);
        }

        return member.get();
    }

    @PostMapping("/members")
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        Member saved = memberRepository.save(member);

        // client로 반환되는 uri header에 해당 사용자를 찾을 수 있는 주소값을 넣어준다.
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saved.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/members/{id}")
    public void deleteMember(@PathVariable Long id) {
        memberRepository.deleteById(id);
    }





}
