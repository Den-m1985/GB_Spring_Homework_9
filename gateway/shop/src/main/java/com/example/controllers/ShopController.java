package com.example.controllers;

import com.example.abserver.ProductCreateEvent;
import com.example.model.Product;
import com.example.config.UrlProperties;
import com.example.services.FileGateway;
import com.example.services.ProductServices;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/shop")
public class ShopController {

    ProductServices productServices;

    private final FileGateway fileGateway;

    ApplicationEventPublisher publisher;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    /**
     * Получает список продуктов из модуля warehouse и добавляет его в модель для отображения на странице.
     *
     * @param model Модель Spring MVC для передачи атрибутов в представление.
     * @return Имя представления для отображения списка продуктов.
     */
    @GetMapping("/")
    public String getProductFromWarehouse(Model model) {
        List<Product> products = productServices.getAllProducts();
        model.addAttribute("products", products);

        // Получаем текущее время
        LocalDateTime currentDateTime = LocalDateTime.now();
        String formattedDateTime = formatter.format(currentDateTime);
        fileGateway.writeToFile("getAllProducts.txt", "get all products ---" + formattedDateTime);
        return "products";
    }


    /**
     * Получает детали продукта из модуля warehouse по его идентификатору и добавляет их в модель для отображения на странице.
     *
     * @param id    Идентификатор продукта.
     * @param model Модель Spring MVC для передачи атрибутов в представление.
     * @return Имя представления для отображения деталей продукта.
     */
    @GetMapping("/product/{id}")
    public String getProductDetails(@PathVariable Long id, Model model) {
        Product product = productServices.getProduct(id);
        model.addAttribute("product", product);

        // Получаем текущее время
        LocalDateTime currentDateTime = LocalDateTime.now();
        String formattedDateTime = formatter.format(currentDateTime);
        fileGateway.writeToFile("getProduct.txt", product.getName() + "---" + formattedDateTime);

        //abserver
        publisher.publishEvent(new ProductCreateEvent(this, product));
        return "product";
    }


    /**
     * Отправляет запрос на резервацию товара с указанным идентификатором продукта и количеством
     * в модуль warehouse и возвращает результат операции.
     *
     * @param productId Идентификатор продукта для покупки.
     * @param quantity  Количество товара для покупки.
     * @return ResponseEntity с результатом операции (например, успех или ошибка).
     */
    @PostMapping("/buyProduct")
    public ResponseEntity<String> buyProduct(@RequestParam Long productId, @RequestParam int quantity) {
        // Получаем текущее время
        LocalDateTime currentDateTime = LocalDateTime.now();
        String formattedDateTime = formatter.format(currentDateTime);
        fileGateway.writeToFile("buyProduct.txt", productId + "---" + quantity + "---" + formattedDateTime);
        return productServices.buyProduct(productId, quantity);
    }

}
