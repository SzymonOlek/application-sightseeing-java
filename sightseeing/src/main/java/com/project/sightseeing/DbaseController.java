package com.project.sightseeing;


import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.sightseeing.Sysuser;
import com.project.sightseeing.SysuserRepository;


@Controller
@RequestMapping(path="/user")
public class DbaseController {
	@Autowired
	
	private SysuserRepository userRepo;
	
//	@PostMapping(path="/add")
	public String addUser(@RequestParam Sysuser sysuser) {
		Sysuser u = new Sysuser();
		u.setF_name(sysuser.getF_name());
		u.setL_name(sysuser.getL_name());
		u.setLogin(sysuser.getLogin());
		u.setPasswd(sysuser.getPasswd());
		u.setEmail(sysuser.getEmail());
		u.setAvatar_path(sysuser.getAvatar_path());
		u.setComment_num(sysuser.getComment_num());
		u.setUser_id((int)(userRepo.count()) + 1);
		
		userRepo.save(u);
		return "User saved";
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Sysuser> getUsers(){
	
		return userRepo.findAll();
	}

}
