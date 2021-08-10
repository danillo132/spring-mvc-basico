package br.com.Model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Pedidos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String medico;
	@Temporal(TemporalType.DATE)
	private Date dataPedido;

	@Temporal(TemporalType.DATE)
	private Date dataEntrega;
	private String crm;
	private String medicamentoTipo;
	private String materiaprima1;
	private Double quantidade1;
	private String materiaprima2;
	private Double quantidade2;
	private String materiaprima3;
	private Double quantidade3;
	private String materiaprima4;
	private Double quantidade4;
	private String materiaprima5;
	private Double quantidade5;
	private String materiaprima6;
	private Double quantidade6;
	private String materiaprima7;
	private Double quantidade7;
	private String materiaprima8;
	private Double quantidade8;
	private String materiaprima9;
	private Double quantidade9;
	private String materiaprima10;
	private Double quantidade10;
	private String materiaprima11;
	private Double quantidade11;
	private String materiaprima12;
	private Double quantidade12;
	private String materiaprima13;
	private Double quantidade13;
	private String materiaprima14;
	private Double quantidade14;
	private String materiaprima15;
	private Double quantidade15;
	private String materiaprima16;
	private Double quantidade16;
	private String materiaprima17;
	private Double quantidade17;
	private String materiaprima18;
	private Double quantidade18;
	private String materiaprima19;
	private Double quantidade19;
	private String materiaprima20;
	private Double quantidade20;
	private String materiaprima21;
	private Double quantidade21;
	private String materiaprima22;
	private Double quantidade22;
	private Double Preco;
	private String status;
	private String clienteNome;
	private String clienteSobrenome;
	private String cpfCliente;
	private Integer statusEntregueTotal;
	
	
	
	
	
	
	
	
	public String getCrm() {
		return crm;
	}

	public void setCrm(String crm) {
		this.crm = crm;
	}

	public String getClienteNome() {
		return clienteNome;
	}

	public void setClienteNome(String clienteNome) {
		this.clienteNome = clienteNome;
	}

	public String getClienteSobrenome() {
		return clienteSobrenome;
	}

	public void setClienteSobrenome(String clienteSobrenome) {
		this.clienteSobrenome = clienteSobrenome;
	}

	public String getCpfCliente() {
		return cpfCliente;
	}

	public void setCpfCliente(String cpfCliente) {
		this.cpfCliente = cpfCliente;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	
	@ManyToOne
	private Clientes clientes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMedico() {
		return medico;
	}

	public void setMedico(String medico) {
		this.medico = medico;
	}

	public Date getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(Date dataPedido) {
		this.dataPedido = dataPedido;
	}

	public Date getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public String getMedicamentoTipo() {
		return medicamentoTipo;
	}

	public void setMedicamentoTipo(String medicamentoTipo) {
		this.medicamentoTipo = medicamentoTipo;
	}

	public String getMateriaprima1() {
		return materiaprima1;
	}

	public void setMateriaprima1(String materiaprima1) {
		this.materiaprima1 = materiaprima1;
	}

	public Double getQuantidade1() {
		return quantidade1;
	}

	public void setQuantidade1(Double quantidade1) {
		this.quantidade1 = quantidade1;
	}

	public String getMateriaprima2() {
		return materiaprima2;
	}

	public void setMateriaprima2(String materiaprima2) {
		this.materiaprima2 = materiaprima2;
	}

	public Double getQuantidade2() {
		return quantidade2;
	}

	public void setQuantidade2(Double quantidade2) {
		this.quantidade2 = quantidade2;
	}

	public String getMateriaprima3() {
		return materiaprima3;
	}

	public void setMateriaprima3(String materiaprima3) {
		this.materiaprima3 = materiaprima3;
	}

	public Double getQuantidade3() {
		return quantidade3;
	}

	public void setQuantidade3(Double quantidade3) {
		this.quantidade3 = quantidade3;
	}

	public String getMateriaprima4() {
		return materiaprima4;
	}

	public void setMateriaprima4(String materiaprima4) {
		this.materiaprima4 = materiaprima4;
	}

	public Double getQuantidade4() {
		return quantidade4;
	}

	public void setQuantidade4(Double quantidade4) {
		this.quantidade4 = quantidade4;
	}

	public String getMateriaprima5() {
		return materiaprima5;
	}

	public void setMateriaprima5(String materiaprima5) {
		this.materiaprima5 = materiaprima5;
	}

	public Double getQuantidade5() {
		return quantidade5;
	}

	public void setQuantidade5(Double quantidade5) {
		this.quantidade5 = quantidade5;
	}

	public String getMateriaprima6() {
		return materiaprima6;
	}

	public void setMateriaprima6(String materiaprima6) {
		this.materiaprima6 = materiaprima6;
	}

	public Double getQuantidade6() {
		return quantidade6;
	}

	public void setQuantidade6(Double quantidade6) {
		this.quantidade6 = quantidade6;
	}

	public String getMateriaprima7() {
		return materiaprima7;
	}

	public void setMateriaprima7(String materiaprima7) {
		this.materiaprima7 = materiaprima7;
	}

	public Double getQuantidade7() {
		return quantidade7;
	}

	public void setQuantidade7(Double quantidade7) {
		this.quantidade7 = quantidade7;
	}

	public String getMateriaprima8() {
		return materiaprima8;
	}

	public void setMateriaprima8(String materiaprima8) {
		this.materiaprima8 = materiaprima8;
	}

	public Double getQuantidade8() {
		return quantidade8;
	}

	public void setQuantidade8(Double quantidade8) {
		this.quantidade8 = quantidade8;
	}

	public String getMateriaprima9() {
		return materiaprima9;
	}

	public void setMateriaprima9(String materiaprima9) {
		this.materiaprima9 = materiaprima9;
	}

	public Double getQuantidade9() {
		return quantidade9;
	}

	public void setQuantidade9(Double quantidade9) {
		this.quantidade9 = quantidade9;
	}

	public String getMateriaprima10() {
		return materiaprima10;
	}

	public void setMateriaprima10(String materiaprima10) {
		this.materiaprima10 = materiaprima10;
	}

	public Double getQuantidade10() {
		return quantidade10;
	}

	public void setQuantidade10(Double quantidade10) {
		this.quantidade10 = quantidade10;
	}
	
	
	

	public Integer getStatusEntregueTotal() {
		return statusEntregueTotal;
	}

	public void setStatusEntregueTotal(Integer statusEntregueTotal) {
		this.statusEntregueTotal = statusEntregueTotal;
	}

	public String getMateriaprima11() {
		return materiaprima11;
	}

	public void setMateriaprima11(String materiaprima11) {
		this.materiaprima11 = materiaprima11;
	}

	public Double getQuantidade11() {
		return quantidade11;
	}

	public void setQuantidade11(Double quantidade11) {
		this.quantidade11 = quantidade11;
	}

	public String getMateriaprima12() {
		return materiaprima12;
	}

	public void setMateriaprima12(String materiaprima12) {
		this.materiaprima12 = materiaprima12;
	}

	public Double getQuantidade12() {
		return quantidade12;
	}

	public void setQuantidade12(Double quantidade12) {
		this.quantidade12 = quantidade12;
	}

	public String getMateriaprima13() {
		return materiaprima13;
	}

	public void setMateriaprima13(String materiaprima13) {
		this.materiaprima13 = materiaprima13;
	}

	public Double getQuantidade13() {
		return quantidade13;
	}

	public void setQuantidade13(Double quantidade13) {
		this.quantidade13 = quantidade13;
	}

	public String getMateriaprima14() {
		return materiaprima14;
	}

	public void setMateriaprima14(String materiaprima14) {
		this.materiaprima14 = materiaprima14;
	}

	public Double getQuantidade14() {
		return quantidade14;
	}

	public void setQuantidade14(Double quantidade14) {
		this.quantidade14 = quantidade14;
	}

	public String getMateriaprima15() {
		return materiaprima15;
	}

	public void setMateriaprima15(String materiaprima15) {
		this.materiaprima15 = materiaprima15;
	}

	public Double getQuantidade15() {
		return quantidade15;
	}

	public void setQuantidade15(Double quantidade15) {
		this.quantidade15 = quantidade15;
	}

	public String getMateriaprima16() {
		return materiaprima16;
	}

	public void setMateriaprima16(String materiaprima16) {
		this.materiaprima16 = materiaprima16;
	}

	public Double getQuantidade16() {
		return quantidade16;
	}

	public void setQuantidade16(Double quantidade16) {
		this.quantidade16 = quantidade16;
	}

	public String getMateriaprima17() {
		return materiaprima17;
	}

	public void setMateriaprima17(String materiaprima17) {
		this.materiaprima17 = materiaprima17;
	}

	public Double getQuantidade17() {
		return quantidade17;
	}

	public void setQuantidade17(Double quantidade17) {
		this.quantidade17 = quantidade17;
	}

	public String getMateriaprima18() {
		return materiaprima18;
	}

	public void setMateriaprima18(String materiaprima18) {
		this.materiaprima18 = materiaprima18;
	}

	public Double getQuantidade18() {
		return quantidade18;
	}

	public void setQuantidade18(Double quantidade18) {
		this.quantidade18 = quantidade18;
	}

	public String getMateriaprima19() {
		return materiaprima19;
	}

	public void setMateriaprima19(String materiaprima19) {
		this.materiaprima19 = materiaprima19;
	}

	public Double getQuantidade19() {
		return quantidade19;
	}

	public void setQuantidade19(Double quantidade19) {
		this.quantidade19 = quantidade19;
	}

	public String getMateriaprima20() {
		return materiaprima20;
	}

	public void setMateriaprima20(String materiaprima20) {
		this.materiaprima20 = materiaprima20;
	}

	public Double getQuantidade20() {
		return quantidade20;
	}

	public void setQuantidade20(Double quantidade20) {
		this.quantidade20 = quantidade20;
	}

	public String getMateriaprima21() {
		return materiaprima21;
	}

	public void setMateriaprima21(String materiaprima21) {
		this.materiaprima21 = materiaprima21;
	}

	public Double getQuantidade21() {
		return quantidade21;
	}

	public void setQuantidade21(Double quantidade21) {
		this.quantidade21 = quantidade21;
	}

	public String getMateriaprima22() {
		return materiaprima22;
	}

	public void setMateriaprima22(String materiaprima22) {
		this.materiaprima22 = materiaprima22;
	}

	public Double getQuantidade22() {
		return quantidade22;
	}

	public void setQuantidade22(Double quantidade22) {
		this.quantidade22 = quantidade22;
	}

	public Double getPreco() {
		return Preco;
	}

	public void setPreco(Double preco) {
		Preco = preco;
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
		Pedidos other = (Pedidos) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pedidos [id=" + id + ", medico=" + medico + ", dataPedido=" + dataPedido + ", dataEntrega="
				+ dataEntrega + ", medicamentoTipo=" + medicamentoTipo + ", materiaprima1=" + materiaprima1
				+ ", quantidade1=" + quantidade1 + ", materiaprima2=" + materiaprima2 + ", quantidade2=" + quantidade2
				+ ", materiaprima3=" + materiaprima3 + ", quantidade3=" + quantidade3 + ", materiaprima4="
				+ materiaprima4 + ", quantidade4=" + quantidade4 + ", materiaprima5=" + materiaprima5 + ", quantidade5="
				+ quantidade5 + ", materiaprima6=" + materiaprima6 + ", quantidade6=" + quantidade6 + ", materiaprima7="
				+ materiaprima7 + ", quantidade7=" + quantidade7 + ", materiaprima8=" + materiaprima8 + ", quantidade8="
				+ quantidade8 + ", materiaprima9=" + materiaprima9 + ", quantidade9=" + quantidade9
				+ ", materiaprima10=" + materiaprima10 + ", quantidade10=" + quantidade10 + ", materiaprima11="
				+ materiaprima11 + ", quantidade11=" + quantidade11 + ", materiaprima12=" + materiaprima12
				+ ", quantidade12=" + quantidade12 + ", materiaprima13=" + materiaprima13 + ", quantidade13="
				+ quantidade13 + ", materiaprima14=" + materiaprima14 + ", quantidade14=" + quantidade14
				+ ", materiaprima15=" + materiaprima15 + ", quantidade15=" + quantidade15 + ", materiaprima16="
				+ materiaprima16 + ", quantidade16=" + quantidade16 + ", materiaprima17=" + materiaprima17
				+ ", quantidade17=" + quantidade17 + ", materiaprima18=" + materiaprima18 + ", quantidade18="
				+ quantidade18 + ", materiaprima19=" + materiaprima19 + ", quantidade19=" + quantidade19
				+ ", materiaprima20=" + materiaprima20 + ", quantidade20=" + quantidade20 + ", materiaprima21="
				+ materiaprima21 + ", quantidade21=" + quantidade21 + ", materiaprima22=" + materiaprima22
				+ ", quantidade22=" + quantidade22 + ", Preco=" + Preco + ", status=" + status + ", clienteNome="
				+ clienteNome + ", clienteSobrenome=" + clienteSobrenome + ", cpfCliente=" + cpfCliente + ", clientes="
				+ clientes + "]";
	}

	
	
	
	
	

}
