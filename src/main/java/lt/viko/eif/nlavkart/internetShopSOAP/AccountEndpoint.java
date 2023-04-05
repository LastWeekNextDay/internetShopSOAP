package lt.viko.eif.nlavkart.internetShopSOAP;

import generated.GetAccountRequest;
import generated.GetAccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

public class AccountEndpoint {
    private static final String NAMESPACE_URI = "urn:internetShop";

    @Autowired
    public AccountEndpoint() {
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAccountRequest")
    @ResponsePayload
    public GetAccountResponse getAccount(@RequestPayload GetAccountRequest request) {
        GetAccountResponse response = new GetAccountResponse();
        //response.setCountry(countryRepository.findCountry(request.getName()));

        return response;
    }
}
