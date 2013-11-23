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
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository
public abstract class BinRangeRepository extends AbstractEntityRepository<BinRange, String> {


    @Query(value = "SELECT br FROM BinRange br WHERE br.fromBin <= ?1 and br.toBin >= ?1")
    public abstract QueryResult<BinRange> findBinRange(Integer bin);

    public boolean isActive(Integer bin){
        QueryResult<BinRange> queryResult = findBinRange(bin);
        if (queryResult.count() == 0){
            return false;
        }else{
            BinRange br = queryResult.getSingleResult();
            return "ACTIVE".equals(br.getStatus());
        }
    }

}
