package com.example.controllers;

import com.example.model.Product;
import com.example.config.UrlProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UrlProperties urlProperties;


    /**
     * Получает список продуктов из модуля warehouse и добавляет его в модель для отображения на странице.
     *
     * @param model Модель Spring MVC для передачи атрибутов в представление.
     * @return Имя представления для отображения списка продуктов.
     */
    @GetMapping("/")
    public String getProductFromWarehouse(Model model) {
        String warehouseUrl = urlProperties.getUriGetProducts(); // URL модуля warehouse
        ResponseEntity<List<Product>> response = restTemplate.exchange(
                warehouseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        List<Product> products = response.getBody();
        model.addAttribute("products", products);
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
        String warehouseUrl = urlProperties.getUriGetProduct() + id; // URL модуля warehouse
        ResponseEntity<Product> response = restTemplate.exchange(
                warehouseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        Product product = response.getBody();
        model.addAttribute("product", product);
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
        String warehouseUrl = urlProperties.getReserveProduct(); // URL метода резервации
        MultiValueMap<String, Long> map = new LinkedMultiValueMap<>();
        map.add("productId", productId);
        map.add("quantity", (long) quantity);
        ResponseEntity<String> response = restTemplate.postForEntity(warehouseUrl, map, String.class);
        return response;
        //return "redirect:/shop/";
    }

}
