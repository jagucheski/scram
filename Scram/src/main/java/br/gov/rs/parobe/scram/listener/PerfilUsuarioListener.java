package br.gov.rs.parobe.scram.listener;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value = "perfilUsuarioListener")
@SessionScoped
public class PerfilUsuarioListener implements Serializable {

	private static final long serialVersionUID = 1L;
	private String perfil;
	private Map<String, String> perfis;

	public PerfilUsuarioListener() {
		perfis = new TreeMap<String, String>();
		perfis.put("Usuário Comum", "ROLE_COMUM");
		perfis.put("Usuário Administrativo", "ROLE_ADMINISTRATIVO");
		perfis.put("Coordenador", "ROLE_COORDENADOR");
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public Map<String, String> getPerfis() {
		return perfis;
	}

	public void setPerfis(Map<String, String> perfis) {
		this.perfis = perfis;
	}	
}
