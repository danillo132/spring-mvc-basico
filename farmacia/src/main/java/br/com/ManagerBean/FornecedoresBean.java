package br.com.ManagerBean;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.file.UploadedFile;

import com.google.gson.Gson;

import br.com.DAO.DaoFornecedores;
import br.com.Model.Fornecedores;
import br.com.lazyDataTable.LazyFornecedores;

@ManagedBean(name = "FornecedoresBean")
@SessionScoped
public class FornecedoresBean {

	private Fornecedores fornecedores = new Fornecedores();
	private DaoFornecedores<Fornecedores> daoFornecedores = new DaoFornecedores<Fornecedores>();

	private LazyFornecedores<Fornecedores> fornecedoreslazy = new LazyFornecedores<Fornecedores>();

	private UploadedFile arquivofoto;

	@PostConstruct
	public void init() {
		fornecedoreslazy.load(0, 5, null, null);
	}

	public List<String> list(String query) {
		String queryLowerCase = query.toLowerCase();
		List<String> autocomplete = new ArrayList<>();
		fornecedoreslazy.list = daoFornecedores.listar(Fornecedores.class);

		for (Fornecedores string : fornecedoreslazy.list) {
			autocomplete.add(string.getNomeEmpresa());
		}

		return autocomplete.stream().filter(t -> t.toLowerCase().startsWith(queryLowerCase))
				.collect(Collectors.toList());
	}

	public String salvar() {

		daoFornecedores.salvar(fornecedores);
		fornecedoreslazy.list.add(fornecedores);
		fornecedores = new Fornecedores();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Fornecedor salvo com sucesso!"));

		return "";
	}

	public String novo() {

		fornecedores = new Fornecedores();

		return "";
	}

	public String removerFornecedor() {
		try {
			daoFornecedores.removerFornecedor(fornecedores);
			fornecedoreslazy.list.remove(fornecedores);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Fornecedor removido com sucesso!"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Não foi possível realizar a remoção."));
			e.printStackTrace();
		}
		return "";
	}

	public Fornecedores editarForne() {

		try {
			String idForne = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("idForne");

			FacesContext.getCurrentInstance().getExternalContext().redirect("cadForne.jsf");
			fornecedores = daoFornecedores.pesquisar(Long.valueOf(idForne), Fornecedores.class);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return fornecedores;
	}

	public void pesquisaCep(AjaxBehaviorEvent event) {

		try {

			URL url = new URL("https://viacep.com.br/ws/" + fornecedores.getCep() + "/json/");
			URLConnection connection = url.openConnection();
			InputStream is = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			String cep = "";
			StringBuilder jsonCep = new StringBuilder();

			while ((cep = reader.readLine()) != null) {
				jsonCep.append(cep);
			}

			Fornecedores fornecedoresCep = new Gson().fromJson(jsonCep.toString(), Fornecedores.class);

			fornecedores.setCep(fornecedoresCep.getCep());
			fornecedores.setLogradouro(fornecedoresCep.getLogradouro());
			fornecedores.setComplemento(fornecedoresCep.getComplemento());
			fornecedores.setBairro(fornecedoresCep.getBairro());

			fornecedores.setLocalidade(fornecedoresCep.getLocalidade());
			fornecedores.setUf(fornecedoresCep.getUf());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void upload(FileUploadEvent image) throws IOException {

		arquivofoto = image.getFile();

		if (arquivofoto != null) {

			byte[] imagemByte = getByte(arquivofoto.getInputStream());
			fornecedores.setFotoIconbase64original(imagemByte); // Salva a imagem original

			// Transforma em bufferimage (Miniatura)

			BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imagemByte));

			// Pega o tipo de imagem

			int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();

			int largura = 200;
			int altura = 200;

			// Criando a miniaura

			BufferedImage resizedImage = new BufferedImage(largura, altura, type);
			Graphics2D g = resizedImage.createGraphics();
			g.drawImage(bufferedImage, 0, 0, largura, altura, null);
			g.dispose();

			// Escrever novamente a imagem em tamanho menor

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			String extensao = arquivofoto.getContentType().split("\\/")[1]; // Retorna: image/png
			ImageIO.write(resizedImage, extensao, baos);

			String miniImagem = "data:" + arquivofoto.getContentType() + ";base64,"
					+ DatatypeConverter.printBase64Binary(baos.toByteArray());

			fornecedores.setFotoIconBase64(miniImagem);
			fornecedores.setExtensao(extensao);

		}

	}
	
	
	public void download() throws IOException {
		
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		String fileDownload = params.get("FiledownloadId");
		
		Fornecedores fornecedorDowload = daoFornecedores.pesquisar(Long.valueOf(fileDownload), Fornecedores.class);
		
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.addHeader("Content-disposition", "attachment; filename="+fornecedorDowload.getNomeEmpresa()+"."+ fornecedorDowload.getExtensao());
		response.setContentType("application/octet-stream");
		response.setContentLength(fornecedorDowload.getFotoIconbase64original().length);
		response.getOutputStream().write(fornecedorDowload.getFotoIconbase64original());
		response.getOutputStream().flush();
		FacesContext.getCurrentInstance().responseComplete();
	}

	private byte[] getByte(InputStream is) throws IOException {

		int len;
		int size = 1024;
		byte[] buf = null;

		if (is instanceof ByteArrayInputStream) {

			size = is.available();
			buf = new byte[size];
			len = is.read(buf, 0, size);

		} else {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			buf = new byte[size];

			while ((len = is.read(buf, 0, size)) != -1) {
				bos.write(buf, 0, len);
			}

			buf = bos.toByteArray();
		}

		return buf;
	}

	public Fornecedores getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(Fornecedores fornecedores) {
		this.fornecedores = fornecedores;
	}

	public LazyFornecedores<Fornecedores> getFornecedoreslazy() {
		return fornecedoreslazy;
	}

	public void setArquivofoto(UploadedFile arquivofoto) {
		this.arquivofoto = arquivofoto;
	}

	public UploadedFile getArquivofoto() {
		return arquivofoto;
	}

}
