package org.entirej.ejinvoice.forms.login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHashGen
{
    private static final String SALT = "EJ$PWD#EJEJ12@$@4&#%^$*";

    public static String toHash(String pwd)
    {

        MessageDigest md;
        try
        {
            md = MessageDigest.getInstance("SHA-256");
            pwd = pwd + SALT;
            md.update(pwd.getBytes());

            byte byteData[] = md.digest();

            // convert the byte to hex format
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < byteData.length; i++)
            {
                String hex = Integer.toHexString(0xff & byteData[i]);
                if (hex.length() == 1)
                {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return pwd;
    }

}
