package rva.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rva.model.Dobavljac;
import rva.model.Porudzbina;
import rva.repository.PorudzbinaRepository;

@Service
public class PorudzbinaService {

	@Autowired
	private PorudzbinaRepository repo;
	
	public List<Porudzbina> getAll(){
		return repo.findAll();
	}
	
	public Optional<Porudzbina> getById(long id){
		return repo.findById(id);
	}
	
	//Obelezje double/numeric
	public Optional<List<Porudzbina>> getByIznosGreaterThan(double iznos){
		Optional<List<Porudzbina>> lista = Optional.of(repo.findByIznosGreaterThanOrderById(iznos));
		return lista;
	}
	
	//Obelezje boolean
	public Optional<List<Porudzbina>> getByPlaceno(){
		Optional<List<Porudzbina>> lista = Optional.of(repo.findByPlacenoTrue());
		return lista;
	}
	
	//Pretraga po atributu dobavljac iz entity klase Porudzbina
	public Optional<List<Porudzbina>> getByDobavljac(Dobavljac dobavljac){
		return Optional.of(repo.findByDobavljac(dobavljac));
	}
	
	public Porudzbina save(Porudzbina porudzbina) {
		return repo.save(porudzbina);
	}
	
	public boolean existsById(long id) {
		if(getById(id).isPresent()) {
			return true;
		}else {
			return false;
		}
	}
	
	public void deleteById(long id) {
		repo.deleteById(id);
	}
}
