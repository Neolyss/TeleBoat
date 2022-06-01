package fr.ensim.interop.introrest.model.question;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataQuestion {
    @JsonProperty("fournisseur")
    private String fournisseur;
    @JsonProperty("rédacteur")
    private String redacteur;
    @JsonProperty("thème")
    private String theme;
    @JsonProperty("difficulté")
    private String difficulte;
    @JsonProperty("quizz")
    Quizz QuizzObject;



    public String getFournisseur() {
        return fournisseur;
    }

    public String getRedacteur() {
        return redacteur;
    }

    public String getTheme() {
        return theme;
    }

    public String getDifficulte() {
        return difficulte;
    }

    public Quizz getQuizz() {
        return QuizzObject;
    }

    // Setter Methods

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    public void setRedacteur(String réedacteur) {
        this.redacteur = redacteur;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setDifficulte(String difficulte) {
        this.difficulte = difficulte;
    }

    public void setQuizz(Quizz quizzObject) {
        this.QuizzObject = quizzObject;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Quizz {
    @JsonProperty("Fr")
    Fr FrObject;


    // Getter Methods

    public Fr getFr() {
        return FrObject;
    }

    // Setter Methods

    public void setFr(Fr frObject) {
        this.FrObject = frObject;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Fr {
    @JsonProperty("débutant")
    ArrayList<Debutant> debutant = new ArrayList<Debutant>();

    @JsonProperty("confirmé")
    ArrayList<Confirme> confirme = new ArrayList<Confirme>();

    @JsonProperty("expert")
    ArrayList<Expert> expert = new ArrayList<Expert>();

    public ArrayList<Debutant> getDebutant() {
        return debutant;
    }

    public void setDebutant(ArrayList<Debutant> debutant) {
        this.debutant = debutant;
    }

    public ArrayList<Confirme> getConfirme() {
        return confirme;
    }

    public void setConfirme(ArrayList<Confirme> confirme) {
        this.confirme = confirme;
    }

    public ArrayList<Expert> getExpert() {
        return expert;
    }

    public void setExpert(ArrayList<Expert> expert) {
        this.expert = expert;
    }
}
@JsonIgnoreProperties(ignoreUnknown = true)
class Confirme{
    @JsonProperty("id")
    public int id;

    @JsonProperty("question")
    public String question;

    @JsonProperty("propositions")
    public ArrayList<String> propositions;

    @JsonProperty("réponse")
    public String reponse;

    @JsonProperty("anecdote")
    public String anecdote;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getPropositions() {
        return propositions;
    }

    public void setPropositions(ArrayList<String> propositions) {
        this.propositions = propositions;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public String getAnecdote() {
        return anecdote;
    }

    public void setAnecdote(String anecdote) {
        this.anecdote = anecdote;
    }
}
@JsonIgnoreProperties(ignoreUnknown = true)
class Debutant{
    @JsonProperty("id")
    public int id;

    @JsonProperty("question")
    public String question;

    @JsonProperty("propositions")
    public ArrayList<String> propositions;

    @JsonProperty("réponse")
    public String reponse;

    @JsonProperty("anecdote")
    public String anecdote;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getPropositions() {
        return propositions;
    }

    public void setPropositions(ArrayList<String> propositions) {
        this.propositions = propositions;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public String getAnecdote() {
        return anecdote;
    }

    public void setAnecdote(String anecdote) {
        this.anecdote = anecdote;
    }
}
@JsonIgnoreProperties(ignoreUnknown = true)
class Expert{
    @JsonProperty("id")
    public int id;

    @JsonProperty("question")
    public String question;

    @JsonProperty("propositions")
    public ArrayList<String> propositions;

    @JsonProperty("réponse")
    public String reponse;

    @JsonProperty("anecdote")
    public String anecdote;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getPropositions() {
        return propositions;
    }

    public void setPropositions(ArrayList<String> propositions) {
        this.propositions = propositions;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public String getAnecdote() {
        return anecdote;
    }

    public void setAnecdote(String anecdote) {
        this.anecdote = anecdote;
    }
}
