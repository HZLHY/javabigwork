import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    public String getMD5(String upwd) throws NoSuchAlgorithmException {
        MessageDigest myMD5 = MessageDigest.getInstance("MD5");
        byte[] MD5bytes = myMD5.digest(upwd.getBytes(StandardCharsets.UTF_8));
        StringBuilder resultBuilder = new StringBuilder();
        String result = new BigInteger(1, MD5bytes).toString(16);
        for (int i = 0; i < 32 - result.length(); i++) {
            resultBuilder.append("0");
            resultBuilder.append(result);
            result = resultBuilder.toString();
        }
        return result;
    }
}
