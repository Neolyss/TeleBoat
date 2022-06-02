package fr.ensim.interop.introrest.model.joke;


import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Joke{

    @Id
    public Long id;
    public String type;
    public String joke;
    public String answer;
    @ManyToOne
    @JoinColumn(name = "notes_id")
    public Notes notes = new Notes();

    public Notes getNotes() {
        return notes;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
    }

    public float getNote () {
        int somme = notes.un;
        somme += notes.deux * 2;
        somme += notes.trois * 3;
        somme += notes.quatre * 4;
        somme += notes.cinq * 5;
        return somme/(getNbNote() == 0 ? 1 : getNbNote());
    }
    public int getNbNote () {
        return notes.zero + notes.un + notes.deux + notes.trois + notes.quatre + notes.cinq;
    }

    @Override
    public String toString() {
        return "Vous m'avez demandez une blague ??? En voulà une p'tite\n"+ joke +"\n→ "+ answer+"\n\n Note : "+getNote();
    }

}

