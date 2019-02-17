package br.gov.rs.parobe.scram.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.gov.rs.parobe.scram.model.Usuaria;

public class UsuariaDao {

	private static UsuariaDao instance;
	protected EntityManager entityManager;

	public static UsuariaDao getInstance() {
		if (instance == null) {
			instance = new UsuariaDao();
		}
		return instance;
	}

	private UsuariaDao() {
		entityManager = getEntityManager();
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("scram");
		if (entityManager == null) {
			entityManager = factory.createEntityManager();
		}
		return entityManager;
	}

	public Usuaria getById(int id) {
		return (Usuaria) entityManager.find(Usuaria.class, Integer.valueOf(id));
	}

	@SuppressWarnings("unchecked")
	public List<Usuaria> findAll() {
		try {
			return (List<Usuaria>) entityManager.createQuery("from Usuaria u order by u.nome").getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Usuaria> findAllByNome(Usuaria usuariaFind) {
		try {
			Query query = entityManager.createQuery("from Usuaria u where upper(u.nome) like upper(:nome) order by u.nome");
			query.setParameter("nome", (new StringBuilder("%")).append(usuariaFind.getNome()).append("%").toString());
			return (ArrayList<Usuaria>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Usuaria> findAllByNome(String usuariaFind) {
		try {
			Query query = entityManager.createQuery("from Usuaria u where upper(u.nome) like upper(:nome) order by u.nome");
			query.setParameter("nome", (new StringBuilder("%")).append(usuariaFind).append("%").toString());
			return (ArrayList<Usuaria>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Usuaria> findAllByCpf(Usuaria usuariaFind) {
		try {
			Query query = entityManager.createQuery("from Usuaria u where u.cpf = :cpf order by u.nome");
			query.setParameter("cpf", usuariaFind.getCpf());
			return (ArrayList<Usuaria>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Usuaria> findAllByNomeCpf(Usuaria usuariaFind) {
		try {
			Query query = entityManager.createQuery("from Usuaria u where upper(u.nome) like upper(:nome) and u.cpf =:cpf order by u.nome");
			query.setParameter("nome", (new StringBuilder("%")).append(usuariaFind.getNome()).append("%").toString());
			query.setParameter("cpf", usuariaFind.getCpf());
			return (ArrayList<Usuaria>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}	

	public Usuaria getByCpf(String cpf) {
		try {
			Query query = entityManager.createQuery("from Usuaria u where u.cpf = :cpf");
			query.setParameter("cpf", cpf);						
			return (Usuaria) query.getSingleResult();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public void persist(Usuaria usuaria) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(usuaria);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}

	public void merge(Usuaria usuaria) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(usuaria);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}

	public void remove(Usuaria usuaria) {
		try {
			entityManager.getTransaction().begin();
			usuaria = (Usuaria) entityManager.find(Usuaria.class, Integer.valueOf(usuaria.getId()));
			entityManager.remove(usuaria);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}

	public void removeById(int id) {
		try {
			Usuaria Usuaria = getById(id);
			remove(Usuaria);
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}
}
