package simon.android.m2l;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.CheckBox;
import android.view.View;
import simon.classes.*;
import android.os.AsyncTask;
import android.content.Intent;
import android.content.SharedPreferences;


public class MainActivity extends AppCompatActivity {

    // les zones de saisie et les variables associées
    private EditText editTextNom;
    private EditText editTextMdp;
    private String nom;
    private String mdp;
    private String typeUtilisateur;       // "utilisateur" ou "administrateur"
    // les 2 cases à cocher
    private CheckBox caseMdpVisible;
    private CheckBox caseMemoriser;
    // les 2 boutons
    private Button buttonConnecter;
    private Button buttonMdpOublie;
    // la zone d'affichage de message
    private TextView textViewMessage;
    // le passage des données entre activités se fait au moyen des "extras" qui sont portés par les Intent.
    // un extra est une couple de clé/valeur
    // nous en utiliserons 3 ici, dont voici les 3 clés :
    private final String EXTRA_NOM = "nom";
    private final String EXTRA_MDP = "mdp";
    private final String EXTRA_TYPE_UTILISATEUR = "typeUtilisateur";
    // la mémorisation des données se fait au moyen de couples clé/valeur :
    private final String KEY_NOM = "nom";
    private final String KEY_MDP = "mdp";



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // récupération des objets XML grâce à leur ID
        editTextNom = (EditText) findViewById(R.id.main_editTextNom);
        editTextMdp = (EditText) findViewById(R.id.main_editTextMdp);

        caseMemoriser = (CheckBox) findViewById(R.id.main_checkBoxMemoriser);
        caseMemoriser.setChecked(true);       // case cochée au démarrage

        caseMdpVisible = (CheckBox) findViewById(R.id.main_checkBoxMdpVisible);
        caseMdpVisible.setChecked(false);  // case décochée au démarrage

        buttonConnecter = (Button) findViewById(R.id.main_buttonConnecter);
        buttonMdpOublie = (Button) findViewById(R.id.main_buttonMdpOublie);

        textViewMessage = (TextView) findViewById(R.id.main_textViewMessage);

        // association d'un écouteur d'évenement à chaque bouton
        buttonConnecter.setOnClickListener ( new buttonConnecterClickListener());
        buttonMdpOublie.setOnClickListener ( new buttonMdpOublieClickListener());

        // Récupération des données de connexion dans le cas où elles ont été mémorisées lors de la dernière connexion
        SharedPreferences mesDonnees = getPreferences(MODE_PRIVATE);
        nom = mesDonnees.getString(KEY_NOM, "");
        mdp = mesDonnees.getString(KEY_MDP, "");
        // si des données avaient été mémorisées, elles sont proposées dans les zones de saisies
        if ( ! nom.equals("") ) editTextNom.setText(nom);
        if ( ! mdp.equals("") ) editTextMdp.setText(mdp);
    }


    /** classe interne pour gérer le clic sur le bouton buttonConnecter. */
    private class buttonConnecterClickListener implements View.OnClickListener {
        public void onClick(View v) {
            nom = editTextNom.getText().toString().trim();
            mdp = editTextMdp.getText().toString().trim();

            // Mémorisation des données de connexion si la case est cochée
            SharedPreferences mesDonnees = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor monEditor = mesDonnees.edit();
            if (caseMemoriser.isChecked()) {
                monEditor.putString(KEY_NOM, nom);
                monEditor.putString(KEY_MDP, mdp);
            }
            else {
                monEditor.putString(KEY_NOM, "");
                monEditor.putString(KEY_MDP, "");
            }
            monEditor.commit();

            // appel du service web Connecter avec une tâche asynchrone
            new TacheConnecter().execute();
        }
    }



    /** classe interne pour gérer le clic sur le bouton buttonMdpOublie. */
    private class buttonMdpOublieClickListener implements View.OnClickListener {
        public void onClick(View v) {
        }
    }
// --------------------------------------------------------------------------------------------------------------------
// -------------------------------- Tâche asynchrone TacheConnecter -----------------------------------
// --------------------------------------------------------------------------------------------------------------------

// l'appel à un service web peut prendre quelques secondes
// à partir d'Android 3.0, une requête HTTP doit être effectuée à l'intérieur d'une tâche asynchrone
// doc sur la classe AsyncTask à l'adresse http://developer.android.com/reference/android/os/AsyncTask.html

    // appel du service web Connecter
    private class TacheConnecter extends AsyncTask<Void, Void, String> {
        protected String doInBackground(Void... params) {
            // cette méthode permet de lancer l'exécution de la tâche longue
            String msg = Passerelle.connecter(nom, mdp);
            return msg;
        }
        protected void onPostExecute(String msg) {
            // cette méthode est automatiquement appelée quand la tâche longue se termine
            if (msg.startsWith("Erreur")) {
                textViewMessage.setText(msg);
            }
            else {
                // détermine le type d'utilisateur
                if (msg.startsWith("Utilisateur authentifié")) typeUtilisateur = "utilisateur";
                if (msg.startsWith("Administrateur authentifié")) typeUtilisateur = "administrateur";

                // crée une Intent pour lancer le menu général
                Intent uneIntent = new Intent(MainActivity.this, MenuGeneral.class);
                // passe nom, mdp et typeUtilisateur à l'Intent
                uneIntent.putExtra(EXTRA_NOM, nom);
                uneIntent.putExtra(EXTRA_MDP, mdp);
                uneIntent.putExtra(EXTRA_TYPE_UTILISATEUR, typeUtilisateur);
                // démarre l'activité à partir de l'Intent
                startActivity(uneIntent);

                // termine l'activité en cours
                finish();
            }
        }
    } // fin tâche asynchrone TacheConnecter

} // FIN DE LA CLASSE
