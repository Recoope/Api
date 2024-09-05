//package recoope.api.services;
//
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import recoope.api.domain.entities.Users;
//import recoope.api.repository.UserRepository;
//
//import java.util.stream.Collectors;
//
//@Service
//public class CustomUserDetailService implements UserDetailsService {
//    private final UserRepository userRepository;
//
//    public CustomUserDetailService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
//        Users user = userRepository.findUsuarioByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));
//
//        return new User(user.getUsername(),
//                user.getPassword(),
//                user.isEnabled(),
//                true,
//                true,
//                true,
//                user.getRoles().stream()
//                        .map(role -> new SimpleGrantedAuthority(role.getNome()))
//                        .collect(Collectors.toList()));
//    }
//}
