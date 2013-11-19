/*
****************************************************
* Atos Worldline -- Global Platforms and Solutions

* Atos Worldline is an Atos company
* All rights reserved
*
*****************************************************
*/
package net.atos.xa.contest.business;

import net.atos.xa.contest.domain.Card;
import net.atos.xa.contest.domain.Merchant;
import net.atos.xa.contest.dto.AuthorizationRequest;
import net.atos.xa.contest.dto.AuthorizationResponse;
import net.atos.xa.contest.repository.*;

import javax.inject.Inject;
import java.util.*;

public class AuthorizationManager {

    public static final String ALL_CHECK_VALID = "000";
    public static final String TECHNICAL_ERROR = "099";
    public static final String BLACKLIST_ERROR = "001";
    public static final String CARD_BIN_ERROR = "002";
    public static final String CARD_VALID_ERROR = "003";
    public static final String CARD_BALANCE_ERROR = "004";
    public static final String MERCHANT_ERROR = "005";
    public static final String DUPLICATE_ERROR = "020";
    public static final String FRAUD_ERROR = "030";

    @Inject
    BlacklistRepository blacklistRepository;

    @Inject
    BinRangeRepository binRangeRepository;

    @Inject
    CardRepository cardRepository;

    @Inject
    AuthorizationRepository authorizationRepository;

    @Inject
    MerchantRepository merchantRepository;


    public AuthorizationResponse authorized(AuthorizationRequest authorizationRequest) {

        Integer authorizationNumber = 0;
        String responseCode = checkIntegrity(authorizationRequest);

        if (ALL_CHECK_VALID.equals(responseCode)) {
            responseCode = cardControl(authorizationRequest);
        }
        if (ALL_CHECK_VALID.equals(responseCode)) {
            responseCode = merchantControl(authorizationRequest);
        }
        if (ALL_CHECK_VALID.equals(responseCode)) {
            responseCode = checkDuplicate(authorizationRequest);
        }
        if (ALL_CHECK_VALID.equals(responseCode)) {
            responseCode = fraudControl(authorizationRequest);
        }
        if (ALL_CHECK_VALID.equals(responseCode)) {
            Long aNumber = new Random(authorizationRequest.getRequestDatetime().getTime() * Long.valueOf(authorizationRequest.getCardNumber())).nextLong();

            if (Long.toString(aNumber).length() > 6) {
                authorizationNumber = Integer.valueOf(Long.toString(aNumber).substring(1, 7));
            }
            //authorizationNumber = Long.toString().substring(0, 6);
        }

        AuthorizationResponse authorizationResponse = new AuthorizationResponse();
        authorizationResponse.setAuthorizationNumber(authorizationNumber);
        authorizationResponse.setResponseCode(responseCode);
        authorizationResponse.setResponseDatetime(new Date());

        // author history
        authorizationRepository.addAuthorizationRequest(authorizationRequest);
        authorizationRepository.addAuthorizationResponse(authorizationResponse);

        return authorizationResponse;
    }

    public String checkIntegrity(AuthorizationRequest authorizationRequest) {
        StringBuilder sb = new StringBuilder();
        sb.append(authorizationRequest.getCardNumber());
        sb.append(authorizationRequest.getExpiryDate());
        sb.append(authorizationRequest.getAmount());
        sb.append(authorizationRequest.getMerchantId());

        String cryptogram = crypt(sb.toString());
        if (!cryptogram.equals(authorizationRequest.getIntegrityString())) {
            return TECHNICAL_ERROR;
        }
        return ALL_CHECK_VALID;
    }


    public String cardControl(AuthorizationRequest authorizationRequest) {

        Card card = cardRepository.read(authorizationRequest.getCardNumber(), authorizationRequest.getExpiryDate());
        if (blacklistRepository.isBlackListed(card)) {
            return BLACKLIST_ERROR;
        }

        Integer binToTest = Integer.valueOf(authorizationRequest.getCardNumber().substring(0, 6));
        if (!binRangeRepository.isActive(binToTest)) {
            return CARD_BIN_ERROR;
        }

        if (null == card) {
            return CARD_VALID_ERROR;
        }

        if (!luhnCheck(card.getCardNumber())) {
            return CARD_VALID_ERROR;
        }

        if (card.getBalance() - authorizationRequest.getAmount() < 0) {
            return CARD_BALANCE_ERROR;
        }

        return ALL_CHECK_VALID;
    }


