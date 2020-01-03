package com.project.sightseeing.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/admin")
public class AdminDataController {
	@Autowired
	private AdminDataRepository adminRepo;
	@PostMapping(path = "/add")
	public @ResponseBody String addAdmin(@RequestParam String f_name, @RequestParam String l_name, @RequestParam String email
			, @RequestParam String login, @RequestParam String passwd, @RequestParam String avatar_path) {
		
		AdminData a = new AdminData();
		
		a.setAdmin_id((int)adminRepo.count() + 1);
		a.setF_name(f_name);
		a.setL_name(l_name);
		a.setEmail(email);
		a.setLogin(login);
		a.setPasswd(passwd);
		a.setAvatar_path(avatar_path);
		
		adminRepo.save(a);
		
		return "Admin saved.";
	}
	
	@GetMapping(path = "/all")
	public @ResponseBody Iterable<AdminData> getAdmins(){

		return adminRepo.findAll();

	}
}
