package br.com.caelum.livraria.modelo;

import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import br.com.caelum.livraria.dao.DAO;

public class LivroDataModel extends LazyDataModel<Livro> {

	private static final long serialVersionUID = 2948232237230849780L;

	private DAO<Livro> dao;

	public LivroDataModel() {
		this.dao = new DAO<Livro>(Livro.class);
	}

	@Override
	public List<Livro> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {		
		List<Livro> livroList = dao.primeFacesFilter(first, pageSize, sortBy, filterBy);
		int rowCount = dao.primeFacesFilterCount(filterBy);
		System.out.println("Teste testando");
		System.out.println("rowCount: "+rowCount);
		
		super.setRowCount(rowCount);
		return livroList;
	}	
	
}
