package com.project.sightseeing.Object;

import java.util.ArrayList;

import org.dom4j.util.UserDataAttribute;
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

import com.project.sightseeing.Admin.Admin;
import com.project.sightseeing.Admin.AdminData;
import com.project.sightseeing.Admin.AdminDataRepository;
import com.project.sightseeing.City.CityData;
import com.project.sightseeing.Commentary.CommentaryData;
import com.project.sightseeing.Commentary.CommentaryDataRepository;
import com.project.sightseeing.Photo.PhotoData;
import com.project.sightseeing.Photo.PhotoDataRepository;
import com.project.sightseeing.Sysuser.Sysuser;
import com.project.sightseeing.Sysuser.SysuserData;
import com.project.sightseeing.Sysuser.SysuserDataRepository;
import com.project.sightseeing.User.User;

@Controller
@RequestMapping(path = "/object")
public class ObjectDataController {
	@Autowired
	private ObjectDataRepository objRepo;
	@Autowired
	private CommentaryDataRepository comentRepo;
	@Autowired
	private AdminDataRepository adminRepo;
	@Autowired
	private SysuserDataRepository userRepo;
	@Autowired
	private PhotoDataRepository photoRepo;
	
	
	@PostMapping(path = "/add")
	public @ResponseBody String addObject(@ModelAttribute ObjectData objectToAdd) {
		objectToAdd.setObject_id((int)objRepo.count() + 1);		
		objRepo.save(objectToAdd);
		return "Object saved.";
	}
	
	
	public SysuserData ufindById(int id) {
		SysuserData a = new SysuserData();
		for(SysuserData entry : userRepo.findAll()) {
			if(entry.getUser_id() == id) {
				a = entry;
				break;
			}
		}
		return a;
	}
	
	public AdminData afindById(int id) {
		AdminData a = new AdminData();
		for(AdminData entry : adminRepo.findAll()) {
			if(entry.getAdmin_id() == id) {
				a = entry;
				break;
			}
		}
		return a;
	}
	
	@GetMapping(path = "/object/{name}")
	public String ChooseObject(@PathVariable("name") String name, Model model){
		
		int val = Integer.parseInt(name);
		ObjectData obj = new ObjectData();
		ArrayList<CommentaryData> cd = new ArrayList<CommentaryData>();
		ArrayList<String> nicks = new ArrayList<>();
		User su = (new User().getUser());
		SysuserData user = new SysuserData();
		AdminData admin = new AdminData();
		int id  = -1;
		
		for(CommentaryData entry : comentRepo.findAll()) {
			if(entry.getObject_id() == val) {
				cd.add(entry);
				if ( id > 0 && id < 1000) {
					id = entry.getUser_id();
					nicks.add(ufindById(id).getLogin());
				}
			}
		}
		
		for(ObjectData entry : objRepo.findAll()) {
			if(entry.getObject_id() == val) {
				obj = entry;
			}
		}
		
		//model.addAttribute("nicks", nicks);
		model.addAttribute("coment",cd);
		model.addAttribute("obj", obj);
		if(su != null) {
			if (su instanceof Sysuser) {
				user = ((Sysuser)su).getSysuserData();
				id = user.getUser_id();
				model.addAttribute("usrid", id);
				return "uObjPage";
			}
		}
		
		
		ArrayList<PhotoData> photos = new ArrayList<PhotoData>();
		
		for(PhotoData p : photoRepo.findAll()) {
			if(p.getObject_id().equals(val)) {
				photos.add(p);
			}
		}
		
		model.addAttribute("photos", photos);
		
			return  "objPage";
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
