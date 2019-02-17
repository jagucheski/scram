package br.gov.rs.parobe.scram.controller;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import br.gov.rs.parobe.scram.dao.CidadeDao;
import br.gov.rs.parobe.scram.dao.DenunciadoDao;
import br.gov.rs.parobe.scram.model.Cidade;
import br.gov.rs.parobe.scram.model.Denunciado;
import br.gov.rs.parobe.scram.uteis.Uteis;

@Named(value = "viewDenunciadoController")
@ViewScoped
public class ViewDenunciadoManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	static final Logger logger = Logger.getLogger("br.gov.rs.parobe.scram.controller.ViewDenunciadoManagedBean");

	private Denunciado denunciadoFind;
	private Denunciado denunciadoView;
	private int denunciadoIdSelecionado;
	private int tabIndexCadDenunciadoPerfis;
	private ArrayList<Denunciado> denunciadoList;
	
	public ViewDenunciadoManagedBean() {
		this.tabIndexCadDenunciadoPerfis = 0;
		this.denunciadoFind = new Denunciado();
		this.denunciadoView = new Denunciado();
		this.denunciadoList = new ArrayList<Denunciado>();
		findAllDenunciado();
	}

	public void findAllDenunciado() {
		try {
			denunciadoList = (ArrayList<Denunciado>) DenunciadoDao.getInstance().findAll();
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
			Uteis.MensagemAtencao("Ops. Ocorreu um erro, tente novamente.");
		}
	}

	public void findDenunciado() {
		try {
			if (StringUtils.isEmpty(denunciadoFind.getNome()) && StringUtils.isBlank(denunciadoFind.getCpf())) {
				findAllDenunciado();
			} else if (StringUtils.isEmpty(denunciadoFind.getNome())) {
				denunciadoList = (ArrayList<Denunciado>) DenunciadoDao.getInstance().findAllByCpf(denunciadoFind);
			} else if (StringUtils.isEmpty(denunciadoFind.getCpf())) {
				denunciadoList = (ArrayList<Denunciado>) DenunciadoDao.getInstance().findAllByNome(denunciadoFind);
			} else {
				denunciadoList = (ArrayList<Denunciado>) DenunciadoDao.getInstance().findAllByNomeCpf(denunciadoFind);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
			Uteis.MensagemAtencao("Ops. Ocorreu um erro, tente novamente.");
		}
	}

	public void detalharDenunciado() {
		try {
			this.denunciadoView = DenunciadoDao.getInstance().getById(denunciadoIdSelecionado);
			setTabIndexCadDenunciadoPerfis(0);
		} catch (Exception e) {
			Uteis.MensagemAtencao("Ops, Ocorreu um erro ao selecionar o denunciado! ");
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

	public Denunciado getDenunciadoFind() {
		return denunciadoFind;
	}

	public void setDenunciadoFind(Denunciado denunciadoFind) {
		this.denunciadoFind = denunciadoFind;
	}

	public Denunciado getDenunciadoView() {
		return denunciadoView;
	}

	public void setDenunciadoView(Denunciado denunciadoView) {
		this.denunciadoView = denunciadoView;
	}

	public int getDenunciadoIdSelecionado() {
		return denunciadoIdSelecionado;
	}

	public void setDenunciadoIdSelecionado(int denunciadoIdSelecionado) {
		this.denunciadoIdSelecionado = denunciadoIdSelecionado;
	}

	public int getTabIndexCadDenunciadoPerfis() {
		return tabIndexCadDenunciadoPerfis;
	}

	public void setTabIndexCadDenunciadoPerfis(int tabIndexCaddenunciadoPerfis) {
		this.tabIndexCadDenunciadoPerfis = tabIndexCaddenunciadoPerfis;
	}

	public ArrayList<Denunciado> getDenunciadoList() {
		return denunciadoList;
	}

	public void setDenunciadoList(ArrayList<Denunciado> denunciadoList) {
		this.denunciadoList = denunciadoList;
	}
}
