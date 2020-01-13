package com.project.sightseeing.Object;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.util.UserDataAttribute;
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
import org.springframework.web.servlet.view.RedirectView;

import com.project.sightseeing.Admin.Admin;
import com.project.sightseeing.Admin.AdminData;
import com.project.sightseeing.Admin.AdminDataRepository;
import com.project.sightseeing.City.CityData;
import com.project.sightseeing.City.CityDataRepository;
import com.project.sightseeing.Commentary.CommentaryData;
import com.project.sightseeing.Commentary.CommentaryDataRepository;
import com.project.sightseeing.Others.Values;
import com.project.sightseeing.Photo.PhotoData;
import com.project.sightseeing.Photo.PhotoDataRepository;
import com.project.sightseeing.Route.RouteData;
import com.project.sightseeing.Route.RouteDataController;
import com.project.sightseeing.Route.RouteDataRepository;
import com.project.sightseeing.Sysuser.Sysuser;
import com.project.sightseeing.Sysuser.SysuserData;
import com.project.sightseeing.Sysuser.SysuserDataRepository;
import com.project.sightseeing.User.User;

@Controller
@RequestMapping(path = "/object")
public class ObjectDataController {
	
	@Autowired
	private ObjectDataRepository objRepo;
	
	@Autowired
	private CommentaryDataRepository comentRepo;
	
	@Autowired
	private AdminDataRepository adminRepo;
	
	@Autowired
	private SysuserDataRepository userRepo;
	
	@Autowired 
	private RouteDataRepository routeRepo;
	
	@Autowired
	CityDataRepository cityRepo;
	
	@Autowired
	private PhotoDataRepository photoRepo;
	
	
	@GetMapping(path = "/addCitSh")
	public String addCS(Model model) {
		model.addAttribute("cities", cityRepo.findAll());
		return "objAddCS";
	}
	
	@GetMapping(path = "/add/{cid}")
	public String addObjects(Model model,@PathVariable("cid") String cid){
		model.addAttribute("cid",Integer.parseInt(cid));
		model.addAttribute("object", new ObjectData());
		
		return "formobject";
	}
	
