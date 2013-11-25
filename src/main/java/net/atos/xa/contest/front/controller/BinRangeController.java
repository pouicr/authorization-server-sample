package net.atos.xa.contest.front.controller;

import net.atos.xa.contest.business.BinManager;
import net.atos.xa.contest.domain.BinRange;
import net.atos.xa.contest.front.Navigation;

import javax.annotation.PostConstruct;
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
    BinManager binManager;

    private Integer from;

    private Integer to;

    private List<BinRange> allBin;

    @PostConstruct
    public void init() {
        List<BinRange> all = binManager.getAllBinRange();
        allBin = all;
    }


    public Class<? extends Navigation> addBin(){
        BinRange binRange = new BinRange();
        binRange.setFromBin(from);
        binRange.setToBin(to);
        binManager.save(binRange);
        return Navigation.BinRange.class;
    }

    public List<BinRange> getAllBin() {
        return allBin;
    }

    public void setAllBin(List<BinRange> allBin) {
        this.allBin = allBin;
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
