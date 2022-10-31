package com.r2s.pte.util;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTUtil {
	public static String KEY_ALGORITHM = "boostpte";
	public static final Algorithm ALGORITHM = Algorithm.HMAC256(KEY_ALGORITHM);
	public static String USER_TYPE = "userType";
	public static String ID = "id";

	public static String generate(Long userId, Long userType) {
		return JWT.create().withSubject(String.valueOf(userId)).withClaim(ID, userId).withClaim(USER_TYPE, userType)
				.sign(ALGORITHM);
	}

	public static boolean verify(String key) {
		try {
			JWTVerifier verifier = JWT.require(ALGORITHM).build();
			DecodedJWT decodedJWT = verifier.verify(key);
			Long userId = Long.valueOf(decodedJWT.getSubject());
			String userType = decodedJWT.getClaims().get(USER_TYPE).toString();
			Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority(userType));
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId,
					null, authorities);
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
