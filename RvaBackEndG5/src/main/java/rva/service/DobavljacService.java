package rva.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rva.model.Dobavljac;
import rva.repository.DobavljacRepository;

@Service
public class DobavljacService {

	@Autowired
	private DobavljacRepository repo;
	
	public Optional<Dobavljac> getById(long id){
		return repo.findById(id);
	}
}
