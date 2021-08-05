package br.com.lazyDataTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import br.com.DAO.DaoFunc;
import br.com.Model.Funcionarios;

public class LazyDataTable<T> extends LazyDataModel<Funcionarios> {

	private DaoFunc<Funcionarios> daoFunc = new DaoFunc<Funcionarios>();
	public List<Funcionarios> list = new ArrayList<Funcionarios>();
	private String sql =  " from Funcionarios ";
	
	
	@Override
	public List<Funcionarios> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		
		list = daoFunc.getEntityManager().createQuery(getSql()).setFirstResult(first).setMaxResults(pageSize).getResultList();
		
		sql = " from Funcionarios ";
		
		setPageSize(pageSize);
		
		Integer qtdDados = Integer.parseInt(daoFunc.getEntityManager().createQuery("select count(1) " +getSql()).getSingleResult().toString());
		setRowCount(qtdDados);
		
		return list;
	}
	
	public List<Funcionarios> loadFuncaoFuncionarios(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy, String funcao) {
		
		list = daoFunc.getEntityManager().createQuery(getSql()).setFirstResult(first).setMaxResults(pageSize).getResultList();
		
		sql = " from Funcionarios where funcao like '"+funcao+"'";
		
		setPageSize(pageSize);
		
		Integer qtdDados = Integer.parseInt(daoFunc.getEntityManager().createQuery("select count(1) " +getSql()).getSingleResult().toString());
		setRowCount(qtdDados);
		
		return list;
	}

	
	
	public String getSql() {
		return sql;
	}
	
	public List<Funcionarios> getList() {
		return list;
	}
	
}
