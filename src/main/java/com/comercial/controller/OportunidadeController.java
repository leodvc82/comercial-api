package com.comercial.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.comercial.exception.ResourceNotFoundException;
import com.comercial.model.Oportunidade;
import com.comercial.repository.OportunidadeRepository;

@CrossOrigin // or @CrossOrigin("http://localhost:4200") 
@RestController
@RequestMapping("/oportunidades")
public class OportunidadeController {

	@Autowired
    private OportunidadeRepository oportunidadeRepository;

    @GetMapping
    public Page<Oportunidade> getOportunidades(Pageable pageable) {
        return oportunidadeRepository.findAll(pageable);
    }
    
    @GetMapping("/{id}")
    public Oportunidade getOportunidade(@PathVariable Long id){
    	return oportunidadeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Oportunidade not found with id " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Oportunidade createOportunidade(@Valid @RequestBody Oportunidade oportunidade) {
    	
    	Oportunidade op = oportunidadeRepository.findByNomeProspectoAndDescricao(oportunidade.getNomeProspecto(), oportunidade.getDescricao());
    	if(op!=null) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prospecto já Existe!");
    	}
        return oportunidadeRepository.save(oportunidade);
    }

    @PutMapping("/{id}")
    public Oportunidade updateOportunidade(@PathVariable Long id,
                                   		   @Valid @RequestBody Oportunidade oportunidadeRequest) {
        return oportunidadeRepository.findById(id)
                .map(oportunidade -> {
                    oportunidade.setNomeProspecto(oportunidadeRequest.getNomeProspecto());
                    oportunidade.setDescricao(oportunidadeRequest.getDescricao());
                    oportunidade.setValor(oportunidadeRequest.getValor());
                    return oportunidadeRepository.save(oportunidade);
                }).orElseThrow(() -> new ResourceNotFoundException("Oportunidade not found with id " + id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOportunidade(@PathVariable Long id) {
        return oportunidadeRepository.findById(id)
                .map(oportunidade -> {
                    oportunidadeRepository.delete(oportunidade);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Oportunidade not found with id " + id));
    }

}
