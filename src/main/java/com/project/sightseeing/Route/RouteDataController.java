package com.project.sightseeing.Route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.sightseeing.Object.ObjectData;

@Controller
@RequestMapping(path = "/route")
public class RouteDataController {
	@Autowired
	RouteDataRepository routeRepo;
	
	@PostMapping(path = "/add")
	public @ResponseBody String addRoute(@ModelAttribute RouteData routeToAdd) {
		
//		RouteData r = new RouteData();
		
		routeToAdd.setRoute_id((int)routeRepo.count() + 1);
//		r.setCity_id(city_id);
//		r.setObject_1_id(object_1_id);
//		r.setObject_2_id(object_2_id);
//		r.setDistance(distane);
		
		routeRepo.save(routeToAdd);
		
		return "Route saved.";
	}
	
	@GetMapping(path = "/all")
	public String getRoutes(Model model){
		model.addAttribute("routes", routeRepo.findAll());
		return "routedata";
	}
	@GetMapping(path = "/add")
	public String addRoutes(Model model){
		model.addAttribute("route", new RouteData());
		return "formroute";
	}
}
