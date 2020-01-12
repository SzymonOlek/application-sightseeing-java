package com.project.sightseeing.Object;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.servlet.view.RedirectView;

import com.project.sightseeing.Admin.Admin;
import com.project.sightseeing.Admin.AdminData;
import com.project.sightseeing.Admin.AdminDataRepository;
import com.project.sightseeing.City.CityData;
import com.project.sightseeing.City.CityDataRepository;
import com.project.sightseeing.Commentary.CommentaryData;
import com.project.sightseeing.Commentary.CommentaryDataRepository;
import com.project.sightseeing.Others.Values;
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
	CityDataRepository cityRepo;
	@Autowired
	private PhotoDataRepository photoRepo;
	
	
	@GetMapping(path = "/addCitSh")
	public String addCS(Model model) {
		model.addAttribute("cities", cityRepo.findAll());
		return "objAddCS";
	}
	
	@GetMapping(path = "/add/{cid}")
	public String addObjects(Model model,@PathVariable("cid") String cid){
		model.addAttribute("cid",Integer.parseInt(cid));
		model.addAttribute("object", new ObjectData());
		
		return "formobject";
	}
	
	@PostMapping(path = "/add/{cid}")
	public RedirectView addObject(@ModelAttribute ObjectData object,@PathVariable("cid") String cid) {
		int id = Integer.parseInt(cid);
		object.setObject_id((int)objRepo.count() + 1);	
		object.setCity_id(id);
		
		System.out.println(object.getObject_name());
		objRepo.save(object);
		
		for(CityData cd : cityRepo.findAll()) {
			if(cd.getCity_id() == id) {
				cd.setObj_quan(cd.getObj_quan() + 1);
				cityRepo.save(cd);
			}
		}
		
		return new RedirectView("http://localhost:9999/sightseeing/admin/new");
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
				if ( entry.getUser_id() > 0) {
					id = entry.getUser_id();
					nicks.add(ufindById(id).getLogin());
				}
			}
		}
		id = -1;
		for(ObjectData entry : objRepo.findAll()) {
			if(entry.getObject_id() == val) {
				obj = entry;
			}
		}
		
		model.addAttribute("nicks", nicks.toArray());
		model.addAttribute("coment",cd);
		model.addAttribute("obj", obj);
		ArrayList<PhotoData> photos = new ArrayList<PhotoData>();
		
		for(PhotoData p : photoRepo.findAll()) {
			if(p.getObject_id().equals(val)) {
				photos.add(p);
			}
		}
		
		model.addAttribute("photos", photos);
		
		
		if(su != null) {
			if (su instanceof Sysuser) {
				user = ((Sysuser)su).getSysuserData();
				id = user.getUser_id();
				model.addAttribute("usrid", id);
				return "uObjPage";
			}
		}
			return  "objPage";
	}
	
	@GetMapping(path = "/all")
	public String getObjects(Model model){
		
		model.addAttribute("objects", objRepo.findAll());
		
		return "objectdata";
	}
	
	
	@GetMapping(path = "/setDistance/{cid}/{oid}")
	public String setDistance(@PathVariable("cid") String cid,@PathVariable("oid") String oid,Model model) {
		int idCity = Integer.parseInt(cid);
		int idObject = Integer.parseInt(oid);

		ArrayList<ObjectData> otherObjectsInCity= new ArrayList<ObjectData>();
		ArrayList<Values> distanceToOtherObj= new ArrayList<Values>();
		WraperInteger arrayDist = new WraperInteger();
		for(ObjectData p : objRepo.findAll()) {
			if(p.getCity_id().equals(idCity)) {
				arrayDist.addObj(p);
				arrayDist.addValues(new Values());
			}
		}	
		
		model.addAttribute("distance", arrayDist);

		
		return "setDistance";
	}
	
	@PostMapping(path = "/setDistance/{cid}/{oid}")
	public String addDistance(@ModelAttribute WraperInteger wraperDist,@PathVariable("cid") String cid,@PathVariable("oid") String oid,Model model) {
		int idCity = Integer.parseInt(cid);
		int idObject = Integer.parseInt(oid);
		
		ArrayList <Values> distance = wraperDist.getDistanceToOtherObj();
		ArrayList<ObjectData> objects = wraperDist.getOtherObjectsInCity();

		for(int i=0;i<distance.size();i++) {
			System.out.println(i + "  ----  "+  distance.get(i).Int);
		}
		
		
		
		
		
		
		
		
		return " zapisywanie obiektu";
	}
	
	

}
