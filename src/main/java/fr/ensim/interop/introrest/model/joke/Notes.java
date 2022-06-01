package fr.ensim.interop.introrest.model.joke;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Notes {
    @Id
    @GeneratedValue()
    private int id;
    public int zero = 0;
    public int un = 0;
    public int deux = 0;
    public int trois = 0;
    public int quatre = 0;
    public int cinq = 0;
}

