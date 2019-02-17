package br.gov.rs.parobe.scram.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-01-30T21:17:19.557-0200")
@StaticMetamodel(FichaAtendimento.class)
public class FichaAtendimento_ {
	public static volatile SingularAttribute<FichaAtendimento, Integer> id;
	public static volatile SingularAttribute<FichaAtendimento, Date> dataFichaAtendimento;
	public static volatile SingularAttribute<FichaAtendimento, Integer> processo;
	public static volatile SingularAttribute<FichaAtendimento, String> referenciadaServico;
	public static volatile SingularAttribute<FichaAtendimento, String> referenciadaServicoObs;
	public static volatile SingularAttribute<FichaAtendimento, String> documentoEncaminhamento;
	public static volatile SingularAttribute<FichaAtendimento, String> documentoEncaminhamentoObs;
	public static volatile SingularAttribute<FichaAtendimento, String> motivoEncaminhamento;
	public static volatile SingularAttribute<FichaAtendimento, String> tipoViolencia;
	public static volatile SingularAttribute<FichaAtendimento, String> providencia;
}
