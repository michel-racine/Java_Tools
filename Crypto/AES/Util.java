package aes;

import java.io.*;
import java.security.*;
import javax.crypto.*;

public class Util {
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
}
