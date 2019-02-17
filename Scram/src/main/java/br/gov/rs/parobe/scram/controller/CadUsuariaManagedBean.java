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
import br.gov.rs.parobe.scram.uteis.ValidaCPF;

@Named(value = "cadUsuariaController")
@ViewScoped
public class CadUsuariaManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	static final Logger logger = Logger.getLogger("br.gov.rs.parobe.scram.controller.CadUsuariaManagedBean");

	private Usuaria usuariaFind;
	private Usuaria usuariaCadastro;
	private int usuariaIdSelecionado;
	private int tabIndexCadUsuaria;
	private int tabIndexCadUsuariaPerfis;
	private ArrayList<Usuaria> usuariaList;
	

	public CadUsuariaManagedBean() {
		this.tabIndexCadUsuaria = 0;
		this.tabIndexCadUsuariaPerfis = 0;
		this.usuariaFind = new Usuaria();
		this.usuariaCadastro = new Usuaria();
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

	public void salvarUsuaria() {
		if (validaCampos()) {
			if (validaCpf()) {
				saveUsuaria();
				findUsuaria();
				setTabIndexCadUsuariaPerfis(0);
			}
		}
		setTabIndexCadUsuaria(1);
	}

	public void saveUsuaria() {
		try {
			UsuariaDao.getInstance().merge(this.usuariaCadastro);
			Uteis.MensagemInfo("Usuária salva com sucesso!");
			this.usuariaCadastro = new Usuaria();
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
			Uteis.MensagemAtencao("Ops, Ocorreu um erro ao salvar a usuária!");			
		}
	}

	public void deletarUsuaria() {
		if (validaUsuaria()) {
			deleteUsuaria();
			findUsuaria();
			setTabIndexCadUsuariaPerfis(0);
			setTabIndexCadUsuaria(1);
		}
	}

	public void deleteUsuaria() {
		try {
			UsuariaDao.getInstance().remove(usuariaCadastro);
			Uteis.MensagemInfo("usuária excluida com sucesso!");
			novaUsuaria();
		} catch (Exception e) {
			Uteis.MensagemAtencao("Ops, Ocorreu um erro ao excluir a usuária!");
		}
	}

		public boolean validaUsuaria() {
		boolean usuariaSelecionada = true;
		if (usuariaCadastro.getId() == 0 || usuariaCadastro.getId() < 1) {
			Uteis.MensagemAtencao("Selecione uma usuária para excluir");
			usuariaSelecionada = false;
		}
		return usuariaSelecionada;
	}

	public boolean validaCampos() {
		boolean camposValidos = true;

		if (StringUtils.isEmpty(usuariaCadastro.getNome()) && StringUtils.isBlank(usuariaCadastro.getNome())) {
			Uteis.MensagemAtencao("Campo Nome é obrigatório");
			camposValidos = false;
		}

		if (StringUtils.isEmpty(usuariaCadastro.getCpf()) && StringUtils.isBlank(usuariaCadastro.getCpf())) {
			Uteis.MensagemAtencao("Campo CPF é obrigatório");
			camposValidos = false;
		} else if (!ValidaCPF.isCPF(usuariaCadastro.getCpf())) {
			Uteis.MensagemAtencao("CPF inválido");
			camposValidos = false;
		}	
			
		if (StringUtils.isEmpty(usuariaCadastro.getEtnia()) && StringUtils.isBlank(usuariaCadastro.getEtnia())) {
			Uteis.MensagemAtencao("Campo Etnia da aba Perfil Cultural é obrigatório");
			camposValidos = false;
		}

		if (StringUtils.isEmpty(usuariaCadastro.getReligiao()) && StringUtils.isBlank(usuariaCadastro.getReligiao())) {
			Uteis.MensagemAtencao("Campo Religião da aba Perfil Cultural é obrigatório");
			camposValidos = false;
		}
		
		if (StringUtils.isEmpty(usuariaCadastro.getSexo()) && StringUtils.isBlank(usuariaCadastro.getSexo())) {
			Uteis.MensagemAtencao("Campo Orientação Sexual da aba Perfil Cultural é obrigatório");
			camposValidos = false;
		}
		
		if (StringUtils.isEmpty(usuariaCadastro.getGrauInstrucao()) && StringUtils.isBlank(usuariaCadastro.getGrauInstrucao())) {
			Uteis.MensagemAtencao("Campo Grau de Instrução da aba Perfil Profissional é obrigatório");
			camposValidos = false;
		}

		return camposValidos;
	}

	public boolean validaCpf() {
		boolean confirm = false;
		Usuaria usuariaTemp = UsuariaDao.getInstance().getByCpf(this.usuariaCadastro.getCpf());

		if (usuariaTemp == null) {
			return true;
		} else {
			if (usuariaCadastro.getId() == usuariaTemp.getId()) {
				confirm = true;
			} else if (usuariaCadastro.getId() != usuariaTemp.getId()) {
				Uteis.MensagemAtencao("Existe uma usuária cadastrada com o CPF informado");
			}
		}
		return confirm;
	}

	public void novaUsuaria() {
		try {
			usuariaCadastro = new Usuaria();
			setTabIndexCadUsuaria(1);
			setTabIndexCadUsuariaPerfis(0);
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
		}
	}

	public void detalharUsuaria() {
		try {
			this.usuariaCadastro = UsuariaDao.getInstance().getById(usuariaIdSelecionado);
			setTabIndexCadUsuaria(1);
			setTabIndexCadUsuariaPerfis(0);
		} catch (Exception e) {
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

	public Usuaria getUsuariaCadastro() {
		return usuariaCadastro;
	}

	public void setUsuariaCadastro(Usuaria usuariaCadastro) {
		this.usuariaCadastro = usuariaCadastro;
	}

	public int getUsuariaIdSelecionado() {
		return usuariaIdSelecionado;
	}

	public void setUsuariaIdSelecionado(int usuariaIdSelecionado) {
		this.usuariaIdSelecionado = usuariaIdSelecionado;
	}

	public int getTabIndexCadUsuaria() {
		return tabIndexCadUsuaria;
	}

	public void setTabIndexCadUsuaria(int tabIndexCadUsuaria) {
		this.tabIndexCadUsuaria = tabIndexCadUsuaria;
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
