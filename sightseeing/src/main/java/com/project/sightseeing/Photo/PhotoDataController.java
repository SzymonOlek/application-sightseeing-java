package com.project.sightseeing.Photo;

import org.apache.tomcat.util.security.PrivilegedSetTccl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.sightseeing.Commentary.CommentaryData;

@Controller
@RequestMapping(path = "/photo")
public class PhotoDataController {
	@Autowired
	private PhotoDataRepository photoRepo;
	
	@PostMapping(path = "/add")
	public @ResponseBody String addPhoto(@ModelAttribute PhotoData photoToAdd) {
//		PhotoData p = new PhotoData();
		
		photoToAdd.setPhoto_id((int)photoRepo.count() + 1);
//		p.setObject_id(object_id);
//		p.setPhoto_path(photo_path);
		
		photoRepo.save(photoToAdd);
		
		return "Photo saved.";
	}
	
	@GetMapping(path = "/all")
	public String getPhotos(Model model){
		model.addAttribute("photos", photoRepo.findAll());
		return "photodata";
	}
	
	@GetMapping(path = "/add")
	public String addPhotos(Model model){
		model.addAttribute("photo", new PhotoData());
		return "formphoto";
	}
}
