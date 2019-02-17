package br.gov.rs.parobe.scram.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "usuaria")
public class Usuaria implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Length(max = 150)
	private String nome;

	@Length(max = 14)
	private String cpf;

	@Length(max = 14)
	private String rg;

	@Length(max = 30)
	private String nis;

	@Temporal(TemporalType.DATE)
	private Date nascimento;

	@Length(max = 255)
	private String deficiente;

	@Length(max = 20)
	private String sexo;

	@Length(max = 20)
	private String etnia;

	@Length(max = 200)
	private String profissao;

	@Length(max = 20)
	private String religiao;

	@Length(max = 150)
	private String endereco;

	@Length(max = 150)
	private String bairro;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinTable(name = "usuaria_cidade", joinColumns = {
			@JoinColumn(name = "usuaria_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "cidade_id", referencedColumnName = "id") })
	private Cidade cidade;

	@Length(max = 200)
	@Column(name = "grau_instrucao")
	private String grauInstrucao;

	@Length(max = 200)
	@Column(name = "programa_social")
	private String programaSocial;

	@Length(max = 200)
	private String medicamento;

	@Length(max = 200)
	@Column(name = "doenca_cronica")
	private String doencaCronica;

	@Length(max = 100)
	@Column(name = "cad_unico")
	private String cadUnico;

	@Column(name = "data_cad_unico")
	@Temporal(TemporalType.DATE)
	private Date dataCadUnico;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "contato_usuaria_id")
	private ContatoUsuaria contato;

	public Usuaria() {
		super();
		this.cidade = new Cidade();
		this.contato = new ContatoUsuaria();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getNis() {
		return nis;
	}

	public void setNis(String nis) {
		this.nis = nis;
	}

	public Date getNascimento() {
		return nascimento;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	public String getDeficiente() {
		return deficiente;
	}

	public void setDeficiente(String deficiente) {
		this.deficiente = deficiente;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getEtnia() {
		return etnia;
	}

	public void setEtnia(String etnia) {
		this.etnia = etnia;
	}

	public String getProfissao() {
		return profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	public String getReligiao() {
		return religiao;
	}

	public void setReligiao(String religiao) {
		this.religiao = religiao;
	}

	public String getGrauInstrucao() {
		return grauInstrucao;
	}

	public void setGrauInstrucao(String grauInstrucao) {
		this.grauInstrucao = grauInstrucao;
	}

	public String getProgramaSocial() {
		return programaSocial;
	}

	public void setProgramaSocial(String programaSocial) {
		this.programaSocial = programaSocial;
	}

	public String getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(String medicamento) {
		this.medicamento = medicamento;
	}

	public String getDoencaCronica() {
		return doencaCronica;
	}

	public void setDoencaCronica(String doencaCronica) {
		this.doencaCronica = doencaCronica;
	}

	public String getCadUnico() {
		return cadUnico;
	}

	public void setCadUnico(String cadUnico) {
		this.cadUnico = cadUnico;
	}

	public Date getDataCadUnico() {
		return dataCadUnico;
	}

	public void setDataCadUnico(Date dataCadUnico) {
		this.dataCadUnico = dataCadUnico;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public ContatoUsuaria getContato() {
		return contato;
	}

	public void setContato(ContatoUsuaria contato) {
		this.contato = contato;
	}

	public String getToDetail() {
		String detail = "";
		if (getId() != 0 && getId() >= 1) {
			detail = (new StringBuilder(String.valueOf(detail))).append(getNome()).append(" - ").toString();
			detail = (new StringBuilder(String.valueOf(detail))).append(" CPF: ").append(getCpf()).toString();
		}
		return detail;
	}

}