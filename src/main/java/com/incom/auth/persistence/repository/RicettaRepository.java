package com.incom.auth.persistence.repository;

import com.incom.auth.persistence.entity.Ricetta;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Map;

@ApplicationScoped
public class RicettaRepository implements PanacheRepositoryBase<Ricetta, Long> {

    public List<Ricetta> findByUsername(String username) {
        String query = "SELECT r from Ricetta r WHERE r.autore.username = :username";
        return find(query, Map.of("username", username)).list();
    }
}
