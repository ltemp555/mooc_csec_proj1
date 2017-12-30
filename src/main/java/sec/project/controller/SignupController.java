package sec.project.controller;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {

    @Autowired
    private SignupRepository signupRepository;

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        return "form";
    }

    @Transactional
    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String address, 
                             @RequestParam String password, @RequestParam String additional) {
        List<Signup> users = signupRepository.findAll();
        int max = 0;
        for (Signup tmp : users) {
            if (tmp.getUserid() > max) {
                max = tmp.getUserid();
            }
        }
        max = max + 1;
        signupRepository.save(new Signup(name, address, password, max, additional));
        return "done";
    }

}
