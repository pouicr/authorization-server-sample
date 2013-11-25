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
import net.atos.xa.contest.domain.BinRange;
import net.atos.xa.contest.domain.Card;
import net.atos.xa.contest.domain.Merchant;
import net.atos.xa.contest.repository.BinRangeRepository;
import net.atos.xa.contest.repository.BlacklistRepository;
import net.atos.xa.contest.repository.CardRepository;
import net.atos.xa.contest.repository.MerchantRepository;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
    private void loadAll() {
        try {
            if (isEmpty()) {
                loadCards(Thread.currentThread().getContextClassLoader().getResourceAsStream("cards.csv"));
                loadMerchants(Thread.currentThread().getContextClassLoader().getResourceAsStream("merchants.csv"));
                loadBinRange(Thread.currentThread().getContextClassLoader().getResourceAsStream("bin-ranges.csv"));
                loadBlacklist(Thread.currentThread().getContextClassLoader().getResourceAsStream("blacklist.csv"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isEmpty() {
        if (cardRepository.findAll().isEmpty()) {
            return true;
        }
        return false;
    }

    private void loadCards(InputStream fileName) throws IOException {
        CSVReader reader = new CSVReader(new InputStreamReader(fileName));
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            Card card = new Card(nextLine[0], nextLine[1], Integer.valueOf(nextLine[2]));
            cardRepository.save(card);
        }
        reader.close();
    }

    private void loadMerchants(InputStream fileName) throws IOException {
        CSVReader reader = new CSVReader(new InputStreamReader(fileName));
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            Merchant merchant = new Merchant(Integer.valueOf(nextLine[0]), nextLine[1]);
            merchantRepository.save(merchant);
        }
        reader.close();
    }

    private void loadBinRange(InputStream fileName) throws IOException {
        CSVReader reader = new CSVReader(new InputStreamReader(fileName));
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            BinRange binRange = new BinRange(Integer.valueOf(nextLine[0]), Integer.valueOf(nextLine[1]), nextLine[2]);
            binRangeRepository.save(binRange);
        }
        reader.close();
    }

    private void loadBlacklist(InputStream arg) throws IOException {
        CSVReader reader = new CSVReader(new InputStreamReader(arg));
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            blacklist.addToBlackList(nextLine[0], nextLine[1]);
        }
        reader.close();
    }
}
