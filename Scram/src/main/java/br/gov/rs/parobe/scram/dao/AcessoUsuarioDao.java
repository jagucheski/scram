package br.gov.rs.parobe.scram.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.gov.rs.parobe.scram.model.AcessoUsuario;

public class AcessoUsuarioDao {

	private static AcessoUsuarioDao instance;
	protected EntityManager entityManager;

	public static AcessoUsuarioDao getInstance() {
		if (instance == null) {
			instance = new AcessoUsuarioDao();
		}
		return instance;
	}

	private AcessoUsuarioDao() {
		entityManager = getEntityManager();
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("scram");
		if (entityManager == null) {
			entityManager = factory.createEntityManager();
		}
		return entityManager;
	}

	public AcessoUsuario getById(int id) {
		return (AcessoUsuario) entityManager.find(AcessoUsuario.class, Integer.valueOf(id));
	}

	@SuppressWarnings("unchecked")
	public List<AcessoUsuario> findAll() {
		try {
			return (List<AcessoUsuario>) entityManager.createQuery("from AcessoUsuario au where au.usuario.nome != 'Administrador' order by au.data").getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	
	@SuppressWarnings("unchecked")
	public List<AcessoUsuario> findAllByUsuarioNome(String nomeUsuario) {
		try {
			Query query = entityManager.createQuery("from AcessoUsuario u where upper(u.usuario.nome) like upper(:nomeUsuario) and u.usuario.nome != 'Administrador' order by u.usuario.nome");
			query.setParameter("nomeUsuario", (new StringBuilder("%")).append(nomeUsuario).append("%").toString());
			return (ArrayList<AcessoUsuario>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}
	
	public void persist(AcessoUsuario acessoUsuario) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(acessoUsuario);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}

	public void merge(AcessoUsuario acessoUsuario) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(acessoUsuario);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}

	public void remove(AcessoUsuario acessousuario) {
		try {
			entityManager.getTransaction().begin();
			acessousuario = (AcessoUsuario) entityManager.find(AcessoUsuario.class, Integer.valueOf(acessousuario.getId()));
			entityManager.remove(acessousuario);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}

	public void removeById(int id) {
		try {
			AcessoUsuario acessoUsuario = getById(id);
			remove(acessoUsuario);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new  PersistenceException();
		}
	}
}
