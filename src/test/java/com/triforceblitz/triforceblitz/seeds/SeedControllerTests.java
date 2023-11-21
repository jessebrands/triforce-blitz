package com.triforceblitz.triforceblitz.seeds;

import com.triforceblitz.triforceblitz.TriforceBlitzVersion;
import com.triforceblitz.triforceblitz.WebSecurityConfig;
import com.triforceblitz.triforceblitz.generator.GeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SeedController.class)
@Import({WebSecurityConfig.class, DefaultSeedService.class})
class SeedControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private GeneratorService generatorService;

    @MockBean
    private SeedRepository seedRepository;

    @BeforeEach
    public void beforeEach() throws Exception {
        when(generatorService.generateSeed(any(TriforceBlitzVersion.class), anyString()))
                .thenAnswer(i -> Seed.create(i.getArgument(0), i.getArgument(1)));

        when(seedRepository.insert(any(Seed.class)))
                .thenAnswer(i -> i.getArgument(0));
    }

    @Test
    void getControllerForm_shouldReturnOK() throws Exception {
        var versions = Set.of(
                TriforceBlitzVersion.fromString("1.0.0-blitz-1.0"),
                TriforceBlitzVersion.fromString("1.3.0-blitz-1.1"),
                TriforceBlitzVersion.fromString("4.2.0-blitz-6.9")
        );

        when(generatorService.getAvailableVersions()).thenReturn(versions);

        mvc.perform(get("/seeds/generate"))
                .andExpect(status().isOk())
                .andExpect(view().name("seeds/generator_form"))
                .andExpect(model().attributeExists("form"))
                .andExpect(model().attribute("versions", versions));
    }

    @Captor
    ArgumentCaptor<Seed> seedCaptor;


    @Test
    void postControllerForm_shouldCreateSeed() throws Exception {
        // The MVC framework should get a redirection to /seeds/{uuid}
        mvc.perform(post("/seeds/generate")
                        .param("version", "7.1.143-blitz-0.43")
                        .param("unlockMode", "ALWAYS_UNLOCKED")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());

        verify(seedRepository).insert(seedCaptor.capture());

        var seed = seedCaptor.getValue();
        assertThat(seed.getVersion()).isEqualTo(TriforceBlitzVersion.fromString("7.1.143-blitz-0.43"));
    }

    @Test
    void postControllerForm_whenSpoilerLocked_shouldBeSet() throws Exception {
        mvc.perform(post("/seeds/generate")
                        .param("version", "1.0.0-blitz-1.0")
                        .param("unlockMode", "ALWAYS_LOCKED")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());

        verify(seedRepository).update(seedCaptor.capture());
        var seed = seedCaptor.getValue();

        assertThat(seed.isSpoilerLocked()).isEqualTo(true);
    }

    @Test
    void postControllerForm_whenSpoilerUnlocked_shouldBeSet() throws Exception {
        mvc.perform(post("/seeds/generate")
                        .param("version", "1.0.0-blitz-1.0")
                        .param("unlockMode", "ALWAYS_UNLOCKED")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());

        verify(seedRepository).update(seedCaptor.capture());
        var seed = seedCaptor.getValue();

        assertThat(seed.isSpoilerLocked()).isEqualTo(false);
    }

    @Test
    void getSeed_shouldReturnOK() throws Exception {
        var version = TriforceBlitzVersion.fromString("6.2.3-blitz-0.33");
        var randomizerSeed = UUID.randomUUID().toString();
        var seed = Seed.create(version, randomizerSeed);

        when(seedRepository.findById(seed.getId()))
                .thenReturn(Optional.of(seed));

        mvc.perform(get("/seeds/" + seed.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("seed", seed))
                .andExpect(view().name("seeds/detail"));
    }
}
