/*
****************************************************
* Atos Worldline -- Global Platforms and Solutions

* Atos Worldline is an Atos company
* All rights reserved
*
*****************************************************
*/
package net.atos.xa.contest.repository;

import net.atos.xa.contest.business.AuthorizationManager;
import net.atos.xa.contest.dto.AuthorizationRequest;
import net.atos.xa.contest.dto.AuthorizationResponse;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AuthorizationRepository {

    List<AuthorizationRequest> passedAuthorizationRequest = new ArrayList<AuthorizationRequest>();
    List<AuthorizationResponse> passedAuthorizationResponse = new ArrayList<AuthorizationResponse>();

    public List<AuthorizationRequest> readForDuplicateSearch(String cardNumber, String expiryDate, Integer amount, Integer merchantId){
        List<AuthorizationRequest> ret = new ArrayList<AuthorizationRequest>();
        for (AuthorizationRequest authorizationRequest : passedAuthorizationRequest) {
            if(authorizationRequest.getCardNumber().equals(cardNumber)
                    && authorizationRequest.getExpiryDate().equals(expiryDate)
                    && authorizationRequest.getAmount().equals(amount)
                    && authorizationRequest.getMerchantId().equals(merchantId)){
                ret.add(authorizationRequest);
            }
        }
        return ret;
    }

    public List<AuthorizationRequest> readByCard(String cardNumber, String expiryDate){
        List<AuthorizationRequest> ret = new ArrayList<AuthorizationRequest>();
        for (AuthorizationRequest authorizationRequest : passedAuthorizationRequest) {
            if(authorizationRequest.getCardNumber().equals(cardNumber)
                    && authorizationRequest.getExpiryDate().equals(expiryDate)){
                ret.add(authorizationRequest);
            }
        }
        return ret;
    }

    public List<AuthorizationRequest> readByCardAndResponseStatus(String cardNumber, String expiryDate,String responseCode){
        List<AuthorizationRequest> ret = new ArrayList<AuthorizationRequest>();
        for ( int i = 0 ; i< passedAuthorizationRequest.size(); i++) {
            AuthorizationRequest authorizationRequest = passedAuthorizationRequest.get(i);
            if(authorizationRequest.getCardNumber().equals(cardNumber)
                    && authorizationRequest.getExpiryDate().equals(expiryDate) && passedAuthorizationResponse.get(i).getResponseCode().equals(AuthorizationManager.ALL_CHECK_VALID)){
                ret.add(authorizationRequest);
            }
        }
        return ret;
    }

    public List<AuthorizationRequest> getPassedAuthorizationRequest() {
        return passedAuthorizationRequest;
    }

    public void setPassedAuthorizationRequest(List<AuthorizationRequest> passedAuthorizationRequest) {
        this.passedAuthorizationRequest = passedAuthorizationRequest;
    }

    public void addAuthorizationRequest(AuthorizationRequest passedAuthorization) {
        this.passedAuthorizationRequest.add(passedAuthorization);
    }

    public void addAuthorizationResponse(AuthorizationResponse passedAuthorizationResponse) {
        this.passedAuthorizationResponse.add(passedAuthorizationResponse);
    }
}
