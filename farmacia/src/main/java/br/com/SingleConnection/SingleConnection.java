package br.com.SingleConnection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;



public class SingleConnection {

	
	
	public static EntityManagerFactory factory = null;
	
	
	static {
		SingleConnection1();
	}
	
	public static void SingleConnection1() {
		
		try {
			
			if(factory == null) {
				factory = Persistence.createEntityManagerFactory("farmacia");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static EntityManager geEntityManager() {
		return factory.createEntityManager();
	}
	
	public static Object getPrimaryKey(Object entity) {
		return factory.getPersistenceUnitUtil().getIdentifier(entity);
	}
	
}
