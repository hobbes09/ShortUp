package com.shortup.models.pojos;

/**
 * Created by sourin on 17/11/15.
 */
public class ResponsePojo {

    private int status_code;
    private String status_txt;
    private DataPojo data;

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getStatus_txt() {
        return status_txt;
    }

    public void setStatus_txt(String status_txt) {
        this.status_txt = status_txt;
    }

    public DataPojo getData() {
        return data;
    }

    public void setData(DataPojo data) {
        this.data = data;
    }

    public ResponsePojo(int status_code, String status_txt, DataPojo data) {
        this.status_code = status_code;
        this.status_txt = status_txt;
        this.data = data;
    }
}
