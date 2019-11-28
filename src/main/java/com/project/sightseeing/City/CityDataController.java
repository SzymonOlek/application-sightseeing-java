package com.project.sightseeing.City;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/city")
public class CityDataController {
@Autowired
	private CityDataRepository cityRepo;
	
	@PostMapping(path = "/add")
	public @ResponseBody String addCity(@RequestParam String city_name, @RequestParam Integer obj_quan) {
		CityData c = new CityData();
		
		c.setCity_id((int)cityRepo.count() + 1);
		c.setCity_name(city_name);
		c.setObj_quan(obj_quan);
		
		cityRepo.save(c);
		
		return "City saved.";
		
	} 
	
	@GetMapping(path = "/all")
	public @ResponseBody Iterable<CityData> getCity(){
		return cityRepo.findAll();
	}
}
