package br.com.DAO;

import br.com.Model.Pedidos;

public class DaoPedidos<E> extends DaoGeneric<Pedidos> {

	
	public void removerPedido(Pedidos pedidos) {
		
		getEntityManager().getTransaction().begin();
		
		getEntityManager().remove(getEntityManager().contains(pedidos) ? pedidos : getEntityManager().merge(pedidos));
		getEntityManager().getTransaction().commit();
		
	}
	
	public Integer contarPedidos(Long idCliente) {
		getEntityManager().getTransaction().begin();
		Integer total = Integer.parseInt(getEntityManager().createQuery("select count(id) from Pedidos where clientes_id = " + idCliente).getSingleResult().toString()); 
		getEntityManager().getTransaction().commit();
		return total;
	}
	
	public Integer contarPedidosEntregues(Long idCliente) {
		getEntityManager().getTransaction().begin();
		Integer total = Integer.parseInt(getEntityManager().createQuery("select count(id) from Pedidos where status like '%Entregue%' and clientes_id  = " + idCliente).getSingleResult().toString()); 
		getEntityManager().getTransaction().commit();
		return total;
	}
	
	
}
