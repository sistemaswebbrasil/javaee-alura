package br.com.siswbrasil.bean;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import br.com.siswbrasil.dao.LivroDao;
import br.com.siswbrasil.entity.Livro;

@Model
public class AdminListaLivrosBean {

        @Inject
        private LivroDao dao;

        private List<Livro> livros = new ArrayList<>();

        public List<Livro> getLivros() {
         this.livros = dao.listar();

         return livros;
        }
}
