package br.com.caelum.livraria.bean;

import java.awt.Container;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.LivroDataModel;
import br.com.caelum.livraria.util.JsfUtil;

@Named
@ViewScoped
public class LivroBean implements Serializable {

	private static final long serialVersionUID = 5483726752452527993L;

	private Livro livro = new Livro();
	private Integer autorId;
	private Integer livroId;

	@Inject
	private LivroDataModel livroDataModel;

	private List<String> generos = Arrays.asList("Romance", "Drama", "Ação");
	private List<Livro> livros;

	@Inject
	private AutorDao autorDao;

	@Inject
	private LivroDao livroDao;

	@Inject
	private JsfUtil context;

	private Livro selected = new Livro();

	public String editar(Livro livro) {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selected", livro);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selected2", livro);
		
		
		//Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		
		
		

		
		
		return "form?faces-redirect=true";
	}

	public List<Autor> getAutores() {
		return autorDao.listaTodos();
	}

	@Transactional
	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
			context.getFacesContext().addMessage("autor", new FacesMessage("Livro deve ter pelo menos um Autor."));
			return;
		}

		if (this.livro.getId() == null) {
			livroDao.adiciona(this.livro);
			this.getLivroDataModel();
		} else {
			livroDao.atualiza(this.livro);
		}
		this.livro = new Livro();
	}

	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		String valor = value.toString();
		if (!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage("Deveria começar com 1"));
		}
	}

	public boolean precoEhMenor(Object valorColuna, Object filtroDigitado, Locale locale) {

		// tirando espaços do filtro
		String textoDigitado = (filtroDigitado == null) ? null : filtroDigitado.toString().trim();

		System.out.println("Filtrando pelo " + textoDigitado + ", Valor do elemento: " + valorColuna);

		// o filtro é nulo ou vazio?
		if (textoDigitado == null || textoDigitado.equals("")) {
			return true;
		}

		// elemento da tabela é nulo?
		if (valorColuna == null) {
			return false;
		}

		try {
			// fazendo o parsing do filtro para converter para Double
			Double precoDigitado = Double.valueOf(textoDigitado);
			Double precoColuna = (Double) valorColuna;

			// comparando os valores, compareTo devolve um valor negativo se o value é menor
			// do que o filtro
			return precoColuna.compareTo(precoDigitado) < 0;

		} catch (NumberFormatException e) {

			// usuario nao digitou um numero
			return false;
		}
	}

	public void gravarAutor() {
		Autor autor = autorDao.buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
	}

	@Transactional
	public void removeLivro(Livro livro) {

		System.out.println(livro);
		livroDao.remove(livro);
	}

	public void removeAutorDoLivro(Autor autor) {
		this.livro.removeAutor(autor);
	}

	public void carregaLivro(Livro livroForm) {
		Livro livroDb = livroDao.buscaPorId(livroForm.getId());
		this.livro = livroDb;
	}

	public void carregaPelaId() {
		this.livro = livroDao.buscaPorId(this.livroId);
	}

	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}

	public Livro getLivro() {
		return livro;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Integer getLivroId() {
		return livroId;
	}

	public void setLivroId(Integer livroId) {
		this.livroId = livroId;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public LivroDataModel getLivroDataModel() {
		return livroDataModel;
	}

	public List<String> getGeneros() {
		return generos;
	}

	public List<Livro> getLivros() {
		if (this.livros == null) {
			this.livros = livroDao.listaTodos();
		}
		return livros;
	}

	public Livro getSelected() {
		return selected;
	}

	public void setSelected(Livro selected) {
		this.selected = selected;
	}

}
