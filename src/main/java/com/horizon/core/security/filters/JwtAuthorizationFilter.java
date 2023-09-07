package com.horizon.core.security.filters;

import com.horizon.core.common.HttpStructure;
import com.horizon.core.security.service.impl.UserServiceImpl;
import com.horizon.core.utils.AuthUtil;
import com.horizon.core.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.inject.Inject;
import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Inject
    private JwtUtil jwtUtil;
    @Inject
    private AuthUtil authUtil;
    @Inject
    private UserServiceImpl userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        logger.info("Invoking JwtAuthFilter.");

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String userId = null;
        String authToken;

        if (authHeader != null && authHeader.startsWith(HttpStructure.BEARER_PREFIX)) {
            authToken = authHeader.replace(HttpStructure.BEARER_PREFIX, "");

            try {
                userId = jwtUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                logger.warn("An error occurred while fetching username from token", e);
                SecurityContextHolder.clearContext();
            } catch (ExpiredJwtException e) {
                logger.warn("The token has expired.", e);
                SecurityContextHolder.clearContext();
            } catch(SignatureException e){
                logger.warn("Authentication Failed. Username or password not valid.");
                SecurityContextHolder.clearContext();
            }
        } else {
            SecurityContextHolder.clearContext();
            logger.warn("Couldn't find bearer string, header will be ignored.");
        }

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(userId);
            UsernamePasswordAuthenticationToken authenticationToken = authUtil.createToken(userDetails);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            logger.info("Checking if the user has indeed been authenticated.");
            logger.info("Principal:" + auth.getPrincipal());
        }

        filterChain.doFilter(request, response);
    }
}
