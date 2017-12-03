package com.redberrystudios.whatsfordinner.jwt;

import com.redberrystudios.whatsfordinner.group.Group;
import com.redberrystudios.whatsfordinner.group.GroupService;
import com.redberrystudios.whatsfordinner.member.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

  @Autowired
  private GroupService groupService;

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private Long expiration;

  private Claims claims;

  public Long getUserIdFromToken(String token) {
    return Long.parseLong(getClaimFromToken(token, Claims::getSubject));
  }

  public Long getGroupIdFromToken(String token) {
    return getClaimFromToken(token, (claims) -> claims.get("groupId", Long.class));
  }

  public Date getIssuedAtDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getIssuedAt);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    return claimsResolver.apply(getAllClaimsFromToken(token));
  }

  private Claims getAllClaimsFromToken(String token) {
    if (claims == null) {
      claims = Jwts.parser()
          .setSigningKey(secret)
          .parseClaimsJws(token)
          .getBody();
    }

    return claims;
  }

  public String generateToken(Member member) {
    Map<String, Object> claims = new HashMap<>();

    claims.put("username", member.getName());
    claims.put("email", member.getEmail());
    claims.put("image", member.getPictureLink());

    Group groupForMember = groupService.findByMember(member.getId());
    if (groupForMember != null) {
      claims.put("groupId", groupForMember.getId());
    }

    return doGenerateToken(claims, member.getId().toString());
  }

  private String doGenerateToken(Map<String, Object> claims, String subject) {
    final Date createdDate = Date.from(ZonedDateTime.now().toInstant());
    final Date expirationDate = calculateExpirationDate(createdDate);

    System.out.println("doGenerateToken " + createdDate);

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(createdDate)
        .setExpiration(expirationDate)
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  public boolean validateToken(String token, Member member) {
    final Long userId = getUserIdFromToken(token);

    return (
        member.getId().equals(userId)
            && !isTokenExpired(token))
        && isTokenIssuedAlready(token);
  }

  private boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(Date.from(ZonedDateTime.now().toInstant()));
  }

  private boolean isTokenIssuedAlready(String token) {
    final Date issuedAt = getIssuedAtDateFromToken(token);
    return issuedAt.before(Date.from(ZonedDateTime.now().toInstant()));
  }

  private Date calculateExpirationDate(Date createdDate) {
    return new Date(createdDate.getTime() + expiration * 1000);
  }
}

