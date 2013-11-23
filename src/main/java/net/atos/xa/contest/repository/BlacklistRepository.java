/*
****************************************************
* Atos Worldline -- Global Platforms and Solutions

* Atos Worldline is an Atos company
* All rights reserved
*
*****************************************************
*/
package net.atos.xa.contest.repository;

import net.atos.xa.contest.domain.Card;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class BlacklistRepository {

    @Inject
    CardRepository cardRepository;

    private List<Card> blackList = new ArrayList<Card>();

    public void addToBlackList(Card blackListedCard) {
        this.blackList.add(blackListedCard);
    }

    public void addToBlackList(String cardNumber, String expiryDate) {
        Card aCard = cardRepository.findByCardNumberAndExpiryDate(cardNumber,expiryDate);
        this.blackList.add(aCard);
    }

    public boolean isBlackListed(Card blackListedCard) {
        return this.blackList.contains(blackListedCard);
    }

    public boolean isBlackListed(String cardNumber, String expiryDate) {
        Card aCard = cardRepository.findByCardNumberAndExpiryDate(cardNumber,expiryDate);
        return this.blackList.contains(aCard);
    }

    public List<Card> getBlackList() {
        return blackList;
    }

    public void setBlackList(List<Card> blackList) {
        this.blackList = blackList;
    }
}
