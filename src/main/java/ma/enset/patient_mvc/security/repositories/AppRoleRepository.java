package ma.enset.patient_mvc.security.repositories;

import ma.enset.patient_mvc.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository <AppRole,Long> {

    AppRole findByRoleName(String rolename);
}
