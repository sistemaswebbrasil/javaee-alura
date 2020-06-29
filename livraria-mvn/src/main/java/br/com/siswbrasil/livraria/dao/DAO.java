package br.com.siswbrasil.livraria.dao;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.IdentifiableType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.MatchMode;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import br.com.siswbrasil.livraria.util.LocalDateTimeToDate;

public class DAO<T> implements Serializable {

	private static final long serialVersionUID = -7869792440648486943L;

	private final Class<T> classe;
	private EntityManager em;

	public DAO(EntityManager manager, Class<T> classe) { 
		this.em = manager;
		this.classe = classe;
	}

	public void adiciona(T t) {
		em.persist(t);
	}

	public void remove(T t) {
		em.remove(em.merge(t));		
	}

	public void atualiza(T t) {
		em.merge(t);		
	}

	public List<T> listaTodos() {

		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		List<T> lista = em.createQuery(query).getResultList();

		
		return lista;
	}

	public T buscaPorId(Integer id) {

		em.getTransaction().begin();
		T instancia = em.find(classe, id);
		
		return instancia;
	}

	public int contaTodos() {

		long result = (Long) em.createQuery("select count(n) from livro n").getSingleResult();
		

		return (int) result;
	}

	public List<T> listaTodosPaginada(int firstResult, int maxResults, String coluna, String valor) {

		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		Root<T> root = query.from(classe);

		if (valor != null && !valor.isEmpty())
			query = query.where(em.getCriteriaBuilder().like(root.<String>get(coluna), valor + "%"));

		List<T> lista = em.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();

		
		return lista;
	}


	public List<T> primeFacesFilter(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		try {		
		
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		Root<T> root = query.from(classe);
		ArrayList<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder builder = em.getCriteriaBuilder();

		addFilters(filterBy, query, root, predicates, builder, em);
		addOrdinations(sortBy, query, root, builder);

		List<T> lista = em.createQuery(query).setFirstResult(first).setMaxResults(pageSize).getResultList();
		
		return lista;
		
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new RuntimeException("Não sei porque está dando erro");
		}		
		
	}

	private void addOrdinations(Map<String, SortMeta> sortBy, CriteriaQuery<T> query, Root<T> root,
			CriteriaBuilder builder) {
		List<Order> orders = new ArrayList<Order>();
		if (sortBy != null && !sortBy.isEmpty()) {

			for (SortMeta meta : sortBy.values()) {
				String sortField = meta.getSortField();
				SortOrder sortOrder = meta.getSortOrder();

				if (sortField.contains(".time")) {
					sortField = sortField.split(".time")[0];
				}

				switch (sortOrder) {
				case DESCENDING:
					orders.add(builder.desc(root.get(sortField)));
					break;
				default:
					orders.add(builder.asc(root.get(sortField)));
					break;
				}
			}
			query.orderBy(orders);
		}
	}
	
	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	private void addFilters(Map<String, FilterMeta> filterBy, CriteriaQuery<?> query, Root<?> root,
			ArrayList<Predicate> predicates, CriteriaBuilder builder, EntityManager em) {
		if (filterBy != null) {
			for (FilterMeta meta : filterBy.values()) {

				String filterField = meta.getFilterField();
				Object filterValue = meta.getFilterValue();
				MatchMode filterMatchMode = meta.getFilterMatchMode();

				if (filterValue != null) {
					if (filterField.contains(".time")) {
						filterField = filterField.split(".time")[0];
					}

					switch (filterMatchMode) {
					case CONTAINS:
						predicates.add(builder.like(root.<String>get(filterField), "%" + filterValue + "%"));
						break;
					case ENDS_WITH:
						predicates.add(builder.like(root.<String>get(filterField), "%" + filterValue));
						break;
					case EQUALS:
						predicates.add(builder.equal(root.<String>get(filterField), filterValue.toString()));
						break;
					case EXACT:
						predicates.add(builder.like(root.<String>get(filterField), filterValue.toString()));
						break;
					case GREATER_THAN:
						predicates.add(builder.greaterThan(root.<String>get(filterField), filterValue.toString()));
						break;
					case GREATER_THAN_EQUALS:
						predicates.add(
								builder.greaterThanOrEqualTo(root.<String>get(filterField), filterValue.toString()));
						break;
					case IN:
						predicates.add(builder.in(root.<String>get(filterField).in(filterValue)));
						break;
					case LESS_THAN:
						predicates.add(builder.lessThan(root.<String>get(filterField), filterValue.toString()));
						break;
					case LESS_THAN_EQUALS:
						if (filterValue instanceof LocalDate) {
							Date date = LocalDateTimeToDate.localDateToDate((LocalDate) filterValue);
							predicates.add(builder.lessThanOrEqualTo(root.<Date>get(filterField), date));
							break;
						}
						predicates.add(
								builder.lessThanOrEqualTo(root.get(filterField), (Comparable) filterValue));
						break;
					case STARTS_WITH:
						Class tipo = getTypeFromEntity(em, classe, filterField);

						if (tipo != null && tipo.getName() == "double") {
							predicates.add(
									builder.lessThanOrEqualTo(root.get(filterField), (Comparable) filterValue));
							break;
						}
						predicates.add(builder.like(root.<String>get(filterField), filterValue + "%"));
						break;
					default:
						predicates.add(builder.equal(root.<String>get(filterField), filterValue.toString()));
						break;
					}
				}
			}
			query.where(builder.and(predicates.toArray(new Predicate[0])));
		}

	}

	public int quantidadeDeElementos() {

		long result = (Long) em.createQuery("select count(n) from " + classe.getSimpleName() + " n").getSingleResult();
		

		return (int) result;
	}

	public int primeFacesFilterCount(Map<String, FilterMeta> filterBy) {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<T> root = query.from(classe);
		ArrayList<Predicate> predicates = new ArrayList<Predicate>();
		addFilters(filterBy, query, root, predicates, builder, em);
		query.multiselect(builder.count(root));
		Long result = em.createQuery(query).getSingleResult();
		
		return result.intValue();

	}

	@SuppressWarnings("unused")
	private <T> SingularAttribute<? super T, ?> getIdAttribute(EntityManager em, Class<T> clazz) {
		Metamodel m = em.getMetamodel();
		IdentifiableType<T> of = (IdentifiableType<T>) m.managedType(clazz);
		return of.getId(of.getIdType().getJavaType());
	}

	@SuppressWarnings("rawtypes")
	private Class getTypeFromEntity(EntityManager em, Class<T> clazz, String field) {
		Metamodel m = em.getMetamodel();
		IdentifiableType<T> of = (IdentifiableType<T>) m.managedType(clazz);
		Class type = of.getAttribute(field).getJavaType();
		return type;
	}

}
