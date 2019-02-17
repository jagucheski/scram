package br.gov.rs.parobe.scram.controller;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import br.gov.rs.parobe.scram.dao.DenunciadoDao;
import br.gov.rs.parobe.scram.dao.EvolucaoDao;
import br.gov.rs.parobe.scram.dao.FichaAtendimentoDao;
import br.gov.rs.parobe.scram.dao.UsuariaDao;
import br.gov.rs.parobe.scram.model.Denunciado;
import br.gov.rs.parobe.scram.model.Evolucao;
import br.gov.rs.parobe.scram.model.FichaAtendimento;
import br.gov.rs.parobe.scram.model.Usuaria;
import br.gov.rs.parobe.scram.model.Usuario;
import br.gov.rs.parobe.scram.uteis.Uteis;

@Named(value = "cadFichaAtendimentoController")
@ViewScoped
public class CadFichaAtendimentoManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	static final Logger logger = Logger.getLogger("br.gov.rs.parobe.scram.controller.CadFichaAtendimentoManagedBean");

	private FichaAtendimento fichaAtendimentoFind;
	private FichaAtendimento fichaAtendimentoCad;
	private int fichaAtendimentoIdSelecionado;
	private int evolucaoFichaAtendimentoIdSelecionado;
	private int tabIndexCadFichaAtendimento;
	private int tabIndexCadFichaAtendimentoDados;
	
	private Evolucao evolucaoCad;
	
	private ArrayList<FichaAtendimento> fichaAtendimentoList;
	private ArrayList<Evolucao> evolucaoFichaAtendimentoList;
	

	public CadFichaAtendimentoManagedBean() {
		this.tabIndexCadFichaAtendimento = 0;
		this.tabIndexCadFichaAtendimentoDados = 0;
		this.fichaAtendimentoFind = new FichaAtendimento();
		this.fichaAtendimentoCad = new FichaAtendimento();
		this.evolucaoCad = new Evolucao();
		this.fichaAtendimentoList = new ArrayList<FichaAtendimento>();
		this.evolucaoFichaAtendimentoList = new ArrayList<Evolucao>();
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
	
	public void findAllEvolucaoFichaAtendimento() {
		try {
			evolucaoFichaAtendimentoList = (ArrayList<Evolucao>) EvolucaoDao.getInstance().findAllByFichaAtendimento(getFichaAtendimentoCad());
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
	
	public void salvarFichaAtendimento() {
		if (validaCampos()) {
			if(validaUsuariaDenunciado()) {
				this.fichaAtendimentoCad.carregaTipoViolencia();
				saveFichaAtendimento();
				findFichaAtendimento();
				setTabIndexCadFichaAtendimentoDados(0);
			}
		}
		setTabIndexCadFichaAtendimento(1);
	}

	public void saveFichaAtendimento() {
		try {			
			FichaAtendimentoDao.getInstance().merge(this.fichaAtendimentoCad);
			Uteis.MensagemInfo("Ficha de Atendimento salva com sucesso!");
			carregaFichaAtendimentoSaved(this.fichaAtendimentoCad);			
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
			Uteis.MensagemAtencao("Ops, Ocorreu um erro ao salvar a ficha de atencimento!");			
		}
	}

	private void carregaFichaAtendimentoSaved(FichaAtendimento fichaAtendimentoCad2) {
		if(this.fichaAtendimentoCad .getId() != 0 ||this.fichaAtendimentoCad.getId()>0) {
			this.fichaAtendimentoCad = FichaAtendimentoDao.getInstance().getById(this.fichaAtendimentoCad.getId());
		}else {
			this.fichaAtendimentoCad = FichaAtendimentoDao.getInstance().getByUsuariaDenunciado(this.fichaAtendimentoCad.getUsuaria(), this.fichaAtendimentoCad.getDenunciado());
		}
		this.fichaAtendimentoCad.carregaTiposViolencia();
		findAllEvolucaoFichaAtendimento();
	}

	public void deletarFichaAtendimento() {
		if (validaFichaAtendimento()) {
			deleteFichaAtendimento();
			findFichaAtendimento();
			setTabIndexCadFichaAtendimentoDados(0);
			setTabIndexCadFichaAtendimento(1);
		}
	}

	public void deletarEvolucaoFichaAtendimento() {
		if (validaEvolucaoFichaAtendimento()) {
			deleteEvolucaoFichaAtendimento();
			findAllEvolucaoFichaAtendimento();
		}
	}

	public void deleteFichaAtendimento() {
		try {
			FichaAtendimentoDao.getInstance().remove(fichaAtendimentoCad);
			Uteis.MensagemInfo("Ficha de Atendimento excluida com sucesso!");
			novaFichaAtendimento();
		} catch (Exception e) {
			Uteis.MensagemAtencao("Ops, Ocorreu um erro ao excluir a Ficha Atendimento!");
		}
	}
	
	public void deleteEvolucaoFichaAtendimento() {
		try {
			EvolucaoDao.getInstance().remove(evolucaoCad);
			Uteis.MensagemInfo("Evolução excluida com sucesso!");
			novaEvolucaoFichaAtendimento();
		} catch (Exception e) {
			Uteis.MensagemAtencao("Ops, Ocorreu um erro ao excluir a Evolução!");
		}
	}
	
	public void salvarEvolucao() {
		if (validaCamposEvolucao()) {
			 saveEvolucao();
			 findAllEvolucaoFichaAtendimento();			 
		}
	}
	
	public void saveEvolucao() {
		try {	
			this.evolucaoCad.setFichaAtendimento(getFichaAtendimentoCad());
			this.evolucaoCad.setUsuario((Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioAutenticado"));			
			EvolucaoDao.getInstance().merge(this.evolucaoCad);
			Uteis.MensagemInfo("Evolução salva com sucesso!");
			this.evolucaoCad = new Evolucao();
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
			Uteis.MensagemAtencao("Ops, Ocorreu um erro ao salvar a Evolução!");			
		}
	}


	public boolean validaFichaAtendimento() {
		boolean fichaAtendimentoSelecionada = true;
		if (this.fichaAtendimentoCad.getId() == 0 || fichaAtendimentoCad.getId() < 1) {
			Uteis.MensagemAtencao("Selecione uma Ficha de Atendimento para excluir");
			fichaAtendimentoSelecionada = false;
		}
		return fichaAtendimentoSelecionada;
	}
	
	public boolean validaEvolucaoFichaAtendimento() {
		boolean evolucaoFichaAtendimentoSelecionada = true;
		if (this.evolucaoCad.getId() == 0 || evolucaoCad.getId() < 1) {
			Uteis.MensagemAtencao("Selecione uma Evolução para excluir");
			evolucaoFichaAtendimentoSelecionada = false;
		}
		return evolucaoFichaAtendimentoSelecionada;
	}

	public boolean validaCampos() {
		boolean camposValidos = true;

		if (fichaAtendimentoCad.getDataFichaAtendimento() == null ) {
			Uteis.MensagemAtencao("Campo Data é obrigatório");
			camposValidos = false;
		}

		if (StringUtils.isEmpty(fichaAtendimentoCad.getReferenciadaServico()) && StringUtils.isBlank(fichaAtendimentoCad.getReferenciadaServico())) {
			Uteis.MensagemAtencao("Campo Serviço da aba Referenciada é obrigatório");
			camposValidos = false;
		} 
			
		if (StringUtils.isEmpty(fichaAtendimentoCad.getMotivoEncaminhamento()) && StringUtils.isBlank(fichaAtendimentoCad.getMotivoEncaminhamento())) {
			Uteis.MensagemAtencao("Campo Motivo da aba Motivo da Procura é obrigatório");
			camposValidos = false;
		}

		if (fichaAtendimentoCad.getTiposViolencia().length==0 || fichaAtendimentoCad.getTiposViolencia().length<1) {
			Uteis.MensagemAtencao("Campo Tipo Violência é obrigatório");
			camposValidos = false;
		}
		
		if (StringUtils.isEmpty(fichaAtendimentoCad.getProvidencia()) && StringUtils.isBlank(fichaAtendimentoCad.getProvidencia())) {
			Uteis.MensagemAtencao("Campo Providência da aba Providência é obrigatório");
			camposValidos = false;
		}
		
		return camposValidos;
	}
	
	public boolean validaCamposEvolucao() {
		boolean camposValidos = true;
		
		if (evolucaoCad.getData() == null ) {
			Uteis.MensagemAtencao("Campo Data é obrigatório");
			camposValidos = false;
		}
		
		if (StringUtils.isEmpty(evolucaoCad.getAssunto()) && StringUtils.isBlank(evolucaoCad.getAssunto())) {
			Uteis.MensagemAtencao("Campo Assunto é obrigatório");
			camposValidos = false;
		} 
		
		if (StringUtils.isEmpty(evolucaoCad.getDescricao()) && StringUtils.isBlank(evolucaoCad.getDescricao())) {
			Uteis.MensagemAtencao("Campo Descrição é obrigatório");
			camposValidos = false;
		}

		return camposValidos;
	}

	public boolean validaUsuariaDenunciado() {
		boolean confirm = false;
		FichaAtendimento fichaAtendimentoTemp = FichaAtendimentoDao.getInstance().getByUsuariaDenunciado(this.fichaAtendimentoCad.getUsuaria(), this.fichaAtendimentoCad.getDenunciado());

		if (fichaAtendimentoTemp == null) {
			return true;
		} else {
			if (this.fichaAtendimentoCad.getId() == fichaAtendimentoTemp.getId()) {
				confirm = true;
			} else if (fichaAtendimentoCad.getId() != fichaAtendimentoTemp.getId()) {
				Uteis.MensagemAtencao("Existe uma ficha de atendimento cadastrada com a Usuária e Denunciado informados");
			}
		}
		return confirm;
	}
	
	public void novaFichaAtendimento() {
		try {
			this.fichaAtendimentoCad = new FichaAtendimento();
			this.evolucaoFichaAtendimentoList = new ArrayList<Evolucao>();
			setTabIndexCadFichaAtendimento(1);
			setTabIndexCadFichaAtendimentoDados(0);
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
		}
	}

	public void novaEvolucaoFichaAtendimento() {
		try {
			evolucaoCad = new Evolucao();			
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
		}
	}

	public void detalharFichaAtendimento() {
		try {
			this.fichaAtendimentoCad = FichaAtendimentoDao.getInstance().getById(fichaAtendimentoIdSelecionado);
			this.fichaAtendimentoCad.carregaTiposViolencia();
			findAllEvolucaoFichaAtendimento();
			setTabIndexCadFichaAtendimento(1);
		} catch (Exception e) {
			Uteis.MensagemAtencao("Ops, Ocorreu um erro ao selecionar a ficha de atendimento! ");
		}
	}
	
	public void detalharEvolucaoFichaAtendimento() {
		try {
			this.evolucaoCad = EvolucaoDao.getInstance().getById(evolucaoFichaAtendimentoIdSelecionado);			
		} catch (Exception e) {
			Uteis.MensagemAtencao("Ops, Ocorreu um erro ao selecionar a evolução! ");
		}
	}
	
	public ArrayList<Usuaria> findUsuariaAutoComplete(String query) {
		ArrayList<Usuaria> usuariaListTemp = new ArrayList<Usuaria>();
		try {
			usuariaListTemp = UsuariaDao.getInstance().findAllByNome(query);
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
		}
		return usuariaListTemp;
	}
	
	public ArrayList<Denunciado> findDenunciadoAutoComplete(String query) {
		ArrayList<Denunciado> denunciadoListTemp = new ArrayList<Denunciado>();
		try {
			denunciadoListTemp = DenunciadoDao.getInstance().findAllByNome(query);
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
		}
		return denunciadoListTemp;
	}

	public FichaAtendimento getFichaAtendimentoFind() {
		return fichaAtendimentoFind;
	}

	public void setFichaAtendimentoFind(FichaAtendimento fichaAtendimentoFind) {
		this.fichaAtendimentoFind = fichaAtendimentoFind;
	}
	
	public FichaAtendimento getFichaAtendimentoCad() {
		return fichaAtendimentoCad;
	}

	public void setFichaAtendimentoCad(FichaAtendimento fichaAtendimentoCad) {
		this.fichaAtendimentoCad = fichaAtendimentoCad;
	}

	public int getFichaAtendimentoIdSelecionado() {
		return fichaAtendimentoIdSelecionado;
	}

	public void setFichaAtendimentoIdSelecionado(int fichaAtendimentoIdSelecionado) {
		this.fichaAtendimentoIdSelecionado = fichaAtendimentoIdSelecionado;
	}
	
	public int getEvolucaoFichaAtendimentoIdSelecionado() {
		return evolucaoFichaAtendimentoIdSelecionado;
	}

	public void setEvolucaoFichaAtendimentoIdSelecionado(int evolucaoFichaAtendimentoIdSelecionado) {
		this.evolucaoFichaAtendimentoIdSelecionado = evolucaoFichaAtendimentoIdSelecionado;
	}

	public int getTabIndexCadFichaAtendimento() {
		return tabIndexCadFichaAtendimento;
	}

	public void setTabIndexCadFichaAtendimento(int tabIndexCadFichaAtendimento) {
		this.tabIndexCadFichaAtendimento = tabIndexCadFichaAtendimento;
	}

	public int getTabIndexCadFichaAtendimentoDados() {
		return tabIndexCadFichaAtendimentoDados;
	}

	public void setTabIndexCadFichaAtendimentoDados(int tabIndexCadFichaAtendimentoDados) {
		this.tabIndexCadFichaAtendimentoDados = tabIndexCadFichaAtendimentoDados;
	}
	
	public Evolucao getEvolucaoCad() {
		return evolucaoCad;
	}

	public void setEvolucaoCad(Evolucao evolucaoCad) {
		this.evolucaoCad = evolucaoCad;
	}

	public ArrayList<FichaAtendimento> getFichaAtendimentoList() {
		return fichaAtendimentoList;
	}

	public void setFichaAtendimentoList(ArrayList<FichaAtendimento> fichaAtendimentoList) {
		this.fichaAtendimentoList = fichaAtendimentoList;
	}

	public ArrayList<Evolucao> getEvolucaoFichaAtendimentoList() {
		return evolucaoFichaAtendimentoList;
	}

	public void setEvolucaoFichaAtendimentoList(ArrayList<Evolucao> evolucaoFichaAtendimentoList) {
		this.evolucaoFichaAtendimentoList = evolucaoFichaAtendimentoList;
	}	
	
}
