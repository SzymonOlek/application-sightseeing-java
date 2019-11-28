package com.project.sightseeing.Object;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/object")
public class ObjectDataController {
	@Autowired
	
	private ObjectDataRepository objRepo;
	
	@PostMapping(path = "/add")
	public @ResponseBody String addObject(@RequestParam String localization, @RequestParam String description
			,@RequestParam String object_name, @RequestParam Integer city_id) {
		
		ObjectData o = new ObjectData();
		
		o.setObject_id((int)objRepo.count() + 1);
		o.setLocalization(localization);
		o.setDescription(description);
		o.setObject_name(object_name);
		o.setCity_id(city_id);
		
		objRepo.save(o);
		
		return "Object saved.";
	}
	
	@GetMapping(path = "/all")
	public @ResponseBody Iterable<ObjectData> getObjects(){
		return objRepo.findAll();
	}
}
