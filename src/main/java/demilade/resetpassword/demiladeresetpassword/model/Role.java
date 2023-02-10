package demilade.resetpassword.demiladeresetpassword.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document

public class Role {
    @Id
    private String id;
    @DBRef
    private RoleType roleType;

    public Role(RoleType roleType){
        this.roleType = roleType;
    }

}
