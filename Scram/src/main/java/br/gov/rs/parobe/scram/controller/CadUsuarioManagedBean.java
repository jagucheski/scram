package br.gov.rs.parobe.scram.controller;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import br.gov.rs.parobe.scram.dao.CidadeDao;
import br.gov.rs.parobe.scram.dao.UsuarioDao;
import br.gov.rs.parobe.scram.model.Cidade;
import br.gov.rs.parobe.scram.model.Usuario;
import br.gov.rs.parobe.scram.uteis.PasswordValidate;
import br.gov.rs.parobe.scram.uteis.Uteis;
import br.gov.rs.parobe.scram.uteis.ValidaCPF;

@Named(value = "cadUsuarioController")
@ViewScoped
public class CadUsuarioManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	static final Logger logger = Logger.getLogger("br.gov.rs.parobe.scram.controller.CadUsuarioManagedBean");

	private Usuario usuarioFind;
	private Usuario usuarioCadastro;
	private int usuarioIdSelecionado;
	private int tabIndexCadUsuario;
	private ArrayList<Usuario> usuarioList;
	private String novaSenha;
	private String confirmaNovaSenha;

	
	public CadUsuarioManagedBean() {
		this.tabIndexCadUsuario = 0;
		this.usuarioFind = new Usuario();
		this.usuarioCadastro = new Usuario();
		this.usuarioList = new ArrayList<Usuario>();
		findAllUsuario();
	}

	public void findAllUsuario() {
		try {
			usuarioList = (ArrayList<Usuario>) UsuarioDao.getInstance().findAll();
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
			Uteis.MensagemAtencao("Ops. Ocorreu um erro, tente novamente.");
		}
	}

	public void findUsuario() {
		try {
			if (StringUtils.isEmpty(usuarioFind.getNome()) && StringUtils.isBlank(usuarioFind.getCpf())) {
				findAllUsuario();
			} else if (StringUtils.isEmpty(usuarioFind.getNome())) {
				usuarioList = (ArrayList<Usuario>) UsuarioDao.getInstance().findAllByCpf(usuarioFind);
			} else if (StringUtils.isEmpty(usuarioFind.getCpf())) {
				usuarioList = (ArrayList<Usuario>) UsuarioDao.getInstance().findAllByNome(usuarioFind);
			} else {
				usuarioList = (ArrayList<Usuario>) UsuarioDao.getInstance().findAllByNomeCpf(usuarioFind);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
			Uteis.MensagemAtencao("Ops. Ocorreu um erro, tente novamente.");
		}
	}

	public void salvarUsuario() {
		if (validaCampos()) {
			if (validaCpf()) {
				saveUsuario();
				findUsuario();
			}
		}
		setTabIndexCadUsuario(1);
	}

	public void saveUsuario() {
		try {
			UsuarioDao.getInstance().merge(this.usuarioCadastro);
			Uteis.MensagemInfo("Usuário salvo com sucesso!");
			this.usuarioCadastro = new Usuario();
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
			Uteis.MensagemAtencao("Ops. Ocorreu um erro, tente novamente.");
		}
	}

	public void deletarUsuario() {
		if (validaUsuario()) {
			deleteUsuario();
			findUsuario();
			setTabIndexCadUsuario(1);
		}
	}
	
	public void deleteUsuario() {
		try {
			UsuarioDao.getInstance().remove(usuarioCadastro);
			Uteis.MensagemInfo("Usuário excluido com sucesso!");
			novoUsuario();
		} catch (Exception e) {
			Uteis.MensagemAtencao("Ops, Ocorreu um erro ao excluir o usuário!");			
		}
	}

	public void salvarSenhaUsuario() {
		Usuario usuarioTemp = null;
		try {
			if (validaCamposNovaSenha()) {
				usuarioTemp = UsuarioDao.getInstance().getById(this.usuarioCadastro.getId());
				usuarioTemp.setSenha(this.novaSenha);
				UsuarioDao.getInstance().merge(usuarioTemp);
				Uteis.MensagemInfo("Nova senha cadastrada com sucesso!");
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
			Uteis.MensagemInfo("Ops. Ocorreu um erro ao salvar a nova senha");
		}
	}

	public boolean validaUsuario() {
		boolean usuarioSelecionado = true;
		if (usuarioCadastro.getId() == 0 || usuarioCadastro.getId() < 1) {
			Uteis.MensagemAtencao("Selecione um Usuário para excluir");
			usuarioSelecionado = false;
		}
		return usuarioSelecionado;
	}

	public boolean validaCampos() {
		boolean camposValidos = true;

		if (StringUtils.isEmpty(usuarioCadastro.getNome()) && StringUtils.isBlank(usuarioCadastro.getNome())) {
			Uteis.MensagemAtencao("Campo Nome é obrigatório");
			camposValidos = false;
		}

		if (StringUtils.isEmpty(usuarioCadastro.getCpf()) && StringUtils.isBlank(usuarioCadastro.getCpf())) {
			Uteis.MensagemAtencao("Campo Cpf é obrigatório");
			camposValidos = false;
		} else if (!ValidaCPF.isCPF(usuarioCadastro.getCpf())) {
			Uteis.MensagemAtencao("CPF inválido");
			camposValidos = false;
		}	

		if (usuarioCadastro.getId() == 0 || usuarioCadastro.getId() < 1) {
			boolean camposSenhaPreenchidos = true;
			if (StringUtils.isEmpty(usuarioCadastro.getSenha()) && StringUtils.isBlank(usuarioCadastro.getSenha())) {
				Uteis.MensagemAtencao("Campo Senha é obrigatório");
				camposValidos = false;
				camposSenhaPreenchidos = false;
			}
			if (StringUtils.isEmpty(usuarioCadastro.getComparaSenha()) && StringUtils.isBlank(usuarioCadastro.getComparaSenha())) {
				Uteis.MensagemAtencao("Campo Confirmar Senha é obrigatório");
				camposValidos = false;
				camposSenhaPreenchidos = false;
			}
			if(camposSenhaPreenchidos) {
				if (!senhaValida(usuarioCadastro.getSenha(), usuarioCadastro.getComparaSenha())) {
					Uteis.MensagemAtencao("Senhas diferentes!");
					camposValidos = false;
				}else if(!PasswordValidate.validatePassword(usuarioCadastro.getSenha())) {
					Uteis.MensagemAtencao("A senha deve conter no mínimo 6 (seis) caracteres!");
					camposValidos = false;
				}
			}
			
		} else {
			usuarioCadastro.setSenha(UsuarioDao.getInstance().getById(usuarioCadastro.getId()).getSenha());
		}

		return camposValidos;
	}

	public boolean validaCamposNovaSenha() {
		boolean camposValidos = true;

		if (StringUtils.isEmpty(this.novaSenha) && StringUtils.isBlank(this.novaSenha)) {
			Uteis.MensagemAtencao("Campo Nova Senha é obrigatório");
			camposValidos = false;
		}
		if (StringUtils.isEmpty(this.confirmaNovaSenha) && StringUtils.isBlank(this.confirmaNovaSenha)) {
			Uteis.MensagemAtencao("Campo Confirmar Nova Senha é obrigatório");
			camposValidos = false;
		}
		if (!senhaValida(this.novaSenha, this.confirmaNovaSenha)) {
			Uteis.MensagemAtencao("Senhas diferentes!");
			camposValidos = false;
		}else if(!PasswordValidate.validatePassword(this.novaSenha)) {
			Uteis.MensagemAtencao("A senha deve conter no mínimo 6 (seis) caracteres!");
			camposValidos = false;
		}	
		return camposValidos;
	}

	public boolean validaCpf() {
		boolean confirm = false;
		Usuario usuarioTemp = UsuarioDao.getInstance().findByCpf(this.usuarioCadastro.getCpf());

		if (usuarioTemp == null) {
			return true;
		} else {
			if (usuarioCadastro.getId() == usuarioTemp.getId()) {
				confirm = true;
			} else if (usuarioCadastro.getId() != usuarioTemp.getId()) {
				Uteis.MensagemAtencao("Existe um Usuário cadastrado com o CPF informado");
			}
		}
		return confirm;
	}

	public void novoUsuario() {
		try {
			usuarioCadastro = new Usuario();
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
		}
	}

	public void detalharUsuario() {
		try {
			this.usuarioCadastro = UsuarioDao.getInstance().getById(usuarioIdSelecionado);
			this.usuarioCadastro.setComparaSenha(usuarioCadastro.getSenha());
			setTabIndexCadUsuario(1);
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
			Uteis.MensagemAtencao("Ops, Ocorreu um erro ao selecionar o usuário! ");
		}
	}

	public boolean senhaValida(String senha, String comparaSenha) {
		boolean senhaCorreta = false;
		if (senha.compareTo(comparaSenha) == 0) {
			senhaCorreta = true;
		}
		return senhaCorreta;
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

	public Usuario getUsuarioFind() {
		return usuarioFind;
	}

	public void setUsuarioFind(Usuario usuarioFind) {
		this.usuarioFind = usuarioFind;
	}

	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

	public int getUsuarioIdSelecionado() {
		return usuarioIdSelecionado;
	}

	public void setUsuarioIdSelecionado(int usuarioIdSelecionado) {
		this.usuarioIdSelecionado = usuarioIdSelecionado;
	}

	public int getTabIndexCadUsuario() {
		return tabIndexCadUsuario;
	}

	public void setTabIndexCadUsuario(int tabIndexCadUsuario) {
		this.tabIndexCadUsuario = tabIndexCadUsuario;
	}

	public ArrayList<Usuario> getUsuarioList() {
		return usuarioList;
	}

	public void setUsuarioList(ArrayList<Usuario> usuarioList) {
		this.usuarioList = usuarioList;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getConfirmaNovaSenha() {
		return confirmaNovaSenha;
	}

	public void setConfirmaNovaSenha(String confirmaNovaSenha) {
		this.confirmaNovaSenha = confirmaNovaSenha;
	}

}
