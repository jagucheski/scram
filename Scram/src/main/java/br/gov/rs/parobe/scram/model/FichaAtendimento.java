package br.gov.rs.parobe.scram.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "ficha_atendimento")
public class FichaAtendimento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_ficha_atendimento")
	private Date dataFichaAtendimento;

	@Length(max = 50)
	private String processo;

	@Length(max = 150)
	@Column(name = "referenciada_servico")
	private String referenciadaServico;

	@Length(max = 200)
	@Column(name = "referenciada_servico_obs")
	private String referenciadaServicoObs;

	@Length(max = 150)
	@Column(name = "documento_encaminhamento")
	private String documentoEncaminhamento;

	@Length(max = 200)
	@Column(name = "documento_encaminhamento_obs")
	private String documentoEncaminhamentoObs;

	@Length(max = 200)
	@Column(name = "motivo_encaminhamento")
	private String motivoEncaminhamento;

	@Length(max = 50)
	@Column(name = "tipo_violencia")
	private String tipoViolencia;

	@Length(max = 200)
	@Column(name = "providencia")
	private String providencia;

	@OneToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "usuaria_id")
	private Usuaria usuaria;	
	
	@OneToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "denunciado_id")
	private Denunciado denunciado;

	@Length(max = 150)
	@Column(name = "grau_parentesco_denunciado")
	private String grauParentescoDenunciado;
	
	@OneToMany(mappedBy = "fichaAtendimento", targetEntity = Evolucao.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Evolucao> evolucao;
	
	@Transient
	private String[] tiposViolencia;

	public FichaAtendimento() {
		super();
		this.usuaria = new Usuaria();
		this.denunciado = new Denunciado();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDataFichaAtendimento() {
		return dataFichaAtendimento;
	}

	public void setDataFichaAtendimento(Date dataFichaAtendimento) {
		this.dataFichaAtendimento = dataFichaAtendimento;
	}

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

	public String getReferenciadaServico() {
		return referenciadaServico;
	}

	public void setReferenciadaServico(String referenciadaServico) {
		this.referenciadaServico = referenciadaServico;
	}

	public String getReferenciadaServicoObs() {
		return referenciadaServicoObs;
	}

	public void setReferenciadaServicoObs(String referenciadaServicoObs) {
		this.referenciadaServicoObs = referenciadaServicoObs;
	}

	public String getDocumentoEncaminhamento() {
		return documentoEncaminhamento;
	}

	public void setDocumentoEncaminhamento(String documentoEncaminhamento) {
		this.documentoEncaminhamento = documentoEncaminhamento;
	}

	public String getDocumentoEncaminhamentoObs() {
		return documentoEncaminhamentoObs;
	}

	public void setDocumentoEncaminhamentoObs(String documentoEncaminhamentoObs) {
		this.documentoEncaminhamentoObs = documentoEncaminhamentoObs;
	}

	public String getMotivoEncaminhamento() {
		return motivoEncaminhamento;
	}

	public void setMotivoEncaminhamento(String motivoEncaminhamento) {
		this.motivoEncaminhamento = motivoEncaminhamento;
	}

	public String getTipoViolencia() {
		return tipoViolencia;
	}

	public void setTipoViolencia(String tipoViolencia) {
		this.tipoViolencia = tipoViolencia;
	}

	public String getProvidencia() {
		return providencia;
	}

	public void setProvidencia(String providencia) {
		this.providencia = providencia;
	}	

	public Usuaria getUsuaria() {
		return usuaria;
	}

	public void setUsuaria(Usuaria usuaria) {
		this.usuaria = usuaria;
	}

	public Denunciado getDenunciado() {
		return denunciado;
	}
	
	public String getGrauParentescoDenunciado() {
		return grauParentescoDenunciado;
	}

	public void setGrauParentescoDenunciado(String grauParentescoDenunciado) {
		this.grauParentescoDenunciado = grauParentescoDenunciado;
	}

	public void setDenunciado(Denunciado denunciado) {
		this.denunciado = denunciado;
	}
	
	public List<Evolucao> getEvolucao() {
		return evolucao;
	}

	public void setEvolucao(List<Evolucao> evolucao) {
		this.evolucao = evolucao;
	}

	public String[] getTiposViolencia() {
		return tiposViolencia;
	}

	public void setTiposViolencia(String[] tiposViolencia) {
		this.tiposViolencia = tiposViolencia;
	}

	/**
	 * Método que prepara dados da view para salvar no banco de dados
	 */
	public void carregaTipoViolencia() {
		this.tipoViolencia = "-";
		for (String tipoViolenciaTemp : this.tiposViolencia) {
			this.tipoViolencia += tipoViolenciaTemp + "-";
		}
	}

	/**
	 * Método que prepara dados do banco de dadps para a view
	 */
	public void carregaTiposViolencia() {
		String[] tiposViolenciaTemp =  new String[5];
		int i = 0;
		StringTokenizer st = new StringTokenizer(this.tipoViolencia, "-");
		while (st.hasMoreTokens()) {
			tiposViolenciaTemp[i] = st.nextToken();
			i++;
		}
		this.tiposViolencia = new String[tiposViolenciaTemp.length];
		this.setTiposViolencia(tiposViolenciaTemp);
		
	}
	
	public String getTipoViolenciaDetail() {
		return this.tipoViolencia!=null ? this.tipoViolencia.substring(1, this.tipoViolencia.length()-1):"";				
	}

}