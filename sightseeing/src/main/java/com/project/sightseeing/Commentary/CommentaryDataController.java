package com.project.sightseeing.Commentary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/comm")
public class CommentaryDataController {
	@Autowired
	private CommentaryDataRepository comRepo;
	
	@PostMapping(path = "/add")
	public @ResponseBody String addCommentary(@RequestParam Integer object_id, @RequestParam Integer user_id
			, @RequestParam String comment_date, @RequestParam Rate rate, @RequestParam String contents) {
		CommentaryData c = new CommentaryData();
		
		c.setCommentary_id((int)comRepo.count() + 1);
		c.setObject_id(object_id);
		c.setUser_id(user_id);
		c.setComment_date(comment_date);
		c.setRate(rate);
		c.setContents(contents);
		
		comRepo.save(c);
		
		return "Commentary saved.";
	}
	
	@GetMapping(path = "/all")
	public @ResponseBody Iterable<CommentaryData> getComments(){
		return comRepo.findAll();
	}
}
