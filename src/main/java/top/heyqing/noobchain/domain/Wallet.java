package top.heyqing.noobchain.domain;

import java.security.*;
import java.security.spec.ECGenParameterSpec;

/**
 * ClassName:Wallet
 * Package:top.heyqing.noobchain.domain
 * Description:
 *
 * @Date:2024/11/17
 * @Author:Heyqing
 */
public class Wallet {
    /**
     * 私钥 - 私钥用于签署我们的交易
     */
    public PrivateKey privateKey;
    /**
     * 公钥 - 公钥将充当我们的地址。可以与他人共享此公钥以接收付款。
     */
    public PublicKey publicKey;

    public Wallet() {
        generateKeyPair();
    }

    public void generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
            // Initialize the key generator and generate a KeyPair
            keyGen.initialize(ecSpec, random);   //256 bytes provides an acceptable security level
            KeyPair keyPair = keyGen.generateKeyPair();
            // Set the public and private keys from the keyPair
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
