package com.qfg.businesshelper.services.clients;

import com.qfg.businesshelper.stores.domain.model.Product;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by rbtq on 9/1/16.
 */
public interface ProductClient {
    @GET("products")
    Observable<List<Product>> getProducts();

    @POST("products/add")
    Observable<Product> addProduct(@Body Product product);

    @GET("products/qr/{qrCode}")
    Observable<Product> getProductByQR(@Path("qrCode") String qr);
}
