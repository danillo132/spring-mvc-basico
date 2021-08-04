package br.com.lazyDataTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import br.com.DAO.DaoClientes;
import br.com.DAO.DaoFunc;
import br.com.Model.Clientes;
import br.com.Model.Funcionarios;

public class LazyClientes<T> extends LazyDataModel<Clientes> {

	private DaoClientes<Clientes> daoCliente = new DaoClientes<Clientes>();
	public List<Clientes> list = new ArrayList<Clientes>();
	private String sql =  " from Clientes ";
	
	
	@Override
	public List<Clientes> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		
		list = daoCliente.getEntityManager().createQuery(getSql()).setFirstResult(first).setMaxResults(pageSize).getResultList();
		
		sql = " from Clientes ";
		
		setPageSize(pageSize);
		
		Integer qtdDados = Integer.parseInt(daoCliente.getEntityManager().createQuery("select count(1) " +getSql()).getSingleResult().toString());
		setRowCount(qtdDados);
		
		return list;
	}

	
	
	public String getSql() {
		return sql;
	}
	
	public List<Clientes> getList() {
		return list;
	}
	
	
	
}
