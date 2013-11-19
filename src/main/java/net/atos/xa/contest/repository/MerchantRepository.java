/*
****************************************************
* Atos Worldline -- Global Platforms and Solutions

* Atos Worldline is an Atos company
* All rights reserved
*
*****************************************************
*/
package net.atos.xa.contest.repository;

import net.atos.xa.contest.domain.Merchant;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class MerchantRepository {

    private Map<Integer,Merchant> merchants = new HashMap<Integer, Merchant>();


    public Merchant read(Integer id ){
        return merchants.get(id);
    }

    public void update(Merchant merchant){
        merchants.put(merchant.getMerchantId(), merchant);
    }

    public Map<Integer, Merchant> getMerchants() {
        return merchants;
    }

    public void setMerchants(Map<Integer, Merchant> merchants) {
        this.merchants = merchants;
    }
}
