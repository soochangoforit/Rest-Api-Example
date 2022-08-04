package server.rest.api.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import server.rest.api.domain.Member;
import server.rest.api.domain.MemberV2;
import server.rest.api.exception.UserNotFoundException;
import server.rest.api.service.UserDaoService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminMemberController {

    private final UserDaoService userDaoService;

    @Autowired
    public AdminMemberController(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    @GetMapping("/v1/members")
    public MappingJacksonValue retrieveAllUsers() {

        List<Member> members = userDaoService.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id" , "name" , "joinDate" , "ssn"); // 해당 필드를 제외하고 나머지는 다 제외 된다.

        // 위에서 만든 필터를 사용가능한 형태로 변경하기 위해서
        // 필터가 추가될때는 , 이 필터가 어떤 Bean을 대상으로 하는 필터인지 명시를 해야한다.
        // User Class로 가보면 UserInfo라고 명시한게 있다.
        // 첫번째 매개변수로 filter를 적용하고자 하는 Bean 대상을 넣어준다.
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        // 반환될때 member가 그대로 client 측으로 반환되는것이 아니라 , filter에 의해서 필터링된 데이터만 반환하고자 한다.
        MappingJacksonValue mapping = new MappingJacksonValue(members);
        mapping.setFilters(filters);

        return mapping;
    }

    @GetMapping("/v1/members/{id}")
    public MappingJacksonValue retrieveUserV1(@PathVariable int id) {

        Member member = userDaoService.findOne(id);

        if (member == null) {
            throw new UserNotFoundException(String.format("Id[%s] not found", id));
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id" , "name" , "joinDate" , "ssn"); // 해당 필드를 제외하고 나머지는 다 제외 된다.

        // 위에서 만든 필터를 사용가능한 형태로 변경하기 위해서
        // 필터가 추가될때는 , 이 필터가 어떤 Bean을 대상으로 하는 필터인지 명시를 해야한다.
        // User Class로 가보면 UserInfo라고 명시한게 있다.
        // 첫번째 매개변수로 filter를 적용하고자 하는 Bean 대상을 넣어준다.
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        // 반환될때 member가 그대로 client 측으로 반환되는것이 아니라 , filter에 의해서 필터링된 데이터만 반환하고자 한다.
        MappingJacksonValue mapping = new MappingJacksonValue(member);
        mapping.setFilters(filters);

        return mapping;
    }

    @GetMapping("/v2/members/{id}")
    public MappingJacksonValue retrieveUserV2(@PathVariable int id) {

        Member member = userDaoService.findOne(id);

        if (member == null) {
            throw new UserNotFoundException(String.format("Id[%s] not found", id));
        }

        // 기본 member가 가지고 있던 데이터를 memberV2로 바꾸고자 한다.
        MemberV2 memberV2 = new MemberV2();
        BeanUtils.copyProperties(member, memberV2); // 필드 복사
        memberV2.setGrade("VIP");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id" , "name" , "joinDate" , "grade"); // 해당 필드를 제외하고 나머지는 다 제외 된다.

        // 위에서 만든 필터를 사용가능한 형태로 변경하기 위해서
        // 필터가 추가될때는 , 이 필터가 어떤 Bean을 대상으로 하는 필터인지 명시를 해야한다.
        // User Class로 가보면 UserInfo라고 명시한게 있다.
        // 첫번째 매개변수로 filter를 적용하고자 하는 Bean 대상을 넣어준다.
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        // 반환될때 member가 그대로 client 측으로 반환되는것이 아니라 , filter에 의해서 필터링된 데이터만 반환하고자 한다.
        MappingJacksonValue mapping = new MappingJacksonValue(memberV2);
        mapping.setFilters(filters);

        return mapping;
    }


}
