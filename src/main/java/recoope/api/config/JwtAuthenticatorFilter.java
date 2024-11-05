package recoope.api.config;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import recoope.api.services.CustomUserDetailService;

import javax.crypto.SecretKey;
import java.io.IOException;

public class JwtAuthenticatorFilter extends OncePerRequestFilter {
    private final CustomUserDetailService userDetailService;
    private final SecretKey secretKey;
    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticatorFilter.class);

    public JwtAuthenticatorFilter(CustomUserDetailService customUserDetailService, SecretKey secretKey) {
        this.userDetailService = customUserDetailService;
        this.secretKey = secretKey;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        logger.info("Auth Header: " + authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            logger.info("JWT Token: " + token);

            try {
                String username = Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject();
                logger.info("Username from Token: " + username);

                if (username != null) {
                    UserDetails userDetails = userDetailService.loadUserByUsername(username);
                    logger.info("User Details: " + userDetails);

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    logger.info("Authentication successful for user: " + username);
                }
            } catch (Exception e) {
                logger.error("Token validation failed: " + e.getMessage());
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        boolean shouldNotFilter = path.startsWith("/login");
        return shouldNotFilter;
    }
}
