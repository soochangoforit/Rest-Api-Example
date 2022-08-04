package server.rest.api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@AllArgsConstructor
@Getter @Setter @NoArgsConstructor
public class Member {

    private Integer id;

    @Size(min = 2 , message = "Name must be at least 2 characters long")
    private String name;

    @Past // 과거 데이터만 허용
    private Date joinDate = new Date();

}
