package br.gov.rs.parobe.scram.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.gov.rs.parobe.scram.model.Denunciado;

public class DenunciadoDao {

	private static DenunciadoDao instance;
	protected EntityManager entityManager;

	public static DenunciadoDao getInstance() {
		if (instance == null) {
			instance = new DenunciadoDao();
		}
		return instance;
	}

	private DenunciadoDao() {
		entityManager = getEntityManager();
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("scram");
		if (entityManager == null) {
			entityManager = factory.createEntityManager();
		}
		return entityManager;
	}

	public Denunciado getById(int id) {
		return (Denunciado) entityManager.find(Denunciado.class, Integer.valueOf(id));
	}

	@SuppressWarnings("unchecked")
	public List<Denunciado> findAll() {
		try {
			return (List<Denunciado>) entityManager.createQuery("from Denunciado d order by d.nome").getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Denunciado> findAllByNome(Denunciado denunciadoFind) {
		try {
			Query query = entityManager.createQuery("from Denunciado d where upper(d.nome) like upper(:nome) order by d.nome");
			query.setParameter("nome", (new StringBuilder("%")).append(denunciadoFind.getNome()).append("%").toString());
			return (ArrayList<Denunciado>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Denunciado> findAllByNome(String denunciadoFind) {
		try {
			Query query = entityManager.createQuery("from Denunciado d where upper(d.nome) like upper(:nome) order by d.nome");
			query.setParameter("nome", (new StringBuilder("%")).append(denunciadoFind).append("%").toString());
			return (ArrayList<Denunciado>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Denunciado> findAllByCpf(Denunciado denunciadoFind) {
		try {
			Query query = entityManager.createQuery("from Denunciado d where d.cpf = :cpf order by d.nome");
			query.setParameter("cpf", denunciadoFind.getCpf());
			return (ArrayList<Denunciado>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Denunciado> findAllByNomeCpf(Denunciado denunciadoFind) {
		try {
			Query query = entityManager.createQuery("from Denunciado d where upper(d.nome) like upper(:nome) and d.cpf =:cpf order by d.nome");
			query.setParameter("nome", (new StringBuilder("%")).append(denunciadoFind.getNome()).append("%").toString());
			query.setParameter("cpf", denunciadoFind.getCpf());
			return (ArrayList<Denunciado>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}	

	public Denunciado getByCpf(String cpf) {
		try {
			Query query = entityManager.createQuery("from Denunciado d where d.cpf = :cpf");
			query.setParameter("cpf", cpf);						
			return (Denunciado) query.getSingleResult();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public void persist(Denunciado denunciado) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(denunciado);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}

	public void merge(Denunciado denunciado) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(denunciado);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}

	public void remove(Denunciado denunciado) {
		try {
			entityManager.getTransaction().begin();
			denunciado = (Denunciado) entityManager.find(Denunciado.class, Integer.valueOf(denunciado.getId()));
			entityManager.remove(denunciado);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}

	public void removeById(int id) {
		try {
			Denunciado denunciado = getById(id);
			remove(denunciado);
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}
}
