package br.com.siswbrasil.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.siswbrasil.dao.LivroDao;
import br.com.siswbrasil.entity.Livro;

@Path("livros")
public class LivroResource {

    @Inject
    private LivroDao dao;

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    @Path("json")
    public List<Livro> ultimosLancamentosJson() {
        return dao.ultimosLancamentos();
    }
}
