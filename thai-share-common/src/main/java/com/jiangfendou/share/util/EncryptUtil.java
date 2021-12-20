package com.jiangfendou.share.util;

import com.jiangfendou.share.common.ApiError;
import com.jiangfendou.share.common.BusinessException;
import com.jiangfendou.share.type.ErrorCode;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

/**
 * EncryptUtil.
 * @author jiangmh
 */
@Slf4j
public class EncryptUtil {

    private static final String PASSWORD_CRYPT_KEY = "cindaportal";
    private final static String DES = "DES";

     /**
    * 加密
    * @param src 数据源
    * @param key 密钥，长度必须是8的倍数
    * @return   返回加密后的数据
    * @throws Exception
    */
    public static byte[] encrypt(byte[] src, byte[] key)throws Exception {
        //DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密匙工厂，然后用它把DESKeySpec转换成
        // 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        // 现在，获取数据并加密
        // 正式执行加密操作
        return cipher.doFinal(src);
     }

    /**
    * 解密
    * @param src 数据源
    * @param key 密钥，长度必须是8的倍数
    * @return     返回解密后的原始数据
    * @throws Exception
    */
    public static byte[] decrypt(byte[] src, byte[] key)throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建一个DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
        // 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        // 现在，获取数据并解密
        // 正式执行解密操作
        return cipher.doFinal(src);
    }

    public static String encodeUTF8StringBase64(String str) {
        String encode = null;
        try {
            encode =Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
        } catch(Exception exception) {
            log.info("不支持的编码格式 =>", exception);
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, new ApiError(ErrorCode.SYSTEM_ERROR.getCode(),
                ErrorCode.SYSTEM_ERROR.getMessage()));
        }
        return encode;
    }

    public static String decodeUTF8StringBase64(String str) {
        String decode = null;
        byte[] bytes = Base64.getDecoder().decode(str);
        try {
            decode = new String(bytes, "utf-8");
        } catch (Exception exception) {
            log.info("不支持的编码格式 =>", exception);
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, new ApiError(ErrorCode.SYSTEM_ERROR.getCode(),
                ErrorCode.SYSTEM_ERROR.getMessage()));
        }
        return decode;
    }
}
