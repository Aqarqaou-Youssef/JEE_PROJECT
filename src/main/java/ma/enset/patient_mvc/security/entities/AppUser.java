package ma.enset.patient_mvc.security.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.patient_mvc.security.entities.AppRole;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    @Id
    private String userId;
    @Column(unique = true)
    private String username;
    private String password;
    private boolean active;
    @ManyToMany(fetch = FetchType.EAGER) //si on charge un user on charge tout ses roles aussi
    private List<AppRole> appRoles=new ArrayList<>();
}
