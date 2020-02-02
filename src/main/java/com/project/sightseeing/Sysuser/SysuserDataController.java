package com.project.sightseeing.Sysuser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.view.RedirectView;

import com.project.sightseeing.SightseeingApplication;
import com.project.sightseeing.Template;
import com.project.sightseeing.Commentary.CommentaryData;
import com.project.sightseeing.Commentary.CommentaryDataRepository;
import com.project.sightseeing.User.User;


@Controller
@RequestMapping(path="/user")
public class SysuserDataController {

	@Autowired
	private SysuserDataRepository userRepo;
	@Autowired
	private CommentaryDataRepository commRepo;
	
	@GetMapping(path = "/cor")
	public String corespondence(Model model) {
		ArrayList<CommentaryData> cor = new ArrayList<CommentaryData>();
		String session = RequestContextHolder.currentRequestAttributes().getSessionId();
		Sysuser s = new Sysuser();
		int userId = -1;
		for(Map.Entry<String , User> entry : SightseeingApplication.loggedInUsers.entrySet()) {
			if (entry.getKey().equals(session)) {
				s = (Sysuser)entry.getValue();
				userId=s.getSysuserData().getUser_id();
				}
			}
		for(CommentaryData cd : commRepo.findAll()) {
			if(cd.getObject_id() == -1 && cd.getUser_id() == userId && !cd.getContents().contains("!@#$%")) {
				cor.add(cd);
			}
		}
		model.addAttribute("messages", cor);
		return "userMsg";
	}
	
	@GetMapping(path = "/msg")
	public String newMsg(Model model) {
		CommentaryData cd = new CommentaryData();
		model.addAttribute("message", cd);
		return "userNewMsg";
	}
	@PostMapping(path = "/msg")
	public RedirectView newMsg(@ModelAttribute CommentaryData message) {
		String session = RequestContextHolder.currentRequestAttributes().getSessionId();
		Sysuser s = new Sysuser();
		int userId = -1;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
		LocalDateTime now = LocalDateTime.now();
		for(Map.Entry<String , User> entry : SightseeingApplication.loggedInUsers.entrySet()) {
			if (entry.getKey().equals(session)) {
				s = (Sysuser)entry.getValue();
				userId=s.getSysuserData().getUser_id();
				}
			}
		message.setObject_id(-1);
		message.setUser_id(userId);
		message.setComment_date(dtf.format(now));
		message.setCommentary_id((int)commRepo.count() + 1);
		commRepo.save(message);
		return new RedirectView("http://localhost:9999/sightseeing/user/cor");
	}
	@GetMapping(path="/change")
	public String accChange(Model model) {
		String session = RequestContextHolder.currentRequestAttributes().getSessionId();
		Sysuser s;
		for(Map.Entry<String , User> entry : SightseeingApplication.loggedInUsers.entrySet()) {
			if (entry.getKey().equals(session)) {
				s = (Sysuser)entry.getValue();
				model.addAttribute("user", s.getSysuserData());
				}
			}
		return "formUserChange";
	}

	@PostMapping(path = "/change")
	public RedirectView accCh(@ModelAttribute SysuserData sd) {
		
		String session = RequestContextHolder.currentRequestAttributes().getSessionId();
		Sysuser s = new Sysuser();
		SysuserData nData = new SysuserData();
		for(Map.Entry<String , User> entry : SightseeingApplication.loggedInUsers.entrySet()) {
			if (entry.getKey().equals(session)) {
				s = (Sysuser)entry.getValue();
				}
			}
		
		nData = s.getSysuserData();
		nData.setF_name(sd.getF_name());
		nData.setL_name(sd.getL_name());
		nData.setLogin(sd.getLogin());
		nData.setPasswd(sd.getPasswd());
		nData.setPasswd(sd.getPasswd());
		
		userRepo.save(nData);
		
		
		return new RedirectView("http://localhost:9999/sightseeing/user/acc");
	}
	
	@GetMapping(path = "acc")
	public String account(Model model) {
		
		String session = RequestContextHolder.currentRequestAttributes().getSessionId();
		Sysuser s;
		for(Map.Entry<String , User> entry : SightseeingApplication.loggedInUsers.entrySet()) {
			if (entry.getKey().equals(session)) {
				s = (Sysuser)entry.getValue();
				model.addAttribute("user", s.getSysuserData());
				return "userAcc";
				}
			}
		return "blad";
	}

	@GetMapping(path = "/del")
	public String delAcc(Model model) {
		return "delUser";
	}
	
	@GetMapping(path = "/del/conf")
	public RedirectView delUser () {
		
		String session = RequestContextHolder.currentRequestAttributes().getSessionId();
		Sysuser s = new Sysuser();
		Sysuser su;
		for(Map.Entry<String , User> entry : SightseeingApplication.loggedInUsers.entrySet()) {
			if (entry.getKey().equals(session)) {
				s = (Sysuser)entry.getValue();
				}
			}
		userRepo.delete(s.getSysuserData());
		for(Map.Entry<String , User> entry : SightseeingApplication.loggedInUsers.entrySet()) {
			su = (Sysuser)entry.getValue();
			if (su.equals(s)) {
				SightseeingApplication.loggedInUsers.remove(entry.getKey());
				}
			}
		return new RedirectView("http://localhost:9999/sightseeing/home");
	}

	@GetMapping(path="/add")
	public String addUsers(Model model){
		  model.addAttribute("sysuser",new SysuserData() );
		return "formsysuser";
	}

	@PostMapping(path="/add")
	public String addUser(@ModelAttribute SysuserData userToAdd) {
		userToAdd.setComment_num(0);
		userToAdd.setUser_id((int)(userRepo.count()) + 1);
		
		userRepo.save(userToAdd);
		return "regSuc";
	}
	
	@GetMapping(path="/all")
	public String getUsers(Model model){
		  model.addAttribute("sysusers",userRepo.findAll() );
		return "sysusersdata";
	}
}
