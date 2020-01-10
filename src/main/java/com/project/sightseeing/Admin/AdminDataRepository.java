package com.project.sightseeing.Admin;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

@Controller
public interface AdminDataRepository extends CrudRepository<AdminData, Integer>{

}
