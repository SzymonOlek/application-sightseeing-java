package com.project.sightseeing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class UserController {

	@Autowired
	DbaseController baza;
	
    @GetMapping("/user/add")
    public String greetingForm(Model model) {
        model.addAttribute("sysuser", new Sysuser());
        return "sysuser";
    }

    @PostMapping("/user/add")
    public String greetingSubmit(@ModelAttribute Sysuser sysuser) {
    	
    	baza.addUser(sysuser);
    	
        return "resu";
    }

}
