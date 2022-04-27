package com.getir.readingisgood.api.filter;

import com.getir.framework.jwt.model.GetirJwtClaims;
import com.getir.framework.jwt.resolver.AccessTokenResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final AccessTokenResolver accessTokenResolver;

    private static final int TOKEN_SUFFIX_LENGTH = 7;

    @Override
    protected void doFilterInternal(final HttpServletRequest httpServletRequest,
                                    final HttpServletResponse httpServletResponse,
                                    final FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = httpServletRequest.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            final String token = authHeader.substring(TOKEN_SUFFIX_LENGTH);
            final GetirJwtClaims getirJwtClaims = accessTokenResolver.resolve(token);

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                final UsernamePasswordAuthenticationToken upassToken = new UsernamePasswordAuthenticationToken(getirJwtClaims, null, new ArrayList<>());
                upassToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(upassToken);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
