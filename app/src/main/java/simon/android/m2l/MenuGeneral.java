package simon.android.m2l;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.LinearLayout;



public class MenuGeneral extends AppCompatActivity {

    // les boutons
    private Button buttonConsulterSalles;
    private Button buttonConsulterReservations;
    private Button buttonConfirmerReservation;
    private Button buttonAnnulerReservation;
    private Button buttonChangerMdp;
    private Button buttonCreerUtilisateur;
    private Button buttonSupprimerUtilisateur;
    private Button buttonQuitter;
    // le passage des données entre activités se fait au moyen des "extras" qui sont portés par les Intent.
    // un extra est une couple de clé/valeur
    // nous en utiliserons 3 ici, dont voici les 3 clés et les 3 variables associées :
    private final String EXTRA_NOM = "nom";
    private final String EXTRA_MDP = "mdp";
    private final String EXTRA_TYPE_UTILISATEUR = "typeUtilisateur";
    private String nom;
    private String mdp;
    private String typeUtilisateur;       // "utilisateur" ou "administrateur"
    // codes utilisés avec la méthode startActivityForResult
    // ces nombres peuvent être quelconques, mais doivent être différents les uns des autres
    private final int CODE_RESULTAT_CHANGEMENT_MDP = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_general);

        // récupération des Button grâce à leur ID
        buttonConsulterSalles = (Button) findViewById(R.id.menu_buttonConsulterSalles);
        buttonConsulterReservations = (Button) findViewById(R.id.menu_buttonConsulterReservations);
        buttonConfirmerReservation = (Button) findViewById(R.id.menu_buttonConfirmerReservation);
        buttonAnnulerReservation = (Button) findViewById(R.id.menu_buttonAnnulerReservation);
        buttonChangerMdp = (Button) findViewById(R.id.menu_buttonChangerMdp);
        buttonCreerUtilisateur = (Button) findViewById(R.id.menu_buttonCreerUtilisateur);
        buttonSupprimerUtilisateur = (Button) findViewById(R.id.menu_buttonSupprimerUtilisateur);
        buttonQuitter = (Button) findViewById(R.id.menu_buttonQuitter);

        // association d'un écouteur d'évenement à chaque bouton
        buttonConsulterSalles.setOnClickListener ( new buttonConsulterSallesClickListener());
        buttonConsulterReservations.setOnClickListener ( new buttonConsulterReservationsClickListener());
        buttonConfirmerReservation.setOnClickListener ( new buttonConfirmerReservationClickListener());
        buttonAnnulerReservation.setOnClickListener ( new buttonAnnulerReservationClickListener());
        buttonChangerMdp.setOnClickListener ( new buttonChangerMdpClickListener());
        buttonCreerUtilisateur.setOnClickListener ( new buttonCreerUtilisateurClickListener());
        buttonSupprimerUtilisateur.setOnClickListener ( new buttonSupprimerUtilisateurClickListener());
        buttonQuitter.setOnClickListener ( new buttonQuitterClickListener());

        // récupération du nom, du mot de passe et du type d'utilisateur passés par l'activité précédente
        Intent uneIntent = getIntent();
        nom = uneIntent.getStringExtra(EXTRA_NOM);
        mdp = uneIntent.getStringExtra(EXTRA_MDP);
        typeUtilisateur = uneIntent.getStringExtra(EXTRA_TYPE_UTILISATEUR);

        // boutons buttonCreerUtilisateur et buttonSupprimerUtilisateur visibles seulement pour un administrateur
        if ( typeUtilisateur.equals("utilisateur") ) {
            // supprimer les 2 boutons pour faire remonter le bouton Quitter
            LinearLayout leLayout = (LinearLayout) findViewById(R.id.menu_linearLayout1);
            leLayout.removeView(buttonCreerUtilisateur);
            leLayout.removeView(buttonSupprimerUtilisateur);
        }
    }
    /** récupére les données fournies par une activité appelée avec startActivityForResult
     *   ici, une seule activité concernée : celle qui permet de changer le mot de passe
     *   il faut alors récupérer ce nouveau mot de passe pour pouvoir l'utiliser lors des appels de services web
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent uneIntent) {
        super.onActivityResult(requestCode, resultCode, uneIntent);

        switch (requestCode) {
            case CODE_RESULTAT_CHANGEMENT_MDP :
                mdp = uneIntent.getStringExtra(EXTRA_MDP); break;
        }

    } // FIN DE LA CLASSE



    /** classe interne pour gérer le clic sur le bouton buttonConsulterSalles. */
    private class buttonConsulterSallesClickListener implements View.OnClickListener {
        public void onClick(View v) {
            // crée une Intent pour lancer l'activité
            Intent uneIntent = new Intent(MenuGeneral.this, ConsulterSalles.class);
            // passe nom, mdp et typeUtilisateur à l'Intent
            uneIntent.putExtra(EXTRA_NOM, nom);
            uneIntent.putExtra(EXTRA_MDP, mdp);
            uneIntent.putExtra(EXTRA_TYPE_UTILISATEUR, typeUtilisateur);
            // démarre l'activité à partir de l'Intent
            startActivity(uneIntent);
        }
    }


    /** classe interne pour gérer le clic sur le bouton buttonConsulterReservations. */
    private class buttonConsulterReservationsClickListener implements View.OnClickListener{
        public void onClick(View v) {
            // crée une Intent pour lancer l'activité
            Intent uneIntent = new Intent(MenuGeneral.this, ConsulterReservations.class);
            // passe nom, mdp et typeUtilisateur à l'Intent
            uneIntent.putExtra(EXTRA_NOM, nom);
            uneIntent.putExtra(EXTRA_MDP, mdp);
            uneIntent.putExtra(EXTRA_TYPE_UTILISATEUR, typeUtilisateur);
            // démarre l'activité à partir de l'Intent
            startActivity(uneIntent);
        }
    }


    /** classe interne pour gérer le clic sur le bouton buttonConfirmerReservation. */
    private class buttonConfirmerReservationClickListener implements View.OnClickListener{
        public void onClick(View v) {
            // crée une Intent pour lancer l'activité
            Intent uneIntent = new Intent(MenuGeneral.this, ConfirmerReservation.class);
            // passe nom, mdp et typeUtilisateur à l'Intent
            uneIntent.putExtra(EXTRA_NOM, nom);
            uneIntent.putExtra(EXTRA_MDP, mdp);
            uneIntent.putExtra(EXTRA_TYPE_UTILISATEUR, typeUtilisateur);
            // démarre l'activité à partir de l'Intent
            startActivity(uneIntent);
        }
    }


    /** classe interne pour gérer le clic sur le bouton buttonAnnulerReservation. */
    private class buttonAnnulerReservationClickListener implements View.OnClickListener{
        public void onClick(View v) {
            // crée une Intent pour lancer l'activité
            Intent uneIntent = new Intent(MenuGeneral.this, AnnulerReservation.class);
            // passe nom, mdp et typeUtilisateur à l'Intent
            uneIntent.putExtra(EXTRA_NOM, nom);
            uneIntent.putExtra(EXTRA_MDP, mdp);
            uneIntent.putExtra(EXTRA_TYPE_UTILISATEUR, typeUtilisateur);
            // démarre l'activité à partir de l'Intent
            startActivity(uneIntent);
        }
    }


    /** classe interne pour gérer le clic sur le bouton buttonChangerMdp. */
    private class buttonChangerMdpClickListener implements View.OnClickListener{
        public void onClick(View v) {
            // crée une Intent pour lancer l'activité
            Intent uneIntent = new Intent(MenuGeneral.this, ChangerMdp.class);
            // passe nom, mdp et typeUtilisateur à l'Intent
            uneIntent.putExtra(EXTRA_NOM, nom);
            uneIntent.putExtra(EXTRA_MDP, mdp);
            uneIntent.putExtra(EXTRA_TYPE_UTILISATEUR, typeUtilisateur);
            // démarre l'activité à partir de l'Intent et attend un résultat (le nouveau mdp)
            startActivityForResult(uneIntent, CODE_RESULTAT_CHANGEMENT_MDP);
        }
    }


    /** classe interne pour gérer le clic sur le bouton buttonCreerUtilisateur. */
    private class buttonCreerUtilisateurClickListener implements View.OnClickListener{
        public void onClick(View v) {
            // crée une Intent pour lancer l'activité
            Intent uneIntent = new Intent(MenuGeneral.this, CreerUtilisateur.class);
            // passe nom, mdp et typeUtilisateur à l'Intent
            uneIntent.putExtra(EXTRA_NOM, nom);
            uneIntent.putExtra(EXTRA_MDP, mdp);
            uneIntent.putExtra(EXTRA_TYPE_UTILISATEUR, typeUtilisateur);
            // démarre l'activité à partir de l'Intent
            startActivity(uneIntent);
        }
    }

    /** classe interne pour gérer le clic sur le bouton buttonSupprimerUtilisateur. */
    private class buttonSupprimerUtilisateurClickListener implements View.OnClickListener{
        public void onClick(View v) {
            // crée une Intent pour lancer l'activité
            Intent uneIntent = new Intent(MenuGeneral.this, SupprimerUtilisateur.class);
            // passe nom, mdp et typeUtilisateur à l'Intent
            uneIntent.putExtra(EXTRA_NOM, nom);
            uneIntent.putExtra(EXTRA_MDP, mdp);
            uneIntent.putExtra(EXTRA_TYPE_UTILISATEUR, typeUtilisateur);
            // démarre l'activité à partir de l'Intent
            startActivity(uneIntent);
        }
    }

    /** classe interne pour gérer le clic sur le bouton buttonQuitter. */
    private class buttonQuitterClickListener implements View.OnClickListener{
        public void onClick(View v) {
            finish();
        }
    }


} // FIN DE LA CLASSE
