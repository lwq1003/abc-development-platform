package tech.abc.platform.common.utils;

import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.exception.EncryptionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Rsa加解密工具类
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Slf4j
public class RsaUtil {


    /**
     * 加密
     */
    public static byte[] encrypt(String data, Key key) {

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(data.getBytes());
        } catch (Exception e) {
            throw new CustomException(EncryptionException.ENCRYPT_FAILED);
        }

    }

    /**
     * 解密
     */
    public static byte[] decrypt(byte[] data, Key key) {

        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new CustomException(EncryptionException.DECRYPT_FAILED);
        }


    }

    /**
     * 通过字符串生成私钥
     */
    public static PrivateKey getPrivateKey(String privateKeyData) {
        PrivateKey privateKey = null;
        try {

            // 将字符串Base64解码
            byte[] decodeKey = Base64.decodeBase64(privateKeyData);
            privateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decodeKey));


        } catch (Exception e) {
            log.error("读取私钥失败", e);
        }
        return privateKey;
    }


    /**
     * 通过字符串生成公钥
     */
    public static PublicKey getPublicKey(String publicKeyData) {
        PublicKey publicKey = null;
        try {
            byte[] decodeKey = Base64.decodeBase64(publicKeyData);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodeKey);
            publicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(keySpec);

        } catch (Exception e) {
            log.error("读取公钥失败", e);
        }
        return publicKey;
    }
}
