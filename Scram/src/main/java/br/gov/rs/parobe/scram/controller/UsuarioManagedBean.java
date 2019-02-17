package br.gov.rs.parobe.scram.controller;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import br.gov.rs.parobe.scram.dao.AcessoUsuarioDao;
import br.gov.rs.parobe.scram.dao.UsuarioDao;
import br.gov.rs.parobe.scram.model.AcessoUsuario;
import br.gov.rs.parobe.scram.model.Usuario;
import br.gov.rs.parobe.scram.uteis.Uteis;

@Named(value="usuarioController")
@SessionScoped
public class UsuarioManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Usuario usuarioLogin;
	private Usuario usuarioLogado;
	
	public UsuarioManagedBean() {
		this.usuarioLogin = new Usuario();
		this.usuarioLogado = new Usuario();
	}

	public Usuario GetUsuarioSession() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return (Usuario) facesContext.getExternalContext().getSessionMap().get("usuarioAutenticado");
	}

	public String Logout() {
		
		AcessoUsuario acessoUsuarioTemp = new AcessoUsuario();
		acessoUsuarioTemp.setUsuario((Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioAutenticado"));
		acessoUsuarioTemp.setTipo("Logout");
		acessoUsuarioTemp.setData(new Date());				
		AcessoUsuarioDao.getInstance().persist(acessoUsuarioTemp);
		
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/index.xhtml?faces-redirect=true";
	}

	public String EfetuarLogin() {
		
		if(StringUtils.isEmpty(usuarioLogin.getCpf()) || StringUtils.isBlank(usuarioLogin.getCpf())){
			Uteis.Mensagem("Favor informar CPF!");
			return null;
		} else if (StringUtils.isEmpty(usuarioLogin.getSenha()) || StringUtils.isBlank(usuarioLogin.getSenha())) {
			Uteis.Mensagem("Favor informar senha!");
			return null;
		} else {
			Usuario usuarioTemp = UsuarioDao.getInstance().findByCpfSenha(usuarioLogin);
			
			if (usuarioTemp == null) {
				Uteis.MensagemAtencao("Não foi possível efetuar login. Verifique suas credênciais!");
				return null;
			}else if(!usuarioTemp.isStatus()){
				Uteis.MensagemAtencao("Não foi possível efetuar login. Usuário bloqueado!");
				return null;
			}else {
				this.usuarioLogado  = new Usuario();
				usuarioLogado.setSenha(null);
				usuarioLogado.setId(usuarioTemp.getId());
				usuarioLogado.setNome(usuarioTemp.getNome());
				usuarioLogado.setCpf(usuarioTemp.getCpf());
				usuarioLogado.setPerfil(usuarioTemp.getPerfil());
				FacesContext facesContext = FacesContext.getCurrentInstance();
				facesContext.getExternalContext().getSessionMap().put("usuarioAutenticado", usuarioLogado);
				
				AcessoUsuario acessoUsuarioTemp = new AcessoUsuario();
				acessoUsuarioTemp.setUsuario(usuarioTemp);
				acessoUsuarioTemp.setTipo("Login");
				acessoUsuarioTemp.setData(new Date());				
				AcessoUsuarioDao.getInstance().persist(acessoUsuarioTemp);
				
				return "sistema/home?faces-redirect=true";
			}
		}
	}

	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}	
	
}
