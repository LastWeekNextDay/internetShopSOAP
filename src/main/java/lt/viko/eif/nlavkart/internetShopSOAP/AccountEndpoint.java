package lt.viko.eif.nlavkart.internetShopSOAP;


import generatedsoap.*;
import lt.viko.eif.nlavkart.internetShopSOAP.database.hibernate.Hibernate;
import lt.viko.eif.nlavkart.internetShopSOAP.database.models.AccountModel;
import lt.viko.eif.nlavkart.internetShopSOAP.database.models.CartModel;
import lt.viko.eif.nlavkart.internetShopSOAP.database.models.CategoryModel;
import lt.viko.eif.nlavkart.internetShopSOAP.database.models.ItemModel;
import lt.viko.eif.nlavkart.internetShopSOAP.database.util.ModelGeneratedConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.io.Serializable;
import java.util.ArrayList;
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
        if (request.getAccountId() >= 0) {
            query = "from AccountModel where id = " + request.getAccountId();
        } else if (!request.getUsername().equals("")) {
            query = "from AccountModel where username = '" + request.getUsername() + "'";
        } else {
            response.setAccount(null);
            return response;
        }
        AccountModel accountModel = hibernate.queryAccountModel(query, true).get(0);
        if (accountModel.getDeleted()) {
            response.setAccount(null);
            return response;
        }
        response.setAccount(ModelGeneratedConverter.convertAccount(accountModel));
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
        for (AccountModel accountModel : hibernate.queryAccountModel("from AccountModel", true)) {
            if (!accountModel.getDeleted()) {
                response.getAccounts().add(ModelGeneratedConverter.convertAccount(accountModel));
            }
        }
        hibernate.closeTransaction();
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createAccountRequest")
    @ResponsePayload
    public CreateAccountResponse createAccount(@RequestPayload CreateAccountRequest request) {
        CreateAccountResponse response = new CreateAccountResponse();
        String query = "from AccountModel where username = '" + request.getUsername() +
                "'" + " and deleted = false";
        Hibernate hibernate = new Hibernate();
        hibernate.createSessionFactory();
        hibernate.openTransaction();
        if (hibernate.queryAccountModel(query, true).size() >= 1) {
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
                "' and password = '" + request.getPassword() + "' and deleted = false";
        response.setAck(hibernate.queryAccountModel(query, true).size() >= 1);
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
            query = "from AccountModel where id = " + request.getAccountId() + " and deleted = false";
        } else if (!request.getUsername().equals("")){
            query = "from AccountModel where username = '" + request.getUsername() + "'" + " and deleted = false";
        }
        List<AccountModel> output = hibernate.queryAccountModel(query, true);
        if (output.size() == 0){
            response.setAck(false);
            response.setMessage("Account not found");
            return response;
        }
        AccountModel accountModel = output.get(0);
        accountModel.setDeleted(true);
        hibernate.getSession().saveOrUpdate(accountModel);
        hibernate.closeTransaction();
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "removeItemFromCartRequest")
    @ResponsePayload
    public RemoveItemFromCartResponse removeItemFromCartResponse(@RequestPayload RemoveItemFromCartRequest request){
        RemoveItemFromCartResponse response = new RemoveItemFromCartResponse();
        Hibernate hibernate = new Hibernate();
        hibernate.createSessionFactory();
        hibernate.openTransaction();
        String query = "";
        if (request.getAccountId() >= 0){
            query = "from AccountModel where id = " + request.getAccountId() + " and deleted = false";
        } else if (!request.getUsername().equals("")){
            query = "from AccountModel where username = '" + request.getUsername() + "'" + " and deleted = false";
        }
        List<AccountModel> output = hibernate.queryAccountModel(query, true);
        hibernate.closeTransaction();
        if (output.size() == 0){
            response.setAck(false);
            response.setMessage("Account not found");
            return response;
        }
        AccountModel accountModel = output.get(0);
        CartModel cart = accountModel.getCart();
        List<ItemModel> items = cart.getItems();
        int amount = items.size();
        for (ItemModel item : items){
            if (item.getId() == request.getItemId() && !item.getDeleted()){
                items.remove(item);
                break;
            }
        }
        cart.setItems(items);
        hibernate.openTransaction();
        hibernate.getSession().saveOrUpdate(cart);
        hibernate.closeTransaction();
        hibernate.openTransaction();
        query = "from AccountModel where id = " + accountModel.getId() + " and deleted = false";
        accountModel = hibernate.queryAccountModel(query, true).get(0);
        hibernate.closeTransaction();
        cart = accountModel.getCart();
        items = cart.getItems();
        if (items.size() == amount){
            response.setAck(false);
            response.setMessage("Item not removed");
        } else {
            response.setAck(true);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addItemToCartRequest")
    @ResponsePayload
    public AddItemToCartResponse addItemToCartResponse(@RequestPayload AddItemToCartRequest request){
        AddItemToCartResponse response = new AddItemToCartResponse();
        Hibernate hibernate = new Hibernate();
        hibernate.createSessionFactory();
        hibernate.openTransaction();
        String query;
        if (request.getAccountId() >= 0){
            query = "from AccountModel where id = " + request.getAccountId() + " and deleted = false";
        } else if (!request.getUsername().equals("")){
            query = "from AccountModel where username = '" + request.getUsername() + "'" + " and deleted = false";
        } else {
            response.setAck(false);
            response.setMessage("Provide account id or username");
            return response;
        }
        List<AccountModel> output = hibernate.queryAccountModel(query, true);
        hibernate.closeTransaction();
        if (output.size() == 0){
            response.setAck(false);
            response.setMessage("Account not found");
            return response;
        }
        AccountModel accountModel = output.get(0);
        CartModel cart = accountModel.getCart();
        List<ItemModel> items = cart.getItems();
        int amount = 0;
        for (ItemModel item : items){
            if (item.getId() == request.getItemId() && !item.getDeleted()){
                amount++;
            }
        }
        hibernate.openTransaction();
        ItemModel itemModel = hibernate.queryItemModel("from ItemModel where id = " + request.getItemId(), true).get(0);
        if (itemModel.getDeleted()){
            response.setAck(false);
            response.setMessage("Item not found");
            return response;
        }
        items.add(itemModel);
        cart.setItems(items);
        hibernate.getSession().saveOrUpdate(cart);
        hibernate.closeTransaction();
        hibernate.openTransaction();
        query = "from AccountModel where id = " + accountModel.getId() + " and deleted = false";
        accountModel = hibernate.queryAccountModel(query, true).get(0);
        hibernate.closeTransaction();
        cart = accountModel.getCart();
        items = cart.getItems();
        int newAmount = 0;
        for (ItemModel item : items){
            if (item.getId() == request.getItemId() && !item.getDeleted()){
                newAmount++;
            }
        }
        if (newAmount == amount){
            response.setAck(false);
            response.setMessage("Item not added");
        } else {
            response.setAck(true);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCategoriesRequest")
    @ResponsePayload
    public GetCategoriesResponse getCategoriesResponse(@RequestPayload GetCategoriesRequest request){
        GetCategoriesResponse response = new GetCategoriesResponse();
        Hibernate hibernate = new Hibernate();
        hibernate.createSessionFactory();
        hibernate.openTransaction();
        String query = "from CategoryModel";
        List<CategoryModel> output = hibernate.queryCategoryModel(query, true);
        hibernate.closeTransaction();
        for (CategoryModel categoryModel : output){
            response.getCategories().add(ModelGeneratedConverter.convertCategory(categoryModel));
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getItemsRequest")
    @ResponsePayload
    public GetItemsResponse getItemsResponse(@RequestPayload GetItemsRequest request){
        GetItemsResponse response = new GetItemsResponse();
        Hibernate hibernate = new Hibernate();
        hibernate.createSessionFactory();
        hibernate.openTransaction();
        String query;
        if (request.getCategoryId() >= 0){
            query = "FROM ItemModel where category = ( FROM CategoryModel WHERE id = " + request.getCategoryId() + " )";
        } else {
            query = "FROM ItemModel";
        }
        List<ItemModel> output = hibernate.queryItemModel(query, true);
        hibernate.closeTransaction();
        for (ItemModel itemModel : output){
            if (!itemModel.getDeleted()){
                response.getItems().add(ModelGeneratedConverter.convertItem(itemModel));
            }
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getItemRequest")
    @ResponsePayload
    public GetItemResponse getItemResponse(@RequestPayload GetItemRequest request){
        GetItemResponse response = new GetItemResponse();
        Hibernate hibernate = new Hibernate();
        hibernate.createSessionFactory();
        hibernate.openTransaction();
        String query = "from ItemModel where id = " + request.getItemId();
        List<ItemModel> output = hibernate.queryItemModel(query, true);
        hibernate.closeTransaction();
        for (ItemModel itemModel : output){
            if (itemModel.getDeleted()){
                output.remove(itemModel);
            }
        }
        if (output.size() == 0){
            response.setItem(null);
            return response;
        }
        ItemModel itemModel = output.get(0);
        response.setItem(ModelGeneratedConverter.convertItem(itemModel));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "removeItemRequest")
    @ResponsePayload
    public RemoveItemResponse removeItemResponse(@RequestPayload RemoveItemRequest request){
        RemoveItemResponse response = new RemoveItemResponse();
        Hibernate hibernate = new Hibernate();
        hibernate.createSessionFactory();
        hibernate.openTransaction();
        List<ItemModel> output = hibernate.queryItemModel("from ItemModel where id = " + request.getItemId(), true);
        hibernate.closeTransaction();
        output.removeIf(ItemModel::getDeleted);
        if (output.size() == 0){
            response.setAck(false);
            response.setMessage("Item not found");
            return response;
        }
        hibernate.openTransaction();
        ItemModel item = hibernate.queryItemModel("from ItemModel where id = " + request.getItemId(), true).get(0);
        item.setDeleted(true);
        hibernate.getSession().saveOrUpdate(item);
        hibernate.closeTransaction();
        response.setAck(true);
        return response;
    }
}
