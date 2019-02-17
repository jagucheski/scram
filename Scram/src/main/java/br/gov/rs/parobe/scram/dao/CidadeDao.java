package br.gov.rs.parobe.scram.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.gov.rs.parobe.scram.model.Cidade;

public class CidadeDao {

	private static CidadeDao instance;
	protected EntityManager entityManager;

	public static CidadeDao getInstance() {
		if (instance == null) {
			instance = new CidadeDao();
		}
		return instance;
	}

	private CidadeDao() {
		entityManager = getEntityManager();
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("scram");
		if (entityManager == null) {
			entityManager = factory.createEntityManager();
		}
		return entityManager;
	}

	public Cidade getById(int id) {
		return (Cidade) entityManager.find(Cidade.class, Integer.valueOf(id));
	}

	@SuppressWarnings("unchecked")
	public List<Cidade> findAll() {
		try {
			return (List<Cidade>) entityManager.createQuery("from Cidade c where c.nome order by c.nome")
					.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Cidade> findAllByNome(String cidadeFind) {
		try {
			Query query = entityManager.createQuery("from Cidade c where upper(c.nome) like upper(:nome) order by c.nome");
			query.setParameter("nome", (new StringBuilder("%")).append(cidadeFind).append("%").toString());
			return (ArrayList<Cidade>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}

	public void persist(Cidade cidade) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(cidade);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void merge(Cidade cidade) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(cidade);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void remove(Cidade cidade) {
		try {
			entityManager.getTransaction().begin();
			cidade = (Cidade) entityManager.find(Cidade.class, Integer.valueOf(cidade.getId()));
			entityManager.remove(cidade);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void removeById(int id) {
		try {
			Cidade cidade = getById(id);
			remove(cidade);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
