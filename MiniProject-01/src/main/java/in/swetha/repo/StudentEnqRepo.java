package in.swetha.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import in.swetha.entity.StudentEnq;

public interface StudentEnqRepo extends JpaRepository<StudentEnq, Integer> {
	
	// Custom method findbyCID to get the list of studentenquiries
	
	public List<StudentEnq> findByCid(Integer cid);

}
