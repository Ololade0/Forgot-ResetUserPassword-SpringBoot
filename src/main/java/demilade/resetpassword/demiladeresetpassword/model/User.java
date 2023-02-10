package demilade.resetpassword.demiladeresetpassword.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Document
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String userId;
    private String name;
    private String email;
    private String password;
    private String passwordResetToken;


    @DBRef
    public Set<Role> roleHashSet = new HashSet<>();

    public User(String name, String email, String phonenumber, String password, RoleType roleType) {
        this.name = name;
        this.email = email;

        this.password = password;
        if (roleHashSet == null) {
            roleHashSet = new HashSet<>();
            roleHashSet.add(new Role(roleType));

        }

    }


}
