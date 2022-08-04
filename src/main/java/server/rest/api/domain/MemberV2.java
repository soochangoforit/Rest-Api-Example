package server.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@AllArgsConstructor
@Getter @Setter @NoArgsConstructor
@JsonFilter("UserInfoV2") // client에게 민감한 정보는 반환하지 않기 위해서 json filter 사용 -> controller에서 만든 필터에서 적용할 이름으로 사용됨
public class MemberV2 extends Member{
    private String grade;
}
