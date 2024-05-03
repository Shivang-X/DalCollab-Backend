    package com.HomEasy.security;
    //import io.jsonwebtoken.Jwts;

    import io.jsonwebtoken.Claims;
    import io.jsonwebtoken.Jwts;
    import io.jsonwebtoken.SignatureAlgorithm;
    import io.jsonwebtoken.io.Decoders;
    import io.jsonwebtoken.security.Keys;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.stereotype.Component;

    import java.security.Key;
    import java.util.Date;
    import java.util.HashMap;
    import java.util.Map;
    import java.util.function.Function;

    @Component
    public class JWTConfig {

//        @Value("JWT_SECRET")
        private String secretKey = "03a1a63237d3c38c2bd8ed163faa40b7f5c83a3fc28dacff644a768a4cb063b9";

//        @Value("JWT_EXPIRATION")
        private long jwtExpiration = 86400000;

        private long refreshExpiration = 604800000;

        public String generateToken(UserDetails userDetails){
            return generateToken(new HashMap<>(), userDetails);
        }
        private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
            return buildToken(extraClaims, userDetails, jwtExpiration);
        }
        public String generateRefreshToken(UserDetails userDetails){
            return buildToken(new HashMap<>(), userDetails, refreshExpiration);
        }
        public String extractUsername(String token){
            return extractClaim(token, Claims::getSubject);
        }
        public boolean isTokenExpired(String token){
            return extractExpiration(token).before(new Date());
        }
        private Date extractExpiration(String token){
            return extractClaim(token, Claims::getExpiration);
        }
        public boolean isTokenValid(String token, UserDetails userDetails){
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        }
        private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration){
            return Jwts
                    .builder()
                    .setClaims(extraClaims)
                    .setSubject(userDetails.getUsername())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + expiration))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
        }
        private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
            Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        }
        private Claims extractAllClaims(String token){
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }
        private Key getSignInKey() {
            byte[] keyBytes = Decoders.BASE64.decode(secretKey);
            return Keys.hmacShaKeyFor(keyBytes);
        }
    }
