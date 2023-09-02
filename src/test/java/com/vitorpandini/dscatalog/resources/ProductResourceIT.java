package com.vitorpandini.dscatalog.resources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductResourceIT {

    @Autowired
    private MockMvc mockMvc;

    private long existingId;
    private Long nonExistingId;

    private Long countTotalProducts;

    @BeforeEach
    void setUp(){
        existingId=1L;
        nonExistingId=100L;
        countTotalProducts=25L;
    }

    @Test
    public void sortedName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/?page=0&size=12&sort=name,asc")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalElements").value(countTotalProducts))
                .andExpect(status().isOk());
    }


}
