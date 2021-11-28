package br.com.Model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Fornecedores implements Serializable {

	
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	private String nomeEmpresa;
	private String cnpj;
	private String tipoFornecedor;
	private String cep;
	private String logradouro;
	private String complemento;
	private String bairro;
	private String numeroEmpresa;
	private String localidade;
	private String uf;
	private String telefoneFornecedor;
	
	
	@Column(columnDefinition = "text") // tipo text grava arquivos em base 64
	private String fotoIconBase64;

	private String extensao; // Extensao jpg, png, jpg, etc

	@Lob // Gravar arquivos no banco de dados
	@Basic(fetch = FetchType.LAZY)
	private byte[] fotoIconbase64original;
	
	
	
	
	
	
	public String getFotoIconBase64() {
		return fotoIconBase64;
	}
	public void setFotoIconBase64(String fotoIconBase64) {
		this.fotoIconBase64 = fotoIconBase64;
	}
	public String getExtensao() {
		return extensao;
	}
	public void setExtensao(String extensao) {
		this.extensao = extensao;
	}
	public byte[] getFotoIconbase64original() {
		return fotoIconbase64original;
	}
	public void setFotoIconbase64original(byte[] fotoIconbase64original) {
		this.fotoIconbase64original = fotoIconbase64original;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNomeEmpresa() {
		return nomeEmpresa;
	}
	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getTipoFornecedor() {
		return tipoFornecedor;
	}
	public void setTipoFornecedor(String tipoFornecedor) {
		this.tipoFornecedor = tipoFornecedor;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getNumeroEmpresa() {
		return numeroEmpresa;
	}
	public void setNumeroEmpresa(String numeroEmpresa) {
		this.numeroEmpresa = numeroEmpresa;
	}
	public String getLocalidade() {
		return localidade;
	}
	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getTelefoneFornecedor() {
		return telefoneFornecedor;
	}
	public void setTelefoneFornecedor(String telefoneFornecedor) {
		this.telefoneFornecedor = telefoneFornecedor;
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
		Fornecedores other = (Fornecedores) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Fornecedores [id=" + id + ", nomeEmpresa=" + nomeEmpresa + ", cnpj=" + cnpj + ", tipoFornecedor="
				+ tipoFornecedor + ", cep=" + cep + ", logradouro=" + logradouro + ", complemento=" + complemento
				+ ", bairro=" + bairro + ", numeroEmpresa=" + numeroEmpresa + ", localidade=" + localidade + ", uf="
				+ uf + ", telefoneFornecedor=" + telefoneFornecedor + "]";
	}
	
	
	
	
}
