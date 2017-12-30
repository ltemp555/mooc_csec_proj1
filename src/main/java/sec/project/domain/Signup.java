package sec.project.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "SIGNUP")
public class Signup extends AbstractPersistable<Long> {

    @Column(unique=true, name="name")
    private String name;
    
    @Column(name="userid")
    private int userid;
    
    @Column(name="address")
    private String address;
    
    @Column(name="password")
    private String password;
    
    @Column(name="additional")
    private String additional;

    public Signup() {
        super();
    }

    public Signup(String name, String address, String password, int userid, String additional) {
        this();
        this.name = name;
        this.address = address;
        this.password = password;
        this.userid = userid;
        this.additional = additional;
               
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }    
   
    public String getPassword() {
        return password;
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

   public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    } 
    
    public void setUserid(int userid) {
        this.userid = userid;
    }
    
    public int getUserid() {
        return userid;
    }
}
