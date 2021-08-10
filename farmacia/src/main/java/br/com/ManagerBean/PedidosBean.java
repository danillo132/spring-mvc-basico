package br.com.ManagerBean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.DAO.DaoClientes;
import br.com.DAO.DaoOrcamentos;
import br.com.DAO.DaoPedidos;
import br.com.Model.Clientes;
import br.com.Model.Orcamentos;
import br.com.Model.Pedidos;
import br.com.lazyDataTable.LazyOrcamentos;
import br.com.lazyDataTable.LazyPedidos;

@ManagedBean(name = "PedidosBean")
@SessionScoped
public class PedidosBean {

	
	private Pedidos pedidos = new Pedidos();
	private Orcamentos orcamentos = new Orcamentos();
	private Clientes clientes = new Clientes();
	private DaoOrcamentos<Orcamentos> daoOrcamentos = new DaoOrcamentos<Orcamentos>();
	private DaoClientes<Clientes> daoClientes = new DaoClientes<Clientes>();
	private DaoPedidos<Pedidos> daoPedidos = new DaoPedidos<Pedidos>();
	private LazyPedidos<Pedidos> pedidosLista = new LazyPedidos<Pedidos>();
	private LazyOrcamentos<Orcamentos> orcamentoslista = new LazyOrcamentos<Orcamentos>();
	private String campoPesquisa;
	
	
	@PostConstruct
	public void init() {
		pedidosEntregue();
	}
	
	
	
