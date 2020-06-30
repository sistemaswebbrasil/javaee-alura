package br.com.siswbrasil.dao;


import java.io.Serializable;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.siswbrasil.entity.Livro;

@Model
public class LivroDetalheBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private Livro livro;
	
	private Integer id;

	@Inject
	private LivroDao dao;

	public void carregarDetalhe() {
		this.livro = dao.buscarPorId(id);
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	

}
