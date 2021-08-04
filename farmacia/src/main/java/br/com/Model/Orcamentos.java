package br.com.Model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Orcamentos implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String crm;
	private String nomeMedico;
	
	@Temporal(TemporalType.DATE)
	private Date dataInicial;
	
	@Temporal(TemporalType.DATE)
	private Date dataFinal;
	
	private String tipoMedicamento;
	private String farmaco1;
	private Double dosagem1;
	private String farmaco2;
	private Double dosagem2;
	private String farmaco3;
	private Double dosagem3;
	private String farmaco4;
	private Double dosagem4;
	private String farmaco5;
	private Double dosagem5;
	private String farmaco6;
	private Double dosagem6;
	private String farmaco7;
	private Double dosagem7;
	private String farmaco8;
	private Double dosagem8;
	private String farmaco9;
	private Double dosagem9;
	private String farmaco10;
	private Double dosagem10;
	private String farmaco11;
	private Double dosagem11;
	private String farmaco12;
	private Double dosagem12;
	private String farmaco13;
	private Double dosagem13;
	private String farmaco14;
	private Double dosagem14;
	private String farmaco15;
	private Double dosagem15;
	private String farmaco16;
	private Double dosagem16;
	private String farmaco17;
	private Double dosagem17;
	private String farmaco18;
	private Double dosagem18;
	private String farmaco19;
	private Double dosagem19;
	private String farmaco20;
	private Double dosagem20;
	private String farmaco21;
	private Double dosagem21;
	private String farmaco22;
	private Double dosagem22;
	private Double PrecoTotal;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private Clientes clientes;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCrm() {
		return crm;
	}
	public void setCrm(String crm) {
		this.crm = crm;
	}
	public String getNomeMedico() {
		return nomeMedico;
	}
	public void setNomeMedico(String nomeMedico) {
		this.nomeMedico = nomeMedico;
	}
	public Date getDataInicial() {
		return dataInicial;
	}
	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}
	public Date getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	public String getTipoMedicamento() {
		return tipoMedicamento;
	}
	public void setTipoMedicamento(String tipoMedicamento) {
		this.tipoMedicamento = tipoMedicamento;
	}
	public String getFarmaco1() {
		return farmaco1;
	}
	public void setFarmaco1(String farmaco1) {
		this.farmaco1 = farmaco1;
	}
	public Double getDosagem1() {
		return dosagem1;
	}
	public void setDosagem1(Double dosagem1) {
		this.dosagem1 = dosagem1;
	}
	public String getFarmaco2() {
		return farmaco2;
	}
	public void setFarmaco2(String farmaco2) {
		this.farmaco2 = farmaco2;
	}
	public Double getDosagem2() {
		return dosagem2;
	}
	public void setDosagem2(Double dosagem2) {
		this.dosagem2 = dosagem2;
	}
	public String getFarmaco3() {
		return farmaco3;
	}
	public void setFarmaco3(String farmaco3) {
		this.farmaco3 = farmaco3;
	}
	public Double getDosagem3() {
		return dosagem3;
	}
	public void setDosagem3(Double dosagem3) {
		this.dosagem3 = dosagem3;
	}
	public String getFarmaco4() {
		return farmaco4;
	}
	public void setFarmaco4(String farmaco4) {
		this.farmaco4 = farmaco4;
	}
	public Double getDosagem4() {
		return dosagem4;
	}
	public void setDosagem4(Double dosagem4) {
		this.dosagem4 = dosagem4;
	}
	public String getFarmaco5() {
		return farmaco5;
	}
	public void setFarmaco5(String farmaco5) {
		this.farmaco5 = farmaco5;
	}
	public Double getDosagem5() {
		return dosagem5;
	}
	public void setDosagem5(Double dosagem5) {
		this.dosagem5 = dosagem5;
	}
	public String getFarmaco6() {
		return farmaco6;
	}
	public void setFarmaco6(String farmaco6) {
		this.farmaco6 = farmaco6;
	}
	public Double getDosagem6() {
		return dosagem6;
	}
	public void setDosagem6(Double dosagem6) {
		this.dosagem6 = dosagem6;
	}
	public String getFarmaco7() {
		return farmaco7;
	}
	public void setFarmaco7(String farmaco7) {
		this.farmaco7 = farmaco7;
	}
	public Double getDosagem7() {
		return dosagem7;
	}
	public void setDosagem7(Double dosagem7) {
		this.dosagem7 = dosagem7;
	}
	public String getFarmaco8() {
		return farmaco8;
	}
	public void setFarmaco8(String farmaco8) {
		this.farmaco8 = farmaco8;
	}
	public Double getDosagem8() {
		return dosagem8;
	}
	public void setDosagem8(Double dosagem8) {
		this.dosagem8 = dosagem8;
	}
	public String getFarmaco9() {
		return farmaco9;
	}
	public void setFarmaco9(String farmaco9) {
		this.farmaco9 = farmaco9;
	}
	public Double getDosagem9() {
		return dosagem9;
	}
	public void setDosagem9(Double dosagem9) {
		this.dosagem9 = dosagem9;
	}
	public String getFarmaco10() {
		return farmaco10;
	}
	public void setFarmaco10(String farmaco10) {
		this.farmaco10 = farmaco10;
	}
	public Double getDosagem10() {
		return dosagem10;
	}
	public void setDosagem10(Double dosagem10) {
		this.dosagem10 = dosagem10;
	}
	public String getFarmaco11() {
		return farmaco11;
	}
	public void setFarmaco11(String farmaco11) {
		this.farmaco11 = farmaco11;
	}
	public Double getDosagem11() {
		return dosagem11;
	}
	public void setDosagem11(Double dosagem11) {
		this.dosagem11 = dosagem11;
	}
	public String getFarmaco12() {
		return farmaco12;
	}
	public void setFarmaco12(String farmaco12) {
		this.farmaco12 = farmaco12;
	}
	public Double getDosagem12() {
		return dosagem12;
	}
	public void setDosagem12(Double dosagem12) {
		this.dosagem12 = dosagem12;
	}
	public String getFarmaco13() {
		return farmaco13;
	}
	public void setFarmaco13(String farmaco13) {
		this.farmaco13 = farmaco13;
	}
	public Double getDosagem13() {
		return dosagem13;
	}
	public void setDosagem13(Double dosagem13) {
		this.dosagem13 = dosagem13;
	}
	public String getFarmaco14() {
		return farmaco14;
	}
	public void setFarmaco14(String farmaco14) {
		this.farmaco14 = farmaco14;
	}
	public Double getDosagem14() {
		return dosagem14;
	}
	public void setDosagem14(Double dosagem14) {
		this.dosagem14 = dosagem14;
	}
	public String getFarmaco15() {
		return farmaco15;
	}
	public void setFarmaco15(String farmaco15) {
		this.farmaco15 = farmaco15;
	}
	public Double getDosagem15() {
		return dosagem15;
	}
	public void setDosagem15(Double dosagem15) {
		this.dosagem15 = dosagem15;
	}
	public String getFarmaco16() {
		return farmaco16;
	}
	public void setFarmaco16(String farmaco16) {
		this.farmaco16 = farmaco16;
	}
	public Double getDosagem16() {
		return dosagem16;
	}
	public void setDosagem16(Double dosagem16) {
		this.dosagem16 = dosagem16;
	}
	public String getFarmaco17() {
		return farmaco17;
	}
	public void setFarmaco17(String farmaco17) {
		this.farmaco17 = farmaco17;
	}
	public Double getDosagem17() {
		return dosagem17;
	}
	public void setDosagem17(Double dosagem17) {
		this.dosagem17 = dosagem17;
	}
	public String getFarmaco18() {
		return farmaco18;
	}
	public void setFarmaco18(String farmaco18) {
		this.farmaco18 = farmaco18;
	}
	public Double getDosagem18() {
		return dosagem18;
	}
	public void setDosagem18(Double dosagem18) {
		this.dosagem18 = dosagem18;
	}
	public String getFarmaco19() {
		return farmaco19;
	}
	public void setFarmaco19(String farmaco19) {
		this.farmaco19 = farmaco19;
	}
	public Double getDosagem19() {
		return dosagem19;
	}
	public void setDosagem19(Double dosagem19) {
		this.dosagem19 = dosagem19;
	}
	public String getFarmaco20() {
		return farmaco20;
	}
	public void setFarmaco20(String farmaco20) {
		this.farmaco20 = farmaco20;
	}
	public Double getDosagem20() {
		return dosagem20;
	}
	public void setDosagem20(Double dosagem20) {
		this.dosagem20 = dosagem20;
	}
	public String getFarmaco21() {
		return farmaco21;
	}
	public void setFarmaco21(String farmaco21) {
		this.farmaco21 = farmaco21;
	}
	public Double getDosagem21() {
		return dosagem21;
	}
	public void setDosagem21(Double dosagem21) {
		this.dosagem21 = dosagem21;
	}
	public String getFarmaco22() {
		return farmaco22;
	}
	public void setFarmaco22(String farmaco22) {
		this.farmaco22 = farmaco22;
	}
	public Double getDosagem22() {
		return dosagem22;
	}
	public void setDosagem22(Double dosagem22) {
		this.dosagem22 = dosagem22;
	}
	public Double getPrecoTotal() {
		return PrecoTotal;
	}
	public void setPrecoTotal(Double precoTotal) {
		PrecoTotal = precoTotal;
	}
	
	
	
	public Clientes getClientes() {
		return clientes;
	}
	public void setClientes(Clientes clientes) {
		this.clientes = clientes;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Orcamentos other = (Orcamentos) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Orcamentos [id=" + id + ", crm=" + crm + ", nomeMedico=" + nomeMedico + ", dataInicial=" + dataInicial
				+ ", dataFinal=" + dataFinal + ", tipoMedicamento=" + tipoMedicamento + ", farmaco1=" + farmaco1
				+ ", dosagem1=" + dosagem1 + ", farmaco2=" + farmaco2 + ", dosagem2=" + dosagem2 + ", farmaco3="
				+ farmaco3 + ", dosagem3=" + dosagem3 + ", farmaco4=" + farmaco4 + ", dosagem4=" + dosagem4
				+ ", farmaco5=" + farmaco5 + ", dosagem5=" + dosagem5 + ", farmaco6=" + farmaco6 + ", dosagem6="
				+ dosagem6 + ", farmaco7=" + farmaco7 + ", dosagem7=" + dosagem7 + ", farmaco8=" + farmaco8
				+ ", dosagem8=" + dosagem8 + ", farmaco9=" + farmaco9 + ", dosagem9=" + dosagem9 + ", farmaco10="
				+ farmaco10 + ", dosagem10=" + dosagem10 + ", farmaco11=" + farmaco11 + ", dosagem11=" + dosagem11
				+ ", farmaco12=" + farmaco12 + ", dosagem12=" + dosagem12 + ", farmaco13=" + farmaco13 + ", dosagem13="
				+ dosagem13 + ", farmaco14=" + farmaco14 + ", dosagem14=" + dosagem14 + ", farmaco15=" + farmaco15
				+ ", dosagem15=" + dosagem15 + ", farmaco16=" + farmaco16 + ", dosagem16=" + dosagem16 + ", farmaco17="
				+ farmaco17 + ", dosagem17=" + dosagem17 + ", farmaco18=" + farmaco18 + ", dosagem18=" + dosagem18
				+ ", farmaco19=" + farmaco19 + ", dosagem19=" + dosagem19 + ", farmaco20=" + farmaco20 + ", dosagem20="
				+ dosagem20 + ", farmaco21=" + farmaco21 + ", dosagem21=" + dosagem21 + ", farmaco22=" + farmaco22
				+ ", dosagem22=" + dosagem22 + ", PrecoTotal=" + PrecoTotal + "]";
	}
	
	
	
}
