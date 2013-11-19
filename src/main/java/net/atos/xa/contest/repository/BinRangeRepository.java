/*
****************************************************
* Atos Worldline -- Global Platforms and Solutions

* Atos Worldline is an Atos company
* All rights reserved
*
*****************************************************
*/
package net.atos.xa.contest.repository;

import net.atos.xa.contest.domain.BinRange;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class BinRangeRepository {

    private List<BinRange> binRanges = new ArrayList<BinRange>();

    public void put(BinRange binRange){
        this.binRanges.add(binRange);
    }

    public List<BinRange> getBinRanges() {
        return binRanges;
    }

    public void setBinRanges(List<BinRange> binRanges) {
        this.binRanges = binRanges;
    }

    public BinRange findBinRange(Integer bin){
        for (BinRange binRange : binRanges) {
            if(binRange.getFromBin() <= bin && bin <= binRange.getToBin()){
                return binRange;
            }
        }
        return null;
    }

    public boolean isActive(Integer bin){
        BinRange binRange = findBinRange(bin);
        if(null == binRange){
            return false;
        }
        return "ACTIVE".equals(binRange.getStatus());
    }
}
