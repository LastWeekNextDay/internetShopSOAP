package lt.viko.eif.nlavkart.internetShopSOAP;


import generatedsoap.*;
import lt.viko.eif.nlavkart.internetShopSOAP.database.hibernate.Hibernate;
import lt.viko.eif.nlavkart.internetShopSOAP.database.models.AccountModel;
import lt.viko.eif.nlavkart.internetShopSOAP.database.models.CartModel;
import lt.viko.eif.nlavkart.internetShopSOAP.database.models.ItemModel;
import lt.viko.eif.nlavkart.internetShopSOAP.database.util.ModelGeneratedConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class AccountEndpoint {
    private static final String NAMESPACE_URI = "generatedsoap";

    @Autowired
    public AccountEndpoint() {
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAccountRequest")
    @ResponsePayload
    public GetAccountResponse getAccount(@RequestPayload GetAccountRequest request) {
        GetAccountResponse response = new GetAccountResponse();
        Hibernate hibernate = new Hibernate();
        hibernate.createSessionFactory();
        hibernate.openTransaction();
        String query = "";
        if (request.getAccountId() > -1) {
            query = "from AccountModel where id = " + request.getAccountId();
        } else if (!request.getUsername().equals("")) {
            query = "from AccountModel where username = '" + request.getUsername() + "'";
        } else {
            return null;
        }
        response.setAccount(ModelGeneratedConverter.convertAccount(hibernate.query(query, true).get(0)));
        hibernate.closeTransaction();
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAccountsRequest")
    @ResponsePayload
    public GetAccountsResponse getAccounts() {
        GetAccountsResponse response = new GetAccountsResponse();
        Hibernate hibernate = new Hibernate();
        hibernate.createSessionFactory();
        hibernate.openTransaction();
        for (AccountModel accountModel : hibernate.query("from AccountModel", true)) {
            response.getAccounts().add(ModelGeneratedConverter.convertAccount(accountModel));
        }
        hibernate.closeTransaction();
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createAccountRequest")
    @ResponsePayload
    public CreateAccountResponse createAccount(@RequestPayload CreateAccountRequest request) {
        CreateAccountResponse response = new CreateAccountResponse();
        String query = "from AccountModel where username = '" + request.getUsername() +
                "'";
        Hibernate hibernate = new Hibernate();
        hibernate.createSessionFactory();
        hibernate.openTransaction();
        if (hibernate.query(query, true).size() >= 1) {
            response.setAck(false);
            response.setMessage("Username already exists");
            return response;
        }
        AccountModel accountModel = new AccountModel();
        accountModel.setUsername(request.getUsername());
        accountModel.setPassword(request.getPassword());
        CartModel cart = new CartModel();
        accountModel.setCart(cart);
        hibernate.save(accountModel);
        hibernate.closeTransaction();
        hibernate.openTransaction();
        query = "from AccountModel where username = '" + request.getUsername() +
                "' and password = '" + request.getPassword() + "'";
        response.setAck(hibernate.query(query, true).size() >= 1);
        hibernate.closeTransaction();
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "removeAccountRequest")
    @ResponsePayload
    public RemoveAccountResponse removeAccount(@RequestPayload RemoveAccountRequest request){
        RemoveAccountResponse response = new RemoveAccountResponse();
        Hibernate hibernate = new Hibernate();
        hibernate.createSessionFactory();
        hibernate.openTransaction();
        String query = "";
        String deleteQuery = "";
        if (request.getAccountId() >= 0){
            query = "from AccountModel where id = " + request.getAccountId();
            deleteQuery = "delete from AccountModel where id = " + request.getAccountId();
        } else if (!request.getUsername().equals("")){
            query = "from AccountModel where username = '" + request.getUsername() + "'";
            deleteQuery = "delete from AccountModel where username = '" + request.getUsername() + "'";
        } else {
            return null;
        }
        if (hibernate.query(query, true).size() == 0){
            response.setAck(false);
            response.setMessage("Account not found");
            return response;
        }
        hibernate.query(deleteQuery, false);
        hibernate.closeTransaction();
        hibernate.openTransaction();
        if (hibernate.query(query, true).size() > 0){
            response.setAck(false);
            response.setMessage("Account not removed");
        } else {
            response.setAck(true);
        }
        hibernate.closeTransaction();
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCartOfAccountRequest")
    @ResponsePayload
    public GetCartOfAccountResponse getCartOfAccount(@RequestPayload GetCartOfAccountRequest request){
        GetCartOfAccountResponse response = new GetCartOfAccountResponse();
        Hibernate hibernate = new Hibernate();
        hibernate.createSessionFactory();
        hibernate.openTransaction();
        String query = "";
        if (request.getAccountId() >= 0){
            query = "from AccountModel where id = " + request.getAccountId();
        } else if (!request.getUsername().equals("")){
            query = "from AccountModel where username = '" + request.getUsername() + "'";
        } else {
            return null;
        }
        List<AccountModel> accounts = hibernate.query(query, true);
        hibernate.closeTransaction();
        if (accounts.size() == 0){
            return null;
        }
        response.setCart(ModelGeneratedConverter.convertCart(accounts.get(0).getCart()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getItemFromCartOfAccountRequest")
    @ResponsePayload
    public GetItemFromCartOfAccountResponse getItemFromCartOfAccountResponse(@RequestPayload GetItemFromCartOfAccountRequest request){
        GetItemFromCartOfAccountResponse response = new GetItemFromCartOfAccountResponse();
        Hibernate hibernate = new Hibernate();
        hibernate.createSessionFactory();
        hibernate.openTransaction();
        String query = "";
        if (request.getAccountId() >= 0){
            query = "from AccountModel where id = " + request.getAccountId();
        } else if (!request.getUsername().equals("")){
            query = "from AccountModel where username = '" + request.getUsername() + "'";
        } else {
            return null;
        }
        List<AccountModel> accounts = hibernate.query(query, true);
        hibernate.closeTransaction();
        if (accounts.size() == 0){
            return null;
        }
        CartModel cart = accounts.get(0).getCart();
        for (ItemModel item : cart.getItems()){
            if (item.getId() == request.getItemId()){
                response.setItem(ModelGeneratedConverter.convertItem(item));
                return response;
            }
        }
        return null;
    }

}
