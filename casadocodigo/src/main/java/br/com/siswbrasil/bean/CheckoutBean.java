package br.com.siswbrasil.bean;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.siswbrasil.entity.CarrinhoCompras;
import br.com.siswbrasil.entity.Usuario;

@Model
public class CheckoutBean {

	private Usuario usuario = new Usuario();

	@Inject
	private CarrinhoCompras carrinho;
	
	@Transactional
	public void finalizar() {
		carrinho.finalizar(usuario);
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
