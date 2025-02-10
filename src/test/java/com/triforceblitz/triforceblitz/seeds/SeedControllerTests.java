package com.triforceblitz.triforceblitz.seeds;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SeedController.class)
class SeedControllerTests {
    @Autowired
    private MockMvc mvc;

    @Test
    void whenGeneratorFormRequested_thenReturnsGeneratorForm() throws Exception {
        mvc.perform(get("/seeds/generate"))
                .andExpect(status().isOk())
                .andExpect(view().name("seeds/generator_form"))
                .andExpect(model().attribute("form", instanceOf(GenerateSeedForm.class)));
    }
}
