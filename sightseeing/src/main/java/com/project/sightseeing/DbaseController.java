package com.project.sightseeing;

import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(path="/user")
public class DbaseController {
	@Autowired
	
	private SysuserRepository userRepo;
	
	@PostMapping(path="/add")
	public @ResponseBody String addUser(@RequestParam String f_name, @RequestParam String l_name
			, @RequestParam String login, @RequestParam String passwd, @RequestParam String email
			, @RequestParam String avatar_path, @RequestParam Integer comment_num) {
		Sysuser u = new Sysuser();
		u.setF_name(f_name);
		u.setL_name(l_name);
		u.setLogin(login);
		u.setPasswd(passwd);
		u.setEmail(email);
		u.setAvatar_path(avatar_path);
		u.setComment_num(comment_num);
		
		userRepo.save(u);
		return "User saved";
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Sysuser> getUsers(){
	
		return userRepo.findAll();
	}

}
