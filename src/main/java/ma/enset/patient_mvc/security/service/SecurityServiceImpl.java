package ma.enset.patient_mvc.security.service;

import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import ma.enset.patient_mvc.security.entities.AppRole;
import ma.enset.patient_mvc.security.entities.AppUser;
import ma.enset.patient_mvc.security.repositories.AppRoleRepository;
import ma.enset.patient_mvc.security.repositories.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j //pour avoir un attribut qui permet de logée les informations
@AllArgsConstructor
@Transactional
public class SecurityServiceImpl implements SecurityService {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser saveNewUser(String username, String password, String rePassword) {
        if(!password.equals(rePassword)) throw new RuntimeException("passwords not match");
        String hashedPWD=passwordEncoder.encode(password);
        AppUser appUser= new AppUser();
        appUser.setUserId(UUID.randomUUID().toString());
        appUser.setUsername(username);
        appUser.setPassword(hashedPWD);
        appUser.setActive(true);
        AppUser savedAppUser = appUserRepository.save(appUser);
        return savedAppUser;
    }

    @Override
    public AppRole saveNewRole(String roleName, String description) {
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        if(appRole!=null) throw  new RuntimeException("Role" +roleName+"Already exist");
        appRole = new AppRole();
        appRole.setRoleName(roleName);
        appRole.setDescription(description);
        AppRole savedAppRole= appRoleRepository.save(appRole);
        return savedAppRole;
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser =appUserRepository.findByUsername(username);
        if(appUser==null) throw  new RuntimeException("user not found");
        AppRole appRole =appRoleRepository.findByRoleName(roleName);
        if(appRole==null) throw  new RuntimeException("role not found");
        appUser.getAppRoles().add(appRole);

    }

    @Override
    public void removeRoleFromUser(String username, String roleName) {

        AppUser appUser =appUserRepository.findByUsername(username);
        if(appUser==null) throw  new RuntimeException("user not found");
        AppRole appRole =appRoleRepository.findByRoleName(roleName);
        if(appRole==null) throw  new RuntimeException("role not found");
        appUser.getAppRoles().remove(appRole);

    }

    @Override
    public AppUser loadUserByUserName(String username) {
        return appUserRepository.findByUsername(username);
    }
}
