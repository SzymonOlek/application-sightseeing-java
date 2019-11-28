package com.project.sightseeing.Route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/route")
public class RouteDataController {
	@Autowired
	RouteDataRepository routeRepo;
	
	@PostMapping(path = "/add")
	public @ResponseBody String addRoute(@RequestParam Integer city_id, @RequestParam Integer object_1_id, @RequestParam Integer object_2_id
			, @RequestParam Float distane) {
		
		RouteData r = new RouteData();
		
		r.setRoute_id((int)routeRepo.count() + 1);
		r.setCity_id(city_id);
		r.setObject_1_id(object_1_id);
		r.setObject_2_id(object_2_id);
		r.setDistance(distane);
		
		routeRepo.save(r);
		
		return "Route saved.";
	}
	
	@GetMapping(path = "/all")
	public @ResponseBody Iterable<RouteData> getRoutes(){
		return routeRepo.findAll();
	}
}
