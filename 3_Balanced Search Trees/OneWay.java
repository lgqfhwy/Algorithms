// Compute the SHA1 of the command line argument.
import java.security.MessageDigest;
public final class OneWay {
    public static void main(String[] args) {
        //String password = args[0];
        String password = "jack";
        // get SHA1 algorithm object and compute SHA1 of password.
        MessageDigest sha1;
        byte[] bytes;
        try {
            sha1 = MessageDigest.getInstance("SHA1");
            bytes = sha1.digest(password.getBytes("ISO-8859-1"));
        }
        catch (java.security.NoSuchAlgorithmException e) {
            StdOut.println(e);
            return;
        }
        catch (java.io.UnsupportedEncodingException e) {
            StdOut.println(e);
            return;
        }

        // convert bytes to hex, careful to handle leading 0s and 2s complement
        String hex = "0123456789abcdef";
        for (int i = 0; i < bytes.length; i++) {
            if (i % 4 == 0)    StdOut.print(" ");
            StdOut.print(hex.charAt((bytes[i] & 0xF0) >> 4));
            StdOut.print(hex.charAt((bytes[i] & 0x0F) >> 0));
        }
        StdOut.println();
    }
}