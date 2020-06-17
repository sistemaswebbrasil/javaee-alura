package br.com.siswbrasil.bean;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class LivroBean {

	private Livro livro = new Livro();

	public void gravar() {
		System.out.println("Gravando livro");
	}

	public Livro getLivro() {
		return livro;
	}
	
}
