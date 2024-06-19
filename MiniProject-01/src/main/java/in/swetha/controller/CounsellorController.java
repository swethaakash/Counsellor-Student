package in.swetha.controller;

//video 23 - ajax

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.swetha.binding.DashboardResponse;
import in.swetha.entity.Counsellor;
import in.swetha.service.CounsellorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller // shows this class as a Controller
public class CounsellorController {
	@Autowired  // Connects and talks with CounsellorService class
	private CounsellorService counsellorSvc;
	
	// Display logout view
    @GetMapping("/logout") // Maps HTTP GET requests to / to this method
    public String logout(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession(false);
    	session.invalidate();// session 
    	
        return "redirect:/"; // Returns the view name to be resolved (e.g., loginView.html)
    }
	
	
	
	
	
	// Display login view
    @GetMapping("/") // Maps HTTP GET requests to / to this method
    public String index(Model model) {
        model.addAttribute("counsellor", new Counsellor()); // Adds an empty Counsellor object to the model
        return "loginView"; // Returns the view name to be resolved (e.g., loginView.html)
    }
	
	
	
	
	//5.login method
	@PostMapping("/login")
	
	//get the session object using HttpServeletRequest obj
	public String handleLogin(Counsellor c, HttpServletRequest req, Model model) { //input data as a Counsellor Object, Model is for UI
		Counsellor obj =  counsellorSvc.loginCheck(c.getEmail(), c.getPwd()); //calling service class and logincheck method and getting email and password from Counsellor object & saving it into obj
		
		if (obj == null) {
			model.addAttribute("errMsg", "Invalid Credentials");
			return "loginView";
		}
		
		// creating a session by getting HttpServeletRequest obj req : true means always new session : false means gives me the existing sesssion
		
		HttpSession session = req.getSession(true);
		//once got the session, stores the session based on CID
		session.setAttribute("CID", obj.getCid());
		
		// when counsellor login, session should be created and stores the ID & data should be availabe in the id
		return "redirect:dashboard";
		     
	}
	
	//6. get data for dashboard : method
	@GetMapping("/dashboard")
	public String buildDashboard(HttpServletRequest req,  Model model) {
		
		// after storing session in Login, the session should be displayed here : create a HTTPSERVELETREQUEST req
		
		HttpSession session= req.getSession(false);// already available session
		Object obj = session.getAttribute("CID");
		Integer cid = (Integer)obj;
	
		
		DashboardResponse dashboardInfo = counsellorSvc.getDashboardInfo(cid);
		model.addAttribute("dashboard",dashboardInfo);
		
		return "dashboardView";
		
		
	}
	
	
    //2. display register page
	@GetMapping("/register") //2. get request API from Frontend to see the regitration page
	public String regPage(Model model) {
		
		model.addAttribute("counsellor", new Counsellor()); // to map with binding object --> "counsellor" is the Key which can be anything
		return "registerView"; // login view should mapped to binding object
	}
	
	// 4. register/signUp page
	@PostMapping("/register") //4. register page where counsellor 
	public String handleRegistration(Counsellor c, Model model) {
		 String msg=counsellorSvc.saveCounsellor(c);
		 model.addAttribute("msg", msg); // whatever the msg that we get from service, sending it to Model which shows on UI
		 return"registerView";
	}
	
	// 3. display forgot password page
	@GetMapping("/forgotPwd") //3.display forgotpwd page
	public String recoverPwdPage(Model model) {
		return "forgotPwdView";
	}
	
	//7. recover password method
	@GetMapping("/recover-pwd")
	public String recoverPwd(@RequestParam String email, Model model) { // @request Param to get request using email parameter
		
		boolean status = counsellorSvc.recoverPwd(email); // sending it to service to the recoverpwd method and store the msg into status Object
		
		//If success: success msg
		//else: errormsg
		if (status) {
			model.addAttribute("smsg", "Pwd sent to your email");
			
		} else {
			model.addAttribute("errmsg", "Invalid email");

		}
		
		//fails: return to again forgotpwdview
		return "forgotPwdView";
		
	}
	
	
	
	

}
