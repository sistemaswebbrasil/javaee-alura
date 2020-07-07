package br.com.caelum.livraria.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.PostRemove;

import br.com.caelum.livraria.modelo.Livro;

@Named
@RequestScoped
public class LivroFormBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Livro livro = new Livro();
		
	@PostConstruct
	public void init() {
		Livro session = (Livro) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("selected")  ;
		Livro session2 = (Livro) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selected2");
		
		System.out.println("Session: " + session);
		System.out.println("Session2: " + session2);
		
		
		if (session != null) {
			this.livro = session;
		}
		System.out.println("Livro1: " + livro);
		if (session2 != null) {
			this.livro = session2;
		}		
		System.out.println("Livro2: " + livro);
		
	}
	
	@PostRemove
	public void destroy() {
		System.out.println("Removendo o bean");
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("selected2");
	}
	
	
	
	

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	
	

}
