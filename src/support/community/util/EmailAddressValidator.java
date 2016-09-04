/*
 * Created on 09-Feb-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package support.community.util;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.AddressException;
import java.util.StringTokenizer;

/**
 * Attempts to validate whether an email address is valid.
 * It can spot badly constructed addresses and missing domains. For instance, "bill",
 * "bill@", "@gulesider.no", * "bill@@gulesider.no",
 * "@bill@gulesider.no", "bill;@gulesider.no" would all be deemed invalid. 
 * However, "blah@gulesider.no" would be okay.
 */
public class EmailAddressValidator {

    private static final String DELIMITER = "@";

    public static boolean isValid(String address) throws Exception {

        try {
            InternetAddress internetAddress = new InternetAddress(address);
        }
        catch (AddressException e) {
            return false;
        }
        catch (NullPointerException e) {
            return false;
        }

        // This bit is to exclude local email addresses (i.e.
        // "bill") which we don't want either.
        StringTokenizer st = new StringTokenizer(address, DELIMITER);

        if (st.countTokens() != 2) {
            return false;
        }

        return true;

    }

}
