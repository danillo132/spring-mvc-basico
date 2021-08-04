package br.com.lazyDataTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import br.com.DAO.DaoOrcamentos;
import br.com.Model.Orcamentos;

public class LazyOrcamentos<T> extends LazyDataModel<Orcamentos> {

	private DaoOrcamentos<Orcamentos> daoOrcamentos = new DaoOrcamentos<Orcamentos>();
	public List<Orcamentos> list = new ArrayList<Orcamentos>();
	private String sql =  " from Orcamentos ";
	
	@Override
	public List<Orcamentos> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		
		list = daoOrcamentos.getEntityManager().createQuery(getSql()).setFirstResult(first).setMaxResults(pageSize).getResultList();
		
		sql = " from Orcamentos ";
		
		setPageSize(pageSize);
		
		Integer qtdDados = Integer.parseInt(daoOrcamentos.getEntityManager().createQuery("select count(1) " +getSql()).getSingleResult().toString());
		setRowCount(qtdDados);
		
		return list;
	}

	public List<Orcamentos> loading(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy, Long id){
		
		list = daoOrcamentos.getEntityManager().createQuery(getSql()).setFirstResult(first).setMaxResults(pageSize).getResultList();
		
		sql = " from Orcamentos where clientes_id = " + id;
		
		setPageSize(pageSize);
		
		Integer qtdDados = Integer.parseInt(daoOrcamentos.getEntityManager().createQuery("select count(1) " +getSql()).getSingleResult().toString());
		setRowCount(qtdDados);
		
		return list;
	}
	
	public String getSql() {
		return sql;
	}
	
	public List<Orcamentos> getList() {
		return list;
	}


	
	



	
	
}
