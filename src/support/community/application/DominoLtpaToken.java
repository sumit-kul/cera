package support.community.application;

import java.util.*;
import javax.servlet.http.*;

import support.community.util.Base64;

import java.security.*;

/**
 * Wraps a domino (not websphere) format LTPA token. Can be used to either create a token
 * or to parse and validate a  token.
 * 
 * 
*/
public class DominoLtpaToken 
{
	/*
		The 20 byte secret key.
	*/
	private byte[] key;

	/*
		The token data comprising 4 byte header, creation time, expiration time
		and user name. 
	*/
	private byte[] tokenData;

	/*
		The 20 byte SHA1 hash of the tokenData concatenated with the key. 
	*/
	private byte[] hash;
	
	/*
		The status of  validation operation. Has one of the listed values. 
	*/
	private int status;		
	public static final int VALID = 0;
	public static final int NO_DATA = 1;
	public static final int BAD_DATA_LENGTH = 2;
	public static final int BAD_HEADER = 3;
	public static final int BAD_HASH = 4;	
	public static final int EXPIRED = 5;	

	/*
		Constants. 
	*/
	public static final String COOKIE_NAME = "LtpaToken";
	private static final byte[] TOKEN_HEADER = { 0, 1, 2, 3 };

  /**  
 		Extract and parse a token cookie from a servlet request. 
 		@param  secret  the 20 byte secret key encoded as a base64 string
 		@param  req  http request which may contain the cookie to be parsed
 		@throws NoSuchAlgorithmException  if the SHA1 algorithm is not available in the JDK
  */   	
  public DominoLtpaToken(String secret, HttpServletRequest req) 
  {
       this(Base64.decode(secret.getBytes()), req);
  }


  /**  
 		Extract and parse a token cookie from a servlet request. 
 		@param  secret  the 20 byte secret key
 		@param  req  http request which may contain the cookie to be parsed
 		@throws NoSuchAlgorithmException  if the SHA1 algorithm is not available in the JDK
  */   	
  public DominoLtpaToken(byte[] secret, HttpServletRequest req)
  {
  	key = secret;
  	
  	byte[] buf = getCookieData(req);
  	if (buf==null) { 
  		status = NO_DATA;
  		return;
  	}
  	
  	if (buf.length<41) { 
  		status = BAD_DATA_LENGTH;
  		return;
  	}

	for (int n=0; n<TOKEN_HEADER.length; n++) 
		if (buf[n] != TOKEN_HEADER[n]) {
			status =  BAD_HEADER;
			return;			
	}
  	
  	tokenData = new byte[buf.length-20];
  	for (int n=0; n<buf.length-20; n++) tokenData[n] = buf[n];
  	
  	hash = new byte[20];
  	for (int n=0; n<20; n++) hash[n] = buf[buf.length-20+n];
  	
  	if (getExpirationTime()<System.currentTimeMillis()/1000) {
  		status = EXPIRED;
  		return;
  	}
  	
  	if (!Arrays.equals(hash, computeHash())) {
  		status = BAD_HASH;
  		return;
  	}
  		
  	status = VALID;
  }
  

  /**  
 		Create a new token for a given user name and lifetime.
 		@param  secret  the 20 byte secret key encoded as a base64 string
 		@param  sName  the user name to encode into the token
 		@param  iLifetime  the lifetime of the token in minutes
 		@throws NoSuchAlgorithmException  if the SHA1 algorithm is not available in the JDK
  */   	
  public DominoLtpaToken(String secret, String sName, int iLifetime) 
  {
  	this(Base64.decode(secret.getBytes()), sName, iLifetime);
  }

  /**  
 		Create a new token for a given user name and lifetime.
 		@param  secret  the 20 byte secret key
 		@param  sName  the user name to encode into the token
 		@param  iLifetime  the lifetime of the token in minutes
 		@throws NoSuchAlgorithmException  if the SHA1 algorithm is not available in the JDK
  */   	
  public DominoLtpaToken(byte[] secret, String sName, int iLifetime) 
  {
  	key = secret;

	tokenData = new byte[20+sName.getBytes().length];
	setHeader();
	setCreationTime();
	setExpirationTime(iLifetime);
	setName(sName);

	hash = computeHash();
  	
  	status = VALID;
  }
  
  private byte[] computeHash() 
  {
      try {
      	MessageDigest sha1 = MessageDigest.getInstance("SHA1");
    	sha1.update(tokenData);
    	sha1.update(key);
    	return sha1.digest();
      }
      catch (NoSuchAlgorithmException x) {
          throw new RuntimeException(x);
      }
  }
  
  private byte[] getCookieData(HttpServletRequest req)
  {
  	Cookie[] a = req.getCookies();
    if (a==null) return null;
  	for (int n=0; n<a.length; n++)
  		if (a[n].getName().equals(COOKIE_NAME)) 
  			return Base64.decode(a[n].getValue().getBytes());
  	return null;
  }


	private void setHeader()
	{
		for (int n=0; n<4; n++) tokenData[n] = TOKEN_HEADER[n];
	}

	private void setCreationTime()
	{
		byte[] b= Integer.toHexString((int)(System.currentTimeMillis()/1000)).getBytes(); 
		for (int n=0; n<8; n++) tokenData[n+4] = b[n];
	}

	private void setExpirationTime(int iLifetime)
	{
		byte[] b= Integer.toHexString((int)(System.currentTimeMillis()/1000)+iLifetime*60).getBytes(); 
		for (int n=0; n<8; n++) tokenData[n+12] = b[n];
	}

	private void setName(String sName)
	{
		byte[] b = sName.getBytes();
		for (int n=0; n<b.length; n++) tokenData[n+20] = b[n];
	}

	public int getCreationTime()
	{
		return Integer.parseInt(new String(tokenData, 4, 8), 16);
	}

	public int getExpirationTime()
	{
		return Integer.parseInt(new String(tokenData, 12, 8), 16);
	}

	public String getName()
	{
		return new String(tokenData, 20, tokenData.length-20);
	}

	public boolean isValid()
	{
		return status==VALID;
	}

	public int getStatus()
	{
		return status;
	}
	
	public String getToken()
	{
		byte[] b = new byte[tokenData.length+hash.length];
		for (int n=0; n<tokenData.length; n++) b[n] = tokenData[n];
		for (int n=0; n<hash.length; n++) b[b.length-hash.length+n] = hash[n];
		return new String(Base64.encode(b));
	}
	
	public void setSessionCookie(HttpServletRequest req, HttpServletResponse resp, String domain, String path)
	{
        if (path==null) path = "/"; 
        if (domain==null) domain = getDefaultCookieDomain(req); 
        
		Cookie ck = new Cookie(COOKIE_NAME, getToken());
        ck.setDomain(domain);
        ck.setPath(path);
        ck.setMaxAge(-1); // Aways a session cookie (more acceptable to browsers and compatible with Idak implementation).

        resp.addCookie(ck);
	}
	
	
	private static String getDefaultCookieDomain(HttpServletRequest req)
	{
        String[] a = req.getServerName().split("\\.");
        int x = a.length;
        if (a[x-1].length()==2 && x>2) 
            return a[x-3]+"."+a[x-2]+"."+a[x-1];
        if (a[x-1].length()==2 && x>1) 
            return a[x-2]+"."+a[x-1];
        return a[x-1];
	}	

    
    public static void clearCookie(HttpServletRequest request, HttpServletResponse resp, String domain, String path) 
    {
        if (path==null) path = "/"; 
        if (domain==null) domain = getDefaultCookieDomain(request); 
        Cookie cookie = new Cookie(COOKIE_NAME, null);
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
    }
    
}



