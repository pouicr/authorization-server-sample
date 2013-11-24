package net.atos.xa.contest.front.controller;

import net.atos.xa.contest.domain.BinRange;
import net.atos.xa.contest.domain.Card;
import net.atos.xa.contest.front.Navigation;
import net.atos.xa.contest.repository.BinRangeRepository;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 */
@Named("binRange")
@ViewScoped
public class BinRangeController implements Serializable{

    @Inject
    BinRangeRepository binRangeRepository;

    private Integer from;

    private Integer to;

    public List<BinRange> getAllBinRange(){
        List<BinRange> all = binRangeRepository.findAll();
        System.out.println("===== Bin range =====");
        for (BinRange bin : all) {
            System.out.println("########");
            System.out.println(bin);
        }
        return all;
    }

    public Class< ? extends Navigation> addBin(){
        BinRange binRange = new BinRange();
        binRange.setFromBin(from);
        binRange.setToBin(to);
        binRangeRepository.saveAndFlush(binRange);
        System.out.println("===== save =====");
        return Navigation.BinRange.class;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }
}
