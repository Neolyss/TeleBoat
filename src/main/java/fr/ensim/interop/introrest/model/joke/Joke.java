package fr.ensim.interop.introrest.model.joke;


import javax.persistence.*;

@Entity
public class Joke{

    @Id
    public Long id;
    public String type;
    public String joke;
    public String answer;
    public int note;

}
