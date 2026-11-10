package com.pfa.helpdesk.service;

import com.pfa.helpdesk.entity.Category;
import com.pfa.helpdesk.repository.CategoryRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AICategorizationEngineTest {

    @Test
    void wifiProblemIsMappedToNetworkCategory() {
        CategoryRepository repo = mock(CategoryRepository.class);
        Category reseau = Category.builder().name("Réseau").build();
        when(repo.findByName("Réseau")).thenReturn(Optional.of(reseau));

        AICategorizationEngine engine = new AICategorizationEngine(repo);

        assertEquals("Réseau", engine.predictCategory("Wifi coupé", "plus de connexion").getName());
    }
}
