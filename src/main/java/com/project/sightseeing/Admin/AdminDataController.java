package com.project.sightseeing.Admin;

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
import com.project.sightseeing.Ban.BanData;
import com.project.sightseeing.Ban.BanDataRepository;
import com.project.sightseeing.City.CityData;
import com.project.sightseeing.City.CityDataRepository;
import com.project.sightseeing.Commentary.CommentaryData;
import com.project.sightseeing.Commentary.CommentaryDataRepository;
import com.project.sightseeing.Object.ObjectData;
import com.project.sightseeing.Object.ObjectDataRepository;
import com.project.sightseeing.Route.RouteData;
import com.project.sightseeing.Route.RouteDataRepository;
import com.project.sightseeing.Sysuser.Sysuser;
import com.project.sightseeing.Sysuser.SysuserData;
import com.project.sightseeing.Sysuser.SysuserDataRepository;
import com.project.sightseeing.User.User;

@Controller
@RequestMapping(path = "/admin")
public class AdminDataController {
	@Autowired
	private AdminDataRepository adminRepo;
	@Autowired
	SysuserDataRepository userRepo;
	@Autowired
	BanDataRepository banRepo;
	@Autowired
	CityDataRepository cityRepo;
	@Autowired
	CommentaryDataRepository commRepo;
	@Autowired
	RouteDataRepository routeRepo;
	@Autowired
	ObjectDataRepository objRepo;
	
	private boolean duplicates(ArrayList<CorThread> th, int id) {
		for(CorThread cd : th) {
			if(cd.id == id)
				return true;
		}
		return false;
	}
	
	@GetMapping(path = "/thread/{uid}")
	public String corespondence(@PathVariable("uid") String uid, Model model) {
		int userId = Integer.parseInt(uid);
		ArrayList<CommentaryData> cor = new ArrayList<CommentaryData>();
		for(CommentaryData cd : commRepo.findAll()) {
			if(cd.getObject_id() == -1 && cd.getUser_id() == userId) {
				cor.add(cd);
			}
		}
		model.addAttribute("messages", cor);
		return "adminMsg";
	}
	@GetMapping(path = "/cor")
	public String corespondenceThreads(Model model) {
		int corId = -1;
		ArrayList<CorThread> threads = new ArrayList<CorThread>();
		for(CommentaryData cd : commRepo.findAll()) {
				if(cd.getObject_id() == corId && !duplicates(threads, cd.getUser_id())) {
					threads.add(new CorThread(cd.getUser_id()));
				}
		}
		for(CorThread ct : threads) {
			for(SysuserData cd : userRepo.findAll()) {
				if(ct.id == cd.getUser_id()) {
					ct.nick = cd.getLogin();
				}
			}
		}
		model.addAttribute("threads", threads);
		return "adminThreads";
	}
	
	
	@GetMapping(path = "/obj")
	public String delObj(Model model) {
		ArrayList<CityData> cit = new ArrayList<CityData>();
		for(CityData cd : cityRepo.findAll()) {
			if(cd.getCity_id() != -1) {
				cit.add(cd);
			}
		}
		model.addAttribute("cities",cit);
		return "adminDelObjC";
	}
	
	@GetMapping(path = "/objSh/{cid}")
	public String objSj(Model model, @PathVariable("cid") String cid) {
		int id = Integer.parseInt(cid);
		ArrayList<ObjectData> obj = new ArrayList<ObjectData>();
		for(ObjectData od : objRepo.findAll()) {
			if(od.getCity_id() == id) {
				obj.add(od);
			}
		}
		model.addAttribute("objs", obj);
		return "adminDelObjSh";
	}
	
	@GetMapping(path = "/objdelconf/{oid}/{cid}")
	public String delObjConf(Model model, @PathVariable("oid") String oid,  @PathVariable("cid") String cid) {
		model.addAttribute("cid",Integer.parseInt(oid));
		model.addAttribute("cid", Integer.parseInt(cid));
		return "adminDelObjConf";
	}
	
	@PostMapping(path = "/objdelconf/{oid}/{cid}")
	public RedirectView delObjConf(@PathVariable("oid") String oidd, @PathVariable("cid") String cidd) {
		int cid = Integer.parseInt(oidd);
		for(RouteData rd : routeRepo.findAll()) {
			if(rd.getObject_1_id()==cid || rd.getObject_2_id() == cid) {
				routeRepo.delete(rd);
			}
		}
		for(CommentaryData com : commRepo.findAll()) {
			if(com.getObject_id() == cid) {
				commRepo.delete(com);
			}
		}
		for(ObjectData ob : objRepo.findAll()) {
			if(ob.getObject_id() == cid) {
				objRepo.delete(ob);
				break;
			}
		}
		
		for(CityData cd : cityRepo.findAll()) {
			if(cd.getCity_id() == Integer.parseInt(cidd)) {
				cd.setObj_quan(cd.getObj_quan() - 1);
				cityRepo.save(cd);
			}
		}
		
		return new RedirectView("http://localhost:9999/sightseeing/");
	}
	
