package br.model.services;


import br.model.util.ResponseData;
import org.apache.log4j.Logger;
import spark.utils.IOUtils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpRequest {

    private static Logger LOG = Logger.getLogger(HttpRequest.class);

    private static final String EMPTY = "";

    private String endPoint;

    private HttpRequest() {

    }

    private HttpRequest(String endPoint) {
        this.endPoint = endPoint;
    }


    public static HttpRequest prepare(String address, int port) {
        return prepare(address, port, false);
    }

    public static HttpRequest prepare(String address, int port, boolean useHTTPS) {
        StringBuffer buffer = new StringBuffer();
        buffer.append( useHTTPS ? "https" : "http" );
        buffer.append("://");
        buffer.append(address);
        buffer.append(":");
        buffer.append(port);

        return new HttpRequest( buffer.toString() );
    }

    public ResponseData doRequest(String method, String path) {
       return doRequest( method, path, null);
    }

    public ResponseData doRequest(String method, String path, String objectToJson) {
        String address = String.format("%s/%s", this.endPoint, path);
        try {
            URL url = new URL( address );
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            if (objectToJson != null) {
                OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
                osw.write( objectToJson );
                osw.flush();
                osw.close();
            }

            connection.connect();

            String body = IOUtils.toString(connection.getInputStream());

            return new ResponseData(connection.getResponseCode(), body);

        } catch (IOException e) {
            LOG.error("Error trying to access ".concat(address));
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
