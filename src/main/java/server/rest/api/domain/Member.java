package server.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@AllArgsConstructor
@Getter @Setter @NoArgsConstructor
@JsonFilter("UserInfo") // client에게 민감한 정보는 반환하지 않기 위해서 json filter 사용 -> controller에서 만든 필터에서 적용할 이름으로 사용됨
public class Member {

    private Integer id;

    @Size(min = 2 , message = "Name must be at least 2 characters long")
    private String name;

    @Past // 과거 데이터만 허용
    private Date joinDate = new Date();

    private String password;
    private String ssn; // 주민등록번호

}
