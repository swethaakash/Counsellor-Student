package in.swetha.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
//import org.springframework.data.jpa.repository.query.EqlParser.Scalar_expressionContext;
import org.springframework.stereotype.Service;

import in.swetha.binding.SearchCriteria;
import in.swetha.entity.StudentEnq;
import in.swetha.repo.StudentEnqRepo;

@Service
public class EnquiryServiceImpl implements EnquiryService {
	
	@Autowired
	private StudentEnqRepo srepo;

	@Override
	public boolean addEnq(StudentEnq se) {
		
		StudentEnq savedEnq = srepo.save(se);
		
		return savedEnq.getEnqId() != null;
	}

	@Override
	public List<StudentEnq> getEnquiries(Integer cid, SearchCriteria sc) {
		
		//get the data based on other factors
		StudentEnq enq = new StudentEnq();
		//setting cid
		enq.setCid(cid);
		
		//if mode selected add enquiry to query
		if(sc.getClassMode()!=null && !sc.getClassMode().equals("")) {
			enq.setClassMode(sc.getClassMode());
		}
		
		if(sc.getCourseName()!=null && !sc.getCourseName().equals("")) {
			enq.setCourseName(sc.getCourseName());
		}
		
		if (sc.getEnqStatus()!=null && !sc.getEnqStatus().equals("")) {
			enq.setEnqStatus(sc.getEnqStatus());
			
		}
		//query by example concept
		Example<StudentEnq> of = Example.of(enq);
		
		
		//get the data based on the ID
		List <StudentEnq> enquiries = srepo.findAll(of);
		
		
		return enquiries;
	}

}
