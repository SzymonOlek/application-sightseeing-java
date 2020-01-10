package com.project.sightseeing.Ban;

<<<<<<< Updated upstream
=======
import java.util.ArrayList;

>>>>>>> Stashed changes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
<<<<<<< Updated upstream
=======
import org.springframework.web.bind.annotation.PathVariable;
>>>>>>> Stashed changes
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

<<<<<<< Updated upstream
import com.project.sightseeing.City.CityData;
=======
import com.project.sightseeing.Admin.Admin;
import com.project.sightseeing.Admin.AdminData;
import com.project.sightseeing.City.CityData;
import com.project.sightseeing.Object.ObjectData;
import com.project.sightseeing.Sysuser.Sysuser;
import com.project.sightseeing.Sysuser.SysuserData;
import com.project.sightseeing.Sysuser.SysuserDataRepository;
import com.project.sightseeing.User.User;
>>>>>>> Stashed changes

@Controller
@RequestMapping(path = "/ban")
public class BanDataController {
	@Autowired
<<<<<<< Updated upstream
	
	private BanDataRepository banRepo;
	
	@PostMapping(path = "/add")
	public @ResponseBody String addBan(@ModelAttribute BanData banToAdd)  {
//		BanData b = new BanData();
		
		banToAdd.setBan_id((int)banRepo.count() + 1);
=======

	private BanDataRepository banRepo;
	@Autowired

	private SysuserDataRepository userRepo;

	@Autowired
	private SysuserDataRepository usersRepo;

	@PostMapping(path = "/add")
	public @ResponseBody String addBan(@ModelAttribute BanData banToAdd) {
//		BanData b = new BanData();

		banToAdd.setBan_id((int) banRepo.count() + 1);
>>>>>>> Stashed changes
//		b.setAdmin_id(admin_id);
//		b.setUser_id(user_id);
//		b.setBan_type(ban_type);
//		b.setDate_by(date_by);
//		b.setDate_since(date_since);
//		
		banRepo.save(banToAdd);
		return "Ban saved.";
	}
<<<<<<< Updated upstream
	
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
=======

	@GetMapping(path = "/all")
	public String getBans(Model model) {
		model.addAttribute("bans", banRepo.findAll());
		return "bandata";
	}

	@GetMapping(path = "/add")
	public String addBans(Model model) {
		model.addAttribute("ban", new BanData());
		return "formban";
	}

	@GetMapping(path = "/chooseUser")
	public String chooseUserToBan(Model model) {

		model.addAttribute("sysusers", userRepo.findAll());

		return "chooseUserToBan";
	}

	@GetMapping(path = "/banSpecifiedUser/{userID}")
	public String banSpecifiedUser(@PathVariable("userID") String userID, Model model) {

		int val = Integer.parseInt(userID);
		ArrayList<ObjectData> objects = new ArrayList<ObjectData>();
		SysuserData user = null;

		User u =  new User();
		if(u.getUser() instanceof Admin) {
			AdminData admin = ((Admin)u.getUser()).getAdminData();
//		
		for (SysuserData entry : usersRepo.findAll()) {
			if (entry.getUser_id() == val) {
				user = entry;
				break;
			}
		}

		if (user == null) {
			return "error";
		} else {

			BanData ban = new BanData();

			ban.setUser_id(user.getUser_id());
			ban.setAdmin_id(admin.getAdmin_id());

			model.addAttribute("ban", ban);
			model.addAttribute("User", user);

			return "banSpecifiedUser";
		}
		}else {
			return " Nie jestes ADMINEM";
		}
	}
	@PostMapping(path = "/banSpecifiedUser")
	public @ResponseBody String addbanSpecifiedUser(@ModelAttribute BanData banToAdd) {

		banToAdd.setBan_id((int) banRepo.count() + 1);
		
		banRepo.save(banToAdd);
		
		return "Ban saved.";

	}

>>>>>>> Stashed changes
}
