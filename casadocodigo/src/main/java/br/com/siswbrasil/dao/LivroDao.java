package br.com.siswbrasil.dao;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.annotations.QueryHints;

import br.com.siswbrasil.entity.Livro;

@Stateful
public class LivroDao {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager manager;

	public void salvar(Livro livro) {
		manager.persist(livro);
	}

	public void limpaCache() {
		Cache cache = manager.getEntityManagerFactory().getCache();
		cache.evict(Livro.class);
	}

	public List<Livro> listar() {
		String jpql = "select distinct(l) from Livro l " + " join fetch l.autores";

		return manager.createQuery(jpql, Livro.class).getResultList();
	}

	public List<Livro> ultimosLancamentos() {
		String jpql = "select l from Livro l " + "where l.capaPath is not null " + "order by l.id desc";
		return manager.createQuery(jpql, Livro.class).setMaxResults(5).setHint(QueryHints.CACHEABLE, true)
				.getResultList();
	}

	public List<Livro> demaisLivros() {
		String jpql = "select l from Livro l  " + "where l.capaPath is not null " + "order by l.id desc";
		return manager.createQuery(jpql, Livro.class).setFirstResult(5).setHint(QueryHints.CACHEABLE, true)
				.getResultList();
	}

	public Livro buscarPorId(Integer id) {
		// return manager.find(Livro.class, id);

		String jpql = "select l from Livro l join fetch l.autores " + "where l.id = :id";
		return manager.createQuery(jpql, Livro.class).setParameter("id", id).getSingleResult();
	}

}
