package br.gov.rs.parobe.scram.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.gov.rs.parobe.scram.model.Evolucao;
import br.gov.rs.parobe.scram.model.FichaAtendimento;

public class EvolucaoDao {

	private static EvolucaoDao instance;
	protected EntityManager entityManager;

	public static EvolucaoDao getInstance() {
		if (instance == null) {
			instance = new EvolucaoDao();
		}
		return instance;
	}

	private EvolucaoDao() {
		entityManager = getEntityManager();
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("scram");
		if (entityManager == null) {
			entityManager = factory.createEntityManager();
		}
		return entityManager;
	}

	public Evolucao getById(int id) {
		return (Evolucao) entityManager.find(Evolucao.class, Integer.valueOf(id));
	}

	@SuppressWarnings("unchecked")
	public List<Evolucao> findAllByFichaAtendimento(FichaAtendimento fichaAtendimentoFind) {
		try {
			Query query = entityManager.createQuery("from Evolucao e where e.fichaAtendimento = :fichaAtendimentoFind order by e.data");
			query.setParameter("fichaAtendimentoFind", fichaAtendimentoFind);
			return (ArrayList<Evolucao>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}
	
	public String numeroAtendimentos(String ano) {
		try {
			Query query = entityManager.createNativeQuery("SELECT COUNT(data) AS total FROM evolucao WHERE TO_CHAR(data, 'YYYY') = '" + ano + "'");
			return query.getSingleResult().toString();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}
	
	public void persist(Evolucao Evolucao) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(Evolucao);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new PersistenceException();
		}
	}

	public void merge(Evolucao Evolucao) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(Evolucao);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new PersistenceException();
		}
	}

	public void remove(Evolucao usuario) {
		try {
			entityManager.getTransaction().begin();
			usuario = (Evolucao) entityManager.find(Evolucao.class, Integer.valueOf(usuario.getId()));
			entityManager.remove(usuario);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new PersistenceException();
		}
	}

	public void removeById(int id) {
		try {
			Evolucao Evolucao = getById(id);
			remove(Evolucao);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new PersistenceException();
		}
	}
}
