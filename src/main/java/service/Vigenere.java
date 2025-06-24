package service;

import model.Constant;

public class Vigenere {
    private static String code  =Constant.EN_CHARS;

    public Vigenere() {
        super();
        this.code = Constant.EN_CHARS;
    }

    public static String encrypt(String key,String plaintext) {
        int[] keynum = new int[key.length()];
        for(int i=0;i<key.length();i++) {
            keynum[i] = code.indexOf(key.charAt(i));
        }
        int[] steps = new int[plaintext.length()];
        int j=0;
        for(int i=0;i<steps.length;i++) {
            if(j>=keynum.length) {
                j=0;
            }
            steps[i]=keynum[j];
            j++;
        }
        StringBuilder re = new StringBuilder();
        int index;
        int stepsIndex=0;
        for(int i=0;i<plaintext.length();i++) {
            if(plaintext.charAt(i)==' ') {
                re.append(' ');
            } else if(plaintext.charAt(i)=='\n') {
                re.append('\n');
            }
            else {
                index = code.indexOf(plaintext.charAt(i)) + steps[stepsIndex];
                stepsIndex++;
                if(index>=code.length()) index=index-code.length();
                re.append(code.charAt(index));

            }

        }
        return re.toString();
    }


    public static String decrypt(String key, String cipherText) {
        int[] keynum = new int[key.length()];
        for(int i=0;i<key.length();i++) {
            keynum[i] = code.indexOf(key.charAt(i));
        }
        int[] steps = new int[cipherText.length()];
        int j=0;
        for(int i=0;i<steps.length;i++) {
            if(j>=keynum.length) {
                j=0;
            }
            steps[i]=keynum[j];
            j++;
        }
        StringBuilder re = new StringBuilder();
        int index;
        int stepsIndex=0;
        for(int i=0;i<cipherText.length();i++) {
            if(cipherText.charAt(i)==' ') {
                re.append(' ');
            } else if(cipherText.charAt(i)=='\n') {
                re.append('\n');
            } else {
                index = code.indexOf(cipherText.charAt(i)) - steps[stepsIndex];
                stepsIndex++;
                if(index<0) index=index+code.length();
                re.append(code.charAt(index));

            }

        }
        return re.toString();
    }

    public static void main(String[] args) {
        String test = "";
        test = Vigenere.decrypt("2003tonhat@gmail.com","I%#^ +[#} [%c8 =<{A");
        System.out.println("test: " + test);
    }

}
