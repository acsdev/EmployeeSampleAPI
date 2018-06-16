package br.model.util;

import java.io.Serializable;

public class ResponseData implements Serializable {

    private final int status;

    private final String body;

    public ResponseData(int status, String body) {
        this.status = status;
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public int getStatus() {
        return status;
    }
}
