package com.project.sightseeing.Ban;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.sightseeing.City.CityData;

@Controller
@RequestMapping(path = "/ban")
public class BanDataController {
	@Autowired
	
	private BanDataRepository banRepo;
	
	@PostMapping(path = "/add")
	public @ResponseBody String addBan(@ModelAttribute BanData banToAdd)  {
//		BanData b = new BanData();
		
		banToAdd.setBan_id((int)banRepo.count() + 1);
//		b.setAdmin_id(admin_id);
//		b.setUser_id(user_id);
//		b.setBan_type(ban_type);
//		b.setDate_by(date_by);
//		b.setDate_since(date_since);
//		
		banRepo.save(banToAdd);
		return "Ban saved.";
	}
	
	@GetMapping(path = "/all")
	public String getBans(Model model){
		model.addAttribute("bans", banRepo.findAll());
		return "bandata";
	}
	
	@GetMapping(path = "/add")
	public String addBans(Model model){
		model.addAttribute("ban", new BanData());
		return "formban";
	}
}
