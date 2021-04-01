import crypto.GaloisProvider;
import crypto.algorithm.ope.fope.FOPECipher;
import crypto.algorithm.ope.gacd.GACDAlgorithmParameterSpec;
import crypto.algorithm.ope.gacd.GACDCipher;

import javax.crypto.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class Galois {
    public static void main(String[] args) {
        GaloisProvider.add();

        String algo = GACDCipher.ALGORITHM_NAME;
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(algo);
            keyGenerator.init(new GACDAlgorithmParameterSpec());

            SecretKey key = keyGenerator.generateKey();


            System.out.println("Key-Size: " + key.getEncoded().length * 8);
            System.out.println("Key: " + Arrays.toString(key.getEncoded()));
            System.out.println("Key: " + new String(Base64.getEncoder().encode(key.getEncoded())));

            Cipher c = Cipher.getInstance(algo);

            long x = 240;

            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = c.doFinal(ByteBuffer.allocate(Long.BYTES).putLong(x).array());

            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypted = c.doFinal(encrypted);

            System.out.println("Value: " + x);
            System.out.println("Encrypted: " + new BigInteger(encrypted));
            System.out.println("Decrypted: " + ByteBuffer.wrap(decrypted).getLong());
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

    }
}

