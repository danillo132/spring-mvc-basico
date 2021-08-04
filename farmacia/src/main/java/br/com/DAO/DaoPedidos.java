package br.com.DAO;

import br.com.Model.Pedidos;

public class DaoPedidos<E> extends DaoGeneric<Pedidos> {

	
	public void removerPedido(Pedidos pedidos) {
		
		getEntityManager().getTransaction().begin();
		
		getEntityManager().remove(getEntityManager().contains(pedidos) ? pedidos : getEntityManager().merge(pedidos));
		getEntityManager().getTransaction().commit();
		
	}
	
}
