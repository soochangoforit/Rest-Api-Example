package server.rest.api.service;

import org.springframework.stereotype.Service;
import server.rest.api.domain.Member;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


@Service
public class UserDaoService {

    private static List<Member> users = new ArrayList<>();

    private static Long usersCount = 3L;

    static {
        users.add(new Member(1L, "John", new Date() , "pass1", "123-45-6789"));
        users.add(new Member(2L, "Mary", new Date(), "pass2", "123-45-6789"));
        users.add(new Member(3L, "Mike", new Date(), "pass3", "123-45-6789"));
    }

    public List<Member> findAll() {
        return users;
    }

    public Member save(Member member) {
        if(member.getId() == null) {
            member.setId(++usersCount);
        }

        users.add(member);
        return member;
    }

    public Member findOne(int id) {
        for (Member user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public void deleteAll() {
        users.clear();
    }

    public Member deleteById(int id) {

        Iterator<Member> iterator = users.iterator();

        while (iterator.hasNext()) {
            Member member = iterator.next();

            if (member.getId().equals(id)) {
                iterator.remove();
                return member;
            }
        }

        return null; // 찾지 못한 경우
    }



}
