package in.swetha.service;

import java.util.List;

import in.swetha.binding.SearchCriteria;
import in.swetha.entity.StudentEnq;

public interface EnquiryService {

	public boolean addEnq(StudentEnq se);
	
	public List<StudentEnq> getEnquiries(Integer cid, SearchCriteria s); 
	
}
