package com.project.sightseeing.Admin;

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
import com.project.sightseeing.Ban.BanDataRepository;
import com.project.sightseeing.Sysuser.Sysuser;
import com.project.sightseeing.Sysuser.SysuserData;
import com.project.sightseeing.Sysuser.SysuserDataRepository;
import com.project.sightseeing.User.User;

@Controller
@RequestMapping(path = "/admin")
public class AdminDataController {
	@Autowired
	private AdminDataRepository adminRepo;
	
	@Autowired
	SysuserDataRepository userRepo;
	@Autowired
	BanDataRepository banRepo;
	
	@GetMapping(path = "/acc")
	public String account(Model model) {
		
		String session = RequestContextHolder.currentRequestAttributes().getSessionId();
		Admin s;
		for(Map.Entry<String , User> entry : SightseeingApplication.loggedInUsers.entrySet()) {
			if (entry.getKey().equals(session)) {
				s = (Admin)entry.getValue();
				model.addAttribute("admin", s.getAdminData());
				return "adminAcc";
				}
			}
		return "blad";
	}
	
	@GetMapping(path = "/del")
	public String delete() {
		return "delForm";
	}
	@GetMapping(path = "/new")
	public String addObj() {
		return "addForm";
	}
	
	@GetMapping(path = "/ban")
	public String banUser(Model model) {
		model.addAttribute("sysusers", userRepo.findAll());
		model.addAttribute("bans", banRepo);
		return "adminBan";
	}
	
	@PostMapping(path = "/add")
	public @ResponseBody String addAdmin(@ModelAttribute AdminData adminToAdd) {
		
//		AdminData a = new AdminData();
		
		adminToAdd.setAdmin_id((int)adminRepo.count() + 1);
//		a.setF_name(f_name);
//		a.setL_name(l_name);
//		a.setEmail(email);
//		a.setLogin(login);
//		a.setPasswd(passwd);
//		a.setAvatar_path(avatar_path);
//		
		adminRepo.save(adminToAdd);
		
		return "Admin saved.";
	}
	
	@GetMapping(path = "/all")
	public String getAdmins(Model model){
		model.addAttribute("admins", adminRepo.findAll());
		return "admindata";

	}
	
	@GetMapping(path = "/add")
	public String addAdmin(Model model){
		model.addAttribute("admin", new AdminData());
		return "formadmin";

	}
	
	
}
