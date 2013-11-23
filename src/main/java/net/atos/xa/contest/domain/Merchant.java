/*
****************************************************
* Atos Worldline -- Global Platforms and Solutions

* Atos Worldline is an Atos company
* All rights reserved
*
*****************************************************
*/
package net.atos.xa.contest.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Merchant {

    @Id
    private Integer merchantId;

    private String countryCode;

    public Merchant() {
    }

    public Merchant(Integer merchantId, String countryCode) {
        this.merchantId = merchantId;
        this.countryCode = countryCode;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
