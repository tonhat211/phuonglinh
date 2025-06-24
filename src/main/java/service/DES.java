package service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.Security;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import model.Constant;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class DES {
    private SecretKey key;
    private Cipher cipher;
    private IvParameterSpec iv;
    private String algorism;
    private String mode;
    private String padding;


    public DES() {
        super();
        this.algorism = Constant.DES_ID;
        iv=null;
        this.mode = Constant.CBC;
        this.padding=Constant.PKCS5Padding;
        setKey(Constant.DES_KEY);
    }

    public String getMode() {
        return mode;
    }

    public String getPadding() {
        return padding;
    }

    // nhap vao String roi chuyen doi sang SecretKeySpec
    public void setKey(String keyString) {
        SecretKeySpec key = new SecretKeySpec(keyString.getBytes(), this.algorism);
        this.key = key;

    }

    public void setMode(String mode) {
        // TODO Auto-generated method stub
        this.mode=mode;
    }

    public void setPadding(String padding) {
        // TODO Auto-generated method stub
        this.padding=padding;
    }


    public String encrypt(String plaintext) {
        try {
            return encryptBase64(plaintext);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error, can't not encrypt.";
        }
    }

    public String decrypt(String text) {
        // TODO Auto-generated method stub
        try {
            return decryptBase64(text);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "Error, can't not decrypt.";
        }
    }

    public Cipher initCipher(String algorithm) throws Exception {
        String input = algorithm+"/"+this.mode+"/"+this.padding;
        this.cipher = Cipher.getInstance(input);
        System.out.println("input: " + input);
        this.iv = generateIV(); // ecb khong can iv
        return cipher;
    }

//	    ECB: Không cần IV.
//	    CBC, CFB, OFB, CTR: Cần IV.

    //tao iv
    public IvParameterSpec generateIV() {
        byte[] iv = new byte[8]; // DES, 3DES (DESede), Blowfish: 8 byte
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);

    }


    //	    vd "AES/CBC/PKCS5Padding"
    public byte[] encryptToBytes(String data) throws Exception {
        this.cipher = initCipher(this.algorism);
        this.cipher.init(Cipher.ENCRYPT_MODE, key,iv);
        // ma hoa
        byte[] encryptedData = this.cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        // ghi iv vao dau chuoi
        byte[] ivBytes = iv.getIV();
        byte[] result = new byte[ivBytes.length + encryptedData.length];
        System.arraycopy(ivBytes, 0, result, 0, ivBytes.length);
        System.arraycopy(encryptedData, 0, result, ivBytes.length, encryptedData.length);
        return result;
    }

    public String decrypt(byte[] data) throws Exception {
        this.cipher = initCipher(this.algorism);
        int ivSize = 8;
        byte[] ivBytes = Arrays.copyOfRange(data, 0, ivSize);
        byte[] actualEncryptedData = Arrays.copyOfRange(data, ivSize, data.length);
        iv = new IvParameterSpec(ivBytes);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        return new String(cipher.doFinal(actualEncryptedData), StandardCharsets.UTF_8);

    }

    public String encryptBase64(String data) throws Exception {
        return Base64.getEncoder().encodeToString(encryptToBytes(data));
    }

    public String decryptBase64(String data) throws Exception {
        byte[] decodedBytes = Base64.getDecoder().decode(data);
        return decrypt(decodedBytes);
    }


    public static void main(String[] args) throws Exception{

        String text ="2003tonhat@gmail.com";
        LocalDateTime time = LocalDateTime.now();
        text=text+"?time="+time;
        String pwd ="-X*]bAEZ";
        DES al = new DES();
//        al.setKey(pwd);
        String cipher = al.encrypt(text);
        System.out.println("cipher: " + cipher);
        cipher = "jTANrHf/LAv6I884nx1OAx3rdPbAYtYymSKSUynDFcydxR2HSiknCgbohzwp1L6ZiCkEbKEIk8FGT3oVNnZf/Q==";
        text = al.decrypt(cipher);
        System.out.println("text: " + text);

        // test voi file
//	        String sourceFile = "C:\\Users\\TO NHAT\\Desktop\\banner.jpg";
//
//	    	ArrayList<String> als= new ArrayList<>();
//	    	als.add(Constant.DES_ID);
//	    	als.add(Constant.AES_ID);
//	    	als.add(Constant.THREE_DES_ID);
//	    	als.add(Constant.BLOWFISH_ID);
//	    	als.add(Constant.RC4_ID);
//
//	    	ArrayList<String> modes= new ArrayList<>();
//	    	modes.add(Constant.ECB);
//	    	modes.add(Constant.CBC);
//	    	modes.add(Constant.CFB);
//	    	modes.add(Constant.OFB);
//	    	modes.add(Constant.CTR);
//
//
//	    	ArrayList<String> paddings= new ArrayList<>();
//	    	paddings.add(Constant.PKCS5Padding);
//	    	paddings.add(Constant.PKCS7Padding);
//	    	paddings.add(Constant.ZeroBytePadding);
//	    	paddings.add(Constant.NoPadding);
//
//	    	String plaintext="To minh nhat";
//	    	int i=0;
//	    	String[] keys = new String[5];
//	    	keys[0]="x[N&:E,x";
//	    	keys[1]="YT{Ib6FTB{>n;Do)";
//	    	keys[2]="y+gI$lAXWXT[2rFXUY+-wc,+";
//	    	keys[3]="X<eLHmBB";
//	    	keys[4]="-:+yt1cKguzQ{:y>";
//	    	int keyIndex=0;
//	        DoiXungAlgorism algorism =null;
//	        for(String a : als) {
//
//	        	algorism = new DoiXungAlgorism(a);
//	        	System.out.println("Giai thuat: " + a);
////	        	String keyString = algorism.generateKey();
//	        	String keyString = keys[keyIndex];
//	        	algorism.setKey(keyString);
//	        	keyIndex++;
//	        	System.out.println("Key: " + keyString + "(" + keyString.length()+")");
//	        	for(String m : modes) {
//	        		algorism.setMode(m);
//	        		for(String p : paddings) {
//	        			i++;
//	        	        algorism.setPadding(p);
//	        	        String situation = a+"-"+algorism.mode+"-" + algorism.padding;
////	        	        System.out.println(situation);
//	        	        String desFile = "C:\\Users\\TO NHAT\\Desktop\\test\\"+i+"en"+situation+".jpg";
//	        	        String out = "C:\\Users\\TO NHAT\\Desktop\\test\\"+i+"de"+situation+".jpg";
//
////	        	        String desFile = "C:\\Users\\TO NHAT\\Desktop\\test\\en"+situation+".jpg";
////	        	        String out = "C:\\Users\\TO NHAT\\Desktop\\test\\de"+situation+".jpg";
//////
////	        	        boolean en = algorism.encryptFile(sourceFile,desFile);
////	        	        System.out.println("Ma hoa: " + en);
//	        	        boolean de = algorism.decryptFile(desFile,out);
//	        	        System.out.println("Giai ma: " + de);
////	        	        System.out.println("--------------------");
//	        		}
//	        	}
////	        	System.out.println();
////	        	System.out.println("========================");
////	        	System.out.println();
//	        }
//
//
//	        System.out.println();
//	        System.out.println("----------------");
//	        System.out.println();
//
//
//	        DoiXungAlgorism des = new DoiXungAlgorism(Constant.AES_ID);
////	        des.generatekey();
//	        String keyString = "1234567890123456"; // Đây là khóa DES, phải có độ dài 8 byte
//	        des.setKey(keyString);
//	        des.setMode(Constant.CFB);
//	        des.setPadding(Constant.PKCS5Padding);
//	        des.setCode(Constant.EN_CHARS);
////	        des.loadKey(key);
//
//
//	        System.out.println();
//	        System.out.println("----------------");
//	        System.out.println();
//	        String sourceFile1 = "C:\\Users\\TO NHAT\\Desktop\\banner.jpg";
//	        String desFile = "C:\\Users\\TO NHAT\\Desktop\\banner6-en.jpg";
////	        String desFile = "C:\\Users\\TO NHAT\\Desktop\\test\\enAES-CBC-NoPadding.jpg";
//	        String out = "C:\\Users\\TO NHAT\\Desktop\\banner6-de.jpg";
////	        System.out.println(des.encryptFile(sourceFile1, desFile));
////	        System.out.println(des.decryptFile(desFile, out));
//
//

        String filePath = "C:\\Users\\TO NHAT\\Documents\\Private_Key_Không chia sẻ.txt";


    }




}
