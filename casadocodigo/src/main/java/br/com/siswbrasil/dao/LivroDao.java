package br.com.siswbrasil.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.siswbrasil.entity.Livro;

public class LivroDao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager manager;

	public void salvar(Livro livro) {
		manager.persist(livro);
	}

}