	@GetMapping(path = "/city")
	public String delCity(Model model) {
		ArrayList<CityData> cit = new ArrayList<CityData>();
		for(CityData cd : cityRepo.findAll()) {
			if(cd.getCity_id() != -1) {
				cit.add(cd);
			}
		}
		model.addAttribute("cities",cit);
		return "adminDelCity";
	}
	
	@GetMapping(path = "/citydelconf/{cid}")
	public String delCityConf(Model model, @PathVariable("cid") String cid) {
		model.addAttribute("cid",Integer.parseInt(cid));
		return "adminDelCityConf";
	}
	
	@PostMapping(path = "/citydelconf/{cid}")
	public RedirectView delCityConf(@PathVariable("cid") String cidd) {
		int cid = Integer.parseInt(cidd);
		for(RouteData rd : routeRepo.findAll()) {
			if(rd.getCity_id()==cid) {
				routeRepo.delete(rd);
			}
		}
		for(ObjectData ob : objRepo.findAll()) {
			if(ob.getCity_id() == cid) {
				for(CommentaryData com : commRepo.findAll()) {
					if(ob.getObject_id() == com.getObject_id()) {
						commRepo.delete(com);
					}
				}
				objRepo.delete(ob);
			}
		}
		for(CityData cd : cityRepo.findAll()) {
			if(cid == cd.getCity_id()) {
				cityRepo.delete(cd);
				break;
			}
		}
		return new RedirectView("http://localhost:9999/sightseeing/admin/del");
	}
	
	@GetMapping(path = "/user")
	public String delUser(Model model) {
		model.addAttribute("users", userRepo.findAll());
		return "formdelUser";
		
	}
	
	@GetMapping(path = "/delconf/{uid}")
	public String DC(Model model, @PathVariable("uid") String uid) {
		System.out.println(uid);
		model.addAttribute("uid", Integer.parseInt(uid));
		return "adminUserDelConf";
	}
	
	@PostMapping(path = "/delconf/{uid}")
	public String delUser (@PathVariable("uid") String uid) {
		System.out.println(uid);
		int id = Integer.parseInt(uid);
		for(BanData bd : banRepo.findAll()) {
			if(bd.getUser_id() == id) {
				banRepo.delete(bd);
			}
		}
		for(SysuserData entry : userRepo.findAll()) {
			if (entry.getUser_id() == id) {
				userRepo.delete(entry);
				}
			}
		return "delSucc";
	}
	
	@GetMapping(path="/change")
	public String accChange(Model model) {
		String session = RequestContextHolder.currentRequestAttributes().getSessionId();
		Admin s;
		for(Map.Entry<String , User> entry : SightseeingApplication.loggedInUsers.entrySet()) {
			if (entry.getKey().equals(session)) {
				s = (Admin)entry.getValue();
				model.addAttribute("admin", s.getAdminData());
				}
			}
		return "formAdminChange";
	}

	@PostMapping(path = "/change")
	public RedirectView accCh(@ModelAttribute AdminData sd) {
		String session = RequestContextHolder.currentRequestAttributes().getSessionId();
		AdminData nData = new AdminData();
		Admin s = new Admin();
		for(Map.Entry<String , User> entry : SightseeingApplication.loggedInUsers.entrySet()) {
			if (entry.getKey().equals(session)) {
				s = (Admin)entry.getValue();
				}
			}
		
		nData = s.getAdminData();
		nData.setF_name(sd.getF_name());
		nData.setL_name(sd.getL_name());
		nData.setLogin(sd.getLogin());
		nData.setPasswd(sd.getPasswd());
		nData.setPasswd(sd.getPasswd());
		
		adminRepo.save(nData);
		return new RedirectView("http://localhost:9999/sightseeing/admin/acc");
	}
	
	@GetMapping(path = "/acc")
	public String account(Model model) {
		String session = RequestContextHolder.currentRequestAttributes().getSessionId();
		Admin s;
		for(Map.Entry<String , User> entry : SightseeingApplication.loggedInUsers.entrySet()) {
			if (entry.getKey().equals(session)) {
				s = (Admin)entry.getValue();
				model.addAttribute("admin", s.getAdminData());
				return "adminAcc";
				}
			}
		return "blad";
	}
	
	@GetMapping(path = "/del")
	public String delete() {
		return "delForm";
	}
	@GetMapping(path = "/new")
	public String addObj() {
		return "addForm";
	}
	
	@GetMapping(path = "/ban")
	public String banUser(Model model) {
		model.addAttribute("sysusers", userRepo.findAll());
		model.addAttribute("bans", banRepo);
		return "adminBan";
	}
	
	@PostMapping(path = "/add")
	public @ResponseBody String addAdmin(@ModelAttribute AdminData adminToAdd) {
		adminRepo.save(adminToAdd);
		return "Admin saved.";
	}
	
	@GetMapping(path = "/all")
	public String getAdmins(Model model){
		model.addAttribute("admins", adminRepo.findAll());
		return "admindata";
	}
	
	@GetMapping(path = "/add")
	public String addAdmin(Model model){
		model.addAttribute("admin", new AdminData());
		return "formadmin";
	}
}
