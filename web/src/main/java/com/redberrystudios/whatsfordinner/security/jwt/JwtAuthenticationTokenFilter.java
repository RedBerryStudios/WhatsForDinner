package com.redberrystudios.whatsfordinner.security.jwt;

import com.redberrystudios.whatsfordinner.jwt.JwtTokenUtil;
import com.redberrystudios.whatsfordinner.member.Member;
import com.redberrystudios.whatsfordinner.member.MemberService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

  @Autowired
  private MemberService memberService;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Value("${jwt.header}")
  private String header;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
    final String requestHeader = request.getHeader(header);

    Long userId = null;
    String authToken = null;

    if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
      authToken = requestHeader.substring(7);

      try {
        userId = jwtTokenUtil.getUserIdFromToken(authToken);
      } catch (IllegalArgumentException e) {
        logger.error("an error occured during getting username from token", e);
      } catch (ExpiredJwtException e) {
        logger.warn("the token is expired and not valid anymore", e);
      }
    } else {
      logger.warn("couldn't find bearer string, will ignore the header");
    }

    logger.info("checking authentication for user " + userId);
    if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      Member member = memberService.find(userId);

      boolean isAuthenticated = jwtTokenUtil.validateToken(authToken, member);

      JwtAuthenticationToken authentication = new JwtAuthenticationToken(member, authToken, isAuthenticated);
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

      logger.info("authenticated user " + userId + ", setting security context");
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    chain.doFilter(request, response);
  }

}
