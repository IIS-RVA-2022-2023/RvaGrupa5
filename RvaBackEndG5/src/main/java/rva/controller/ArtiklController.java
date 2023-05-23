package rva.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rva.model.Artikl;
import rva.service.ArtiklService;

@CrossOrigin
@RestController
public class ArtiklController {

	@Autowired
	private ArtiklService service;
	
	
	@GetMapping("/hello")
	public String sayHello() {
		return "Hello there!";
	}
	
	@GetMapping("/artikl")
	public ResponseEntity<List<Artikl>> findAllArtikls(){
		return ResponseEntity.ok(service.findAll());
	}
	
	@GetMapping("/artikl/{id}")
	public ResponseEntity<?> getArtiklById(@PathVariable long id){
		if(service.existsById(id)) {
			return ResponseEntity.ok(service.findById(id));
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resource with requested ID: " + id + " does not exist");
		}
	}
	
	@GetMapping("/artikl/naziv/{naziv}")
	public ResponseEntity<?> getArtiklsByNaziv(@PathVariable String naziv){
		
		if(service.findByNaziv(naziv).get().isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resources with requested Naziv: " + naziv + " does not exist");
		}else {
			return ResponseEntity.ok(service.findByNaziv(naziv).get());
			//return ResponseEntity.status(HttpStatus.OK).body(service.findByNaziv(naziv).get());
		}
	}
	
	@PostMapping("/artikl")
	public ResponseEntity<?> createArtikl(@RequestBody Artikl artikl){
		Artikl savedArtikl;
		if(!service.existsById(artikl.getId())) {
			savedArtikl = service.save(artikl);
		}else {
			List<Artikl> lista = service.findAll();
			long najvecaVrednost = 1;
			for(int i = 0; i< lista.size(); i++) {
				if(najvecaVrednost <= lista.get(i).getId()) {
					najvecaVrednost = lista.get(i).getId();
				}
				
				if(i == lista.size() - 1) {
					najvecaVrednost++;
				}
				
			}
			artikl.setId(najvecaVrednost);
			savedArtikl = service.save(artikl);
			
		}
		URI uri = URI.create("/artikl/" + savedArtikl.getId());
		return ResponseEntity.created(uri).body(savedArtikl);
	}
	
	@PutMapping("/artikl/{id}")
	public ResponseEntity<?> updateArtikl(@RequestBody Artikl artikl, @PathVariable long id){
		if(service.existsById(id)) {
			artikl.setId(id);
			Artikl updatedArtikl = service.save(artikl);
			return ResponseEntity.ok(updatedArtikl);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resource with requested ID: " + id + " has not been found");
		}
	}
	
	@DeleteMapping("/artikl/{id}")
	public ResponseEntity<?> deleteArtikl(@PathVariable long id){
		if(service.existsById(id)) {
			service.deleteById(id);
			return ResponseEntity.ok("Resource with ID: " + id + " has been deleted");
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resource with requested ID: " + id + " has not been found");
		}
	}
	
}
