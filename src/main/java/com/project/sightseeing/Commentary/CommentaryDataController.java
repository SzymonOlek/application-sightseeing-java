package com.project.sightseeing.Commentary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.sightseeing.Route.RouteData;

@Controller
@RequestMapping(path = "/comm")
public class CommentaryDataController {
	@Autowired
	private CommentaryDataRepository comRepo;
	
	@PostMapping(path = "/add")
	public @ResponseBody String addCommentary(@ModelAttribute CommentaryData commentToAdd) {
//		CommentaryData c = new CommentaryData();
		
		commentToAdd.setCommentary_id((int)comRepo.count() + 1);
//		c.setObject_id(object_id);
//		c.setUser_id(user_id);
//		c.setComment_date(comment_date);
//		c.setRate(rate);
//		c.setContents(contents);
		comRepo.save(commentToAdd);
		
		return "Commentary saved.";
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
}
