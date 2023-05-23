package rva.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rva.model.Artikl;
import rva.repository.ArtiklRepository;

@Service
public class ArtiklService {

	@Autowired
	private ArtiklRepository repo;
	
	public List<Artikl> findAll(){
		return repo.findAll();
	}
	
	public Optional<Artikl> findById(long id){
		return repo.findById(id);
	}
	
	public Optional<List<Artikl>> findByNaziv(String naziv){
		Optional<List<Artikl>> lista = Optional.of(repo.findByNazivContainingIgnoreCase(naziv));
		return lista;
	}
	
	public Artikl save(Artikl artikl) {
		return repo.save(artikl);
	}
	
	public boolean existsById(long id) {
		if(findById(id).isPresent()) {
			return true;
		}else {
			return false;
		}
	}
	
	public void deleteById(long id) {
		repo.deleteById(id);
	}
	
	
}
