package br.gov.rs.parobe.scram.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-01-30T21:17:19.559-0200")
@StaticMetamodel(Usuaria.class)
public class Usuaria_ {
	public static volatile SingularAttribute<Usuaria, Integer> id;
	public static volatile SingularAttribute<Usuaria, String> nome;
	public static volatile SingularAttribute<Usuaria, String> cpf;
	public static volatile SingularAttribute<Usuaria, String> rg;
	public static volatile SingularAttribute<Usuaria, String> nis;
	public static volatile SingularAttribute<Usuaria, Date> nascimento;
	public static volatile SingularAttribute<Usuaria, String> deficiente;
	public static volatile SingularAttribute<Usuaria, String> sexo;
	public static volatile SingularAttribute<Usuaria, String> etnia;
	public static volatile SingularAttribute<Usuaria, String> profissao;
	public static volatile SingularAttribute<Usuaria, String> religiao;
	public static volatile SingularAttribute<Usuaria, String> endereco;
	public static volatile SingularAttribute<Usuaria, String> bairro;
	public static volatile SingularAttribute<Usuaria, Cidade> cidade;
	public static volatile SingularAttribute<Usuaria, String> grauInstrucao;
	public static volatile SingularAttribute<Usuaria, String> programaSocial;
	public static volatile SingularAttribute<Usuaria, String> medicamento;
	public static volatile SingularAttribute<Usuaria, String> doencaCronica;
	public static volatile SingularAttribute<Usuaria, String> cadUnico;
	public static volatile SingularAttribute<Usuaria, Date> dataCadUnico;
	public static volatile SingularAttribute<Usuaria, ContatoUsuaria> contato;
}
