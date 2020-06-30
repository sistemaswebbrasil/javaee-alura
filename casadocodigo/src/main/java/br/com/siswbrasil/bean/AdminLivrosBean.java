package br.com.siswbrasil.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.siswbrasil.dao.AutorDao;
import br.com.siswbrasil.dao.LivroDao;
import br.com.siswbrasil.entity.Autor;
import br.com.siswbrasil.entity.Livro;

@Named
@RequestScoped
public class AdminLivrosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private LivroDao livroDao;

	@Inject
	private AutorDao autorDao;

	@Inject
	private FacesContext context;

	private Livro livro = new Livro();

	private List<Integer> autoresId = new ArrayList<>();
	


	@Transactional
	public String salva() {
		for (Integer autorId : autoresId) {
			livro.getAutores().add(new Autor(autorId));
		}
		livroDao.salvar(livro);

		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Livro cadastrado com sucesso!"));

		return "/livro/lista?faces-redirect=true";
	}

	public List<Autor> getAutores() {
		return autorDao.getLista();
	}

	public List<Integer> getAutoresId() {
		return autoresId;
	}

	public void setAutoresId(List<Integer> autoresId) {
		this.autoresId = autoresId;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

}
