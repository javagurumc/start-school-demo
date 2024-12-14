package com.moma.start_school_demo.product;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Test
    void givenProductDataSupplied_WhenSave_ThenReturnNewProduct() throws Exception {
        //Given
        var savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setName("abc");
        savedProduct.setPrice(1.51d);
        savedProduct.setQuantity(5);

        when(service.save(any())).thenReturn(savedProduct);

        //When
        this.mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "abc",
                                    "price": 1.51,
                                    "quantity": 5
                                }
                                """))
                //Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                          "id": 1,
                          "name": "abc",
                          "price": 1.51,
                          "quantity": 5
                        }
                        """));

        ArgumentCaptor<Product> argumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(service).save(argumentCaptor.capture());

        var newProduct = argumentCaptor.getValue();
        assertThat(newProduct)
                .hasFieldOrPropertyWithValue("name", "abc")
                .hasFieldOrPropertyWithValue("price", 1.51)
                .hasFieldOrPropertyWithValue("quantity", 5);
        assertThat(newProduct.getId()).isNull();
    }

    @Test
    void givenProductsExists_WhenGetAll_ThenReturnAllProducts() throws Exception {
        //Given
        var product = new Product();
        product.setId(1L);
        product.setName("abc");
        product.setPrice(1.51d);
        product.setQuantity(5);

        when(service.getAll()).thenReturn(List.of(product));

        //When
        this.mockMvc.perform(get("/api/v1/products"))
                //Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [{
                                "id": 1,
                                "name": "abc",
                                "price": 1.51,
                                "quantity": 5
                            }]
                        """));
    }

    @Test
    void givenProductExists_WhenGetById_ThenReturnProduct() throws Exception {
        //Given
        var product = new Product();
        product.setId(1L);
        product.setName("abc");
        product.setPrice(1.51d);
        product.setQuantity(5);

        when(service.getById(1L)).thenReturn(Optional.of(product));

        //When
        this.mockMvc.perform(get("/api/v1/products/1"))
                //Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                            "id": 1,
                            "name": "abc",
                            "price": 1.51,
                            "quantity": 5
                        }
                        """));
    }

    @Test
    void givenMissingProduct_WhenGetById_ThenFailWith404() throws Exception {
        //Given
        when(service.getById(1L)).thenReturn(Optional.empty());

        //When
        this.mockMvc.perform(get("/api/v1/products/1"))
                //Then
                .andExpect(status().isNotFound());
    }

    @Test
    void givenProductDataSupplied_WhenUpdate_ThenReturnUpdateProductById() throws Exception {
        //Given
        var savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setName("abc");
        savedProduct.setPrice(1.51d);
        savedProduct.setQuantity(5);

        when(service.save(any())).thenReturn(savedProduct);

        //When
        this.mockMvc.perform(put("/api/v1/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "id": 1,
                                    "name": "abc",
                                    "price": 1.51,
                                    "quantity": 5
                                }
                                """))
                //Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                          "id": 1,
                          "name": "abc",
                          "price": 1.51,
                          "quantity": 5
                        }
                        """));

        ArgumentCaptor<Product> argumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(service).update(eq(1L), argumentCaptor.capture());

        var newProduct = argumentCaptor.getValue();
        assertThat(newProduct)
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "abc")
                .hasFieldOrPropertyWithValue("price", 1.51)
                .hasFieldOrPropertyWithValue("quantity", 5);
    }
}