package net.atos.xa.contest.business;

import net.atos.xa.contest.domain.BinRange;
import net.atos.xa.contest.repository.BinRangeRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 */
@Stateless
public class BinManager {

    @Inject
    BinRangeRepository binRangeRepository;

    public List<BinRange> getAllBinRange(){
        return binRangeRepository.findAll();
    }

    public void save(BinRange binRange) {
        binRangeRepository.save(binRange);
    }
}
