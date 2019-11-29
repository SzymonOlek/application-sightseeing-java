package com.project.sightseeing.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.sightseeing.Sysuser.SysuserData;

@Controller
@RequestMapping(path = "/admin")
public class AdminDataController {
	@Autowired
	private AdminDataRepository adminRepo;
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
