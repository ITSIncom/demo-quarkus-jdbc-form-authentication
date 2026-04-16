package com.incom.auth.persistence.entity;

import jakarta.persistence.*;
import jakarta.ws.rs.GET;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "type", nullable = false, unique = true)
    private String type;

    @ManyToMany(mappedBy = "categories")
    private List<Ricetta> ricette = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Ricetta> getRicette() {
        return ricette;
    }

    public void setRicette(List<Ricetta> ricette) {
        this.ricette = ricette;
    }

    @Override
    public String toString() {
        return "Category{" +
               "id=" + id +
               ", type='" + type + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Category)) {
            return false;
        }
        if (this.id != null && Objects.equals(this.id, ((Category) obj).id)) {
            return true;
        }
        return Objects.equals(this.type, ((Category) obj).type);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
