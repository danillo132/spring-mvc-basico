package br.com.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.SingleConnection.SingleConnection;

public class DaoGeneric<E> {

	
	private  EntityManager entityManager = SingleConnection.geEntityManager();
	
	
	public E salvar(E entidade) {
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		E salvaEntidade = entityManager.merge(entidade);
		transaction.commit();
		
		return salvaEntidade;
	}
	
	public E pesquisar(Long id, Class<E> entidade) {
		entityManager.clear();
		E e = (E) entityManager.createQuery("from " + entidade.getSimpleName()+ " where id = "+id).getSingleResult();
	
		return e;
	}
	
	public E pesquisaCpf(String campoPesquisa, Class<E> entidade) {
		entityManager.clear();
		
		E e =  (E) entityManager.createQuery("from " + entidade.getSimpleName()+ " where cpf like '%"+campoPesquisa+"%'").getSingleResult();
		
		return e;
	}
	
	
	public void Deletar(E entidade) throws Exception {
		
		
		Object id = SingleConnection.getPrimaryKey(entidade);
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		entityManager.createNativeQuery("delete from " + entidade.getClass().getSimpleName().toLowerCase() + " where id = "+ id).executeUpdate();
		
		transaction.commit();
	}
	
	public List<E> listar(Class<E> entidade){
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		List<E> lista =  entityManager.createQuery("from "+entidade.getName()).getResultList();
		
		transaction.commit();
		
		return lista;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	
	
}
