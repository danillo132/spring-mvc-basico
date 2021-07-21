package br.com.lazyDataTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import br.com.DAO.DaoEqui;
import br.com.Model.Equipamentos;

public class LazyEquipamentos<T> extends LazyDataModel<Equipamentos> {

	private DaoEqui<Equipamentos> daoEqui =  new DaoEqui<Equipamentos>();
	public List<Equipamentos> list = new ArrayList<Equipamentos>();
	private String sql =  " from Equipamentos ";
	
	
	@Override
	public List<Equipamentos> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		
		list = daoEqui.getEntityManager().createQuery(getSql()).setFirstResult(first).setMaxResults(pageSize).getResultList();
		
		sql = " from Equipamentos ";
		
		setPageSize(pageSize);
		
		Integer qtdDados = Integer.parseInt(daoEqui.getEntityManager().createQuery("select count(1) " +getSql()).getSingleResult().toString());
		setRowCount(qtdDados);
		
		return list;
	}

	
	
	public String getSql() {
		return sql;
	}
	
	public List<Equipamentos> getList() {
		return list;
	}

	
}
