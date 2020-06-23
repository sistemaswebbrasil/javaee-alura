package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.LivroDataModel;

@ViewScoped
@ManagedBean
public class LivroBean implements Serializable {

	private static final long serialVersionUID = 5483726752452527993L;

	private Livro livro = new Livro();
	private Integer autorId;
	private Integer livroId;
	private List<Livro> livros;
	private LivroDataModel livroDataModel = new LivroDataModel(); 
	private List<String> generos = Arrays.asList("Romance", "Drama", "Ação");

	public List<Autor> getAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}
	
	

	public void gravar() {
	    System.out.println("Gravando livro " + this.livro.getTitulo());

	    if (livro.getAutores().isEmpty()) {
	        FacesContext.getCurrentInstance().addMessage("autor",
	                new FacesMessage("Livro deve ter pelo menos um Autor."));
	        return;
	    }

	    DAO<Livro> dao = new DAO<Livro>(Livro.class);

	    if(this.livro.getId() == null) {	    	
	        dao.adiciona(this.livro);
	        this.livros = dao.listaTodos();
	    } else {
	        dao.atualiza(this.livro);
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

        //tirando espaços do filtro
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

            // comparando os valores, compareTo devolve um valor negativo se o value é menor do que o filtro
            return precoColuna.compareTo(precoDigitado) < 0;

        } catch (NumberFormatException e) {

            // usuario nao digitou um numero
            return false;
        }
}	

	public void gravarAutor() {
		Autor autor = new DAO<Autor>(Autor.class).buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
	}

	public void removeLivro(Livro livro) {

		System.out.println(livro);
		new DAO<Livro>(Livro.class).remove(livro);
	}

	public void removeAutorDoLivro(Autor autor) {
		this.livro.removeAutor(autor);
	}

	public void carregaLivro(Livro livroForm) {
		Livro livroDb = new DAO<Livro>(Livro.class).buscaPorId(livroForm.getId());
		this.livro = livroDb;
	}

	public void carregaPelaId() {
		this.livro = new DAO<Livro>(Livro.class).buscaPorId(this.livroId);
	}

	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}

	public List<Livro> getLivros() {
	    DAO<Livro> dao = new DAO<Livro>(Livro.class);

	    if(this.livros == null) {
	        this.livros = dao.listaTodos();            
	    }

	    return livros;
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

}
