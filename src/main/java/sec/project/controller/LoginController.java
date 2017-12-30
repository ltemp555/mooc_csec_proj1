package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Controller
public class LoginController {

    @Autowired
    private SignupRepository signupRepository;

    @RequestMapping(value = "/login", method = RequestMethod.GET) 
    public String loadLogin() {
        return "login";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String password, RedirectAttributes redirectAttributes) {
        
        Signup details = signupRepository.findByName(name);
        if (details == null) {
            System.out.println("User does not exist: "+name);
            String msg = "Invalid user name";
            redirectAttributes.addFlashAttribute("errorStr", msg); 
            return "redirect:/login";
        }
        if (!details.getPassword().equals(password)) {
            System.out.println("Passwords do not match: " + password + ", " + details.getPassword());
            String msg = "Invalid password";
            redirectAttributes.addFlashAttribute("errorStr", msg); 
            return "redirect:/login";
        }
        int userid = details.getUserid();
        return "redirect:/details/"+userid;
    }
}
