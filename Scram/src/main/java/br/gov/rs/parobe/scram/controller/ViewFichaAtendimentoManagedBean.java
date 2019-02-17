package br.gov.rs.parobe.scram.controller;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import br.gov.rs.parobe.scram.dao.FichaAtendimentoDao;
import br.gov.rs.parobe.scram.model.FichaAtendimento;
import br.gov.rs.parobe.scram.uteis.Uteis;

@Named(value = "viewFichaAtendimentoController")
@ViewScoped
public class ViewFichaAtendimentoManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	static final Logger logger = Logger.getLogger("br.gov.rs.parobe.scram.controller.ViewFichaAtendimentoManagedBean");

	private FichaAtendimento fichaAtendimentoFind;
	private FichaAtendimento fichaAtendimentoView;
	private int fichaAtendimentoIdSelecionado;
	private int tabIndexViewFichaAtendimentoDados;
	private ArrayList<FichaAtendimento> fichaAtendimentoList;

	public ViewFichaAtendimentoManagedBean() {
		this.tabIndexViewFichaAtendimentoDados = 0;
		this.fichaAtendimentoFind = new FichaAtendimento();
		this.fichaAtendimentoView = new FichaAtendimento();
		this.fichaAtendimentoList = new ArrayList<FichaAtendimento>();
		findAllFichaAtendimento();
	}

	public void findAllFichaAtendimento() {
		try {
			fichaAtendimentoList = (ArrayList<FichaAtendimento>) FichaAtendimentoDao.getInstance().findAll();
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
			Uteis.MensagemAtencao("Ops. Ocorreu um erro, tente novamente.");
		}
	}
	
	public void findFichaAtendimento() {
		try {
			if (StringUtils.isEmpty(fichaAtendimentoFind.getUsuaria().getNome()) && StringUtils.isBlank(fichaAtendimentoFind.getUsuaria().getCpf())) {
				findAllFichaAtendimento();
			} else if (StringUtils.isEmpty(fichaAtendimentoFind.getUsuaria().getNome())) {
				fichaAtendimentoList = (ArrayList<FichaAtendimento>) FichaAtendimentoDao.getInstance().findAllByUsuariaCpf(fichaAtendimentoFind.getUsuaria().getCpf());			
			} else if (StringUtils.isEmpty(fichaAtendimentoFind.getUsuaria().getCpf())) {
				fichaAtendimentoList = (ArrayList<FichaAtendimento>) FichaAtendimentoDao.getInstance().findAllByUsuariaNome(fichaAtendimentoFind.getUsuaria().getNome());
			} else {
				fichaAtendimentoList = (ArrayList<FichaAtendimento>) FichaAtendimentoDao.getInstance().findAllByUsuariaNomeCpf(fichaAtendimentoFind.getUsuaria().getNome(),fichaAtendimentoFind.getUsuaria().getCpf());				
			}			
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
			Uteis.MensagemAtencao("Ops. Ocorreu um erro, tente novamente.");
		}
	}
		
		public void detalharFichaAtendimento() {
		try {
			this.fichaAtendimentoView = FichaAtendimentoDao.getInstance().getById(fichaAtendimentoIdSelecionado);
			this.fichaAtendimentoView.carregaTiposViolencia();
			setTabIndexViewFichaAtendimentoDados(0);
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
			Uteis.MensagemAtencao("Ops, Ocorreu um erro ao selecionar a ficha de atendimento! ");
		}
	}

	public FichaAtendimento getFichaAtendimentoFind() {
		return fichaAtendimentoFind;
	}

	public void setFichaAtendimentoFind(FichaAtendimento fichaAtendimentoFind) {
		this.fichaAtendimentoFind = fichaAtendimentoFind;
	}

	public FichaAtendimento getFichaAtendimentoView() {
		return fichaAtendimentoView;
	}

	public void setFichaAtendimentoView(FichaAtendimento fichaAtendimentoView) {
		this.fichaAtendimentoView = fichaAtendimentoView;
	}

	public int getTabIndexViewFichaAtendimentoDados() {
		return tabIndexViewFichaAtendimentoDados;
	}

	public void setTabIndexViewFichaAtendimentoDados(int tabIndexViewFichaAtendimentoDados) {
		this.tabIndexViewFichaAtendimentoDados = tabIndexViewFichaAtendimentoDados;
	}

	public int getFichaAtendimentoIdSelecionado() {
		return fichaAtendimentoIdSelecionado;
	}

	public void setFichaAtendimentoIdSelecionado(int fichaAtendimentoIdSelecionado) {
		this.fichaAtendimentoIdSelecionado = fichaAtendimentoIdSelecionado;
	}

	public ArrayList<FichaAtendimento> getFichaAtendimentoList() {
		return fichaAtendimentoList;
	}

	public void setFichaAtendimentoList(ArrayList<FichaAtendimento> fichaAtendimentoList) {
		this.fichaAtendimentoList = fichaAtendimentoList;
	}
	
}
