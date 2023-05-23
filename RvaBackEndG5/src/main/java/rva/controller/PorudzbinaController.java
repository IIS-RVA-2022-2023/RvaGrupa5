package rva.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rva.model.Dobavljac;
import rva.model.Porudzbina;
import rva.service.DobavljacService;
import rva.service.PorudzbinaService;

@CrossOrigin
@RestController
@RequestMapping("porudzbina")
public class PorudzbinaController {

	@Autowired
	private PorudzbinaService service;
	
	@Autowired
	private DobavljacService dobavljacService;
	
	@GetMapping
	public ResponseEntity<List<Porudzbina>> getAllPorudzbine(){
		return ResponseEntity.ok(service.getAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getPorudzbinaById(@PathVariable long id){
		if(service.getById(id).isPresent()) {
			return ResponseEntity.ok(service.getById(id).get());
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resource with requested ID: " + id + " has not been found");
		}
	}
	
	@GetMapping("/iznos/{iznos}")
	public ResponseEntity<?> getPorudzbinaByIzosGreaterThan(@PathVariable double iznos){
		Optional<List<Porudzbina>> lista = service.getByIznosGreaterThan(iznos);
		if(!lista.get().isEmpty()) {
			return ResponseEntity.ok(lista.get());
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resources with iznos greater than: " + iznos + " have not been found");
		}
	}
	
	@GetMapping("/dobavljac/{id}")
	public ResponseEntity<?> getPorudzbinaByDobavljac(@PathVariable long id){
		Optional<Dobavljac> dobavljac = dobavljacService.findById(id);
		if(dobavljac.isPresent()) {
			List<Porudzbina> lista = service.getByDobavljac(dobavljac.get()).get();
			if(!lista.isEmpty()) {
				return ResponseEntity.ok(lista);
			}else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("Resources with requested foreign key dobavljac: " + id + 
								" do not exist");
			}
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Dobavljac with ID: " + id + " does not exist");
		}
		
	}
	
	
	@PostMapping
	public ResponseEntity<Porudzbina> createPorudzbina(@RequestBody Porudzbina porudzbina){
		Porudzbina savedPorudzbina;
		if(!service.existsById(porudzbina.getId())) {
			savedPorudzbina = service.save(porudzbina);
		}else {
			List<Porudzbina> lista = service.getAll();
			long najvecaVrednost = 1;
			for(int i = 0; i< lista.size(); i++) {
				if(najvecaVrednost <= lista.get(i).getId()) {
					najvecaVrednost = lista.get(i).getId();
				}
				
				if(i == lista.size() - 1) {
					najvecaVrednost++;
				}
				
			}
			porudzbina.setId(najvecaVrednost);
			savedPorudzbina = service.save(porudzbina);
			
		}
		URI uri = URI.create("/porudzbina/" + savedPorudzbina.getId());
		return ResponseEntity.created(uri).body(savedPorudzbina);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updatePorudzbina(@RequestBody Porudzbina porudzbina, @PathVariable long id){
		if(service.existsById(id)) {
			porudzbina.setId(id);
			Porudzbina savedPorudzbina = service.save(porudzbina);
			return ResponseEntity.ok(savedPorudzbina);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resource with requested ID: " + id + " has not been found");
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePorudzbina(@PathVariable long id){
		if(service.existsById(id)) {
			service.deleteById(id);
			return ResponseEntity.ok("Resource with ID: " + id + " has been deleted");
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resource with ID: " + id + " has not been found");
		}
	}
	
	
	
	
	
	
	
}
