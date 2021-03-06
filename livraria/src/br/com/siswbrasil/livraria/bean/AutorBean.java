package br.com.siswbrasil.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.siswbrasil.livraria.dao.AutorDao;
import br.com.siswbrasil.livraria.modelo.Autor;
import br.com.siswbrasil.livraria.tx.Transacional;

@Named
@ViewScoped
public class AutorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Autor autor = new Autor();

	@Inject
	private AutorDao autorDao;

	private Integer autorId;

	@Transacional
	public String gravar() {
		if (this.autor.getId() == null) {
			autorDao.adiciona(this.autor);
		} else {
			autorDao.atualiza(this.autor);
		}
		return "livro?faces-redirect=true";
	}
	
	public void carregaAutorPelaID() {
		this.autor = autorDao.buscaPorId(autorId);
	}

	public List<Autor> getAutores() {
		return autorDao.listaTodos();
	}

	@Transacional
	public void remover(Autor autor) {
		autorDao.remove(autor);
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

}
