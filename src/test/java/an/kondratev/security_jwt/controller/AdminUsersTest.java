package an.kondratev.security_jwt.controller;

import an.kondratev.security_jwt.dto.UserDTO;
import an.kondratev.security_jwt.model.Product;
import an.kondratev.security_jwt.service.ProductServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AdminUsersTest {

    @InjectMocks
    private AdminUsers adminUsers;

    @Mock
    private ProductServiceInterface productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenGetProducts() {
        Product mockProduct = new Product();
        mockProduct.setName("Test Product");
        when(productService.getAllProducts()).thenReturn(Collections.singletonList(mockProduct));

        ResponseEntity<Object> response = adminUsers.getProducts();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(Collections.singletonList(mockProduct), response.getBody());
    }

    @Test
    public void whenSignUpProduct() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("New Product");
        Product storedProduct = new Product();
        storedProduct.setName("New Product");
        when(productService.createProduct(any(Product.class))).thenReturn(storedProduct);

        ResponseEntity<Object> response = adminUsers.signUpProduct(userDTO);

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productService).createProduct(productCaptor.capture());
        assertEquals("New Product", productCaptor.getValue().getName());
        assertEquals(200, response.getStatusCode().value());
        assertEquals(storedProduct, response.getBody());
    }

    @Test
    public void whenUpdateProduct() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Updated Product");
        Product updatedProduct = new Product();
        updatedProduct.setName("Updated Product");
        when(productService.updateProduct(any(Product.class))).thenReturn(updatedProduct);

        ResponseEntity<Object> response = adminUsers.updateProduct(userDTO);

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productService).updateProduct(productCaptor.capture());
        assertEquals("Updated Product", productCaptor.getValue().getName());
        assertEquals(200, response.getStatusCode().value());
        assertEquals(updatedProduct, response.getBody());
    }

    @Test
    public void whenUserAlone() {
        ResponseEntity<Object> response = adminUsers.userAlone();

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Available to user only", response.getBody());
    }
}

