package br.gov.rs.parobe.scram.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "cidade")
public class Cidade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Length(max = 200)
	private String nome;

	@Length(max = 2)
	private String uf;

	@OneToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "usuario_cidade", joinColumns = {
			@JoinColumn(name = "cidade_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "usuario_id", referencedColumnName = "id") })
	private List<Usuario> usuarioList;

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

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getToDetail() {
		String detail = "";
		if (getId() != 0 && getId() >= 1) {
			detail = (new StringBuilder(String.valueOf(detail))).append(getNome()).append(" - ").toString();
			detail = (new StringBuilder(String.valueOf(detail))).append(getUf()).toString();
		}
		return detail;
	}

	public List<Usuario> getUsuarioList() {
		return usuarioList;
	}

	public void setUsuarioList(ArrayList<Usuario> usuarioList) {
		this.usuarioList = usuarioList;
	}

}