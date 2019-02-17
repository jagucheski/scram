package br.gov.rs.parobe.scram.uteis;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.gov.rs.parobe.scram.model.Usuario;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class ReportUtil implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String RELATORIOUSUARIAS = "WEB-INF\\classes\\report\\RelatorioUsuarias.jasper";
	private final String RELATORIODENUNCIADOS = "WEB-INF\\classes\\report\\RelatorioDenunciados.jasper";
	private final String RELATORIOFICHAATENDIMENTO = "WEB-INF\\classes\\report\\RelatorioFichaAtendimento.jasper";
	private final String RELATORIOEVOLUCAO = "WEB-INF\\classes\\report\\RelatorioEvolucao.jasper";
	private final String RELATORIOATENDIMENTOS = "WEB-INF\\classes\\report\\RelatorioAtendimentos.jasper";
	private ServletContext servletContext;
	
	public ReportUtil() {		
		this.servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	}

	public StreamedContent geraRelatorioUsuarias(String condicaoNomeCidade) throws Exception {
		StreamedContent arquivoRetorno = null;
		Usuario usuarioLogado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioAutenticado");
		
		try{
			String sql = (new StringBuilder(
					"	SELECT usuaria.id as codigo,  " + 
					"       usuaria.nome, " + 
					"       usuaria.cpf, " + 
					"		usuaria.rg, " + 
					"		TO_CHAR (usuaria.nascimento, 'DD/MM/YYYY') as nascimento, " + 
					"		contato_usuaria.tel_residencial, " + 
					"		contato_usuaria.tel_celular, " + 
					"		usuaria.bairro, " + 
					"		cidade.nome as cidade,  " + 
					"		cidade.uf " + 
					" FROM usuaria " + 
					" 	left join usuaria_cidade ON ( usuaria.id = usuaria_cidade.usuaria_id) " + 
					"	left join cidade ON ( cidade.id = usuaria_cidade.cidade_id) " + 
					"	left join contato_usuaria ON (usuaria.contato_usuaria_id = contato_usuaria.id) " 
					+  condicaoNomeCidade +
					" order by cidade.nome asc, usuaria.nome asc "	
					)).toString();			
			
			Map<String, Object> parameters = new HashMap<String, Object>(); 
			parameters.put("REPORT_CONNECTION", this.getConexao());
			parameters.put("CIDADE", condicaoNomeCidade);
			parameters.put("USUARIO", usuarioLogado.getNome());
			parameters.put("CONTEXT",this.servletContext.getRealPath("/"));
	        
			PreparedStatement stm = this.getConexao().prepareStatement(sql);
			JRResultSetDataSource jrRS = new JRResultSetDataSource(stm.executeQuery());
			
			JasperPrint jp = JasperFillManager.fillReport(this.servletContext.getRealPath("/").toString()+RELATORIOUSUARIAS, parameters, jrRS);
			ByteArrayInputStream bais = new ByteArrayInputStream(JasperExportManager.exportReportToPdf(jp));
			arquivoRetorno = new DefaultStreamedContent(bais, "pdf", "relatorio_usuarias.pdf");
			
		} catch (JRException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (FileNotFoundException e) {
			throw new Exception("Arquivo do relatório não encontrado.", e);
		}
		return arquivoRetorno;
	}
	
	public StreamedContent geraRelatorioDenunciados(String condicaoNomeCidade) throws Exception {
		StreamedContent arquivoRetorno = null;
		Usuario usuarioLogado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioAutenticado");
		
		try{
			String sql = (new StringBuilder(
					"	SELECT denunciado.id as codigo,  " + 
							"       denunciado.nome, " + 
							"       denunciado.cpf, " + 
							"		denunciado.rg, " + 
							"		TO_CHAR (denunciado.nascimento, 'DD/MM/YYYY') as nascimento, " + 
							"		denunciado.tel_residencial, " + 
							"		denunciado.tel_celular, " + 
							"		denunciado.bairro, " + 
							"		cidade.nome as cidade,  " + 
							"		cidade.uf " + 
							" FROM denunciado " + 
							" 	left join denunciado_cidade ON ( denunciado.id = denunciado_cidade.denunciado_id) " + 
							"	left join cidade ON ( cidade.id = denunciado_cidade.cidade_id) " 
							+  condicaoNomeCidade +
							" order by cidade.nome asc, denunciado.nome asc "	
					)).toString();			
			
			Map<String, Object> parameters = new HashMap<String, Object>(); 
			parameters.put("REPORT_CONNECTION", this.getConexao());
			parameters.put("CIDADE", condicaoNomeCidade);
			parameters.put("USUARIO", usuarioLogado.getNome());
			parameters.put("CONTEXT",servletContext.getRealPath("/"));
			
			PreparedStatement stm = this.getConexao().prepareStatement(sql);
			JRResultSetDataSource jrRS = new JRResultSetDataSource(stm.executeQuery());
			
			JasperPrint jp = JasperFillManager.fillReport(this.servletContext.getRealPath("/").toString()+RELATORIODENUNCIADOS, parameters, jrRS);
			ByteArrayInputStream bais = new ByteArrayInputStream(JasperExportManager.exportReportToPdf(jp));
			arquivoRetorno = new DefaultStreamedContent(bais, "pdf", "relatorio_denunciados.pdf");
			
		} catch (JRException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (FileNotFoundException e) {
			throw new Exception("Arquivo do relatório não encontrado.", e);
		}
		return arquivoRetorno;
	}

	public StreamedContent geraRelatorioFichaAtendimento(String condicaoTipoViolencia, String condicaoPeriodo, String tipoViolencia, String inicioPeriodo, String fimPeriodo) throws Exception {
		StreamedContent arquivoRetorno = null;
		Usuario usuarioLogado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioAutenticado");
	    
	   try{
			String sql = (new StringBuilder(
					  " select 	ficha_atendimento.id as codigo,  " + 
					  "			TO_CHAR (ficha_atendimento.data_ficha_atendimento, 'DD/MM/YYYY') as data_ficha_atendimento, " + 
					  "			usuaria.nome as usuaria, " + 
					  "			denunciado.nome as denunciado,	" + 
					  "			ficha_atendimento.tipo_violencia as tipo_violencia, " + 
					  "			ficha_atendimento.grau_parentesco_denunciado as grau_parentesco_denunciado " + 
					  "		 from ficha_atendimento " + 
					  "			inner join denunciado ON ( denunciado.id = ficha_atendimento.denunciado_id) " + 
					  "			inner join usuaria ON ( usuaria.id = ficha_atendimento.usuaria_id) "
					  + condicaoTipoViolencia 
					  + condicaoPeriodo
					  + " order by ficha_atendimento.id, ficha_atendimento.grau_parentesco_denunciado, ficha_atendimento.tipo_violencia "))
									.toString();
			
			Map<String, Object> parameters = new HashMap<String, Object>(); 
			parameters.put("REPORT_CONNECTION", this.getConexao());
			parameters.put("USUARIO", usuarioLogado.getNome());
			parameters.put("DATA_INICIO", inicioPeriodo);
			parameters.put("DATA_FIM", fimPeriodo);
			parameters.put("VIOLENCIA", tipoViolencia);
			 parameters.put("CONTEXT",servletContext.getRealPath("/"));
	        
			PreparedStatement stm = this.getConexao().prepareStatement(sql);
			JRResultSetDataSource jrRS = new JRResultSetDataSource(stm.executeQuery());
			
			JasperPrint jp = JasperFillManager.fillReport(this.servletContext.getRealPath("/").toString()+RELATORIOFICHAATENDIMENTO, parameters, jrRS);
			ByteArrayInputStream bais = new ByteArrayInputStream(JasperExportManager.exportReportToPdf(jp));
			arquivoRetorno = new DefaultStreamedContent(bais, "pdf", "relatorio_ficha_atendimento.pdf");
			
		} catch (JRException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (FileNotFoundException e) {
			throw new Exception("Arquivo do relatório não encontrado.", e);
		}
		return arquivoRetorno;
	}
	
	public StreamedContent geraRelatorioEvolucao(String condicaoPeriodo, String inicioPeriodo, String fimPeriodo) throws Exception {
		StreamedContent arquivoRetorno = null;
		Usuario usuarioLogado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioAutenticado");
		
		try{
			String sql = (new StringBuilder(
					"	select evolucao.id as codigo," + 
					"		       TO_CHAR (evolucao.data, 'DD/MM/YYYY') as data_atendimento, " + 
					"		       evolucao.assunto as assunto, " + 
					"		       usuario.nome as responsavel, " + 
					"		       ficha_atendimento_usuaria.usuaria as usuaria, " + 
					"		       ficha_atendimento_usuaria.cpf as cpf, " + 
					"		       ficha_atendimento_usuaria.bairro as bairro, " + 
					"		       ficha_atendimento_usuaria.cidade as cidade, " + 
					"              ficha_atendimento_usuaria.uf as uf " + 
					"		from evolucao " + 
					"			inner join usuario ON (usuario.id = evolucao.usuario_id) " + 
					"			inner join ( " + 
					"				select ficha_atendimento.id as ficha_atendimento_id,  " + 
					"					usuaria.nome as usuaria, " + 
					"					usuaria.cpf as cpf, " + 
					"					usuaria.bairro as bairro, " + 
					"					cidade.nome as cidade, " + 
					"					cidade.uf as uf " + 
					"				from ficha_atendimento " + 
					"					inner join usuaria ON ( usuaria.id = ficha_atendimento.usuaria_id) " + 
					"					inner join usuaria_cidade ON ( usuaria_cidade.usuaria_id = usuaria.id) " + 
					"					inner join cidade ON (cidade.id = usuaria_cidade.cidade_id)) " + 
					"			 as ficha_atendimento_usuaria ON (ficha_atendimento_usuaria.ficha_atendimento_id = evolucao.ficha_atendimento_id) " 
					
					
							+ condicaoPeriodo
							+ " order by ficha_atendimento_usuaria.usuaria asc, evolucao.data asc "))
					.toString();
	
			Map<String, Object> parameters = new HashMap<String, Object>(); 
			parameters.put("REPORT_CONNECTION", this.getConexao());
			parameters.put("USUARIO", usuarioLogado.getNome());
			parameters.put("DATA_INICIO", inicioPeriodo);
			parameters.put("DATA_FIM", fimPeriodo);
			parameters.put("CONTEXT",servletContext.getRealPath("/"));
			
			PreparedStatement stm = this.getConexao().prepareStatement(sql);
			JRResultSetDataSource jrRS = new JRResultSetDataSource(stm.executeQuery());
			
			JasperPrint jp = JasperFillManager.fillReport(this.servletContext.getRealPath("/").toString()+RELATORIOEVOLUCAO, parameters, jrRS);
			ByteArrayInputStream bais = new ByteArrayInputStream(JasperExportManager.exportReportToPdf(jp));
			arquivoRetorno = new DefaultStreamedContent(bais, "pdf", "relatorio_evolucao.pdf");
			
		} catch (JRException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (FileNotFoundException e) {
			throw new Exception("Arquivo do relatório não encontrado.", e);
		}
		return arquivoRetorno;
	}
	
	public StreamedContent geraRelatorioAtendimentos(String condicaoPeriodo, String anoPeriodo, String totalAtendimentos) throws Exception {
		StreamedContent arquivoRetorno = null;
		Usuario usuarioLogado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioAutenticado");
		
		try{
			String sql = (new StringBuilder(
							"	SELECT	" + 
							"	to_char(data, 'MM/YYYY') as mes_ano,	" + 
							"	COUNT(data) AS atendimento	" + 
							"	FROM evolucao	" + 
							condicaoPeriodo +
							"	GROUP BY mes_ano	" + 
							"	ORDER BY mes_ano ")).toString();
			
			Map<String, Object> parameters = new HashMap<String, Object>(); 
			parameters.put("REPORT_CONNECTION", this.getConexao());
			parameters.put("USUARIO", usuarioLogado.getNome());
			parameters.put("ANO", anoPeriodo);
			parameters.put("TOTALATENDIMENTOS", totalAtendimentos);
			parameters.put("CONTEXT",servletContext.getRealPath("/"));
			
			PreparedStatement stm = this.getConexao().prepareStatement(sql);
			JRResultSetDataSource jrRS = new JRResultSetDataSource(stm.executeQuery());
			
			JasperPrint jp = JasperFillManager.fillReport(this.servletContext.getRealPath("/").toString()+RELATORIOATENDIMENTOS, parameters, jrRS);
			ByteArrayInputStream bais = new ByteArrayInputStream(JasperExportManager.exportReportToPdf(jp));
			arquivoRetorno = new DefaultStreamedContent(bais, "pdf", "relatorio_atendimentos.pdf");
			
		} catch (JRException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (FileNotFoundException e) {
			throw new Exception("Arquivo do relatório não encontrado.", e);
		}
		return arquivoRetorno;
	}

	private Connection getConexao() throws Exception {
		Connection conexao = null;
		String driver = "org.postgresql.Driver";
		String url = "jdbc:postgresql://localhost:5432/scram";
		String login = "postgres";
		String senha = "p0stgr3s!";
		try {
			Class.forName(driver);
			conexao = DriverManager.getConnection(url, login, senha);
		} catch (Exception e) {
			throw new Exception("Ocorreu um erro de SQL.", e);
		}
		return conexao;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}	
	
}
