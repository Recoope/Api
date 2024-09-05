//package recoope.api.config.filter;
//
//import io.jsonwebtoken.Jwts;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.filter.OncePerRequestFilter;
//import recoope.api.services.CustomUserDetailService;
//
//import javax.crypto.SecretKey;
//import java.io.IOException;
//
//public class JwtAuthFilter extends OncePerRequestFilter {
//    private final CustomUserDetailService userDetailService;
//    private final SecretKey secretKey;
//
//    public JwtAuthFilter(CustomUserDetailService customUserDetailService, SecretKey secretKey) {
//        this.userDetailService = customUserDetailService;
//        this.secretKey = secretKey;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        String authHeader = request.getHeader("Authorization");
//        if (authHeader != null && authHeader
//                .startsWith("Bearer ")) {
//            String token = authHeader.substring(7);
//
//            try {
//                String username = Jwts.parserBuilder()
//                        .setSigningKey(secretKey)
//                        .build()
//                        .parseClaimsJws(token)
//                        .getBody()
//                        .getSubject();
//
//                if (username != null) {
//                    UserDetails userDetails = userDetailService.loadUserByUsername(username);
//                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                            userDetails, null, userDetails.getAuthorities()
//                    );
//
//                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                }
//            } catch (Exception e) {
//                response.setContentType("application/json");
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                response.getWriter().write("{ \"error\": \"" + e.getMessage() + "\" }");
//                return;
//            }
//
//            filterChain.doFilter(request, response);
//        }
//    }
//}
