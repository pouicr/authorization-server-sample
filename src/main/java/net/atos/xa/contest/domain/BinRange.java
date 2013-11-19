/*
****************************************************
* Atos Worldline -- Global Platforms and Solutions

* Atos Worldline is an Atos company
* All rights reserved
*
*****************************************************
*/
package net.atos.xa.contest.domain;

public class BinRange {

    private Integer fromBin;
    private Integer toBin;
    private String status;

    public BinRange(Integer fromBin, Integer toBin, String status) {
        this.fromBin = fromBin;
        this.toBin = toBin;
        this.status = status;
    }

    public Integer getFromBin() {
        return fromBin;
    }

    public void setFromBin(Integer fromBin) {
        this.fromBin = fromBin;
    }

    public Integer getToBin() {
        return toBin;
    }

    public void setToBin(Integer toBin) {
        this.toBin = toBin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
