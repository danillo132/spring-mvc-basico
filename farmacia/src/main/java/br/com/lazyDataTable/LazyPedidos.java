package br.com.lazyDataTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import br.com.DAO.DaoOrcamentos;
import br.com.DAO.DaoPedidos;
import br.com.Model.Orcamentos;
import br.com.Model.Pedidos;

public class LazyPedidos<T> extends LazyDataModel<Pedidos> {

	private DaoPedidos<Pedidos> daoPedidos = new DaoPedidos<Pedidos>();
	public List<Pedidos> list = new ArrayList<Pedidos>();
	private String sql =  " from Pedidos ";
	
	@Override
	public List<Pedidos> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		
		list = daoPedidos.getEntityManager().createQuery(getSql()).setFirstResult(first).setMaxResults(pageSize).getResultList();
		
		sql = " from Pedidos ";
		
		setPageSize(pageSize);
		
		Integer qtdDados = Integer.parseInt(daoPedidos.getEntityManager().createQuery("select count(1) " +getSql()).getSingleResult().toString());
		setRowCount(qtdDados);
		
		return list;
	}

	public List<Pedidos> loading(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy, Long id){
		
		list = daoPedidos.getEntityManager().createQuery(getSql()).setFirstResult(first).setMaxResults(pageSize).getResultList();
		
		sql = " from Pedidos where clientes_id = " + id;
		
		setPageSize(pageSize);
		
		Integer qtdDados = Integer.parseInt(daoPedidos.getEntityManager().createQuery("select count(1) " +getSql()).getSingleResult().toString());
		setRowCount(qtdDados);
		
		return list;
	}
	
	public List<Pedidos> loadingPed(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy, String status){
		
		list = daoPedidos.getEntityManager().createQuery(getSql()).setFirstResult(first).setMaxResults(pageSize).getResultList();
		
		sql = " from Pedidos where status like '%"+status+"%'";
		
		setPageSize(pageSize);
		
		Integer qtdDados = Integer.parseInt(daoPedidos.getEntityManager().createQuery("select count(1) " +getSql()).getSingleResult().toString());
		setRowCount(qtdDados);
		
		return list;
	}
	
	
	
	public String getSql() {
		return sql;
	}
	
	public List<Pedidos> getList() {
		return list;
	}



	



	
	
}
