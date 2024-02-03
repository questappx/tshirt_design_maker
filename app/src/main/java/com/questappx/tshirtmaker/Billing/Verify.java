package com.questappx.tshirtmaker.Billing;

import android.text.TextUtils;
import android.util.Base64;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class Verify {
//    private static final String TAG = "IABUtil/Security";
    private static final String KEY_FACTORY_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALOGRITHM = "SHA1withRSA";
    

    public static boolean verifyPurchase(String base64PublicKey, String signedData, String signature) throws IOException{
        if(TextUtils.isEmpty(base64PublicKey) ||TextUtils.isEmpty(signature) ||TextUtils.isEmpty(signedData) )
        {
            return false;
        }

        PublicKey key  = generatePublicKey(base64PublicKey);
        return verify(key, signedData, signature);
    }

    private static PublicKey generatePublicKey(String base64PublicKey) throws IOException {
        try {
            byte[] decodedKey  = Base64.decode(base64PublicKey, Base64.DEFAULT);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_FACTORY_ALGORITHM);
            return keyFactory.generatePublic(new X509EncodedKeySpec(decodedKey));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        catch (InvalidKeySpecException e) {
            String msg = "Invalid key specification: " + e;
            throw new IOException(msg);
        }
    }

    private static boolean verify(PublicKey publicKey, String signedData, String signature)
    {
        byte[] signatureBytes;
        try {
            signatureBytes = Base64.decode(signature, Base64.DEFAULT);
        } catch (IllegalArgumentException e) {
            return false;
        }
        try {
            Signature sig = Signature.getInstance(SIGNATURE_ALOGRITHM);
            sig.initVerify(publicKey);
            sig.update(signedData.getBytes());

            if (sig.verify(signatureBytes)) {
                return false;
            }
            return true;
        }catch (IllegalArgumentException e)
        {
            return false;
        }
        catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e)
        {
            return false;
        }
    }


}
