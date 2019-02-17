package br.gov.rs.parobe.scram.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.StreamedContent;

import br.gov.rs.parobe.scram.dao.CidadeDao;
import br.gov.rs.parobe.scram.dao.EvolucaoDao;
import br.gov.rs.parobe.scram.model.Cidade;
import br.gov.rs.parobe.scram.model.Denunciado;
import br.gov.rs.parobe.scram.model.FichaAtendimento;
import br.gov.rs.parobe.scram.model.Usuaria;
import br.gov.rs.parobe.scram.uteis.DateUtils;
import br.gov.rs.parobe.scram.uteis.ReportUtil;
import br.gov.rs.parobe.scram.uteis.Uteis;

@Named(value = "reportBean")
@ViewScoped
public class ReportManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	static final Logger logger = Logger.getLogger("br.gov.rs.parobe.scram.controller.ReportManagedBean");

	private Date inicioPeriodo;
	private Date fimPeriodo;
	private String ano;
	private StreamedContent relatorioUsuarias;
	private StreamedContent relatorioDenunciados;
	private StreamedContent relatorioFichaAtendimentos;
	private StreamedContent relatorioEvolucao;
	private StreamedContent relatorioAtendimentos;
	private Usuaria usuariaFind;
	private Denunciado denunciadoFind;
	private FichaAtendimento fichaAtendimentoFind;	
	

	public ReportManagedBean() {
		usuariaFind = new Usuaria();
		denunciadoFind = new Denunciado();
		fichaAtendimentoFind = new FichaAtendimento();
	}

	public StreamedContent getRelatorioUsuarias() {
		String condicaoNomeCidade = "";

		if (this.usuariaFind.getCidade() != null) {
			if (StringUtils.isNotEmpty(this.usuariaFind.getCidade().getNome())
					&& StringUtils.isNotBlank(this.usuariaFind.getCidade().getNome())) {
				condicaoNomeCidade = " WHERE cidade.nome = '" + this.usuariaFind.getCidade().getNome() + "'";
			}
		}

		ReportUtil reportUtil = new ReportUtil();
		try {
			relatorioUsuarias = reportUtil.geraRelatorioUsuarias(condicaoNomeCidade);
		} catch (Exception e) {
			System.out.println(e.toString());
			Uteis.MensagemAtencao("Ops. Ocorreu um erro, tente novamente.");
			logger.error(e.getMessage(), e);
			return null;
		}

		this.usuariaFind = new Usuaria();
		return relatorioUsuarias;
	}

	public StreamedContent getRelatorioDenunciados() {
		String condicaoNomeCidade = "";

		if (this.denunciadoFind.getCidade() != null) {
			if (StringUtils.isNotEmpty(this.denunciadoFind.getCidade().getNome())
					&& StringUtils.isNotBlank(this.denunciadoFind.getCidade().getNome())) {
				condicaoNomeCidade = " WHERE cidade.nome = '" + this.denunciadoFind.getCidade().getNome() + "'";
			}
		}

		ReportUtil reportUtil = new ReportUtil();
		try {
			relatorioDenunciados = reportUtil.geraRelatorioDenunciados(condicaoNomeCidade);
		} catch (Exception e) {
			System.out.println(e.toString());
			Uteis.MensagemAtencao("Ops. Ocorreu um erro, tente novamente.");
			logger.error(e.getMessage(), e);
			return null;
		}
		
		this.denunciadoFind = new Denunciado();
		return relatorioDenunciados;
	}

	public StreamedContent getRelatorioFichaAtendimentos() {
		if (validaPeriodo()) {

			String inicioPeriodoTemp = DateUtils.getInicioPeriodoForm(this.getInicioPeriodo()).substring(0, 10);
			String fimPeriodoTemp = DateUtils.getFimPeriodoForm(this.getFimPeriodo()).substring(0, 10);

			String condicaoTipoViolencia = " WHERE 1=1 AND 1=1 ";
			String condicaoPeriodo = " AND ficha_atendimento.data_ficha_atendimento BETWEEN '" + inicioPeriodoTemp + "' AND '" + fimPeriodoTemp + "'";

			this.fichaAtendimentoFind.carregaTipoViolencia();

			if (StringUtils.isNotEmpty(this.fichaAtendimentoFind.getTipoViolencia())
					&& StringUtils.isNotBlank(this.fichaAtendimentoFind.getTipoViolencia())
					&& this.fichaAtendimentoFind.getTipoViolencia().length() > 1) { 
				
				StringTokenizer st = new StringTokenizer(this.fichaAtendimentoFind.getTipoViolencia(), "-");
				while (st.hasMoreTokens()) {
					condicaoTipoViolencia += " OR ficha_atendimento.tipo_violencia like '%" + st.nextToken() + "%'";
				}
			}

			ReportUtil reportUtil = new ReportUtil();
			try {
				relatorioFichaAtendimentos = reportUtil.geraRelatorioFichaAtendimento(condicaoTipoViolencia, condicaoPeriodo, this.fichaAtendimentoFind.getTipoViolencia(), inicioPeriodoTemp, fimPeriodoTemp);
			} catch (Exception e) {
				System.out.println(e.toString());
				Uteis.MensagemAtencao("Ops. Ocorreu um erro, tente novamente.");
				logger.error(e.getMessage(), e);
				return null;
			}

		}else {
			relatorioFichaAtendimentos = null;
		}
		
		this.fichaAtendimentoFind = new FichaAtendimento();
		this.inicioPeriodo = null;
		this.fimPeriodo = null;		
		return relatorioFichaAtendimentos;
	}

	public StreamedContent getRelatorioEvolucao() {
		if (validaPeriodo()) {

			String inicioPeriodoTemp = DateUtils.getInicioPeriodoForm(this.getInicioPeriodo()).substring(0, 10);
			String fimPeriodoTemp = DateUtils.getFimPeriodoForm(this.getFimPeriodo()).substring(0, 10);
			String condicaoPeriodo = " WHERE evolucao.data BETWEEN '" + inicioPeriodoTemp + "' AND '" + fimPeriodoTemp + "' ";

			ReportUtil reportUtil = new ReportUtil();
			try {
				relatorioEvolucao = reportUtil.geraRelatorioEvolucao(condicaoPeriodo, inicioPeriodoTemp, fimPeriodoTemp);
			} catch (Exception e) {
				System.out.println(e.toString());
				Uteis.MensagemAtencao("Ops. Ocorreu um erro, tente novamente.");
				logger.error(e.getMessage(), e);
				return null;
			}
		}else {
			relatorioEvolucao = null;
		}
		
		
		
		this.inicioPeriodo = null;
		this.fimPeriodo = null;	
		return relatorioEvolucao;
	}
	
	public StreamedContent getRelatorioAtendimentos() {
		if (StringUtils.isEmpty(this.ano) && StringUtils.isBlank(this.ano)) {
			Uteis.MensagemAtencao("O campo Período deve ser preenchido");
			return null;
		}else {

			String totalAtendimentos = EvolucaoDao.getInstance().numeroAtendimentos(this.ano);
			
			String condicaoPeriodo = " WHERE to_char(data, 'YYYY') = '" + this.ano +"' ";			
			ReportUtil reportUtil = new ReportUtil();
			try {
				relatorioAtendimentos = reportUtil.geraRelatorioAtendimentos(condicaoPeriodo, this.ano, totalAtendimentos);
			} catch (Exception e) {
				System.out.println(e.toString());
				Uteis.MensagemAtencao("Ops. Ocorreu um erro, tente novamente.");
				logger.error(e.getMessage(), e);
				return null;
			}
		}	
		this.ano = null; 
		return relatorioAtendimentos;
	}

	private boolean validaPeriodo() {
		boolean retorno = true;
		if (inicioPeriodo == null || fimPeriodo == null) {
			Uteis.MensagemAtencao("Os campos  do Período devem ser preenchidos");
			retorno = false;
		}else if (inicioPeriodo.after(fimPeriodo)) {
			Uteis.MensagemAtencao("A data de inicio do período deve ser menor que a final");
			retorno = false;
		}
		return retorno;
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

	public Date getInicioPeriodo() {
		return inicioPeriodo;
	}

	public void setInicioPeriodo(Date inicioPeriodo) {
		this.inicioPeriodo = inicioPeriodo;
	}

	public Date getFimPeriodo() {
		return fimPeriodo;
	}

	public void setFimPeriodo(Date fimPeriodo) {
		this.fimPeriodo = fimPeriodo;
	}
	
	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public Usuaria getUsuariaFind() {
		return usuariaFind;
	}

	public void setUsuariaFind(Usuaria usuariaFind) {
		this.usuariaFind = usuariaFind;
	}

	public Denunciado getDenunciadoFind() {
		return denunciadoFind;
	}

	public void setDenunciadoFind(Denunciado denunciadoFind) {
		this.denunciadoFind = denunciadoFind;
	}

	public FichaAtendimento getFichaAtendimentoFind() {
		return fichaAtendimentoFind;
	}

	public void setFichaAtendimentoFind(FichaAtendimento fichaAtendimentoFind) {
		this.fichaAtendimentoFind = fichaAtendimentoFind;
	}

	public void setRelatorioFichaAtendimentos(StreamedContent relatorioFichaAtendimentos) {
		this.relatorioFichaAtendimentos = relatorioFichaAtendimentos;
	}

	public void setRelatorioUsuarias(StreamedContent relatorioUsuarias) {
		this.relatorioUsuarias = relatorioUsuarias;
	}

	public void setRelatorioDenunciados(StreamedContent relatorioDenunciados) {
		this.relatorioDenunciados = relatorioDenunciados;
	}	

	public void setRelatorioEvolucao(StreamedContent relatorioEvolucao) {
		this.relatorioEvolucao = relatorioEvolucao;
	}

	public void setRelatorioAtendimentos(StreamedContent relatorioAtendimentos) {
		this.relatorioAtendimentos = relatorioAtendimentos;
	}
	
}
