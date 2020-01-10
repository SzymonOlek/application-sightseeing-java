package com.project.sightseeing.Commentary;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.aspectj.weaver.NewConstructorTypeMunger;
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

import com.project.sightseeing.Ban.BanData;
import com.project.sightseeing.Ban.BanDataRepository;
import com.project.sightseeing.Object.ObjectData;
import com.project.sightseeing.Route.RouteData;
import com.project.sightseeing.Sysuser.SysuserData;
import com.project.sightseeing.Sysuser.SysuserDataRepository;

@Controller
@RequestMapping(path = "/comm")
public class CommentaryDataController {
	@Autowired
	private CommentaryDataRepository comRepo;
	@Autowired
	private BanDataRepository banRepo;
	@Autowired
	private SysuserDataRepository userRepo;
	
	@PostMapping(path = "/add/{oid}/{uid}")
	public String addCommentary(@PathVariable("oid") String oid, @PathVariable("uid") String uid,@ModelAttribute CommentaryData commentToAdd, Model model) {
		int objId = Integer.parseInt(oid);
		int usrId = Integer.parseInt(uid);
		String path = "http://localhost:9999/sightseeing/object/object/" + oid;
		SysuserData us = ufindById(usrId);
		us.setComment_num(us.getComment_num() + 1);
		model.addAttribute("path", path);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
		LocalDateTime now = LocalDateTime.now();
		commentToAdd.setCommentary_id((int)comRepo.count() + 1);
		commentToAdd.setUser_id(usrId);
		commentToAdd.setObject_id(objId);
		commentToAdd.setComment_date(dtf.format(now));
		comRepo.save(commentToAdd);
		
		return "commSaved";
	}
	
	@GetMapping(path = "/uadd/{oid}/{uid}")
	public String ChooseCity(@PathVariable("oid") String oid, @PathVariable("uid") String uid, Model model){
		
		int objId = Integer.parseInt(oid);
		int usrId = Integer.parseInt(uid);
		CommentaryData data = new CommentaryData();
		BanData bd = new BanData();
		bd = banById(usrId);
		if(bd != null) {
			return "banned";
		}
		data.setObject_id(objId);
		data.setUser_id(usrId);
		model.addAttribute("comm", data);
		return  "uFormComment";
	}
	
	@GetMapping(path = "/all")
	public String getComments(Model model){
		
		model.addAttribute("comments", comRepo.findAll());
		
		return "commentarydata";
	}
	@GetMapping(path = "add")
	public String addComments(Model model){
		
		model.addAttribute("comment", new CommentaryData());
		
		return "formcomment";
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
	public BanData banById(int id) {
		BanData a = new BanData();
		for(BanData entry : banRepo.findAll()) {
			if(entry.getUser_id() == id) {
				a = entry;
				return a;
			}
		}
		return null;
	}
	
}
