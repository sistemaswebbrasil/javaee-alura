package br.com.siswbrasil.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import br.com.siswbrasil.entity.Livro;

@Stateful
public class LivroDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager manager;

	public void salvar(Livro livro) {
		manager.persist(livro);
	}

	public List<Livro> listar() {
		String jpql = "select distinct(l) from Livro l" + " join fetch l.autores where l.capaPath is not null ";
		
		return manager.createQuery(jpql, Livro.class).getResultList();
	}

	public List<Livro> ultimosLancamentos() {
	    String jpql = "select l from Livro l order by l.id desc";
	    return manager.createQuery(jpql, Livro.class)
	            .setMaxResults(5)
	            .getResultList();
	}
	
    public List<Livro> demaisLivros() {
        String jpql = "select l from Livro l order by l.id desc";
        return manager.createQuery(jpql, Livro.class)
                .getResultList();
    }    
    
    public Livro buscarPorId(Integer id) {
        //return manager.find(Livro.class, id);
    	
        String jpql = "select l from Livro l join fetch l.autores "
                + "where l.id = :id";
        return manager.createQuery(jpql, Livro.class)
                .setParameter("id", id)
                .getSingleResult();        
    }    

}
