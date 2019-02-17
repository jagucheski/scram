package br.gov.rs.parobe.scram.filters;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/***
 * ESSE FILTER VAI SER CHAMADO TODA VEZ QUE FOR REALIZADO UMA REQUISIO PARA O
 * FACES SERVLET.
 */
@WebFilter(servletNames = { "Faces Servlet" })
public class JPAFilter implements Filter {

	private EntityManagerFactory entityManagerFactory;

	private String persistence_unit_name = "scram";

	/**
	 * Default constructor.
	 */
	public JPAFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		this.entityManagerFactory.close();
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		/* CRIANDO UM ENTITYMANAGER */
		EntityManager entityManager = this.entityManagerFactory.createEntityManager();

		/* ADICIONANDO ELE NA REQUISIÇÃO */
		request.setAttribute("entityManager", entityManager);

		/* INICIANDO UMA TRANSAÇÃOO */
		entityManager.getTransaction().begin();

		/* INICIANDO FACES SERVLET */
		chain.doFilter(request, response);

		try {

			/* SE N�O TIVER ERRO NA OPERAÇÃO ELE EXECUTA O COMMIT */
			entityManager.getTransaction().commit();

		} catch (Exception e) {

			/* SE TIVER ERRO NA OPERAÇÃOO E EXECUTADO O rollback */
			entityManager.getTransaction().rollback();
		} finally {

			/* DEPOIS DE DAR O COMMIT OU ROLLBACK ELE FINALIZA O entityManager */
			entityManager.close();
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// *CRIA O entityManagerFactory COM OS PAR�METROS DEFINIDOS NO persistence.xml*/
		this.entityManagerFactory = Persistence.createEntityManagerFactory(this.persistence_unit_name);
	}

}
