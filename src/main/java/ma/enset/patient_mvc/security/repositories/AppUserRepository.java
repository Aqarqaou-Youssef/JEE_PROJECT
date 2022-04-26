package ma.enset.patient_mvc.security.repositories;

import ma.enset.patient_mvc.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository <AppUser,String> {

    AppUser findByUsername(String username);
}
