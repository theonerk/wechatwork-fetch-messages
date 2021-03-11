package com.nike.wechatwork;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

/**
 * Java RSA 加密工具类 参考： https://blog.csdn.net/qy20115549/article/details/83105736
 */
public class RSAUtil {
	// 用此方法先获取秘钥
	public static String getPrivateKey(String str) throws Exception {

		String MyPrivKeyPEM = System.getenv("PK");

		String privKeyPEMnew = MyPrivKeyPEM.replaceAll("\\n", "").replaceAll(" ", "")
				.replace("-----BEGINPRIVATEKEY-----", "").replace("-----ENDPRIVATEKEY-----", "");
		// System.out.println("privKeyPEMnew:" + privKeyPEMnew);

		byte[] decoded = Base64.getDecoder().decode(privKeyPEMnew);
		RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA")
				.generatePrivate(new PKCS8EncodedKeySpec(decoded));
		// 64位解码加密后的字符串
		byte[] inputByte = Base64.getDecoder().decode(str);
		// RSA解密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, priKey);
		String outStr = new String(cipher.doFinal(inputByte));

		return outStr;
	}
}