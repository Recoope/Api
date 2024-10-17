package recoope.api.services;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import recoope.api.domain.entities.Empresa;
import recoope.api.repository.IEmpresaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final IEmpresaRepository _empresaRepository;

    public CustomUserDetailService(IEmpresaRepository empresaRepository) {
        _empresaRepository = empresaRepository;
    }

    public UserDetails loadUserByUsername(String cnpjOuEmail) throws UsernameNotFoundException{
        Optional<Empresa> empresaOpt = _empresaRepository.login(cnpjOuEmail);

        if (empresaOpt.isPresent()) {
            Empresa empresa = empresaOpt.get();
            return new User(empresa.getCnpj(),
                    empresa.getSenha(),
                    true,
                    true,
                    true,
                    true,
                    List.of(new SimpleGrantedAuthority("ROLE_EMPRESA"))
            );
        }

        throw new UsernameNotFoundException("Usuario não encontrado");
    }
}
