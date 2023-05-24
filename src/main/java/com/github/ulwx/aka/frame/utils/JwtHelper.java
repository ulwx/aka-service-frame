package com.github.ulwx.aka.frame.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ulwx.tool.CTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;


public class JwtHelper {
		private static Logger log =LoggerFactory.getLogger(JwtHelper.class);


	    public static String createJWT(JwtInfo jwtInfo,String jwtKey
				,int expireSeconds)  {

	        /***
	        iss: jwt签发者
	        sub: jwt所面向的用户
	        aud: 接收jwt的一方
	        exp: jwt的过期时间，这个过期时间必须要大于签发时间
	        nbf: 定义在什么时间之前，该jwt都是不可用的.
	        iat: jwt的签发时间
	        jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
	        **/
	        Date now = new Date();
	        Date notBefore=CTime.addMinutes(-2);
	        Date expirationDate=CTime.addSenconds(expireSeconds);
		    Algorithm algorithm = Algorithm.HMAC256(jwtKey);
		    
		    String token = JWT.create()
		        .withIssuer(Constant.JWT_ISSUER)
		        .withSubject(jwtInfo.getDeviceID())
		        .withIssuedAt(now)
		        .withNotBefore(notBefore)
		        .withJWTId(jwtInfo.getJwtID())
		        .withExpiresAt(expirationDate)
		        .withClaim(JwtInfo.PHONE, jwtInfo.getPhone())
		        .withClaim(JwtInfo.USER, jwtInfo.getUser())
		        .withClaim(JwtInfo.USER_TYPE, jwtInfo.getUserType())
		        .withClaim(JwtInfo.JWT_EXT, jwtInfo.getExt())
		        .sign(algorithm);
		    
		    return token;
	    }


	    public static JwtInfo parseJWT(String jwtToken,String jwtKey) throws Exception{
	    	
		  Algorithm algorithm = Algorithm.HMAC256(jwtKey);
		JWTVerifier verifier = JWT.require(algorithm)
		    .withIssuer(Constant.JWT_ISSUER)
		.build(); //Reusable verifier instance
		DecodedJWT jwt = verifier.verify(jwtToken);
		JwtInfo jwtInfo=new JwtInfo();
		jwtInfo.setDeviceID(jwt.getSubject());
		jwtInfo.setJwtID(jwt.getId());
		jwtInfo.setPhone(jwt.getClaim(jwtInfo.PHONE).asString());
		jwtInfo.setUser(jwt.getClaim(jwtInfo.USER).asString());
		jwtInfo.setUserType(jwt.getClaim(jwtInfo.USER_TYPE).asString());
		jwtInfo.setExt(jwt.getClaim(jwtInfo.JWT_EXT).asString());
		return jwtInfo;
		

	    }
	    

	
}


class Constant 
{
    public static final String JWT_ISSUER="org.swt";
    
}