package sec.project.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Controller
public class DetailsController {

    @Autowired
    private SignupRepository signupRepository;
    
    @Autowired
    private HttpSession session;
    
    @RequestMapping(value = "/details/{userid}", method = RequestMethod.GET)
    public String getDetails(Model model, @PathVariable int userid) {
        Signup details = signupRepository.findByUserid(userid);
        model.addAttribute("details", details);
        
        if (!model.containsAttribute("showDetails")) {
            model.addAttribute("showDetails", false);
        }
       
        return "details";
    }
   
    @RequestMapping(value = "/changepw", method = RequestMethod.POST)
    public String submitChangePw(@RequestParam String name, @RequestParam String oldpassword, 
                                 @RequestParam String newpassword) {
        Signup details = signupRepository.findByName(name);
        if (details == null) {
            System.out.println("User not found: "+name);
            return "redirect:/login";
        }
        String address = details.getAddress();
        int userid = details.getUserid();
        String additional = details.getAdditional();
        signupRepository.delete(details.getId());
        signupRepository.save(new Signup(name, address, newpassword, userid, additional));
        return "done";
    }
    
    @RequestMapping(value = "/updateadditional", method = RequestMethod.POST)
    public String submitUpdateAdditional(@RequestParam String name, @RequestParam String additional) {
        Signup details = signupRepository.findByName(name);
        if (details == null) {
            System.out.println("User not found: "+name);
            return "redirect:/login";
        }
        String address = details.getAddress();
        String password = details.getPassword();
        int userid = details.getUserid();
        signupRepository.delete(details.getId());
        signupRepository.save(new Signup(name, address, password, userid, additional));
        return "redirect:/details/"+userid;
    } 
    @Transactional
    @RequestMapping(value = "/showadditional", method = RequestMethod.POST)
    public String submitShowAdditional(@RequestParam String name, RedirectAttributes redirectAttributes) {
        Signup details = signupRepository.findByName(name);
        if (details == null) {
            System.out.println("User not found: "+name);
            return "redirect:/login";
        }
        redirectAttributes.addFlashAttribute("showDetails", true);
      
        String databaseAddress = "jdbc:h2:mem:testdb;DB_CLOSE_ON_EXIT=FALSE;DB_CLOSE_DELAY=-1";
        //String databaseAddress = "jdbc:h2:./db/testdb;DB_CLOSE_ON_EXIT=FALSE;DB_CLOSE_DELAY=-1";

        try {          
            
            Connection connection = DriverManager.getConnection(databaseAddress, "sa", ""); 
            
            String query = "SELECT * FROM SIGNUP WHERE name = '" + name + "'";

            System.out.println("Run query " + query);
            
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            String additional = new String();
            while (resultSet.next()) {
                additional = resultSet.getString("additional");
                System.out.println("For: " + resultSet.getString("name") + ", " + additional);
            }
            redirectAttributes.addFlashAttribute("additional", additional);

        } catch (Exception e) {
            System.err.println("Caught exception: " + e.getMessage());           
        }
        
        return "redirect:/details/"+details.getUserid();
    } 
}
