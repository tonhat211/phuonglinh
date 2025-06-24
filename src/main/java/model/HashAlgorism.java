package model;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.Signature;

public class HashAlgorism {
	public String algorism;
	
	public HashAlgorism() {
		super();
		this.algorism = "MD5";
	}

	
	// goi encrypt de thuc hien hash
	public String encrypt(String plaintext) {
		// TODO Auto-generated method stub
		try {
			return hash(plaintext);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public String hash(String mes) throws Exception {
        MessageDigest md = MessageDigest.getInstance(this.algorism);
        byte[] data = mes.getBytes();
        byte[] digest = md.digest(data);
        BigInteger bi = new BigInteger(1, digest);
        return bi.toString(16);
    }

    public String hashFile(String src) throws Exception {
        MessageDigest md = MessageDigest.getInstance(this.algorism);
        File f = new File(src);
        if(!f.exists()) return null;
        DigestInputStream dis = new DigestInputStream(new BufferedInputStream(new FileInputStream(f)),md);
        byte[] buffer = new byte[1024];
        int read;
        do {
            read=dis.read(buffer);
        } while (read!=-1);
        byte[] digest = dis.getMessageDigest().digest();
        dis.close();
        BigInteger bi = new BigInteger(1, digest);
        return bi.toString(16);
    }

    public static void main(String[] args) throws Exception {
        // sau khi co ket qua md5 thi co the dung tool online de test
        // neu result giong nhau thi la dung
        HashAlgorism md5 = new HashAlgorism();
        long t=System.currentTimeMillis();
        System.out.println(md5.hash("1234"));
        System.out.println(System.currentTimeMillis()-t);
    }
}
