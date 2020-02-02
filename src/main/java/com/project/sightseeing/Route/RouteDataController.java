package com.project.sightseeing.Route;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.sightseeing.Object.ObjectData;
import com.project.sightseeing.Object.ObjectDataRepository;

@Controller
@RequestMapping(path = "/route")
public class RouteDataController {
	@Autowired
	RouteDataRepository routeRepo;
	@Autowired
	ObjectDataRepository objRepo;
	
	
	private Algorithm a = new Algorithm();
	
	private int cid;
	
	private int []parseToint(String str2parse, String delimiter){
		String[] objs = str2parse.split(delimiter);
		cid = Integer.parseInt(objs[0]);
		System.out.println("cid: " + cid);
		int [] toAlg = new int[objs.length];
		System.out.println("obj parse");
		for(int i = 0; i < objs.length; i++) {
			
			toAlg[i] = Integer.parseInt(objs[i]);
			
		}
		System.out.println("obj parse - done");
		return toAlg;
	}
	
	private int [][] getNeighMatrix(int [] objects) {
		int len = objects.length;
		int [][] matrix = new int[len][len];
		
		System.out.println("matrix");
		for(int i = 0; i < len; i++) {
			for(int j = 0; j < len; j++) {
				if(j == i) {
					matrix[j][i]=0;
				}
				for(RouteData r : routeRepo.findAll()) {
					if(r.getCity_id() == cid && r.getObject_1_id() == objects[i] && r.getObject_2_id() == objects[j]) {
						matrix[i][j] = r.getDistance();
						matrix[j][i] = r.getDistance();
					}
				}
			}
			
		}
		System.out.println("matrix - done");
		return matrix;
	}
	
	private int[] delFp(int arr[]) {
		int len = arr.length -1;
		int ret[] = new int[len];
		
		for(int i =0; i < len; i++) {
			ret[i]=arr[i+1];
		}
		
		return ret;
	}
	@PostMapping(path = "/find/{objects}")
	public String findRoute(@PathVariable("objects") String objects, Model model) {
		String lastTrace;
		int [] trace;
		int [] toAlg = parseToint(objects, "q");
		ArrayList<ObjectData> odat = new ArrayList<ObjectData>();
		if (toAlg.length < 2) {
			return "tooFewArgs";
		}
		int cid = toAlg[0];
		toAlg = delFp(toAlg);
		int matrix[][] = getNeighMatrix(toAlg);
		
		for(int i = 0; i < toAlg.length; i++) {
			for(int j = 0; j < toAlg.length; j++) {
				System.out.printf("%d ",matrix[i][j]);
			}
			System.out.println();
		}
		a.minCost(matrix);
		lastTrace = a.GetLastTrace();
		trace = parseToint(lastTrace, "->");
		for(int i = 0; i < trace.length; i++) {
			for(ObjectData o : objRepo.findAll()) {
				if(o.getCity_id() == cid && o.getObject_id() == toAlg[trace[i]]) {
					odat.add(o);
				}
			}
		}
		model.addAttribute("objects", odat);
		return "showRoute";
	}
	
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
