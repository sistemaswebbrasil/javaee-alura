package br.com.siswbrasil.livraria.modelo;

import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import br.com.siswbrasil.livraria.dao.LivroDao;

@ViewScoped
public class LivroDataModel extends LazyDataModel<Livro> {

	private static final long serialVersionUID = 2948232237230849780L;

	@Inject
	private LivroDao dao;

	@Override
	public List<Livro> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {

		try {
			List<Livro> livroList = dao.primeFacesFilter(first, pageSize, sortBy, filterBy);
			int rowCount = dao.primeFacesFilterCount(filterBy);
			super.setRowCount(rowCount);
			return livroList;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
