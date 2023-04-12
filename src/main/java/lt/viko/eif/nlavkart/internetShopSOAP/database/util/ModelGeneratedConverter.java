package lt.viko.eif.nlavkart.internetShopSOAP.database.util;

import generatedsoap.Account;
import generatedsoap.Cart;
import generatedsoap.Category;
import generatedsoap.Item;
import lt.viko.eif.nlavkart.internetShopSOAP.database.models.AccountModel;
import lt.viko.eif.nlavkart.internetShopSOAP.database.models.CartModel;
import lt.viko.eif.nlavkart.internetShopSOAP.database.models.CategoryModel;
import lt.viko.eif.nlavkart.internetShopSOAP.database.models.ItemModel;

public class ModelGeneratedConverter {
    public static Account convertAccount(AccountModel accountModel) {
        Account a = new Account();
        a.setId(accountModel.getId());
        a.setUsername(accountModel.getUsername());
        a.setPassword(accountModel.getPassword());
        a.setCart(ModelGeneratedConverter.convertCart(accountModel.getCart()));
        return a;
    }

    public static Cart convertCart(CartModel cartModel) {
        Cart c = new Cart();
        c.setId(cartModel.getId());
        for (int i = 0; i < cartModel.getItems().size(); i++) {
            c.getItems().add(ModelGeneratedConverter.convertItem(cartModel.getItems().get(i)));
        }
        return c;
    }

    public static Item convertItem(ItemModel itemModel) {
        Item i = new Item();
        i.setId(itemModel.getId());
        i.setName(itemModel.getName());
        i.setDescription(itemModel.getDescription());
        i.setCategory(ModelGeneratedConverter.convertCategory(itemModel.getCategory()));
        i.setPrice(itemModel.getPrice());
        i.setQuantity(itemModel.getQuantity());
        return i;
    }

    public static Category convertCategory(CategoryModel categoryModel) {
        Category c = new Category();
        c.setId(categoryModel.getId());
        c.setName(categoryModel.getName());
        c.setDescription(categoryModel.getDescription());
        return c;
    }
}
