package br.com.lazyDataTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import br.com.DAO.DaoEmbalagem;
import br.com.Model.Embalagens;

public class LazyEmbalagens<T> extends LazyDataModel<Embalagens> {

	
	private DaoEmbalagem<Embalagens> daoEmbalagem =  new DaoEmbalagem<Embalagens>();
	public List<Embalagens> list = new ArrayList<Embalagens>();
	private String sql =  " from Embalagens ";
	
	
	@Override
	public List<Embalagens> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		
		list = daoEmbalagem.getEntityManager().createQuery(getSql()).setFirstResult(first).setMaxResults(pageSize).getResultList();
		
		sql = " from Embalagens ";
		
		setPageSize(pageSize);
		
		Integer qtdDados = Integer.parseInt(daoEmbalagem.getEntityManager().createQuery("select count(1) " +getSql()).getSingleResult().toString());
		setRowCount(qtdDados);
		
		return list;
	}

	
	
	public String getSql() {
		return sql;
	}
	
	public List<Embalagens> getList() {
		return list;
	}
	
}
