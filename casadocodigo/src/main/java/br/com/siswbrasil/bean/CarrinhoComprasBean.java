package br.com.siswbrasil.bean;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import br.com.siswbrasil.dao.LivroDao;
import br.com.siswbrasil.entity.CarrinhoCompras;
import br.com.siswbrasil.entity.CarrinhoItem;
import br.com.siswbrasil.entity.Livro;

@Model
public class CarrinhoComprasBean {

	@Inject
	private LivroDao livroDao;

	@Inject
	private CarrinhoCompras carrinho;

	public String add(Integer id) {
		Livro livro = livroDao.buscarPorId(id);
		CarrinhoItem item = new CarrinhoItem(livro);
		carrinho.add(item);
		return "carrinho?faces-redirect=true";
	}

	public List<CarrinhoItem> getItens() {
		return new ArrayList<CarrinhoItem>(carrinho.getItens());
	}
	
	public void remover(CarrinhoItem item) {
		carrinho.remover(item);
	}	
}