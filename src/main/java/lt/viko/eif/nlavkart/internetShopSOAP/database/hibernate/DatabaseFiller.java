package lt.viko.eif.nlavkart.internetShopSOAP.database.hibernate;

import lt.viko.eif.nlavkart.internetShopSOAP.database.models.AccountModel;
import lt.viko.eif.nlavkart.internetShopSOAP.database.models.CartModel;
import lt.viko.eif.nlavkart.internetShopSOAP.database.models.CategoryModel;
import lt.viko.eif.nlavkart.internetShopSOAP.database.models.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseFiller {
    public static void fillDatabase(){
        int j = 0;
        Hibernate hibernate = new Hibernate();
        hibernate.createSessionFactory();
        CategoryModel cat1 = new CategoryModel();
        cat1.setName("cat1");
        cat1.setDescription("cat1");
        CategoryModel cat2 = new CategoryModel();
        cat2.setName("cat2");
        cat2.setDescription("cat2");
        CategoryModel cat3 = new CategoryModel();
        cat3.setName("cat3");
        cat3.setDescription("cat3");
        for (int i = 0; i < 5; i++){
            AccountModel a = new AccountModel();
            a.setUsername("username" + i);
            a.setPassword("password" + i);

            CartModel c = new CartModel();

            List<ItemModel> items = new ArrayList<>();

            for (int k = 0; k < 4; k++){
                ItemModel item = new ItemModel();
                item.setName("item" + j);
                item.setDescription("description" + j);
                if (k % 3 == 0)
                    item.setCategory(cat1);
                else if (k % 3 == 1)
                    item.setCategory(cat2);
                else
                    item.setCategory(cat3);
                item.setPrice(k);
                item.setQuantity(k);
                items.add(item);
                j++;
            }
            c.setItems(items);
            a.setCart(c);
            hibernate.openTransaction();
            hibernate.save(a);
            hibernate.closeTransaction();
        }
    }
}
