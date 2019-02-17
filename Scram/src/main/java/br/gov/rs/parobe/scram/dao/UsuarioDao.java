package br.gov.rs.parobe.scram.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.gov.rs.parobe.scram.model.Usuario;

public class UsuarioDao {

	private static UsuarioDao instance;
	protected EntityManager entityManager;

	public static UsuarioDao getInstance() {
		if (instance == null) {
			instance = new UsuarioDao();
		}
		return instance;
	}

	private UsuarioDao() {
		entityManager = getEntityManager();
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("scram");
		if (entityManager == null) {
			entityManager = factory.createEntityManager();
		}
		return entityManager;
	}

	public Usuario getById(int id) {
		return (Usuario) entityManager.find(Usuario.class, Integer.valueOf(id));
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> findAll() {
		try {
			return (List<Usuario>) entityManager.createQuery("from Usuario u where u.nome != 'Administrador' order by u.nome").getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> findAllByNome(Usuario usuarioFind) {
		try {
			Query query = entityManager.createQuery("from Usuario u where upper(u.nome) like upper(:nome) and u.nome != 'Administrador' order by u.nome");
			query.setParameter("nome", (new StringBuilder("%")).append(usuarioFind.getNome()).append("%").toString());
			return (ArrayList<Usuario>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Usuario> findAllByCpf(Usuario usuarioFind) {
		try {
			Query query = entityManager.createQuery("from Usuario u where u.cpf = :cpf and u.nome != 'Administrador' order by u.nome");
			query.setParameter("cpf", usuarioFind.getCpf());
			return (ArrayList<Usuario>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Usuario> findAllByNomeCpf(Usuario usuarioFind) {
		try {
			Query query = entityManager.createQuery("from Usuario u where upper(u.nome) like upper(:nome) and u.cpf =:cpf and u.nome != 'Administrador' order by u.nome");
			query.setParameter("nome", (new StringBuilder("%")).append(usuarioFind.getNome()).append("%").toString());
			query.setParameter("cpf", usuarioFind.getCpf());
			return (ArrayList<Usuario>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}	
	
	/***
	 * METODO RESPONS�VEL POR VALIDAR O USUARIO INFORMADO NO LOGIN
	 * 
	 * @param usuario
	 */
	public Usuario findByCpfSenha(Usuario usuario) {
		try {
			Query query = entityManager.createQuery("from Usuario u where u.cpf = :cpf and u.senha = :senha");
			query.setParameter("cpf", usuario.getCpf());
			query.setParameter("senha", usuario.getSenha());			
			return (Usuario) query.getSingleResult();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	/***
	 * METODO USADO PARA PESQUISAR USUARIO POR CPF QUE SER� UTILIZADO PARA VALIDAR CPF N�O DEIXANDO TER DOIS CPF IGUAIS NA BASE
	 * 
	 * @param usuario
	 */
	public Usuario findByCpf(String cpf) {
		try {
			Query query = entityManager.createQuery("from Usuario u where u.cpf = :cpf");
			query.setParameter("cpf", cpf);						
			return (Usuario) query.getSingleResult();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public void persist(Usuario Usuario) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(Usuario);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}

	public void merge(Usuario Usuario) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(Usuario);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}

	public void remove(Usuario usuario) {
		try {
			entityManager.getTransaction().begin();
			usuario = (Usuario) entityManager.find(Usuario.class, Integer.valueOf(usuario.getId()));
			entityManager.remove(usuario);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}

	public void removeById(int id) {
		try {
			Usuario Usuario = getById(id);
			remove(Usuario);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new  PersistenceException();
		}
	}
}
