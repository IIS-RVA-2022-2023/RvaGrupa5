package rva.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import rva.model.Artikl;
import rva.service.ArtiklService;

@RestController
public class ArtiklController {

	@Autowired
	private ArtiklService service;
	
	@GetMapping("/hello")
	public ResponseEntity<?> sayHello(){
		return new ResponseEntity<>("Hello!", HttpStatus.OK);
	}
	
	@GetMapping("/artikl")
	public ResponseEntity<List<Artikl>> getAllArtikl(){
		List<Artikl> artikli = service.getAll();
		return new ResponseEntity<>(artikli,HttpStatus.OK);
	}
	
	@GetMapping("/artikl/{id}")
	public ResponseEntity<?> getArtiklById(@PathVariable long id){
		if(service.existsById(id)) {
			return ResponseEntity.ok(service.getById(id));
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artikl with requested id is not found!");
		}
	}
	
	@GetMapping("/artikl/naziv/{naziv}")
	public ResponseEntity<?> getArtiklByNaziv(@PathVariable String naziv){
		if(!service.getByNaziv(naziv).get().isEmpty()) {
			return ResponseEntity.ok(service.getByNaziv(naziv));
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).
					body("Artikls containing requested naziv:" + naziv +" do not exist" );
		}
	}
}
