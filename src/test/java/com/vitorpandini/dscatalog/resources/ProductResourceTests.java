package com.vitorpandini.dscatalog.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitorpandini.dscatalog.dto.ProductDTO;
import com.vitorpandini.dscatalog.services.ProductService;
import com.vitorpandini.dscatalog.services.exceptions.DatabaseException;
import com.vitorpandini.dscatalog.services.exceptions.ResourceNotFoundException;
import com.vitorpandini.dscatalog.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductResource.class)
public class ProductResourceTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Long existingId;

    private Long nonExistingId;

    private long dependentId ;

    private ProductDTO productDTO;

    @BeforeEach
    void setUp() throws Exception{
        ProductDTO productDTO = Factory.createProductDTO();
        PageImpl<ProductDTO> page = new PageImpl<>(List.of(productDTO));
        existingId= 4L;
        nonExistingId=100L;
        dependentId =5L;




        when(service.findById(existingId)).thenReturn(productDTO);
        when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

        when(service.update(eq(existingId),any())).thenReturn(productDTO);
        when(service.update(eq(nonExistingId),any())).thenThrow(ResourceNotFoundException.class);

        when(service.findAllPaged(any())).thenReturn(page);

        doNothing().when(service).delete(existingId);
        doThrow(ResourceNotFoundException.class).when(service).delete(nonExistingId);
        doThrow(DatabaseException.class).when(service).delete(dependentId);
    }

    @Test
    public void updateShouldReturnProductDTO() throws Exception {

        String jsonBody = objectMapper.writeValueAsString(productDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/products/{id}",existingId)
                .content(jsonBody).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))


                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.description").exists())
                .andExpect(status().isOk());


    }

    @Test
    public void updateShouldReturnDoesNotExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/products/{id}",nonExistingId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void findAllShouldReturnPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void findByIdShouldReturnProductDTO() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}",existingId).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(status().isOk());
    }
    @Test
    public void findByIdShouldReturnResourceNotFoundException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}",nonExistingId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }



}
