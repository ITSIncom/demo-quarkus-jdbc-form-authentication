package com.incom.auth.persistence.entity;

import jakarta.persistence.*;
import jdk.jfr.Enabled;

import java.util.*;

@Entity
@Table(name = "ricetta")
public class Ricetta {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "titolo")
    private String titolo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "autore_id", nullable = false)
    private User autore;

    @ManyToMany
    @JoinTable(
            name = "ricetta_categoria",
            joinColumns = @JoinColumn(name = "ricetta_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"),
            uniqueConstraints = @UniqueConstraint(
                    name = "uq_ricetta_id_category_id",
                    columnNames = {"ricetta_id", "category_id"}
            )
    )
    private Set<Category> categories = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public User getAutore() {
        return autore;
    }

    public void setAutore(User autore) {
        this.autore = autore;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Ricetta{" +
               "id=" + id +
               ", titolo='" + titolo + '\'' +
               ", autore=" + autore +
               '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Ricetta)) {
            return false;
        }
        return this.id != null && Objects.equals(this.id, ((Ricetta) obj).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
