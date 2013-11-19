/*
****************************************************
* Atos Worldline -- Global Platforms and Solutions

* Atos Worldline is an Atos company
* All rights reserved
*
*****************************************************
*/
package net.atos.xa.contest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class AuthorizationResponse {

    private String responseCode;
    private Integer authorizationNumber;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date responseDatetime;

    public AuthorizationResponse() {
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public Integer getAuthorizationNumber() {
        return authorizationNumber;
    }

    public void setAuthorizationNumber(Integer authorizationNumber) {
        this.authorizationNumber = authorizationNumber;
    }

    public Date getResponseDatetime() {
        return responseDatetime;
    }

    public void setResponseDatetime(Date responseDatetime) {
        this.responseDatetime = responseDatetime;
    }

    @Override
    public String toString() {
        return "AuthorizationResponse{" +
                "responseCode='" + responseCode + '\'' +
                ", authorizationNumber='" + authorizationNumber + '\'' +
                ", responseDatetime=" + responseDatetime +
                '}';
    }
}
