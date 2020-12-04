import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

public class Team {
    public String id;
    public String name;
    public float squad_rating = 0.0f;
    private List<Injury> injuries = new ArrayList<>();
    private String form_results;

    // Constants
    public static final float INJURY_PROBABILITY = 5.0f;
    public static final int FORM_RESULTS_SIZE = 6;

    public Team() {
        this.name = "";
        this.squad_rating = 0.0f;
        this.injuries = new ArrayList<>();
        this.form_results = "DDDDDD";
    }

    public Team(String name, float rating) {
        this.name = name;
        this.squad_rating = rating;
        this.injuries = new ArrayList<>();
        this.form_results = "DDDDDD";
    }

    public Document getTeamDocument() {
        return new Document("id", this.id).append("name", this.name).append("squad_rating", this.squad_rating);
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public float getSquadRating() {
        return this.squad_rating;
    }

    public float getForm() {
        float form = 0.0f;
        for (int i = 0; i < form_results.length(); i++) {
            if (form_results.charAt(i) == 'W')
                form += (form_results.length() - i) / 10.0f;
            else if (form_results.charAt(i) == 'L')
                form -= (form_results.length() - i) / 5.0f;
        }
        return form;
    }

    public void updateForm(int type) {
        char result = 'D';
        if (type == 1)
            result = 'W';
        else if (type == 2)
            result = 'L';
        this.form_results = this.form_results.substring(0, FORM_RESULTS_SIZE - 1);
        this.form_results = result + this.form_results;
        physio();
    }

    public void physio() {
        for (int i = 0; i < injuries.size(); i++) {
            injuries.get(i).gameMissed();
            if (injuries.get(i).getGames() < 1) {
                injuries.remove(i);
                i--;
            }
        }

        double _injury = Math.random() * 100;
        if (_injury > (100 - INJURY_PROBABILITY)) {
            float effect = (float) (Math.random() * this.squad_rating * 0.1f);
            Injury injury = new Injury(Injury.getInjury((int) (Math.random() * FORM_RESULTS_SIZE)), effect);
            injuries.add(injury);
        }
    }

    public float getInjuries() {
        float effect = 0.0f;
        for (Injury injury : injuries) {
            if (injury.getGames() > 0) {
                effect += injury.getEffect();
            }
        }
        return effect;
    }
}
