package server.rest.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.rest.api.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

}
