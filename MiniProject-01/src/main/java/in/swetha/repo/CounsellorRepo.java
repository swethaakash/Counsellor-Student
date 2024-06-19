package in.swetha.repo;


import org.springframework.data.jpa.repository.JpaRepository;




import in.swetha.entity.Counsellor;
import java.util.List;





public interface CounsellorRepo extends JpaRepository<Counsellor, Integer >{
	
	//check duplicate email
	
	public Counsellor findByEmail(String email);
	
	public Counsellor findByEmailAndPwd(String email, String pwd);
	
	

}
