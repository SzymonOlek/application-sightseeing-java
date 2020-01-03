package com.project.sightseeing.Object;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.sightseeing.City.CityData;

@Controller
@RequestMapping(path = "/object")
public class ObjectDataController {
	@Autowired
	
	private ObjectDataRepository objRepo;
	
	@PostMapping(path = "/add")
	public @ResponseBody String addObject(@ModelAttribute ObjectData objectToAdd) {
		
//		ObjectData o = new ObjectData();
		
		objectToAdd.setObject_id((int)objRepo.count() + 1);
//		o.setLocalization(localization);
//		o.setDescription(description);
//		o.setObject_name(object_name);
//		o.setCity_id(city_id);
		
		objRepo.save(objectToAdd);
		
		return "Object saved.";
	}
	
	@GetMapping(path = "/all")
	public String getObjects(Model model){
		
		model.addAttribute("objects", objRepo.findAll());
		
		return "objectdata";
	}
	
	@GetMapping(path = "/add")
	public String addObjects(Model model){
		
		model.addAttribute("object", new ObjectData());
		
		return "formobject";
	}
	
}
