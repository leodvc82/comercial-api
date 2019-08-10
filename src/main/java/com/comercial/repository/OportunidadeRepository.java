package com.comercial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.comercial.model.Oportunidade;

@Repository
public interface OportunidadeRepository extends JpaRepository<Oportunidade, Long> {
	
	//List<Oportunidade> findByOportunidadeId(Long oportunidadeId);
	Oportunidade findByNomeProspectoAndDescricao(String nomeProspecto, String Descricao);

}
