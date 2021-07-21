package br.com.lazyDataTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import br.com.DAO.DaoFunc;
import br.com.DAO.DaoMateria;
import br.com.Model.Funcionarios;
import br.com.Model.MateriaPrima;

public class LazyMateriaPrima<T> extends LazyDataModel<MateriaPrima> {

	private DaoMateria<MateriaPrima> daoMateria = new DaoMateria<MateriaPrima>();
	public List<MateriaPrima> list = new ArrayList<MateriaPrima>();
	private String sql =  " from MateriaPrima ";
	
	
	@Override
	public List<MateriaPrima> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		
		list = daoMateria.getEntityManager().createQuery(getSql()).setFirstResult(first).setMaxResults(pageSize).getResultList();
		
		sql = " from MateriaPrima ";
		
		setPageSize(pageSize);
		
		Integer qtdDados = Integer.parseInt(daoMateria.getEntityManager().createQuery("select count(1) " +getSql()).getSingleResult().toString());
		setRowCount(qtdDados);
		
		return list;
	}

	
	
	public String getSql() {
		return sql;
	}
	
	public List<MateriaPrima> getList() {
		return list;
	}
	
}
