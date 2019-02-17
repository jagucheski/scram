package br.gov.rs.parobe.scram.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "evolucao")
public class Evolucao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Length(max = 150)
	private String assunto;

	@Temporal(TemporalType.DATE)
	private Date data;

	@Length(max = 1000)
	private String descricao;

	@ManyToOne
	@JoinColumn(name = "ficha_atendimento_id")
	private FichaAtendimento fichaAtendimento;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	public Evolucao() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public FichaAtendimento getFichaAtendimento() {
		return fichaAtendimento;
	}

	public void setFichaAtendimento(FichaAtendimento fichaAtendimento) {
		this.fichaAtendimento = fichaAtendimento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}