import java.io.*;
import java.security.*;
import javax.crypto.*;

/*    modified example from "java volume 2 advanced features" p. 610
 *    Usage is so easy and awesome.
 *
 *    java RSA.java -genkey publicKey privateKey					// Generate a key.
 *    java RSA.java -encrypt plainFile encryptedFile publicKey		// Encrypt a file.
 *    java RSA.java -decrypt encryptedFile decryptedFile privateKey	// Decrypt a file. */

public class RSATest {
	private static final int KEYSIZE = 512;

	public static void crypt(InputStream in, OutputStream out, Cipher cipher) throws IOException, GeneralSecurityException {

		int blockSize = cipher.getBlockSize();
		int outputSize = cipher.getOutputSize(blockSize);
		var inBytes = new byte[blockSize];
		var outBytes = new byte[outputSize];

		int inLength = 0;
		var done = false;
		while (!done) {
			inLength = in.read(inBytes);
			if (inLength == blockSize) {
				int outLength = cipher.update(inBytes, 0, blockSize, outBytes);
				out.write(outBytes, 0, outLength);
			}
			else done = true;
		}
		if (inLength > 0) outBytes = cipher.doFinal(inBytes, 0, inLength);
		else outBytes = cipher.doFinal();
		out.write(outBytes);
	}

	public static void main(String[] args) throws IOException, GeneralSecurityException, ClassNotFoundException {

		if (args[0].equals("-genkey")) {
			KeyPairGenerator pairgen = KeyPairGenerator.getInstance("RSA");
			var random = new SecureRandom();
			pairgen.initialize(KEYSIZE, random);
			KeyPair keyPair = pairgen.generateKeyPair();
			try (var out = new ObjectOutputStream(new FileOutputStream(args[1]))) {
				out.writeObject(keyPair.getPublic());
			}
			try (var out = new ObjectOutputStream(new FileOutputStream(args[2]))) {
				out.writeObject(keyPair.getPrivate());
			}
		}
		else if (args[0].equals("-encrypt")) {
			KeyGenerator keygen = KeyGenerator.getInstance("AES");
			var random = new SecureRandom();
			keygen.init(random);
			SecretKey key = keygen.generateKey();

			// Wrap with RSA public key
			try (var keyIn = new ObjectInputStream(new FileInputStream(args[3]));
                             var out = new DataOutputStream(new FileOutputStream(args[2]));
                             var in = new FileInputStream(args[1])) {
				var publicKey = (Key) keyIn.readObject();
				Cipher cipher = Cipher.getInstance("RSA");
				cipher.init(Cipher.WRAP_MODE, publicKey);
				byte[] wrappedKey = cipher.wrap(key);
				out.writeInt(wrappedKey.length);
				out.write(wrappedKey);

				cipher = Cipher.getInstance("AES");
				cipher.init(Cipher.ENCRYPT_MODE, key);
				crypt(in, out, cipher);
			}
		}
		else {
			try (var in = new DataInputStream(new FileInputStream(args[1]));
			     var keyIn = new ObjectInputStream(new FileInputStream(args[3]));
			     var out = new FileOutputStream(args[2]))
			{
				int length = in.readInt();
				var wrappedKey = new byte[length];
				in.read(wrappedKey, 0, length);

				// Unwrap with RSA private key
				var privateKey = (Key) keyIn.readObject();

				Cipher cipher = Cipher.getInstance("RSA");
				cipher.init(Cipher.UNWRAP_MODE, privateKey);
				Key key = cipher.unwrap(wrappedKey, "AES", Cipher.SECRET_KEY);

				cipher = Cipher.getInstance("AES");
				cipher.init(Cipher.DECRYPT_MODE, key);

				crypt(in, out, cipher);
			}
		}
	}
}

