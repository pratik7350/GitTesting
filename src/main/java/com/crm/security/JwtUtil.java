package com.crm.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.naming.AuthenticationException;

import org.springframework.stereotype.Service;

import com.crm.user.UserServiceException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtUtil {

	private final String SECRET_KEY = "Deqzfgkivkalsuetyf=nwxxn12ndiendi34mnvnbxvsgeirolfjfuywbqhbwuxnnfkfpogmmvbfmvormvkrmvjvnmtovmtmgvmgitmvtimggitjgitmfjswe432=0";
//	private final String SECRET_KEY = "xJUDiCOlbfONweKhAM0i0X9jrMXTEEZV27sm+fGkfEcEbzH5aUfz+dQNYVTANOkbXFffonAtVJrPUmfTGvpyeQ==";

//	private static final long EXPIRATION_TIME = 1000 * 60 * 30; 
	private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 6;
	private JwtParser jwtParser;
	private final String TOKEN_HEADER = "Authorization";
	private final String TOKEN_PREFIX = "Bearer";

	public JwtUtil() {
		this.jwtParser = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).build();
	}

//	public String createToken(String user) {
//		try {
//			ObjectMapper objectMapper = new ObjectMapper();
//			JsonNode jsonNode = objectMapper.readTree(user);
//			String mobileNumber = jsonNode.get("mobileNumber").asText();
//			// String role = jsonNode.get("role").asText();
//			Claims claims = Jwts.claims().setSubject(mobileNumber);
//			System.out.println("Check claims :: " + claims.getSubject());
//			// claims.put("role", role);
////		claims.put("mobileNo", entity.getMobileNo());
//
//			Date expiryDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME); // Convert long to Date
//
//			return Jwts.builder().setClaims(claims).setIssuedAt(new Date()).setExpiration(expiryDate)
//					.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).compact();
//
//		} catch (
//
//		Exception ex) {
//			throw new UserControllerException(409, "Invalid Credentials ");
//		}
//	}

//	public String createToken(String value, String role) {
//		try {
//			Claims claims;
//			if ("SUPER ADMIN".equalsIgnoreCase(role)) {
//				System.out.println("Admin role check");
//				claims = Jwts.claims().setSubject(value);
//				claims.put("role", "SUPER ADMIN");
//			} else if ("ADMIN".equalsIgnoreCase(role)) {
//				System.out.println("Admin role check");
//				claims = Jwts.claims().setSubject(value);
//				claims.put("role", "ADMIN");
//			} else if ("SALES".equalsIgnoreCase(role)) {
//				System.out.println("User role check");
//				claims = Jwts.claims().setSubject(value);
//				claims.put("role", "SALES");
//			} else if ("CRM".equalsIgnoreCase(role)) {
//				System.out.println("CRM role check");
//				claims = Jwts.claims().setSubject(value);
//				claims.put("role", "CRM");
//			} else {
//				System.out.println("CLIENT role check");
//				claims = Jwts.claims().setSubject(value);
//				claims.put("role", "CLIENT");
//			}
//			System.out.println("Check claims :: " + claims.getSubject());
//
//			Date expiryDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME); // Convert long to Date
//
//			return Jwts.builder().setClaims(claims).setIssuedAt(new Date()).setExpiration(expiryDate)
//					.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).compact();
//		} catch (Exception ex) {
//			throw new UserServiceException(409, "Invalid Credentials ");
//		}
//	}

	public String createToken(String email, String role) {
		try {
			Claims claims = Jwts.claims().setSubject(email);
			claims.put("role", role);
			claims.put("email", email);

			System.out.println("Check claims :: " + claims);

			Date expiryDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);

			return Jwts.builder().setClaims(claims).setIssuedAt(new Date()).setExpiration(expiryDate)
					.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).compact();

		} catch (Exception ex) {
			throw new UserServiceException(409, "Invalid Credentials");
		}
	}

	private Claims parseJwtClaims(String token) {
		return jwtParser.parseClaimsJws(token).getBody();
	}

	public String extractEmail(String token) {
		return extractClaims(token).getSubject();
	}

	public Claims resolveClaims(HttpServletRequest req) {
		try {
			String token = resolveToken(req);
			if (token != null) {
				return parseJwtClaims(token);
			}
			return null;
		} catch (ExpiredJwtException ex) {
			req.setAttribute("expired", ex.getMessage());
			throw ex;
		} catch (Exception ex) {
			req.setAttribute("invalid", ex.getMessage());
			throw ex;
		}
	}

	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(TOKEN_HEADER);
		if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
			return bearerToken.substring(TOKEN_PREFIX.length());
		}
		return null;
	}

//	public Claims decodetoken(String token) {
//		try {
//			System.out.println("Check point 101");
////			Claims claims = Jwts.parser().setSigningKey(secret_key).parseClaimsJws(token).getBody();
//
//			JwtParser parser = Jwts.parser().setSigningKey(SECRET_KEY);
//			Jws<Claims> jws = parser.parseClaimsJws(token);
//			Claims claims = jws.getBody();
//
//			System.out.println("Check point 102");
//			System.out.println("Getting cliams==>" + claims.getExpiration());
//			return claims;
//		} catch (Exception e) {
//			// Handle other exceptions (invalid token, signature issues, etc.)
//			System.out.println("Invalid token: " + e.getMessage());
//		}
//
//		return null;
//	}

	public boolean validateClaims(Claims claims) throws AuthenticationException {
		try {
//			System.out.println("Check exp with clamin "+claims.getExpiration());
			return claims.getExpiration().after(new Date());
		} catch (Exception e) {
			throw e;
		}
	}

	public String extractRole(String token) {
		String string = extractClaims(token).get("role", String.class);
		System.err.println("Role :: " + string);
		return string;
	}

	public Map<String, String> extractRole1(String token) {
		Claims claims = extractClaims(token);

		String role = claims.get("role", String.class);
		String email = claims.get("email", String.class);

		System.err.println("Extracted Role: " + role + ", Email: " + email);

		Map<String, String> roleEmailMap = new HashMap<>();
		roleEmailMap.put("role", role);
		roleEmailMap.put("email", email);

		return roleEmailMap;
	}

	public String getEmail(Claims claims) {
		return claims.getSubject();
	}

	public Claims extractClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).build()
				.parseClaimsJws(token).getBody();
	}

//	public String getUserFromToken(String token) {
//		System.out.println(token);
//		Claims claims = decodetoken(token);
//		System.err.println(claims);
//		String email = claims.getSubject();
//		System.out.println("user:::: " + email);
//		return email;
//	}

	public Date getExpirationDateFromToken(String token) {

		Claims parseJwtClaims = parseJwtClaims(token);
		System.out.println("Check Expiration  " + parseJwtClaims.getExpiration());
		return parseJwtClaims.getExpiration();

	}

	public Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		System.out.println("Token Expiration: " + expiration);
		System.out.println("Current Time: " + new Date());
		return expiration.before(new Date());
	}

	public boolean isTokenValid(String token, String username) {
		return (username.equals(extractEmail(token)) && !isTokenExpired(token));
	}

}
