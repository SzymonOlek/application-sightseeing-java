package com.project.sightseeing.Sysuser;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;

import com.project.sightseeing.SightseeingApplication;
import com.project.sightseeing.Template;
import com.project.sightseeing.User.User;


@Controller
@RequestMapping(path="/user")
public class SysuserDataController {
@Autowired
	
	private SysuserDataRepository userRepo;
	

	@GetMapping(path = "acc")
	public String account(Model model) {
		
		String session = RequestContextHolder.currentRequestAttributes().getSessionId();
		Sysuser s;
		for(Map.Entry<String , User> entry : SightseeingApplication.loggedInUsers.entrySet()) {
			if (entry.getKey().equals(session)) {
				s = (Sysuser)entry.getValue();
				model.addAttribute("user", s.getSysuserData());
				return "userAcc";
				}
			}
		return "blad";
	}

	@GetMapping(path = "/del")
	public String delAcc(Model model) {
		return "delUser";
	}
	
	@GetMapping(path = "/del/conf")
	public String delUser () {
		
		String session = RequestContextHolder.currentRequestAttributes().getSessionId();
		Sysuser s = new Sysuser();
		Sysuser su;
		for(Map.Entry<String , User> entry : SightseeingApplication.loggedInUsers.entrySet()) {
			if (entry.getKey().equals(session)) {
				s = (Sysuser)entry.getValue();
				}
			}
		userRepo.delete(s.getSysuserData());
		for(Map.Entry<String , User> entry : SightseeingApplication.loggedInUsers.entrySet()) {
			su = (Sysuser)entry.getValue();
			if (su.equals(s)) {
				SightseeingApplication.loggedInUsers.remove(entry.getKey());
				}
			}
		return "delSucc";
	}

	@GetMapping(path="/add")
	public String addUsers(Model model){
		  model.addAttribute("sysuser",new SysuserData() );
		return "formsysuser";
	}

	@PostMapping(path="/add")
	public String addUser(@ModelAttribute SysuserData userToAdd) {
//		SysuserData u = new SysuserData();
//		u.setF_name("paw");
//		u.setL_name("mid");
//		u.setLogin("asd");
//		u.setPasswd("asd");
//		u.setEmail("asd@asd");
//		u.setAvatar_path("apath");
		userToAdd.setComment_num(0);
		userToAdd.setUser_id((int)(userRepo.count()) + 1);
		
		userRepo.save(userToAdd);
		return "regSuc";
	}
	
	@GetMapping(path="/all")
	public String getUsers(Model model){
		  model.addAttribute("sysusers",userRepo.findAll() );
		return "sysusersdata";
	}
}
