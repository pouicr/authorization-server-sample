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
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class CardRepository {

    private Map<String,Card> cards = new HashMap<String, Card>();


    public Card read(String num, String expiryDate){
        Card card = cards.get(num);
        if(null != card && card.getExpiryDate().equals(expiryDate)){
            return card;
        }
        return null;
    }

    public void update(Card card){
        cards.put(card.getCardNumber(),card);
    }

    public Map<String, Card> getCards() {
        return cards;
    }

    public void setCards(Map<String, Card> cards) {
        this.cards = cards;
    }

}
