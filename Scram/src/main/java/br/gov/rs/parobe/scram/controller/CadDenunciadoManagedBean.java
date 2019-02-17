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
import br.gov.rs.parobe.scram.uteis.ValidaCPF;

@Named(value = "cadDenunciadoController")
@ViewScoped
public class CadDenunciadoManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	static final Logger logger = Logger.getLogger("br.gov.rs.parobe.scram.controller.CadDenunciadoManagedBean");

	private Denunciado denunciadoFind;
	private Denunciado denunciadoCadastro;
	private int denunciadoIdSelecionado;
	private int tabIndexCadDenunciado;
	private int tabIndexCadDenunciadoPerfis;
	private ArrayList<Denunciado> denunciadoList;
	

	public CadDenunciadoManagedBean() {
		this.tabIndexCadDenunciado = 0;
		this.tabIndexCadDenunciadoPerfis = 0;
		this.denunciadoFind = new Denunciado();
		this.denunciadoCadastro = new Denunciado();
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

	public void salvarDenunciado() {
		if (validaCampos()) {
			if (validaCpf()) {
				saveDenunciado();
				findDenunciado();
				setTabIndexCadDenunciadoPerfis(0);
			}
		}
		setTabIndexCadDenunciado(1);
	}

	public void saveDenunciado() {
		try {
			DenunciadoDao.getInstance().merge(this.denunciadoCadastro);
			Uteis.MensagemInfo("Denunciado salvo com sucesso!");
			this.denunciadoCadastro = new Denunciado();
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
			Uteis.MensagemAtencao("Ops, Ocorreu um erro ao salvar o Denunciado!");			
		}
	}

	public void deletarDenunciado() {
		if (validaDenunciado()) {
			deleteDenunciado();
			findDenunciado();
			setTabIndexCadDenunciadoPerfis(0);
			setTabIndexCadDenunciado(1);
		}
	}

	public void deleteDenunciado() {
		try {
			DenunciadoDao.getInstance().remove(denunciadoCadastro);
			Uteis.MensagemInfo("Denunciado excluido com sucesso!");
			novoDenunciado();
		} catch (Exception e) {
			Uteis.MensagemAtencao("Ops, Ocorreu um erro ao excluir o Denunciado!");
		}
	}

		public boolean validaDenunciado() {
		boolean denunciadoselecionado = true;
		if (denunciadoCadastro.getId() == 0 || denunciadoCadastro.getId() < 1) {
			Uteis.MensagemAtencao("Selecione um denunciado para excluir");
			denunciadoselecionado = false;
		}
		return denunciadoselecionado;
	}

	public boolean validaCampos() {
		boolean camposValidos = true;

		if (StringUtils.isEmpty(denunciadoCadastro.getNome()) && StringUtils.isBlank(denunciadoCadastro.getNome())) {
			Uteis.MensagemAtencao("Campo Nome é obrigatório");
			camposValidos = false;
		}

		if (StringUtils.isNotEmpty(denunciadoCadastro.getCpf()) && StringUtils.isNotBlank(denunciadoCadastro.getCpf())) {
			if (!ValidaCPF.isCPF(denunciadoCadastro.getCpf())) {
				Uteis.MensagemAtencao("CPF inválido");
				camposValidos = false;
			}
		}	
			
		if (StringUtils.isEmpty(denunciadoCadastro.getEtnia()) && StringUtils.isBlank(denunciadoCadastro.getEtnia())) {
			Uteis.MensagemAtencao("Campo Etnia da aba Perfil Cultural é obrigatório");
			camposValidos = false;
		}

		if (StringUtils.isEmpty(denunciadoCadastro.getReligiao()) && StringUtils.isBlank(denunciadoCadastro.getReligiao())) {
			Uteis.MensagemAtencao("Campo Religião da aba Perfil Cultural é obrigatório");
			camposValidos = false;
		}
		
		if (StringUtils.isEmpty(denunciadoCadastro.getSexo()) && StringUtils.isBlank(denunciadoCadastro.getSexo())) {
			Uteis.MensagemAtencao("Campo Orientação Sexual da aba Perfil Cultural é obrigatório");
			camposValidos = false;
		}
		
		if (StringUtils.isEmpty(denunciadoCadastro.getGrauInstrucao()) && StringUtils.isBlank(denunciadoCadastro.getGrauInstrucao())) {
			Uteis.MensagemAtencao("Campo Grau de Instrução da aba Perfil Profissional é obrigatório");
			camposValidos = false;
		}

		return camposValidos;
	}

	public boolean validaCpf() {
		boolean confirm = true;
		if (StringUtils.isNotEmpty(denunciadoCadastro.getCpf())
				&& StringUtils.isNotBlank(denunciadoCadastro.getCpf())) {
			Denunciado denunciadoTemp = DenunciadoDao.getInstance().getByCpf(this.denunciadoCadastro.getCpf());

			if (denunciadoTemp == null) {
				return true;
			} else {
				if (denunciadoCadastro.getId() == denunciadoTemp.getId()) {
					confirm = true;
				} else if (denunciadoCadastro.getId() != denunciadoTemp.getId()) {
					confirm = false;
					Uteis.MensagemAtencao("Existe um Denunciado cadastrado com o CPF informado");
				}
			}
		}
		return confirm;
	}

	public void novoDenunciado() {
		try {
			denunciadoCadastro = new Denunciado();
			setTabIndexCadDenunciadoPerfis(0);
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
		}
	}

	public void detalharDenunciado() {
		try {
			this.denunciadoCadastro = DenunciadoDao.getInstance().getById(denunciadoIdSelecionado);
			setTabIndexCadDenunciado(1);
			setTabIndexCadDenunciadoPerfis(0);
		} catch (Exception e) {
			Uteis.MensagemAtencao("Ops, Ocorreu um erro ao selecionar o Denunciado! ");
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

	public Denunciado getDenunciadoCadastro() {
		return denunciadoCadastro;
	}

	public void setDenunciadoCadastro(Denunciado denunciadoCadastro) {
		this.denunciadoCadastro = denunciadoCadastro;
	}

	public int getDenunciadoIdSelecionado() {
		return denunciadoIdSelecionado;
	}

	public void setDenunciadoIdSelecionado(int denunciadoIdSelecionado) {
		this.denunciadoIdSelecionado = denunciadoIdSelecionado;
	}

	public int getTabIndexCadDenunciado() {
		return tabIndexCadDenunciado;
	}

	public void setTabIndexCadDenunciado(int tabIndexCadDenunciado) {
		this.tabIndexCadDenunciado = tabIndexCadDenunciado;
	}

	public int getTabIndexCadDenunciadoPerfis() {
		return tabIndexCadDenunciadoPerfis;
	}

	public void setTabIndexCadDenunciadoPerfis(int tabIndexCadDenunciadoPerfis) {
		this.tabIndexCadDenunciadoPerfis = tabIndexCadDenunciadoPerfis;
	}

	public ArrayList<Denunciado> getDenunciadoList() {
		return denunciadoList;
	}

	public void setDenunciadoList(ArrayList<Denunciado> denunciadoList) {
		this.denunciadoList = denunciadoList;
	}

}
