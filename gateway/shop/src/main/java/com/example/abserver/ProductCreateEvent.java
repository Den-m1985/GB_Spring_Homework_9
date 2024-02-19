package com.example.abserver;

import com.example.model.Product;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ProductCreateEvent extends ApplicationEvent {

    private Product product;

    public ProductCreateEvent(Object source, Product product) {
        super(source);
        this.product = product;
    }

}
