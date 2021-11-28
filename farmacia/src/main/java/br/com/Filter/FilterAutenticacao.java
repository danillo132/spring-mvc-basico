package br.com.Filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.Model.Funcionarios;
import br.com.SingleConnection.SingleConnection;



@WebFilter(urlPatterns = {"/*"})
public class FilterAutenticacao implements Filter {

	
	
	
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		SingleConnection.geEntityManager();
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		
		Funcionarios funcionarioLogado = (Funcionarios) session.getAttribute("funcionariologado");
		String url = req.getServletPath();
		
		if(!url.equalsIgnoreCase("index.jsf") && funcionarioLogado == null) {
			
			
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsf");
			dispatcher.forward(request, response);
			return;
			
		}else {
			chain.doFilter(request, response);
		}
		
	}

	@Override
	public void destroy() {
		
		
	}

	

	
	
}
