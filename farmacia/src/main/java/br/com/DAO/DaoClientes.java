package br.com.DAO;

import br.com.Model.Clientes;

public class DaoClientes<E> extends DaoGeneric<Clientes> {
	
	

	public void removerCliente(Clientes clientes) {
		getEntityManager().getTransaction().begin();
		getEntityManager().remove(getEntityManager().contains(clientes) ? clientes : getEntityManager().merge(clientes));
		getEntityManager().getTransaction().commit();
	
	}
	
	public Integer contarClientes(Long idFunc) {
		getEntityManager().getTransaction().begin();
		Integer total = Integer.parseInt(getEntityManager().createQuery("select count(id) from Clientes where funcionarios_id = " + idFunc).toString()); 
		getEntityManager().getTransaction().commit();
		return total;
	}
	
	
}
