package simon.android.m2l;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;
import android.os.AsyncTask;
import simon.classes.*;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.SimpleAdapter;


public class ConsulterReservations extends AppCompatActivity {

    // le bouton
    private Button buttonRetourner;
    // les zones d'affichage de message et le ListView
    private TextView textViewListeReservations;
    private TextView textViewMessage;
    private ListView listViewReservations;
    // le passage des données entre activités se fait au moyen des "extras" qui sont portés par les Intent.
    // un extra est une couple de clé/valeur
    // nous en utiliserons 2 ici, dont voici les 2 clés et les 2 variables associées :
    private final String EXTRA_NOM = "nom";
    private final String EXTRA_MDP = "mdp";
    private String nom;
    private String mdp;
    Utilisateur unUtilisateur;
    // -----------------------------------------------------------------------------------------------------------------------------
    // -------------------------- Tâche asynchrone TacheConsulterReservations -----------------------------
    // -----------------------------------------------------------------------------------------------------------------------------

    // l'appel à un service web peut prendre quelques secondes
    // à partir d'Android 3.0, une requête HTTP doit être effectuée à l'intérieur d'une tâche asynchrone
    // doc sur la classe AsyncTask à l'adresse http://developer.android.com/reference/android/os/AsyncTask.html

    // appel du service web ConsulterReservations
    private class TacheConsulterReservations extends AsyncTask<Void, Void, String> {
        protected String doInBackground(Void... params) {
            // cette méthode permet de lancer l'exécution de la tâche longue
            unUtilisateur = new Utilisateur();
            unUtilisateur.setName(nom);
            unUtilisateur.setPassword(mdp);
            String msg = Passerelle.consulterReservations(unUtilisateur);
            return msg;
        }
        protected void onPostExecute(String msg) {
            // cette méthode est automatiquement appelée quand la tâche longue se termine
            if (msg.startsWith("Erreur")) {
                textViewMessage.setText(msg);
            }
            else {
                String message = "Nombre de réservations : " + unUtilisateur.getNbReservations();
                textViewListeReservations.setText(message);

                // création de la ArrayList qui permettra de remplir la listView
                ArrayList<HashMap<String, String>> lesElementsDuListView = new ArrayList<HashMap<String, String>>();

                for (int i = 0 ; i < unUtilisateur.getNbReservations() ; i++) {
                    Reservation laReservation = unUtilisateur.getLaReservation(i);

                    // création d'une HashMap pour insérer les informations d'une réservation
                    HashMap<String, String> unElementDuListView = new HashMap<String, String>();
                    unElementDuListView.put("Id_Date", "Réservation n° " + laReservation.getId() + " du " +
                            Outils.FormaterDate(laReservation.getTimeStamp()));
                    unElementDuListView.put("Debut", "Début : " + Outils.FormaterDateHeure(laReservation.getStartTime()));
                    unElementDuListView.put("Fin", "Fin : " + Outils.FormaterDateHeure(laReservation.getEndTime()));
                    unElementDuListView.put("Salle", "Salle : " + laReservation.getRoomName());
                    if (laReservation.getStatus() == 0)
                        message = "Etat : confirmé      Digicode : " + laReservation.getDigicode();
                    else
                        message = "Etat : provisoire";
                    unElementDuListView.put("Etat_Digicode", message);

                    // ajoute le HashMap dans le ArrayList
                    lesElementsDuListView.add(unElementDuListView);
                }

                // crée un SimpleAdapter qui met les items de la liste (lesElementsDuListView) dans la vue item_listview
                SimpleAdapter monAdapter = new SimpleAdapter (getBaseContext(), lesElementsDuListView,
                        R.layout.items_listview_reservations,
                        new String[] {
                                "Id_Date",
                                "Debut",
                                "Fin",
                                "Salle",
                                "Etat_Digicode"},
                        new int[] {
                                R.id.textView_Id_Date,
                                R.id.textView_Debut,
                                R.id.textView_Fin,
                                R.id.textView_Salle,
                                R.id.textView_Etat_Digicode});

                // attribuer au listView l'adapter que l'on vient de créer
                listViewReservations.setAdapter(monAdapter);
            }
            buttonRetourner.setEnabled(true);
        }
    } // fin tâche asynchrone TacheConsulterReservations




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_reservations);

        // récupération du Button grâce à son ID
        buttonRetourner = (Button) findViewById(R.id.consulter_reservations_buttonRetourner);

        // récupération des TextView et ListView grâce à leur ID
        textViewListeReservations = (TextView) findViewById(R.id.consulter_reservations_textViewNombreReservations);
        textViewMessage = (TextView) findViewById(R.id.consulter_reservations_textViewMessage);
        listViewReservations = (ListView) findViewById(R.id.consulter_reservations_listView);

        // association d'un écouteur d'évenement au bouton
        buttonRetourner.setOnClickListener ( new buttonRetournerClickListener());
        buttonRetourner.setEnabled(false);

        // récupération du nom et du mot de passe passés par l'activité précédente
        Intent uneIntent = getIntent();
        nom = uneIntent.getStringExtra(EXTRA_NOM);
        mdp = uneIntent.getStringExtra(EXTRA_MDP);

        // appel du service web ConsulterReservations avec une tâche asynchrone
        new TacheConsulterReservations().execute();
    }


/** classe interne pour gérer le clic sur le bouton buttonRetourner. */
    private class buttonRetournerClickListener implements View.OnClickListener {
        public void onClick(View v) {
            finish();
        }
    }

} // FIN DE LA CLASSE