    private String merchantControl(AuthorizationRequest authorizationRequest) {
        return merchantRepository.read(authorizationRequest.getMerchantId()) != null ? ALL_CHECK_VALID : MERCHANT_ERROR;
    }

    public String checkDuplicate(AuthorizationRequest authorizationRequest) {
        List<AuthorizationRequest> find = authorizationRepository.readForDuplicateSearch(authorizationRequest.getCardNumber(), authorizationRequest.getExpiryDate(), authorizationRequest.getAmount(), authorizationRequest.getMerchantId());
        if (!find.isEmpty()) {
            Calendar c = new GregorianCalendar();
            c.setTimeInMillis(authorizationRequest.getRequestDatetime().getTime());
            c.add(Calendar.SECOND, -2);
            Date from = c.getTime();
            c.add(Calendar.SECOND, +4);
            Date to = c.getTime();

            for (AuthorizationRequest request : find) {
                if (request.getRequestDatetime().after(from) && request.getRequestDatetime().before(to)) {
                    return DUPLICATE_ERROR;
                }
            }
        }
        return ALL_CHECK_VALID;
    }

    public String fraudControl(AuthorizationRequest authorizationRequest) {
        List<AuthorizationRequest> find = authorizationRepository.readByCard(authorizationRequest.getCardNumber(), authorizationRequest.getExpiryDate());
        if (!find.isEmpty()) {
            Merchant requestMerchant = merchantRepository.read(authorizationRequest.getMerchantId());
            // for each authorization on this card
            for (AuthorizationRequest currentAuthorization : find) {
                Calendar c = new GregorianCalendar();
                c.setTimeInMillis(authorizationRequest.getRequestDatetime().getTime());
                c.add(Calendar.MINUTE, -5);
                Date from = c.getTime();
                //if current author date time is after request author date time - 5 and before request author date time
                if (currentAuthorization.getRequestDatetime().before(authorizationRequest.getRequestDatetime()) && currentAuthorization.getRequestDatetime().after(from)) {
                    Merchant currentMerchant = merchantRepository.read(currentAuthorization.getMerchantId());
                    // and if merchant.country is different
                    if (!currentMerchant.getCountryCode().equals(requestMerchant.getCountryCode())) {
                        return FRAUD_ERROR;
                    }
                }
            }
        }
        return ALL_CHECK_VALID;
    }

    public String crypt(String input) {
        byte[] bigArray = input.getBytes();
        int chunkNb = bigArray.length / 8 + (bigArray.length % 8 > 0 ? 1 : 0);
        List<byte[]> list = new ArrayList<byte[]>();
        byte[] chunk;

        //divide array in 8 sized chunks
        for (int i = 0; i < chunkNb; i++) {
            chunk = new byte[8];
            if ((bigArray.length - i * 8) < 8) {
                System.arraycopy(bigArray, i * 8, chunk, 0, bigArray.length - i * 8);
            } else {
                System.arraycopy(bigArray, i * 8, chunk, 0, 8);
            }
            list.add(chunk);
        }

        // xor
        byte[] result = list.get(0).clone();
        for (int bitCpt = 0; bitCpt < 8; bitCpt++) {
            for (int chunkCpt = 1; chunkCpt < chunkNb; chunkCpt++) {
                result[bitCpt] = (byte) (result[bitCpt] ^ list.get(chunkCpt)[bitCpt]);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (byte aResult : result) {
            sb.append(toHex(aResult));
        }
        //System.out.println("Result = " + sb.toString());
        return sb.toString();
    }

    public static String toHex(byte a) {
        String s = "00" + Integer.toHexString(a);
        return s.substring(s.length() - 2);
    }

    public void reset() {
        authorizationRepository.getPassedAuthorizationRequest().clear();
    }

    public boolean luhnCheck(String cardNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }
}
