package in.swetha.service;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.stereotype.Service;

import in.swetha.binding.DashboardResponse;

import in.swetha.entity.Counsellor;
import in.swetha.entity.StudentEnq;
import in.swetha.repo.CounsellorRepo;
import in.swetha.repo.StudentEnqRepo;
import in.swetha.util.Emailutils;


@Service
public class CounsellorServiceImpl implements CounsellorService {
	
	@Autowired
	private CounsellorRepo crepo;
	
	@Autowired
	private Emailutils emailUtils;
	
	
	//inject studentenqrepo to get the student details
	@Autowired
	private StudentEnqRepo srepo;

	@Override
	public String saveCounsellor(Counsellor c) {
		
		//check duplicates if found don't create
		
		Counsellor obj = crepo.findByEmail(c.getEmail());
		
		if(obj !=null) {
			return "Email is already Registered";
		}
		
		//Saving register details
		Counsellor savedobj= crepo.save(c);
		
		if (savedobj.getCid()!=null) {
			return "Registration Sucessfully Completed";
			
		}
		
		// TODO Auto-generated method stub
		return "Registration Failed";
	}

	@Override
	public Counsellor loginCheck(String email, String pwd) {

		return crepo.findByEmailAndPwd(email, pwd);
	}

	@Override
	public boolean recoverPwd(String email) {
		
		Counsellor c = crepo.findByEmail(email); //finds the email which is given is available or not... if not available sends errmsg
		
		if (c==null) {
			return false;
			
		}
		String subject = "Recover Password-MiniProject";
		String body = "<h1>Your password:" + c.getPwd()+" </h1>";
		 return emailUtils.sendEmail(subject, body, email);
	}

	@Override
	public DashboardResponse getDashboardInfo(Integer cid) {
		
		// getting of enquiries from studentenqrepo by  using findbycid custom method
		//list of all enquiries
		 List<StudentEnq> allEnqs = srepo.findByCid(cid);
		 
		 // how many enrolled & lost enquries : java 8 concept Streams
		 int enrolledEnqs= allEnqs.stream()
				 .filter(e ->e.getEnqStatus().equals("Enrolled"))
				 .collect(Collectors.toList()).size();
		 
		 // create a dashboard response using DashboardResponse from binding class
		 
		 DashboardResponse resp= new DashboardResponse();
		 
		 //set the values
		 //resp.setTotalEnq(cid);
		 //resp.setEnrolledEnq(cid);
		// resp.setLostEnq(cid);
		 
		 resp.setTotalEnq(allEnqs.size());
		 resp.setEnrolledEnq(enrolledEnqs);
		 resp.setLostEnq(allEnqs.size()-enrolledEnqs);
		
		 
		 
		return resp;
	}

}
