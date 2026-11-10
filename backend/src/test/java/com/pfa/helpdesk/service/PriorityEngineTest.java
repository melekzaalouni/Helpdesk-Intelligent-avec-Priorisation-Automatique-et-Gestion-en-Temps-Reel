package com.pfa.helpdesk.service;

import com.pfa.helpdesk.entity.Category;
import com.pfa.helpdesk.entity.Ticket;
import com.pfa.helpdesk.entity.enums.Priority;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriorityEngineTest {

    private final PriorityEngine engine = new PriorityEngine();

    private Ticket ticket(String description, String category) {
        Ticket t = new Ticket();
        t.setDescription(description);
        t.setCategory(Category.builder().name(category).build());
        return t;
    }

    @Test
    void urgentNetworkTicketIsCritical() {
        Ticket t = ticket("Panne urgente, plus rien ne marche", "Réseau");
        engine.assignPriorityAndSla(t);
        assertEquals(90, t.getPriorityScore());
        assertEquals(Priority.CRITICAL, t.getPriority());
    }

    @Test
    void minorSoftwareTicketIsLow() {
        Ticket t = ticket("Petit problème d'affichage", "Logiciel");
        engine.assignPriorityAndSla(t);
        assertEquals(Priority.LOW, t.getPriority());
    }

    @Test
    void hardwareTicketWithoutKeywordsIsMedium() {
        Ticket t = ticket("Le clavier ne répond plus", "Matériel");
        engine.assignPriorityAndSla(t);
        assertEquals(Priority.MEDIUM, t.getPriority());
    }
}
