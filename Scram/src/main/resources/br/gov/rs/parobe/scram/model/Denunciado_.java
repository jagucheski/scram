package br.gov.rs.parobe.scram.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-01-30T21:17:19.554-0200")
@StaticMetamodel(Denunciado.class)
public class Denunciado_ {
	public static volatile SingularAttribute<Denunciado, Integer> id;
	public static volatile SingularAttribute<Denunciado, String> nome;
	public static volatile SingularAttribute<Denunciado, String> cpf;
	public static volatile SingularAttribute<Denunciado, String> rg;
	public static volatile SingularAttribute<Denunciado, String> email;
	public static volatile SingularAttribute<Denunciado, String> deficiente;
	public static volatile SingularAttribute<Denunciado, String> sexo;
	public static volatile SingularAttribute<Denunciado, String> etnia;
	public static volatile SingularAttribute<Denunciado, String> profissao;
	public static volatile SingularAttribute<Denunciado, String> religiao;
	public static volatile SingularAttribute<Denunciado, String> grauInstrucao;
	public static volatile SingularAttribute<Denunciado, String> programaSocial;
	public static volatile SingularAttribute<Denunciado, String> medicamento;
	public static volatile SingularAttribute<Denunciado, String> doencaCronica;
	public static volatile SingularAttribute<Denunciado, Date> nascimento;
	public static volatile SingularAttribute<Denunciado, String> endereco;
	public static volatile SingularAttribute<Denunciado, String> bairro;
	public static volatile SingularAttribute<Denunciado, Cidade> cidade;
	public static volatile SingularAttribute<Denunciado, String> telefoneResidencial;
	public static volatile SingularAttribute<Denunciado, String> telefoneCelular;
}
