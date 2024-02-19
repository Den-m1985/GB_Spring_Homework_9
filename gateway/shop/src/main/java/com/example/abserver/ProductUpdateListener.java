package com.example.abserver;

import com.example.services.FileGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

public class ProductUpdateListener  implements ApplicationListener<ProductCreateEvent> {

    @Autowired
    FileGateway fileGateway;
    @Override
    public void onApplicationEvent(ProductCreateEvent event) {
        fileGateway.writeToFile("Product_log.txt", event.getProduct().getName());
        System.out.println("Product: " + event.getProduct().getName());
    }

}