	public Pedidos confirmarPedido() {
		try {
		String idPedido = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idPedido");
		FacesContext.getCurrentInstance().getExternalContext().redirect("pedidos.jsf");
		
		orcamentos = daoOrcamentos.pesquisar(Long.valueOf(idPedido), Orcamentos.class);
		pedidos.setClientes(orcamentos.getClientes());
		pedidos.setMedico(orcamentos.getNomeMedico());
		pedidos.setCrm(orcamentos.getCrm());
		pedidos.setDataPedido(orcamentos.getDataInicial());
		pedidos.setDataEntrega(orcamentos.getDataFinal());
		pedidos.setMedicamentoTipo(orcamentos.getTipoMedicamento());
		pedidos.setMateriaprima1(orcamentos.getFarmaco1());
		pedidos.setQuantidade1(orcamentos.getDosagem1());
		pedidos.setMateriaprima2(orcamentos.getFarmaco2());
		pedidos.setQuantidade2(orcamentos.getDosagem2());
		pedidos.setMateriaprima3(orcamentos.getFarmaco3());
		pedidos.setQuantidade3(orcamentos.getDosagem3());
		pedidos.setMateriaprima4(orcamentos.getFarmaco4());
		pedidos.setQuantidade4(orcamentos.getDosagem4());
		pedidos.setMateriaprima5(orcamentos.getFarmaco5());
		pedidos.setQuantidade5(orcamentos.getDosagem5());
		pedidos.setMateriaprima6(orcamentos.getFarmaco6());
		pedidos.setQuantidade6(orcamentos.getDosagem6());
		pedidos.setMateriaprima7(orcamentos.getFarmaco7());
		pedidos.setQuantidade7(orcamentos.getDosagem7());
		pedidos.setMateriaprima8(orcamentos.getFarmaco8());
		pedidos.setQuantidade8(orcamentos.getDosagem8());
		pedidos.setMateriaprima9(orcamentos.getFarmaco9());
		pedidos.setQuantidade9(orcamentos.getDosagem9());
		pedidos.setMateriaprima10(orcamentos.getFarmaco10());
		pedidos.setQuantidade10(orcamentos.getDosagem10());
		pedidos.setMateriaprima11(orcamentos.getFarmaco11());
		pedidos.setQuantidade11(orcamentos.getDosagem11());
		pedidos.setMateriaprima12(orcamentos.getFarmaco12());
		pedidos.setQuantidade12(orcamentos.getDosagem12());
		pedidos.setMateriaprima13(orcamentos.getFarmaco13());
		pedidos.setQuantidade13(orcamentos.getDosagem13());
		pedidos.setMateriaprima14(orcamentos.getFarmaco14());
		pedidos.setQuantidade14(orcamentos.getDosagem14());
		pedidos.setMateriaprima15(orcamentos.getFarmaco15());
		pedidos.setQuantidade15(orcamentos.getDosagem15());
		pedidos.setMateriaprima16(orcamentos.getFarmaco16());
		pedidos.setQuantidade16(orcamentos.getDosagem16());
		pedidos.setMateriaprima17(orcamentos.getFarmaco17());
		pedidos.setQuantidade17(orcamentos.getDosagem17());
		pedidos.setMateriaprima18(orcamentos.getFarmaco18());
		pedidos.setQuantidade18(orcamentos.getDosagem18());
		pedidos.setMateriaprima19(orcamentos.getFarmaco19());
		pedidos.setQuantidade19(orcamentos.getDosagem19());
		pedidos.setMateriaprima20(orcamentos.getFarmaco20());
		pedidos.setQuantidade20(orcamentos.getDosagem20());
		pedidos.setMateriaprima21(orcamentos.getFarmaco21());
		pedidos.setQuantidade21(orcamentos.getDosagem21());
		pedidos.setMateriaprima22(orcamentos.getFarmaco22());
		pedidos.setQuantidade22(orcamentos.getDosagem22());
		pedidos.setPreco(orcamentos.getPrecoTotal());
		pedidos.setStatus("Não entregue");
		pedidos.setClienteNome(orcamentos.getClientes().getNome());
		pedidos.setClienteSobrenome(orcamentos.getClientes().getSobrenome());
		pedidos.setCpfCliente(orcamentos.getClientes().getCpf());
		
		daoPedidos.salvar(pedidos);
		pedidosLista.list.add(pedidos);
		
		
		pedidosLista.loading(0, 5, null, null, orcamentos.getClientes().getId());
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "Pedido confirmado com sucesso!"));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return pedidos;
	}
	
	
	
	public void pedidosEntregues(){
		try {
		FacesContext.getCurrentInstance().getExternalContext().redirect("ReceitasEntregues.jsf");
		
		pedidosLista.loadingPed(0, 5, null, null, "Entregue");
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void pedidosNaoEntregues(){
		try {
		FacesContext.getCurrentInstance().getExternalContext().redirect("ReceitasAbertas.jsf");
		
		pedidosLista.loadingPed(0, 5, null, null, "Não entregue");
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void pedidosEntregue() {
		int qtdTotalEntregues = 0;
		
		qtdTotalEntregues = daoPedidos.contarStatusEntregue();
		pedidos.setStatusEntregueTotal(qtdTotalEntregues);
		
	}

	
	public void pesquisaPedido() {
		
		try {
		clientes = daoClientes.pesquisaCpf(campoPesquisa, Clientes.class);
		pedidosLista.loading(0, 5, null, null, clientes.getId());
		}catch(Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:", "Usuário não possui receitas!"));
		}
	}
	
	
	public void entregarPedido() {
		
		try {
			String idPed = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idPed");
			
			
			pedidos = daoPedidos.pesquisar(Long.valueOf(idPed), Pedidos.class);
			pedidos.setStatus("Entregue");
			daoPedidos.salvar(pedidos);
			pedidosLista.loading(0, 5, null, null, pedidos.getClientes().getId());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação:", "Pedido entregue com sucesso!"));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	
	public String voltar()  {
		try {
		FacesContext.getCurrentInstance().getExternalContext().redirect("orcamentos.jsf");
		System.out.println(orcamentos.getClientes().getId());
		orcamentoslista.loading(0, 5, null, null, orcamentos.getClientes().getId());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	
	public String removerpedido() {
		
		daoPedidos.removerPedido(pedidos);
		pedidosLista.list.remove(pedidos);
		pedidosLista.loading(0, 5, null, null, pedidos.getClientes().getId());
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação:", "Pedido removido com sucesso!"));
		
		
		return "";
	}



	public Pedidos getPedidos() {
		return pedidos;
	}




	public void setPedidos(Pedidos pedidos) {
		this.pedidos = pedidos;
	}




	public Orcamentos getOrcamentos() {
		return orcamentos;
	}




	public void setOrcamentos(Orcamentos orcamentos) {
		this.orcamentos = orcamentos;
	}




	public LazyPedidos<Pedidos> getPedidosLista() {
		return pedidosLista;
	}



	public void setCampoPesquisa(String campoPesquisa) {
		this.campoPesquisa = campoPesquisa;
	}
	
	public String getCampoPesquisa() {
		return campoPesquisa;
	}
	
	
	
	
}
