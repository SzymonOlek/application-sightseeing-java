package com.project.sightseeing.City;

<<<<<<< Updated upstream
import org.springframework.beans.factory.annotation.Autowired;
=======
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
>>>>>>> Stashed changes
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
<<<<<<< Updated upstream
=======
import org.springframework.web.bind.annotation.PathVariable;
>>>>>>> Stashed changes
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.sightseeing.Sysuser.SysuserData;

<<<<<<< Updated upstream
@Controller
@RequestMapping(path = "/city")
public class CityDataController {
@Autowired
	private CityDataRepository cityRepo;
	
=======
import antlr.collections.List;

import com.project.sightseeing.Object.ObjectData;
import com.project.sightseeing.Object.ObjectDataRepository;
import com.project.sightseeing.Others.*;
@Controller
@RequestMapping(path = "/city")
public class CityDataController {
	
@Autowired
	private CityDataRepository cityRepo;

@Autowired
private ObjectDataRepository objectRepo;
	
	@GetMapping(path = "/add")
	public String addCity(Model model){
		model.addAttribute("city", new CityData());
		return "formcity";
	}
>>>>>>> Stashed changes
	@PostMapping(path = "/add")
	public @ResponseBody String addCity(@ModelAttribute CityData cityToAdd) {
//		CityData c = new CityData();
		
		cityToAdd.setCity_id((int)cityRepo.count() + 1);
//		c.setCity_name(city_name);
//		c.setObj_quan(obj_quan);
		
		cityRepo.save(cityToAdd);
		
<<<<<<< Updated upstream
		return "City saved.";
		
	} 
=======
		return "Citysaved.";
		
	} 

	@GetMapping(path = "/sel")
	public String selCity(Model model){
		Values cid = new Values();
		Integer ccid = new Integer(0);
		model.addAttribute("cities", cityRepo.findAll());
		model.addAttribute("cid", cid);
		return "citysel";
	}

	@GetMapping(path = "/miasto/{name}")
	public String ChooseCity(@PathVariable("name") String name, Model model){
		
		int val = Integer.parseInt(name);
		ArrayList<ObjectData> objects = new ArrayList<ObjectData>();
		
//		
		for(ObjectData entry : objectRepo.findAll()) {
			if(entry.getCity_id() == val) {
				objects.add(entry);
			}
		}
		model.addAttribute("obiekty", objects);
		return  "TestPage";
	}
	
	
	
	@PostMapping(path = "/sel")
	public @ResponseBody String citysel(@ModelAttribute Values cid) {
		return Integer.toString(cid.Int);
	}
>>>>>>> Stashed changes
	
	@GetMapping(path = "/all")
	public String getCity(Model model){
		model.addAttribute("cities", cityRepo.findAll());
		return "citydata";
	}
	
<<<<<<< Updated upstream
	@GetMapping(path = "/add")
	public String addCity(Model model){
		model.addAttribute("city", new CityData());
		return "formcity";
	}
=======

>>>>>>> Stashed changes
	
}
