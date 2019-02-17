package br.gov.rs.parobe.scram.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "usuario", uniqueConstraints = { @UniqueConstraint(columnNames = "cpf") })
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Length(max = 150)
	private String nome;

	@Length(max = 14)
	private String cpf;

	@Length(max = 150)
	private String email;

	@Length(max = 15)
	private String tel_residencial;

	@Length(max = 15)
	private String tel_celular;

	@Length(max = 150)
	private String endereco;

	private boolean status;

	@Length(max = 150)
	private String perfil;

	@Length(max = 150)
	private String senha;

	@Transient
	private String comparaSenha;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinTable(name = "usuario_cidade", joinColumns = {
			@JoinColumn(name = "usuario_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "cidade_id", referencedColumnName = "id") })
	private Cidade cidade;
	
	@OneToMany(mappedBy = "usuario", targetEntity = Evolucao.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Evolucao> evolucao;

	public Usuario() {
		super();
		this.cidade = new Cidade();
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel_residencial() {
		return tel_residencial;
	}

	public void setTel_residencial(String tel_residencial) {
		this.tel_residencial = tel_residencial;
	}

	public String getTel_celular() {
		return tel_celular;
	}

	public void setTel_celular(String tel_celular) {
		this.tel_celular = tel_celular;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getComparaSenha() {
		return comparaSenha;
	}

	public void setComparaSenha(String comparaSenha) {
		this.comparaSenha = comparaSenha;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	public List<Evolucao> getEvolucao() {
		return evolucao;
	}

	public void setEvolucao(List<Evolucao> evolucao) {
		this.evolucao = evolucao;
	}

	public String getPerfilDetail() {
		String perfilUsuario = "";
		switch (this.perfil) {
		case "ROLE_COMUM":
			perfilUsuario = "Usuário Comum";
			break;
		case "ROLE_ADMINISTRATIVO":
			perfilUsuario = "Usuário Administrativo";
			break;
		case "ROLE_COORDENADOR":
			perfilUsuario = "Coordenador";
			break;
		}

		return perfilUsuario;
	}
}