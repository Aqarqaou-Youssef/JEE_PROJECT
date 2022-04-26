package ma.enset.patient_mvc.repositories;

import ma.enset.patient_mvc.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepositorie extends JpaRepository<Patient,Long> {

    Page<Patient> findByNomContains(String kw, Pageable pageable);

}
