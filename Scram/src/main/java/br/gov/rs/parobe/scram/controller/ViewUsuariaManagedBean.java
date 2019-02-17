package br.gov.rs.parobe.scram.controller;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import br.gov.rs.parobe.scram.dao.CidadeDao;
import br.gov.rs.parobe.scram.dao.UsuariaDao;
import br.gov.rs.parobe.scram.model.Cidade;
import br.gov.rs.parobe.scram.model.Usuaria;
import br.gov.rs.parobe.scram.uteis.Uteis;

@Named(value = "viewUsuariaController")
@ViewScoped
public class ViewUsuariaManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	static final Logger logger = Logger.getLogger("br.gov.rs.parobe.scram.controller.ViewUsuariaManagedBean");

	private Usuaria usuariaFind;
	private Usuaria usuariaView;
	private int usuariaIdSelecionado;
	private int tabIndexCadUsuariaPerfis;
	private ArrayList<Usuaria> usuariaList;
	
	public ViewUsuariaManagedBean() {
		this.tabIndexCadUsuariaPerfis = 0;
		this.usuariaFind = new Usuaria();
		this.usuariaView = new Usuaria();
		this.usuariaList = new ArrayList<Usuaria>();
		findAllUsuaria();
	}

	public void findAllUsuaria() {
		try {
			usuariaList = (ArrayList<Usuaria>) UsuariaDao.getInstance().findAll();
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
			Uteis.MensagemAtencao("Ops. Ocorreu um erro, tente novamente.");
		}
	}

	public void findUsuaria() {
		try {
			if (StringUtils.isEmpty(usuariaFind.getNome()) && StringUtils.isBlank(usuariaFind.getCpf())) {
				findAllUsuaria();
			} else if (StringUtils.isEmpty(usuariaFind.getNome())) {
				usuariaList = (ArrayList<Usuaria>) UsuariaDao.getInstance().findAllByCpf(usuariaFind);
			} else if (StringUtils.isEmpty(usuariaFind.getCpf())) {
				usuariaList = (ArrayList<Usuaria>) UsuariaDao.getInstance().findAllByNome(usuariaFind);
			} else {
				usuariaList = (ArrayList<Usuaria>) UsuariaDao.getInstance().findAllByNomeCpf(usuariaFind);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
			Uteis.MensagemAtencao("Ops. Ocorreu um erro, tente novamente.");
		}
	}

	public void detalharUsuaria() {
		try {
			this.usuariaView = UsuariaDao.getInstance().getById(usuariaIdSelecionado);
			setTabIndexCadUsuariaPerfis(0);
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
			Uteis.MensagemAtencao("Ops, Ocorreu um erro ao selecionar a usuária! ");
		}
	}
	
	public ArrayList<Cidade> findCidadeAutoComplete(String query) {
		ArrayList<Cidade> cidadeListTemp = new ArrayList<Cidade>();
		try {
			cidadeListTemp = CidadeDao.getInstance().findAllByNome(query);
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
		}
		return cidadeListTemp;
	}

	public Usuaria getUsuariaFind() {
		return usuariaFind;
	}

	public void setUsuariaFind(Usuaria usuariaFind) {
		this.usuariaFind = usuariaFind;
	}

	public Usuaria getUsuariaView() {
		return usuariaView;
	}

	public void setUsuariaView(Usuaria usuariaView) {
		this.usuariaView = usuariaView;
	}

	public int getUsuariaIdSelecionado() {
		return usuariaIdSelecionado;
	}

	public void setUsuariaIdSelecionado(int usuariaIdSelecionado) {
		this.usuariaIdSelecionado = usuariaIdSelecionado;
	}

	public int getTabIndexCadUsuariaPerfis() {
		return tabIndexCadUsuariaPerfis;
	}

	public void setTabIndexCadUsuariaPerfis(int tabIndexCadUsuariaPerfis) {
		this.tabIndexCadUsuariaPerfis = tabIndexCadUsuariaPerfis;
	}

	public ArrayList<Usuaria> getUsuariaList() {
		return usuariaList;
	}

	public void setUsuariaList(ArrayList<Usuaria> usuariaList) {
		this.usuariaList = usuariaList;
	}
}
