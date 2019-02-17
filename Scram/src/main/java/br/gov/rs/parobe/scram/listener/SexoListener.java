package br.gov.rs.parobe.scram.listener;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value = "sexoListener")
@SessionScoped
public class SexoListener implements Serializable {

	private static final long serialVersionUID = 1L;
	private String sexo;
	private Map<String, String> sexos;

	public SexoListener() {
		sexos = new TreeMap<String, String>();
		sexos.put("Heterosexual", "Heterosexual");		
		sexos.put("Homosexual", "Homosexual");		
		sexos.put("Bisexual", "Bisexual");		
		sexos.put("Transgênero", "Transgênero");		
		sexos.put("Transsexual", "Transsexual");		
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Map<String, String> getSexos() {
		return sexos;
	}

	public void setSexos(Map<String, String> sexos) {
		this.sexos = sexos;
	}

}
