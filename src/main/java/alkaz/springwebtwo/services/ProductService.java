package alkaz.springwebtwo.services;

import alkaz.springwebtwo.entities.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service    //аннотация, которая говорит Спрингу, что этот класс является "сервисом" - частным случаем "компонента"
            //благодаря этому аннотация @ComponentScan на уровне конфигурации приложения автоматически
            //создает бин, помещает его в контекст и выполняет инициализацию
public class ProductService {
    List<Product> products = new ArrayList<>();

    public List<Product> getAllList() {
        return products;
    }
    @PostConstruct  //аннотация, которая говорит Спрингу, что данный метод нужно вызвать для иницализации
                    //после создания бина
    public void fill(){
        try{
            products.add(new Product(1,"Notebook Acer i5", 60000));
            products.add(new Product(2,"Macbook", 180000));
            products.add(new Product(3,"Desktop computer i7", 85000));
            products.add(new Product(4, "x17",17,1500, "usd"));
        } catch (Exception e) {
            System.out.println("Что-то не так с заполнением списка товаров "+e.getMessage());
        }

    }

    public void addProduct(Product product){
        products.add(product);
    }

    public Product findFirstByName(String s){
        for (Product p: products)
            //if(p.getName().equals(s))
            if (p.getName().equalsIgnoreCase(s))
                return p;
        return null;
    }

    public List<Product> filterByName(String s){
        return products.stream().filter(p->p.getName().equals(s)).toList();
    }

}
