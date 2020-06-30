package br.com.siswbrasil.bean;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.siswbrasil.dao.LivroDao;
import br.com.siswbrasil.entity.Livro;

@Named
@RequestScoped
public class AdminLivrosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private LivroDao livroDao;

	private Livro livro = new Livro();

	@Transactional
	public void salva() {
		System.out.println("Livro cadastrado: " + livro);
		livroDao.salvar(livro);
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}
}
