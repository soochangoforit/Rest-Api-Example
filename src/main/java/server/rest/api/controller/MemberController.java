package server.rest.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import server.rest.api.domain.Member;
import server.rest.api.exception.UserNotFoundException;
import server.rest.api.service.UserDaoService;

import java.net.URI;
import java.util.List;

@RestController
public class MemberController {

    private final UserDaoService userDaoService;

    @Autowired
    public MemberController(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    @GetMapping("/members")
    public List<Member> retrieveAllUsers() {

        return userDaoService.findAll();
    }

    @GetMapping("/members/{id}")
    public Member retrieveUser(@PathVariable int id) {

        Member member = userDaoService.findOne(id);

        if (member == null) {
            throw new UserNotFoundException(String.format("Id[%s] not found", id));
        }
        return member;
    }

    @PostMapping("/members")
    public ResponseEntity<Member> createUser(@RequestBody Member member) {
        Member saved = userDaoService.save(member);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest() // 현재 가지고 있는 request 값을 사용할 예정이다.
                .path("/{id}") // 현재 request에다가 추가적으로 해당 path를 연결시켜준다.
                .buildAndExpand(saved.getId()) // 연결시켜준 id값에 값을 할당시킨다.0
                .toUri();// uri로 만든다.

        // 서버측에서 사용자의 고유 id에 대해서 성성되는것이고, 이것을 요청한 client에 path_varaiable로써 반환을 하면 상세 조회하기 위한
        // 아이디를 찾는 트래픽이 감소하여 좋은 설계라고 할 수 있다.

        return ResponseEntity.created(location).build(); // body에 데이터가 담기는건 아니다. uri는 사용자로 부터 해당 uri로 redirect가 아닌 그냥 uri 정보만 header에 넘겨준다.

    }

    @DeleteMapping("/members/{id}")
    public void deleteUser(@PathVariable int id) {

        Member member = userDaoService.deleteById(id);

        // 존재하지 않는다는 의미이다.
        if (member == null) {
            throw new UserNotFoundException(String.format("Id[%s] not found", id));
        }
    }



}
