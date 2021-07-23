package br.com.lazyDataTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import br.com.DAO.DaoFornecedores;
import br.com.DAO.DaoFunc;
import br.com.Model.Fornecedores;
import br.com.Model.Funcionarios;

public class LazyFornecedores<T> extends LazyDataModel<Fornecedores> {

	private DaoFornecedores<Fornecedores> daoFornecedores = new DaoFornecedores<Fornecedores>();
	public List<Fornecedores> list = new ArrayList<Fornecedores>();
	private String sql =  " from Fornecedores ";
	
	
	@Override
	public List<Fornecedores> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		
		list = daoFornecedores.getEntityManager().createQuery(getSql()).setFirstResult(first).setMaxResults(pageSize).getResultList();
		
		sql = " from Fornecedores ";
		
		setPageSize(pageSize);
		
		Integer qtdDados = Integer.parseInt(daoFornecedores.getEntityManager().createQuery("select count(1) " +getSql()).getSingleResult().toString());
		setRowCount(qtdDados);
		
		return list;
	}

	
	
	public String getSql() {
		return sql;
	}
	
	public List<Fornecedores> getList() {
		return list;
	}
	
}