	@PostMapping(path = "/add/{cid}")
	public RedirectView addObject(@ModelAttribute ObjectData object,@PathVariable("cid") String cid) {
		int id = Integer.parseInt(cid);
		object.setObject_id((int)objRepo.count() + 1);	
		object.setCity_id(id);
		
		System.out.println(object.getObject_name());
		objRepo.save(object);
		
		for(CityData cd : cityRepo.findAll()) {
			if(cd.getCity_id() == id) {
				cd.setObj_quan(cd.getObj_quan() + 1);
				cityRepo.save(cd);
			}
		}
		
		return new RedirectView("http://localhost:9999/sightseeing/admin/new");
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
	
	public AdminData afindById(int id) {
		AdminData a = new AdminData();
		for(AdminData entry : adminRepo.findAll()) {
			if(entry.getAdmin_id() == id) {
				a = entry;
				break;
			}
		}
		return a;
	}
	
	@GetMapping(path = "/object/{name}")
	public String ChooseObject(@PathVariable("name") String name, Model model){
		
		int val = Integer.parseInt(name);
		ObjectData obj = new ObjectData();
		ArrayList<CommentaryData> cd = new ArrayList<CommentaryData>();
		ArrayList<String> nicks = new ArrayList<>();
		User su = (new User().getUser());
		SysuserData user = new SysuserData();
		AdminData admin = new AdminData();
		int id  = -1;
		
		for(CommentaryData entry : comentRepo.findAll()) {
			if(entry.getObject_id() == val) {
				cd.add(entry);
				if ( entry.getUser_id() > 0) {
					id = entry.getUser_id();
					nicks.add(ufindById(id).getLogin());
				}
			}
		}
		id = -1;
		for(ObjectData entry : objRepo.findAll()) {
			if(entry.getObject_id() == val) {
				obj = entry;
			}
		}
		
		model.addAttribute("nicks", nicks.toArray());
		model.addAttribute("coment",cd);
		model.addAttribute("obj", obj);
		ArrayList<PhotoData> photos = new ArrayList<PhotoData>();
		
		for(PhotoData p : photoRepo.findAll()) {
			if(p.getObject_id().equals(val)) {
				photos.add(p);
			}
		}
		
		model.addAttribute("photos", photos);
		
		
		if(su != null) {
			if (su instanceof Sysuser) {
				user = ((Sysuser)su).getSysuserData();
				id = user.getUser_id();
				model.addAttribute("usrid", id);
				return "uObjPage";
			}
		}
			return  "objPage";
	}
	
	@GetMapping(path = "/all")
	public String getObjects(Model model){
		
		model.addAttribute("objects", objRepo.findAll());
		
		return "objectdata";
	}
	
	
	@GetMapping(path = "/setDistance/{cid}/{oid}")
	public String setDistance(@PathVariable("cid") String cid,@PathVariable("oid") String oid,Model model) {
		int idCity = Integer.parseInt(cid);
		int idObject = Integer.parseInt(oid);

		ArrayList<ObjectData> otherObjectsInCity= new ArrayList<ObjectData>();
		ArrayList<Values> distanceToOtherObj= new ArrayList<Values>();
		WraperInteger arrayDist = new WraperInteger();
		for(ObjectData p : objRepo.findAll()) {
			if(p.getCity_id().equals(idCity)) {
				arrayDist.addObj(p);
				Values v = new Values();
				v.setInt(-1);
				arrayDist.addValues(v);
			}
		}	
		
		model.addAttribute("distance", arrayDist);
		model.addAttribute("cid", idCity);
		model.addAttribute("oid", idObject);
		
		

		
		return "setDistance";
	}
	
	@PostMapping(path = "/setDistance/{cid}/{oid}")
	public String addDistance(@ModelAttribute WraperInteger wraperDist, @PathVariable("cid") String cid,@PathVariable("oid") String oid,Model model) {
		int idCity = Integer.parseInt(cid);
		int idObject = Integer.parseInt(oid);
		
		cityIdToMatrix=idCity;
		objIdToMatrix=idObject;
		
		ArrayList <Values> distance = wraperDist.getDistanceToOtherObj();
		ArrayList<ObjectData> objects = wraperDist.getOtherObjectsInCity();

		for(int i=0;i<distance.size();i++) {
			System.out.println(i + "  ----  "+  distance.get(i).Int);
		}
		
		int[] distTable = new int [distance.size()];
		
		for(int i=0;i<distance.size();i++) {
			distTable[i] = distance.get(i).getInt();
		}
		
		CalculateDistanceToOtherObj(distTable);
		
		
		
		return "zapisywanie obiektu";
	}
	
	private int V = 9;
	private int tableToMatrix[];
	
	public void CalculateDistanceToOtherObj(int distanceTable[]) {
		
			V = distanceTable.length;
		
		int objIndexes[] = new int [(int) objRepo.count()-1];
		int iter=0;
		for(ObjectData obj : objRepo.findAll()) {
			if(!obj.getObject_id().equals(-1)) {
			objIndexes[iter] = obj.getObject_id();
			iter+=1;
			}
		}
		
		int graph[][] = getNeighMatrix(objIndexes);
		tableToMatrix = objIndexes;

		int graphCorrect[][] = new int[graph.length + 1][graph[0].length + 1];

		for (int i = 0; i < graphCorrect.length; i++) {
			for (int j = 0; j < graphCorrect[0].length; j++) {
				if(i<graph.length && j<graph[0].length) {
				graphCorrect[i][j]=graph[i][j];
			}else {
				graphCorrect[i][j]=Integer.MAX_VALUE;
			}
				if(i==j) {
					graphCorrect[i][j]=0;
				}
			}
		}
		
		System.out.println( " tabela");
		
		for(int i=0;i<distanceTable.length;i++) {
			
			System.out.println( " tabela" +distanceTable[i]);
			
			if(distanceTable[i] != -1) {
				graphCorrect[i][graphCorrect.length-1] = distanceTable[i];
				graphCorrect[graphCorrect.length-1][i] = distanceTable[i];
			}else
			{
				graphCorrect[i][graphCorrect.length-1] = Integer.MAX_VALUE;
				graphCorrect[graphCorrect.length-1][i] = Integer.MAX_VALUE;
			}
			
		}
		
		
		
	    this.dijkstra(graphCorrect, graphCorrect.length-1); 

	}

	int minDistance(int dist[], Boolean sptSet[]) {
		// Initialize min value
		int min = Integer.MAX_VALUE, min_index = -1;

		for (int v = 0; v < V; v++)
			if (sptSet[v] == false && dist[v] <= min) {
				min = dist[v];
				min_index = v;
			}

		return min_index;
	}

	// A utility function to print the constructed distance array
	void printSolution(int dist[], int n, int graph[][]) {
		System.out.println("Vertex   Distance from Source");
		for (int i = 0; i < V; i++) {

			graph[i][n - 1] = dist[i];
			graph[n - 1][i] = dist[i];
			System.out.println(i + "  ----  " + dist[i]);
		}
		for (int i = 0; i < graph.length; i++) {
			for (int j = 0; j < graph[0].length; j++) {

				System.out.print(graph[i][j] + " ");

			}
			System.out.println("");
		}

		for(CityData cd : cityRepo.findAll()) {
			if(cd.getCity_id() == cityIdToMatrix) {
				cd.setObj_quan(cd.getObj_quan() + 1);
				cityRepo.save(cd);
			}
		}
		
		ObjectData newObj = new ObjectData();
		
		newObj.setCity_id(cityIdToMatrix);
		newObj.setDescription("aaaaaaaaaaaaaaaaaa");
		newObj.setLocalization("gdzies");
		newObj.setObject_name("nazwa");
		
		objRepo.save(newObj);
		
		objIdToMatrix=newObj.getObject_id();
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+objIdToMatrix);
		
		for(int i=0;i<dist.length;i++) {
			
			RouteData routeToAdd = new RouteData();
			
			routeToAdd.setDistance(dist[i]);
			
			routeToAdd.setCity_id(cityIdToMatrix);
			if(i==dist.length-1) routeToAdd.setObject_1_id(objIdToMatrix);
			else			
			routeToAdd.setObject_1_id(tableToMatrix[i]);

			routeToAdd.setObject_2_id(objIdToMatrix);
			
			routeRepo.save(routeToAdd);
			
		}
		
	}

