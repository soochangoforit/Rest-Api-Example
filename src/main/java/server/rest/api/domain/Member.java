package server.rest.api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter @Setter @NoArgsConstructor
public class Member {

    private Integer id;
    private String name;
    private Date joinDate = new Date();

}
