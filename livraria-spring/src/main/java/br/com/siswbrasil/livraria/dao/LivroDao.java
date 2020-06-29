package br.com.siswbrasil.livraria.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.springframework.stereotype.Repository;

import br.com.siswbrasil.livraria.modelo.Livro;

@Repository
@SuppressWarnings("serial")
public class LivroDao implements Serializable {

	@PersistenceContext
	EntityManager manager;

	private DAO<Livro> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<Livro>(this.manager, Livro.class);
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

	public List<Livro> listaTodos() {
		return dao.listaTodos();
	}

	public Livro buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}

	public List<Livro> primeFacesFilter(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		return dao.primeFacesFilter(first, pageSize, sortBy, filterBy);
	}

	public int primeFacesFilterCount(Map<String, FilterMeta> filterBy) {
		return dao.primeFacesFilterCount(filterBy);
	}

}
