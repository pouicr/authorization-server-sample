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

public class AuthorizationRequest {

    private String cardNumber;
    private String expiryDate;
    private Integer amount;
    private Integer merchantId;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date requestDatetime;
    private String integrityString;

    public AuthorizationRequest() {
    }

    public AuthorizationRequest(String cardNumber, String expiryDate, Integer amount, Integer merchantId, Date requestDatetime, String integrityString) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.amount = amount;
        this.merchantId = merchantId;
        this.requestDatetime = requestDatetime;
        this.integrityString = integrityString;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Date getRequestDatetime() {
        return requestDatetime;
    }

    public void setRequestDatetime(Date requestDatetime) {
        this.requestDatetime = requestDatetime;
    }

    public String getIntegrityString() {
        return integrityString;
    }

    public void setIntegrityString(String integrityString) {
        this.integrityString = integrityString;
    }

    @Override
    public String toString() {
        return "AuthorizationRequest{" +
                "cardNumber='" + cardNumber + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", amount=" + amount +
                ", merchantId=" + merchantId +
                ", requestDatetime='" + requestDatetime + '\'' +
                ", integrityString='" + integrityString + '\'' +
                '}';
    }
}
