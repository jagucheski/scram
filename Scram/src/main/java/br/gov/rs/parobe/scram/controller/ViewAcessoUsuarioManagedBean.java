package br.gov.rs.parobe.scram.controller;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import br.gov.rs.parobe.scram.dao.AcessoUsuarioDao;
import br.gov.rs.parobe.scram.model.AcessoUsuario;
import br.gov.rs.parobe.scram.uteis.Uteis;

@Named(value = "viewAcessoUsuarioController")
@ViewScoped
public class ViewAcessoUsuarioManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	static final Logger logger = Logger.getLogger("br.gov.rs.parobe.scram.controller.viewAcessoUsuarioController");

	private AcessoUsuario acessoUsuarioFind;
	private ArrayList<AcessoUsuario> acessoUsuarioList;

	public ViewAcessoUsuarioManagedBean() {
		this.acessoUsuarioFind = new AcessoUsuario();
		findAllAcessoUsuario();
	}

	public void findAllAcessoUsuario() {
		try {
			acessoUsuarioList = (ArrayList<AcessoUsuario>) AcessoUsuarioDao.getInstance().findAll();
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
			Uteis.MensagemAtencao("Ops. Ocorreu um erro, tente novamente.");
		}
	}

	public void findAcessoUsuario() {
		try {
			if (StringUtils.isEmpty(acessoUsuarioFind.getUsuario().getNome())) {
				findAllAcessoUsuario();
			} else if (StringUtils.isNotEmpty(acessoUsuarioFind.getUsuario().getNome())) {
				acessoUsuarioList = (ArrayList<AcessoUsuario>) AcessoUsuarioDao.getInstance().findAllByUsuarioNome(acessoUsuarioFind.getUsuario().getNome());
			} 
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
			Uteis.MensagemAtencao("Ops. Ocorreu um erro, tente novamente.");
		}
	}

	public AcessoUsuario getAcessoUsuarioFind() {
		return acessoUsuarioFind;
	}

	public void setAcessoUsuarioFind(AcessoUsuario acessoUsuarioFind) {
		this.acessoUsuarioFind = acessoUsuarioFind;
	}

	public ArrayList<AcessoUsuario> getAcessoUsuarioList() {
		return acessoUsuarioList;
	}

	public void setAcessoUsuarioList(ArrayList<AcessoUsuario> acessoUsuarioList) {
		this.acessoUsuarioList = acessoUsuarioList;
	}

}
