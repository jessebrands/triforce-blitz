package com.triforceblitz.triforceblitz.seeds;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SeedController.class)
class SeedControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private SeedService seedService;

    @Test
    void whenGeneratorFormRequested_thenReturnsGeneratorForm() throws Exception {
        mvc.perform(get("/seeds/generate"))
                .andExpect(status().isOk())
                .andExpect(view().name("seeds/generator_form"))
                .andExpect(model().attribute("form", instanceOf(GenerateSeedForm.class)));
    }

    @Test
    void whenValidGeneratorFormPosted_thenRedirectToSeed() throws Exception {
        var expected = new Seed(UUID.randomUUID().toString());
        when(seedService.generateSeed()).thenReturn(expected);

        mvc.perform(post("/seeds/generate"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/seeds/" + expected.getId()));
    }
}
