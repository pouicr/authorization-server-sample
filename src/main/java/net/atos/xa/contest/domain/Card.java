/*
****************************************************
* Atos Worldline -- Global Platforms and Solutions

* Atos Worldline is an Atos company
* All rights reserved
*
*****************************************************
*/
package net.atos.xa.contest.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Card {

    @Id
    private String cardNumber;
    private String expiryDate;
    private Integer balance;


    public Card(String cardNumber, String expiryDate, Integer balance) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.balance = balance;
    }

    public Card() {
        // no-op
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (!cardNumber.equals(card.cardNumber)) return false;
        if (!expiryDate.equals(card.expiryDate)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cardNumber.hashCode();
        result = 31 * result + expiryDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardNumber='" + cardNumber + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", balance=" + balance +
                '}';
    }
}
