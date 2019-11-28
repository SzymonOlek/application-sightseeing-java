package com.project.sightseeing.Photo;

import org.apache.tomcat.util.security.PrivilegedSetTccl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/photo")
public class PhotoDataController {
	@Autowired
	private PhotoDataRepository photoRepo;
	
	@PostMapping(path = "/add")
	public @ResponseBody String addPhoto(@RequestParam Integer object_id, @RequestParam String photo_path) {
		PhotoData p = new PhotoData();
		
		p.setPhoto_id((int)photoRepo.count() + 1);
		p.setObject_id(object_id);
		p.setPhoto_path(photo_path);
		
		photoRepo.save(p);
		
		return "Photo saved.";
	}
	
	@GetMapping(path = "/all")
	public @ResponseBody Iterable<PhotoData> getPhotos(){
		return photoRepo.findAll();
	}
}
