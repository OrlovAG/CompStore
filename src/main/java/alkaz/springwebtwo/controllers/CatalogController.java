package alkaz.springwebtwo.controllers;

import alkaz.springwebtwo.entities.Product;
import alkaz.springwebtwo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller //аннотация указывает Спрингу, что данный класс является контроллером, то есть его единственный
            //экземпляр обрабатывает какие-то http-запросы, то есть в нем будут методы с @GetMapping или @PostMapping
public class CatalogController {
    @Autowired              // автосвязывание текущего бина (productController) с бином productsService,
                            // который должен быть в контексте спрингового приложения
    ProductService productService;

    @GetMapping("/catalog")
    public String showCatalog(Model model){
        List<Product> spisok = productService.getAllList();

        model.addAttribute("spisok_produktov", spisok);
        return "catalog";        //передаем шаблон, который уже будет работать с model
    }

    @GetMapping("/new_product")
    public String newProduct(){
        return "new_product";        //передаем шаблон
    }

    @GetMapping("/save_new_product")
    public String saveNewProduct(@RequestParam(name = "id")   String strId,
                                 @RequestParam(name = "name") String name,
                                 @RequestParam(name = "diagonal") String diagonal,
                                 @RequestParam(name = "cost") String strCost,
                                 @RequestParam(name = "currency") String currency,
                                         Model model)                                {
        if(strId != null && name!= null && strCost!=null) {
            try{
                int id = Integer.parseInt(strId);
                double cost = Double.parseDouble(strCost);
                double d = Double.parseDouble(diagonal);
                Product prod = new Product(id, name, d, cost, currency);
                productService.addProduct(prod);
            } catch (Exception e) {
                System.out.println("все пропало "+e.getMessage());
            }
        }

       // return showCatalog(model);
        return "redirect:/catalog";     //перенаправление заставляет клиента (браузер)
                                    //забыть предыдущий http-запрос и его параметры
    }

    @GetMapping("/find_product")
    public String findProduct(@RequestParam(name = "s", required = false) String s, Model model){
        if(s == null || s.isEmpty() || s.isBlank())
            return "catalog";

        Product product = productService.findFirstByName(s);
        model.addAttribute("product", product);
        return "product_info";
    }
}
