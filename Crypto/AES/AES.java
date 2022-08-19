package aes;

import java.io.*;
import java.security.*;
import javax.crypto.*;

/*    modified example from "java volume 2 advanced features" p. 603
 *
 *    java AES.java -genkey keyFile					// Generate a key.
 *    java AES.java -encrypt plainFile encryptedFile keyFile		// Encrypt a file.
 *    java AES.java -decrypt encryptedFile decryptedFile keyFile	// Decrypt a file. */


public class AESTest {

	public static void main(String[] args) throws IOException, GeneralSecurityException, ClassNotFoundException {

		if (args[0].equals("-genkey")) {
			KeyGenerator keygen = KeyGenerator.getInstance("AES");
			var random = new SecureRandom();
			keygen.init(random);
			SecretKey key = keygen.generateKey();
			try (var out = new ObjectOutputStream(new FileOutputStream(args[1]))) {
				out.writeObject(key);
				System.out.println(key.getAlgorithm());
				System.out.println(key.getFormat());
				System.out.println(key.hashCode());
				System.out.println(key.getEncoded());
			}
		}
		else {
			int mode;
			if (args[0].equals("-encrypt")) mode = Cipher.ENCRYPT_MODE;
			else mode = Cipher.DECRYPT_MODE;

			try (var keyIn = new ObjectInputStream(new FileInputStream(args[3]));
			     var in = new FileInputStream(args[1]);
			     var out = new FileOutputStream(args[2]))
			{
				var key = (Key) keyIn.readObject();
				Cipher cipher = Cipher.getInstance("AES");
				cipher.init(mode, key);
				Util.crypt(in, out, cipher);
			}
		}
	}
}


