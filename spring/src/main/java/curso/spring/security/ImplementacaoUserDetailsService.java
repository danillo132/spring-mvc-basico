package curso.spring.security;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import curso.spring.model.Usuario;
import curso.spring.repository.UsuarioRepository;


@Service
public class ImplementacaoUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = repository.findUserByLogin(username);
		
		
		if(usuario == null) {
			throw new UsernameNotFoundException("Usuário não foi encontrado!");
		}
		
		return usuario;
	}

}
