package com.project.sightseeing.Sysuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.sightseeing.Template;


@Controller
@RequestMapping(path="/user")
public class SysuserDataController {
@Autowired
	
	private SysuserDataRepository userRepo;
	
	@PostMapping(path="/add")
	public @ResponseBody String addUser(@ModelAttribute SysuserData userToAdd) {
//		SysuserData u = new SysuserData();
//		u.setF_name(f_name);
//		u.setL_name(l_name);
//		u.setLogin(login);
//		u.setPasswd(passwd);
//		u.setEmail(email);
//		u.setAvatar_path(avatar_path);
		userToAdd.setComment_num(0);
		userToAdd.setUser_id((int)(userRepo.count()) + 1);
		
		userRepo.save(userToAdd);
		return "User saved";
	}
	
	@GetMapping(path="/all")
	public String getUsers(Model model){
		  model.addAttribute("sysusers",userRepo.findAll() );
		return "sysusersdata";
	}
	@GetMapping(path="/add")
	public String addUsers(Model model){
		  model.addAttribute("sysuser",new SysuserData() );
		return "formsysuser";
	}
}
