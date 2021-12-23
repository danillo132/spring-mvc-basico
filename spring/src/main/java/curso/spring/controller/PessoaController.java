package curso.spring.controller;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import curso.spring.model.Pessoa;
import curso.spring.model.Telefone;
import curso.spring.repository.PessoaRepository;
import curso.spring.repository.ProfissaoRepository;
import curso.spring.repository.TelefoneRepository;

@Controller
public class PessoaController {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private TelefoneRepository telefoneRepository;

	@Autowired
	private ReportUtil reportUtil;

	@Autowired
	private ProfissaoRepository profissaoRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/cadastroPessoa")
	public ModelAndView inicio() {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastroPessoa");
		modelAndView.addObject("pessoaobj", new Pessoa());
		modelAndView.addObject("pessoas", pessoaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		modelAndView.addObject("profissoes", profissaoRepository.findAll());
		return modelAndView;
	}
	
	@GetMapping("/pessoaspag")
	public ModelAndView carregaPessoaPorPaginacao(@PageableDefault(size = 5) Pageable pageable,
			ModelAndView modelAndView, @RequestParam("nomepesquisa") String nomepesquisa) {
		
		Page<Pessoa> pagePessoa= pessoaRepository.findPersonByNamePage(nomepesquisa, pageable);
		
		modelAndView.addObject("pessoas", pagePessoa);
		modelAndView.addObject("pessoaobj", new Pessoa());
		modelAndView.addObject("nomepesquisa", nomepesquisa);
		modelAndView.setViewName("cadastro/cadastroPessoa");
		
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST, value = "**/salvarPessoa", consumes = { "multipart/form-data" })
	public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult bindingResult, final MultipartFile file)
			throws IOException {

		pessoa.setTelefone(telefoneRepository.telefones(pessoa.getId()));

		if (bindingResult.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView("cadastro/cadastroPessoa");
			modelAndView.addObject("pessoas", pessoaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
			modelAndView.addObject("pessoaobj", pessoa);

			List<String> msg = new ArrayList<String>();

			for (ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage());
			}

			modelAndView.addObject("msg", msg);

			return modelAndView;
		} else {

			if (file.getSize() > 0) {
				pessoa.setCurriculo(file.getBytes());
				pessoa.setTipoFileCurriculo(file.getContentType());
				pessoa.setNomeFileCurriculo(file.getOriginalFilename());

			} else {
				if (pessoa.getId() != null && pessoa.getId() > 0) { // editando
					Pessoa pessoaTemp = pessoaRepository.findById(pessoa.getId()).get();
					pessoa.setCurriculo(pessoaTemp.getCurriculo());
					pessoa.setTipoFileCurriculo(pessoaTemp.getTipoFileCurriculo());
					pessoa.setNomeFileCurriculo(pessoaTemp.getNomeFileCurriculo());
				}
			}

			pessoaRepository.save(pessoa);

			ModelAndView andView = new ModelAndView("cadastro/cadastroPessoa");
			andView.addObject("pessoas", pessoaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
			andView.addObject("pessoaobj", new Pessoa());
			return andView;
		}
	}

	@GetMapping("**/baixarcurriculo/{idpessoa}")
	public void baixarcurriculo(@PathVariable("idpessoa") Long idpessoa, HttpServletResponse response)
			throws IOException {

		Pessoa pessoa = pessoaRepository.findById(idpessoa).get();
		if (pessoa.getCurriculo() != null) {

			response.setContentLength(pessoa.getCurriculo().length);
			response.setContentType(pessoa.getTipoFileCurriculo());

			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", pessoa.getNomeFileCurriculo());
			response.setHeader(headerKey, headerValue);

			response.getOutputStream().write(pessoa.getCurriculo());

		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/listaPessoas")
	public ModelAndView pessoas() {

		ModelAndView andView = new ModelAndView("cadastro/cadastroPessoa");
		andView.addObject("pessoas", pessoaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		andView.addObject("pessoaobj", new Pessoa());
		return andView;
	}

	@GetMapping("/editarpessoa/{idpessoa}")
	public ModelAndView editar(@PathVariable("idpessoa") Long idpessoa) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastroPessoa");
		
		modelAndView.addObject("pessoaobj", pessoa.get());
		modelAndView.addObject("profissoes", profissaoRepository.findAll());
		modelAndView.addObject("pessoas", pessoaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		return modelAndView;

	}

	@GetMapping("/telefones/{idpessoa}")
	public ModelAndView telefones(@PathVariable("idpessoa") Long idpessoa) {

		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		modelAndView.addObject("pessoaobj", pessoa.get());
		modelAndView.addObject("telefones", telefoneRepository.telefones(idpessoa));

		return modelAndView;

	}

	@GetMapping("/excluirpessoa/{idpessoa}")
	public ModelAndView deletar(@PathVariable("idpessoa") Long idpessoa) {

		ModelAndView modelAndView = new ModelAndView("cadastro/cadastroPessoa");
		pessoaRepository.deleteById(idpessoa);
		modelAndView.addObject("pessoas", pessoaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		modelAndView.addObject("pessoaobj", new Pessoa());

		return modelAndView;

	}

	@GetMapping("/excluirtelefone/{idtelefone}")
	public ModelAndView deletarTelefone(@PathVariable("idtelefone") Long idtelefone) {

		Pessoa pessoa = telefoneRepository.findById(idtelefone).get().getPessoa();
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		telefoneRepository.deleteById(idtelefone);
		modelAndView.addObject("pessoaobj", new Pessoa());
		modelAndView.addObject("telefones", telefoneRepository.telefones(pessoa.getId()));

		return modelAndView;

	}

	@PostMapping("**/pesquisapessoa")
	public ModelAndView buscaPorNome(@RequestParam("nomepesquisa") String nomepesquisa,
			@RequestParam("pesquisasexo") String pesquisasexo,
			@PageableDefault(size = 5, sort = {"nome"}) Pageable pageable) {

		Page<Pessoa> pessoas = null;

		if (pesquisasexo != null && !pesquisasexo.isEmpty()) {
			pessoas = pessoaRepository.findPersonByNameAndSexPage(nomepesquisa, pesquisasexo, pageable);
		} else {
			pessoas = pessoaRepository.findPersonByNamePage(nomepesquisa, pageable);
		}

		ModelAndView andView = new ModelAndView("cadastro/cadastroPessoa");
		andView.addObject("pessoas", pessoas);
		andView.addObject("pessoaobj", new Pessoa());
		andView.addObject("nomepesquisa", nomepesquisa);
		return andView;
	}

	@GetMapping("**/pesquisapessoa")
	public void imprimepdf(@RequestParam("nomepesquisa") String nomepesquisa,
			@RequestParam("pesquisasexo") String pesquisasexo, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		List<Pessoa> pessoas = new ArrayList<Pessoa>();

		if (pesquisasexo != null && !pesquisasexo.isEmpty() && nomepesquisa != null && !nomepesquisa.isEmpty()) {
			pessoas = pessoaRepository.findPersonByNameAndSex(nomepesquisa, pesquisasexo);

		} else if (nomepesquisa != null && !nomepesquisa.isEmpty()) {
			pessoas = pessoaRepository.findPersonByName(nomepesquisa);
		} else if (pesquisasexo != null && !pesquisasexo.isEmpty()) {
			pessoas = pessoaRepository.findPersonBySexo(pesquisasexo);

		} else {
			Iterable<Pessoa> iterable = pessoaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome")));

			for (Pessoa pessoa : iterable) {
				pessoas.add(pessoa);
			}
		}

		byte[] pdf = reportUtil.gerarRelatorio(pessoas, "pessoa", request.getServletContext());

		// Tamanho da resposta
		response.setContentLength(pdf.length);

		// Definir na resposta o tipo de arquivo
		response.setContentType("application/octet-stream");

		// Definir o cabecalho da resposta
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", "relatorio.pdf");
		response.setHeader(headerKey, headerValue);

		// Finaliza a resposta para o navegador
		response.getOutputStream().write(pdf);
	}

	@PostMapping("/addfonepessoa/{pessoaid}")
	public ModelAndView addpessoa(Telefone telefone, @PathVariable("pessoaid") Long pessoaid) {

		Pessoa pessoa = pessoaRepository.findById(pessoaid).get();

		if (telefone != null && (telefone.getNumero().isEmpty() || telefone.getTipo().isEmpty())) {

			ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
			modelAndView.addObject("pessoaobj", pessoa);
			modelAndView.addObject("telefones", telefoneRepository.telefones(pessoaid));
			List<String> msg = new ArrayList<String>();

			if (telefone.getNumero().isEmpty()) {
				msg.add("Número deve ser informado!");
			}

			if (telefone.getTipo().isEmpty()) {
				msg.add("O tipo de telefone deve ser informado!");
			}

			modelAndView.addObject("msg", msg);

			return modelAndView;
		}

		telefone.setPessoa(pessoa);

		telefoneRepository.save(telefone);

		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");

		modelAndView.addObject("pessoaobj", pessoa);
		modelAndView.addObject("telefones", telefoneRepository.telefones(pessoaid));
		return modelAndView;

	}
}