	// Function that implements Dijkstra's single source shortest path
	// algorithm for a graph represented using adjacency matrix
	// representation
	void dijkstra(int graph[][], int src) {
		V = graph[0].length;

		int dist[] = new int[V]; // The output array. dist[i] will hold
		// the shortest distance from src to i

		// sptSet[i] will true if vertex i is included in shortest
		// path tree or shortest distance from src to i is finalized
		Boolean sptSet[] = new Boolean[V];

		// Initialize all distances as INFINITE and stpSet[] as false
		for (int i = 0; i < V; i++) {
			dist[i] = Integer.MAX_VALUE;
			sptSet[i] = false;
		}

		// Distance of source vertex from itself is always 0
		dist[src] = 0;

		// Find shortest path for all vertices
		for (int count = 0; count < V - 1; count++) {
			// Pick the minimum distance vertex from the set of vertices
			// not yet processed. u is always equal to src in first
			// iteration.
			int u = minDistance(dist, sptSet);

			// Mark the picked vertex as processed
			sptSet[u] = true;

			// Update dist value of the adjacent vertices of the
			// picked vertex.
			for (int v = 0; v < V; v++)

				// Update dist[v] only if is not in sptSet, there is an
				// edge from u to v, and total weight of path from src to
				// v through u is smaller than current value of dist[v]
				if (!sptSet[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v])
					dist[v] = dist[u] + graph[u][v];
		}

		// print the constructed distance array
		

		
		printSolution(dist, V, graph);
	}
	
	
	

	private int cityIdToMatrix;

	private int objIdToMatrix;
	
	
	
	public int [][] getNeighMatrix(int [] objects) {
		int len = objects.length;
		int [][] matrix = new int[len][len];
		
		System.out.println("matrix");
		for(int i = 0; i < len; i++) {
			for(int j = 0; j < len; j++) {
				if(j == i) {
					matrix[j][i]=0;
				}
				for(RouteData r : routeRepo.findAll()) {
					if(r.getCity_id() == cityIdToMatrix && r.getObject_1_id() == objects[i] && r.getObject_2_id() == objects[j]) {
						matrix[i][j] = r.getDistance();
						matrix[j][i] = r.getDistance();
					}
				}
			}
			
		}

		System.out.println("matrix - done");
		return matrix;
	}

}
