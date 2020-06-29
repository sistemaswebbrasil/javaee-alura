package br.com.siswbrasil.livraria.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import br.com.siswbrasil.livraria.modelo.Livro;
import br.com.siswbrasil.livraria.tx.Log;

public class LivroDao implements Serializable{

	private static final long serialVersionUID = 1L;
		
	@Inject
	private EntityManager em;
	
	private DAO<Livro> dao ;
	
	@PostConstruct
	public void init() {
		this.dao = new DAO<Livro>(em, Livro.class);		
	}

	public void adiciona(Livro t) {
		dao.adiciona(t);
	}

	public void remove(Livro t) {
		dao.remove(t);
	}

	public void atualiza(Livro t) {
		dao.atualiza(t);
	}

	public Livro buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}

	@Log
	public List<Livro> listaTodos() {
		return dao.listaTodos();
	}

	@Log
	public List<Livro> primeFacesFilter(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		return dao.primeFacesFilter(first, pageSize, sortBy, filterBy);
	}

	public int primeFacesFilterCount(Map<String, FilterMeta> filterBy) {
		return dao.primeFacesFilterCount(filterBy);
	}

}
