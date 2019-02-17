package br.gov.rs.parobe.scram.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.gov.rs.parobe.scram.model.Denunciado;
import br.gov.rs.parobe.scram.model.FichaAtendimento;
import br.gov.rs.parobe.scram.model.Usuaria;

public class FichaAtendimentoDao {

	private static FichaAtendimentoDao instance;
	protected EntityManager entityManager;

	public static FichaAtendimentoDao getInstance() {
		if (instance == null) {
			instance = new FichaAtendimentoDao();
		}
		return instance;
	}

	private FichaAtendimentoDao() {
		entityManager = getEntityManager();
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("scram");
		if (entityManager == null) {
			entityManager = factory.createEntityManager();
		}
		return entityManager;
	}

	public FichaAtendimento getById(int id) {
		return (FichaAtendimento) entityManager.find(FichaAtendimento.class, Integer.valueOf(id));
	}

	@SuppressWarnings("unchecked")
	public List<FichaAtendimento> findAll() {
		try {
			return (List<FichaAtendimento>) entityManager.createQuery("from FichaAtendimento f order by f.id").getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<FichaAtendimento> findAllById(int idFind) {
		try {
			Query query = entityManager.createQuery("from FichaAtendimento f where f.id = :id order by f.id");
			query.setParameter("id", idFind);
			return (ArrayList<FichaAtendimento>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}

	public FichaAtendimento getByUsuariaDenunciado(Usuaria usuaria, Denunciado denunciado) {
		try {
			Query query = entityManager.createQuery("from FichaAtendimento f where f.usuaria = :usuaria and f.denunciado = :denunciado ");
			query.setParameter("usuaria", usuaria);						
			query.setParameter("denunciado", denunciado);						
			return (FichaAtendimento) query.getSingleResult();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public void persist(FichaAtendimento fichaAtendimento) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(fichaAtendimento);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}

	public void merge(FichaAtendimento fichaAtendimento) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(fichaAtendimento);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}

	public void remove(FichaAtendimento fichaAtendimento) {
		try {
			entityManager.getTransaction().begin();
			fichaAtendimento = (FichaAtendimento) entityManager.find(FichaAtendimento.class, Integer.valueOf(fichaAtendimento.getId()));
			entityManager.remove(fichaAtendimento);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}

	public void removeById(int id) {
		try {
			FichaAtendimento fichaAtendimento = getById(id);
			remove(fichaAtendimento);
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<FichaAtendimento> findAllByUsuariaCpf(String cpfUsuaria) {
		try {
			Query query = entityManager.createQuery("from FichaAtendimento f where f.usuaria.cpf = :cpf order by f.id");
			query.setParameter("cpf", cpfUsuaria);
			return (ArrayList<FichaAtendimento>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<FichaAtendimento> findAllByUsuariaNome(String nomeUsuaria) {
		try {
			Query query = entityManager.createQuery("from FichaAtendimento f where upper(f.usuaria.nome) like upper(:nome) order by f.id");
			query.setParameter("nome", (new StringBuilder("%")).append(nomeUsuaria).append("%").toString());
			return (ArrayList<FichaAtendimento>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public ArrayList<FichaAtendimento> findAllByUsuariaNomeCpf(String nomeUsuaria, String cpfUsuaria) {
		try {
			Query query = entityManager.createQuery("from FichaAtendimento f where upper(f.usuaria.nome) like upper(:nome) and f.usuaria.cpf = :cpf  order by f.id");
			query.setParameter("nome", (new StringBuilder("%")).append(nomeUsuaria).append("%").toString());
			query.setParameter("cpf", cpfUsuaria);
			return (ArrayList<FichaAtendimento>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}
}
