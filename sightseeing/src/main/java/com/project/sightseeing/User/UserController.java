package com.project.sightseeing.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;
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
import com.project.sightseeing.SightseeingApplication;

@Controller
@RequestMapping(path = "/home")
public class UserController {
	
	//public static Map<String , User> loggedInUsers = new HashMap<String, User>();
	@Autowired
	AdminDataRepository adminRepo;
	@Autowired
	SysuserDataRepository userRepo;

	
//	@GetMapping(path = "")
//	public String mainPage(Model model) {
//		
//		String lg = new User().isLogged();
//		
//		if (lg.equals("a")) {
//			return "admin";
//		} else if ( lg.equals("u")) {
//			return "user";
//		}else
//			return "def";
//	}
	
	@GetMapping(path = "/def")
	public String defform(Model model) {
		model.addAttribute("choice", new String());	
		return "def";
	}
	
	@PostMapping(path = "/def")
	public String def(@ModelAttribute String s) {
		if (s.equals("login")){
			return "login";
		}else if (s.equals("registration"))
			return "registration";
		else if (s.equals("cityCh")) {
			return "cityCh";
		} else
			return "";
	}
	
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
				SightseeingApplication.loggedInUsers.put(RequestContextHolder.currentRequestAttributes().getSessionId(), a);
				return "loginSucc";
			}
		}
		
		for(SysuserData i : userRepo.findAll()) {
			if (i.getLogin().equals(u.getLogin()) && i.getPasswd().equals(u.getPasswd())) {
				a = new Sysuser(i);
				SightseeingApplication.loggedInUsers.put(RequestContextHolder.currentRequestAttributes().getSessionId(), a);
				return "loginSucc";
			}
		}
		
		return "loginFail";
	}


	@GetMapping(path = "/allU")
	public @ResponseBody Iterable<User> loggedIn(){
		
		Iterable<User> u = SightseeingApplication.loggedInUsers.values();
		
		return u;
	}

	@GetMapping(path = "/allid")
	public @ResponseBody Iterable<String> loggedSessions(){
		
		Iterable<String> u = SightseeingApplication.loggedInUsers.keySet();
		
		return u;
	}






}