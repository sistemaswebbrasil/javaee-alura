package br.com.siswbrasil.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import javax.transaction.Transactional;

import br.com.siswbrasil.dao.AutorDao;
import br.com.siswbrasil.dao.LivroDao;
import br.com.siswbrasil.model.Autor;
import br.com.siswbrasil.model.Livro;
import br.com.siswbrasil.util.FileSaver;

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
	
	private Part capaLivro;


    @Transactional
    public String salvar() throws IOException {
        livroDao.salvar(livro);
        FileSaver fileSaver = new FileSaver();
        livro.setCapaPath(fileSaver.write(capaLivro, "livros"));

        context.getExternalContext()
            .getFlash().setKeepMessages(true);
        context
            .addMessage(null, new FacesMessage("Livro cadastrado com sucesso!"));

        return "/livros/lista?faces-redirect=true";
    }

	public List<Autor> getAutores() {
		return autorDao.getLista();
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Part getCapaLivro() {
		return capaLivro;
	}

	public void setCapaLivro(Part capaLivro) {
		this.capaLivro = capaLivro;
	}

}
