package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import service.PacijentService;

@RestController
public class PacijentController {

	@Autowired
	private PacijentService pacijentService;
	
}
