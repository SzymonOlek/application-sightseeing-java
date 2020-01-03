package com.project.sightseeing.City;

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
@RequestMapping(path = "/city")
public class CityDataController {
@Autowired
	private CityDataRepository cityRepo;
	
	@PostMapping(path = "/add")
	public @ResponseBody String addCity(@ModelAttribute CityData cityToAdd) {
//		CityData c = new CityData();
		
		cityToAdd.setCity_id((int)cityRepo.count() + 1);
//		c.setCity_name(city_name);
//		c.setObj_quan(obj_quan);
		
		cityRepo.save(cityToAdd);
		
		return "City saved.";
		
	} 
	
	@GetMapping(path = "/all")
	public String getCity(Model model){
		model.addAttribute("cities", cityRepo.findAll());
		return "citydata";
	}
	
	@GetMapping(path = "/add")
	public String addCity(Model model){
		model.addAttribute("city", new CityData());
		return "formcity";
	}
	
}
