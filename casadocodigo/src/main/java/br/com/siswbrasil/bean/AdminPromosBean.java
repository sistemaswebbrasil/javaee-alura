package br.com.siswbrasil.bean;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import br.com.siswbrasil.model.Promo;
import br.com.siswbrasil.websockets.PromosEndpoint;

@Model
public class AdminPromosBean {

	private Promo promo = new Promo();

	@Inject
	private PromosEndpoint promos;

	public void enviar() {
		promos.send(promo);
	}

	public Promo getPromo() {
		return promo;
	}

	public void setPromo(Promo promo) {
		this.promo = promo;
	}
}
