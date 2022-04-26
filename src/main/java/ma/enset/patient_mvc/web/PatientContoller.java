package ma.enset.patient_mvc.web;

import lombok.AllArgsConstructor;
import ma.enset.patient_mvc.entities.Patient;
import ma.enset.patient_mvc.repositories.PatientRepositorie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.Binding;
import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class PatientContoller {

    private PatientRepositorie patientRepositorie;
@GetMapping(path = "/user/index")
    public  String patients(Model model ,
                           @RequestParam (name ="page" ,defaultValue ="0") int page ,
                           @RequestParam (name ="size" ,defaultValue ="5") int size,
                           @RequestParam (name ="keyword",defaultValue = "")String keyword){
    Page<Patient> pagePatients=patientRepositorie.findByNomContains(keyword,PageRequest.of(page,size));
    model.addAttribute("listPatients",pagePatients.getContent());
    model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
    model.addAttribute("currentPage",page);
    model.addAttribute("keyword",keyword);
        return "patients";
    }
@GetMapping("/admin/delete")
    public String delete(Long id,String keyword ,int page ){
        patientRepositorie.deleteById(id);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/")
    public String home(){
        return "home";
    }

    //c'est pour avoir la liste des patients format json
    @GetMapping("/user/patients")
    @ResponseBody
    public List<Patient> listPatients(){
    return patientRepositorie.findAll();
    }

    @GetMapping("/admin/formPatients")
    public String formPatients(Model model){
    model.addAttribute("patient",new Patient());
        return "/formPatients";
    }
    @GetMapping("/admin/editPatients")
    public String editPatients(Model model,Long id ,String keyword ,int page){
    Patient patient = patientRepositorie.findById(id).orElse(null);
    if (patient==null) throw new RuntimeException("Patient introuvable");
        model.addAttribute("patient",patient);
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);
        return "/editPatients";
    }

//validaion
    @PostMapping(path = "/admin/save")
    public String formPatients(Model model, @Valid Patient patient, BindingResult bindingResult ,
                               @RequestParam (defaultValue = "0") int page ,
                               @RequestParam (defaultValue = "")String keyword){
    if(bindingResult.hasErrors()) return "/formPatients";
        patientRepositorie.save(patient);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }
}
