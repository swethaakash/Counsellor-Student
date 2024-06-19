package in.swetha.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.repository.query.EqlParser.New_valueContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.swetha.binding.SearchCriteria;
import in.swetha.entity.StudentEnq;
import in.swetha.service.EnquiryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {
	
	@Autowired
	private EnquiryService enqService;
	
	
	@GetMapping("/enquiry")
	public String enqPage(Model model) {
		model.addAttribute("enq", new StudentEnq());
		return"addEnqView";
		
	}
	
	@PostMapping("/enquiry")
	public String addEnquiry(@ModelAttribute("enq")  StudentEnq enq, HttpServletRequest req, Model model) { //1. get counsellor Id fromthe exisisting Session
		
		//2. getting cid from existing session
		HttpSession session = req.getSession(false);
		//3. store cid by using Typecasting
		Integer cid = (Integer) session.getAttribute("CID");
		//4. setting the object into cid
		enq.setCid(cid);
		
		if (cid==null) {
			return "redirect:/logout";
			
		}
		
		boolean addEnq= enqService.addEnq(enq);
		if (addEnq) {
			model.addAttribute("succMsg", "Enquiry added Successfully");
		} else {
			model.addAttribute("errMsg", "Enquiry Failed to add");
		}
		return "addEnqView";
	}
	
	@GetMapping("/enquiries")
	
	//1. get the session using HttpServeletRequest
	public String viewEnquiries(HttpServletRequest req, Model model) {
		//2. from the request, get the session
		HttpSession session = req.getSession(false);
		//3. from the session get the Id
		Integer cid = (Integer) session.getAttribute("CID");
		
		if (cid==null) {
			return "redirect:/logout";
			
		}
		
		
		model.addAttribute("sc", new SearchCriteria());
		
		
		List<StudentEnq> enquiriesList =  enqService.getEnquiries(cid,new SearchCriteria());
		model.addAttribute("enquiries", enquiriesList);
		return "displayEnqView";
		
	}
	
	
	@PostMapping("/filter-enquiries")
	public String filterEnquiries(@ModelAttribute("sc")  SearchCriteria sc,HttpServletRequest req, Model model) {
		
		HttpSession session = req.getSession(false);
		Integer cid = (Integer) session.getAttribute("CID");
		
		//model.addAttribute("sc", new SearchCriteria());
		
		if (cid==null) {
			return "redirect:/logout";
			
		}
		
		List<StudentEnq> enquiriesList =  enqService.getEnquiries(cid,sc);
		model.addAttribute("enquiries", enquiriesList);
		return "displayEnqView";
		
		
	}
	
	
	

}
