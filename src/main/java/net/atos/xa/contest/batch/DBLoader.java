/*
****************************************************
* Atos Worldline -- Global Platforms and Solutions

* Atos Worldline is an Atos company
* All rights reserved
*
*****************************************************
*/
package net.atos.xa.contest.batch;

import au.com.bytecode.opencsv.CSVReader;
import net.atos.xa.contest.repository.BinRangeRepository;
import net.atos.xa.contest.repository.BlacklistRepository;
import net.atos.xa.contest.repository.CardRepository;
import net.atos.xa.contest.repository.MerchantRepository;
import net.atos.xa.contest.domain.BinRange;
import net.atos.xa.contest.domain.Card;
import net.atos.xa.contest.domain.Merchant;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.io.FileReader;
import java.io.IOException;

@Startup
@Singleton
public class DBLoader {

    @Inject
    CardRepository cardRepository;

    @Inject
    MerchantRepository merchantRepository;

    @Inject
    BinRangeRepository binRangeRepository;

    @Inject
    BlacklistRepository blacklist;


    @PostConstruct
    private void loadAll(){
        try {
            loadCards(Thread.currentThread().getContextClassLoader().getResource("cards.csv").getPath());
            loadMerchants(Thread.currentThread().getContextClassLoader().getResource("merchants.csv").getPath());
            loadBinRange(Thread.currentThread().getContextClassLoader().getResource("bin-ranges.csv").getPath());
            loadBlacklist(Thread.currentThread().getContextClassLoader().getResource("blacklist.csv").getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadCards(String fileName) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(fileName));
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            Card card = new Card(nextLine[0],nextLine[1],Integer.valueOf(nextLine[2]));
            cardRepository.update(card);
        }
        reader.close();
    }

    private void loadMerchants(String fileName) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(fileName));
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            Merchant merchant= new Merchant(Integer.valueOf(nextLine[0]),nextLine[1]);
            merchantRepository.update(merchant);
        }
        reader.close();
    }

    private void loadBinRange(String fileName) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(fileName));
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            BinRange binRange = new BinRange(Integer.valueOf(nextLine[0]),Integer.valueOf(nextLine[1]),nextLine[2]);
            binRangeRepository.put(binRange);
        }
        reader.close();
    }

    private void loadBlacklist(String arg) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(arg));
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            blacklist.addToBlackList(nextLine[0],nextLine[1]);
        }
        reader.close();
    }
}
