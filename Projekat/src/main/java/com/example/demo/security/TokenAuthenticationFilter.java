package com.example.demo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

//Filter koji ce presretati svaki zahtev klijenta ka serveru
//Sem nad putanjama navedenim u WebSecurityConfig.configure(WebSecurity web)
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private TokenUtils tokenUtils;

	private UserDetailsService userDetailsService;

	public TokenAuthenticationFilter(TokenUtils tokenHelper, UserDetailsService userDetailsService) {
		System.out.println("konstruktor tokenAuthFilter");
		this.tokenUtils = tokenHelper;
		this.userDetailsService = userDetailsService;
	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String username;
		System.out.println("doFilterInternal");
		String authToken = tokenUtils.getToken(request);
		System.out.println("authTOKEN doFilterInternal " + authToken);

		if (authToken != null) {
			// uzmi username iz tokena
			username = tokenUtils.getUsernameFromToken(authToken);
			System.out.println(username);

			if (username != null) {
				// uzmi user-a na osnovu username-a
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				System.out.println(userDetails.getAuthorities());
				System.out.println(userDetails.getUsername());

				// proveri da li je prosledjeni token validan
				if (tokenUtils.validateToken(authToken, userDetails)) {
					System.out.println("validan token");
					// kreiraj autentifikaciju
					TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
					authentication.setToken(authToken);
					SecurityContextHolder.getContext().setAuthentication(authentication);
				} else {
					System.out.println("nije validan token");
				}
			} else {
				System.out.println("username null");
			}
		} else {
			System.out.println("auth token null");
		}

		// prosledi request dalje u sledeci filter
		chain.doFilter(request, response);
	}

}