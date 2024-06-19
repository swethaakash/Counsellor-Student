package in.swetha.service;

import in.swetha.binding.DashboardResponse;
import in.swetha.entity.Counsellor;

public interface CounsellorService {
	
	public String saveCounsellor(Counsellor c);
	
	public Counsellor loginCheck(String email, String pwd);
	
	public boolean recoverPwd(String email);
	
	public DashboardResponse getDashboardInfo(Integer cid);
	

}
