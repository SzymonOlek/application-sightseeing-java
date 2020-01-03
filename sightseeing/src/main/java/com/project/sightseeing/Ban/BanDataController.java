package com.project.sightseeing.Ban;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/ban")
public class BanDataController {
	@Autowired
	
	private BanDataRepository banRepo;
	
	@PostMapping(path = "/add")
	public @ResponseBody String addBan(@RequestParam Integer admin_id, @RequestParam Integer user_id, @RequestParam Ban_type ban_type
			,@RequestParam String date_by, @RequestParam String date_since) {
		BanData b = new BanData();
		
		b.setBan_id((int)banRepo.count() + 1);
		b.setAdmin_id(admin_id);
		b.setUser_id(user_id);
		b.setBan_type(ban_type);
		b.setDate_by(date_by);
		b.setDate_since(date_since);
		
		banRepo.save(b);
		return "Ban saved.";
	}
	
	@GetMapping(path = "/all")
	public @ResponseBody Iterable<BanData> getBans(){
		return banRepo.findAll();
	}
}
