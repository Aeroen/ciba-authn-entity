package org.tnorimat.oss.ciba.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class BasicAuthHelper
{
    public static String createHeader(String username, String password)
    {
        StringBuffer buf = new StringBuffer(username);
        buf.append(':').append(password);
        try
        {
            return "Basic " + Base64.encodeBytes(buf.toString().getBytes("UTF-8"));
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static String[] parseHeader(String header)
    {
        if (header.length() < 6) return null;
        String type = header.substring(0, 5);
        type = type.toLowerCase();
        if (!type.equalsIgnoreCase("Basic")) return null;
        String val = header.substring(6);
        try {
            val = new String(Base64.decode(val.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int seperatorIndex = val.indexOf(":");
        if(seperatorIndex == -1) return null;
        String user = val.substring(0, seperatorIndex);
        String pw = val.substring(seperatorIndex + 1);
        return new String[]{user,pw};
    }
}
