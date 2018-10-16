package utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RawUtil {

    /**
     * method, used to read file by name and return content in html format
     */
    public static String readInHTMLFormat(Context context, String name) {
        int id = context.getResources().getIdentifier("raw/" + name, "raw", context.getPackageName());
        return readInHTMLFormat(context, id);
    }

    /**
     * method, used to read file by id and return content in html format
     */
    public static String readInHTMLFormat(Context context, int rawResourceId) {
        InputStream is = context.getResources().openRawResource(rawResourceId);

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            int i = 0;
            while ((line = reader.readLine()) != null) {
                if (i != 0) {
                    sb.append("<br>");
                }
                sb.append(line + "\n");

                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
