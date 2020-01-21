package com.project.sightseeing;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import com.project.sightseeing.Admin.AdminDataRepository;
import com.project.sightseeing.Ban.BanDataRepository;
import com.project.sightseeing.City.CityDataRepository;
import com.project.sightseeing.Commentary.CommentaryDataRepository;
import com.project.sightseeing.Object.ObjectDataRepository;
import com.project.sightseeing.Photo.PhotoDataRepository;
import com.project.sightseeing.Route.RouteDataRepository;
import com.project.sightseeing.Sysuser.SysuserDataController;
import com.project.sightseeing.Sysuser.SysuserDataRepository;
import com.project.sightseeing.User.UserController;

@SpringBootTest
class SightseeingApplicationTests {

	@Autowired
	private UserController controller;

	@Test
	public void CheckUserController() throws Exception {

		assertThat(controller).isNotNull();

	}

	@Autowired
	private SysuserDataController controllerSysUser;

	@Test
	public void CheckSysUserController() throws Exception {

		assertThat(controllerSysUser).isNotNull();

	}

	@Autowired
	private ObjectDataRepository objRepo;

	@Test
	public void CheckObjectDataRepository() throws Exception {

		assertThat(objRepo).isNotNull();

	}

	@Autowired
	private CommentaryDataRepository comentRepo;

	@Test
	public void CheckCommentaryDataRepository() throws Exception {

		assertThat(comentRepo).isNotNull();

	}

	@Autowired
	private AdminDataRepository adminRepo;

	@Test
	public void CheckAdminDataRepository() throws Exception {

		assertThat(adminRepo).isNotNull();

	}

	@Autowired
	private SysuserDataRepository userRepo;

	@Test
	public void CheckSysuserDataRepository() throws Exception {

		assertThat(userRepo).isNotNull();

	}

	@Autowired
	CityDataRepository cityRepo;

	@Test
	public void CheckCityDataRepository() throws Exception {

		assertThat(cityRepo).isNotNull();

	}

	@Autowired
	RouteDataRepository routeRepo;

	@Test
	public void CheckRouteDataRepository() throws Exception {

		assertThat(routeRepo).isNotNull();

	}

	@Autowired
	PhotoDataRepository photoRepo;

	@Test
	public void CheckPhotoDataRepository() throws Exception {

		assertThat(photoRepo).isNotNull();

	}
	
	@Test
	public void CheckIfRouteDataRepository() throws Exception {

		assertNotEquals(null, routeRepo.findAll(), "puste repozytorium");

	}

	@Test
	public void CheckIfPhotoDataRepositoryEmpty() throws Exception {

		assertNotEquals(null, photoRepo.findAll(), "puste repozytorium");

	}

	@Test
	public void CheckIfCityDataRepository() throws Exception {

		assertNotEquals(null, cityRepo.findAll(), "puste repozytorium");

	}

	@Test
	public void CheckIfSysuserDataRepository() throws Exception {

		assertNotEquals(null, userRepo.findAll(), "puste repozytorium");

	}

	@Test
	public void CheckIfAdminDataRepository() throws Exception {

		assertNotEquals(null, adminRepo.findAll(), "puste repozytorium");

	}
	
	@Autowired
	private CommentaryDataRepository comRepo;
	@Autowired
	private BanDataRepository banRepo;
	@Test
	public void CheckIfCommentaryDataRepository() throws Exception {

		assertNotEquals(null, comRepo.findAll(), "puste repozytorium");

	}
	@Test
	public void CheckIfBanDataRepository() throws Exception {

		assertNotEquals(null, banRepo.findAll(), "puste repozytorium");

	}
	
}
