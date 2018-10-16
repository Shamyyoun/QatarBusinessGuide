package json;

import android.os.AsyncTask;

import org.apache.http.NameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class JsonReader {
    private String url;

    public JsonReader(String url) {
        this.url = url;
    }

    /**
     * method used to send async get request
     */
    public void sendAsyncRequest() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                sendRequest();
                return null;
            }
        }.execute();
    }

    /**
     * method used to send async get request with parameters
     */
    public void sendAsyncRequest(final List<NameValuePair> parameters) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                sendRequest(parameters);
                return null;
            }
        }.execute();
    }

    /**
     * method used to send get request
     */
    public String sendRequest() {
        return sendRequest(null);
    }

    /**
     * method used to send get request with parameters
     */
    public String sendRequest(List<NameValuePair> parameters) {
        String response = null;
        try {
            // check if found parameters
            if (parameters != null) {
                // concatenate parameters to url
                for (int i = 0; i < parameters.size(); i++) {
                    if (!this.url.contains("?")) {
                        this.url += "?";
                    }

                    this.url += (parameters.get(i).getName() + "=" + parameters.get(i).getValue() + "&");
                }
            }

            // create HTTPURLConnection
            URL url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            // set connection properties
            connection.setReadTimeout(5 * 60 * 1000);
            connection.setConnectTimeout(5 * 60 * 1000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            // then connect
            connection.connect();

            // get response from connection
            InputStream is = connection.getInputStream();
            response = convertToString(is);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    /**
     * method, used to convert stream to string
     */
    private String convertToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
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
