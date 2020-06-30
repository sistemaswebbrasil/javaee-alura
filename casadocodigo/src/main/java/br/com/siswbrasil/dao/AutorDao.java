package br.com.siswbrasil.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.siswbrasil.entity.Autor;

public class AutorDao {
	
    @PersistenceContext
    private EntityManager manager;

    public List<Autor> getLista() {
        return  manager.createQuery("select a from Autor a", Autor.class)
                .getResultList();
    }

}
