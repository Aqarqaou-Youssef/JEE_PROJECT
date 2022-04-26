package ma.enset.patient_mvc;

import ma.enset.patient_mvc.entities.Patient;
import ma.enset.patient_mvc.repositories.PatientRepositorie;
import ma.enset.patient_mvc.security.service.SecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class PatientMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientMvcApplication.class, args);
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//@Bean cest pour l'executer au moment de demarage
//@Bean
    CommandLineRunner commandLineRunner(PatientRepositorie patientRepositorie){
        return args -> {
            patientRepositorie.save(new Patient(null,"youssef",new Date(),false,450));
            patientRepositorie.save(new Patient(null,"janate",new Date(),true,240));
            patientRepositorie.save(new Patient(null,"the_killer",new Date(),true,510));
            patientRepositorie.save(new Patient(null,"tokyo",new Date(),false,550));
            patientRepositorie.findAll().forEach(p->{
                System.out.println(p.getNom());
                System.out.println(p.getDateNaissance());
                System.out.println(p.getScore());
                System.out.println(p.isMalade());
            });
        };


    }

    //@Bean
    CommandLineRunner saveUsers(SecurityService securityService){
    return args -> {
        securityService.saveNewUser("youssef","1234","1234");
        securityService.saveNewUser("thekiller","1234","1234");
        securityService.saveNewUser("youyou","1234","1234");

        securityService.saveNewRole("USER","");
        securityService.saveNewRole("ADMIN","");

        securityService.addRoleToUser("youssef" ,"USER");
        securityService.addRoleToUser("youssef" ,"ADMIN");
        securityService.addRoleToUser("thekiller" ,"USER");
        securityService.addRoleToUser("youyou" ,"USER");
    };
    }

}
