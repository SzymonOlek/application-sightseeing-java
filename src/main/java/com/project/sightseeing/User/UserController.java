package com.project.sightseeing.User;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;

import com.project.sightseeing.Admin.Admin;
import com.project.sightseeing.Admin.AdminData;
import com.project.sightseeing.Admin.AdminDataRepository;
import com.project.sightseeing.Sysuser.Sysuser;
import com.project.sightseeing.Sysuser.SysuserData;
import com.project.sightseeing.Sysuser.SysuserDataRepository;

@Controller
@RequestMapping(path = "/home")
public class UserController {
	
	public Map<String , User> loggedInUsers = new HashMap<String, User>();
	@Autowired
	AdminDataRepository adminRepo;
	@Autowired
	SysuserDataRepository userRepo;

	
	@GetMapping(path = "/login")
	public String loginForm(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}
	
	@PostMapping(path = "/login")
	public String login(@ModelAttribute User u) {
		
		User a = new User();
		
		for(AdminData i : adminRepo.findAll()) {
			if (i.getLogin().equals(u.getLogin()) && i.getPasswd().equals(u.getPasswd())) {
				a = new Admin(i);
				loggedInUsers.put(RequestContextHolder.currentRequestAttributes().getSessionId(), a);
				return "loginSucc";
			}
		}
		
		for(SysuserData i : userRepo.findAll()) {
			if (i.getLogin().equals(u.getLogin()) && i.getPasswd().equals(u.getPasswd())) {
				a = new Sysuser(i);
				loggedInUsers.put(RequestContextHolder.currentRequestAttributes().getSessionId(), a);
				return "loginSucc";
			}
		}
		
		return "loginFail";
	}
}