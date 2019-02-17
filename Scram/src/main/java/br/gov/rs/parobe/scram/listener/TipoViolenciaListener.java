package br.gov.rs.parobe.scram.listener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value = "tipoViolenciaListener")
@SessionScoped
public class TipoViolenciaListener implements Serializable {

	private static final long serialVersionUID = 1L;

	private String[] selectedTiposViolencia;
	private List<String> tiposViolencia;
	

	 public TipoViolenciaListener() {
		 tiposViolencia = new ArrayList<String>();
		 tiposViolencia.add("Física");
		 tiposViolencia.add("Psicológica");
		 tiposViolencia.add("Sexual");
		 tiposViolencia.add("Patrimonial");
		 tiposViolencia.add("Moral");
	 }
	  
	public String[] getSelectedTiposViolencia() {
		return selectedTiposViolencia;
	}

	public void setSelectedTiposViolencia(String[] selectedTiposViolencia) {
		this.selectedTiposViolencia = selectedTiposViolencia;
	}

	public List<String> getTiposViolencia() {
		return tiposViolencia;
	}

	public void setTiposViolencia(List<String> tiposViolencia) {
		this.tiposViolencia = tiposViolencia;
	}

	
}