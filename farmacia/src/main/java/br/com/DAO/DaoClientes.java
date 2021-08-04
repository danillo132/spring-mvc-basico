package br.com.DAO;

import java.util.List;

import br.com.Model.Clientes;

public class DaoClientes<E> extends DaoGeneric<Clientes> {
	
	

	public void removerCliente(Clientes clientes) {
		getEntityManager().getTransaction().begin();
		getEntityManager().remove(getEntityManager().contains(clientes) ? clientes : getEntityManager().merge(clientes));
		getEntityManager().getTransaction().commit();
	
	}
	
	public Integer contarClientes(Long idFunc) {
		getEntityManager().getTransaction().begin();
		Integer total = Integer.parseInt(getEntityManager().createQuery("select count(id) from Clientes where funcionarios_id = " + idFunc).getSingleResult().toString()); 
		getEntityManager().getTransaction().commit();
		return total;
	}
	
	public List<Clientes> pesquisarClientes(Long idFunc){
		getEntityManager().getTransaction().begin();
		List<Clientes> listadeclientes = getEntityManager().createQuery("from Clientes where funcionarios_id = " + idFunc).getResultList();
		getEntityManager().getTransaction().commit();
		
		return listadeclientes;
		
	}
	
	
}
