package com.project.sightseeing;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class TemplateForControler {

	    @GetMapping("/template")
	    public String greetingForm(Model model) {
	        model.addAttribute("template", new Template());
	        return "template";
	    }

	    @PostMapping("/template")
	    public String greetingSubmit(@ModelAttribute Template template) {
	        return "templateres";
	    }

	
	

}
