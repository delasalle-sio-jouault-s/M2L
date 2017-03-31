/**
 * Application de suivi des réservations de la Maison des Ligues de Lorraine
 * Thème : développement et test des classes Salle, Reservation, Utilisateur et Passerelle
 * Auteur : JM CARTRON
 * Dernière mise à jour : 15/3/2017
 */

package simon.classes;

/**
 * Cette classe représente une salle
 */
public class Salle {

	/** Membres privés */
	private int _id;				// identifiant de la salle
	private String _room_name;		// nom de la salle
	private int _capacity;			// capacité (nombre de places)
	private String _area_name;		// nom du domaine dont fait partie la salle

	/** Constructeur  */	
	public Salle(int unId, String unRoomName, int unCapacity, String unAreaName) {
		this._id = unId;
		this._room_name = unRoomName;
		this._capacity = unCapacity;
		this._area_name = unAreaName;
	}	
	
	/** Accesseurs */	
	public int getId() {
		return _id;
	}
	public void setId(int unId) {
		this._id = unId;
	}
	public String getRoomName() {
		return _room_name;
	}
	public void setRoomName(String unRoomName) {
		this._room_name = unRoomName;
	}
	public int getCapacity() {
		return _capacity;
	}
	public void setCapacity(int unCapacity) {
		this._capacity = unCapacity;
	}
	public String getAreaName() {
		return _area_name;
	}
	public void setAreaName(String unAreaName) {
		this._area_name = unAreaName;
	}
	
	/** Méthodes publiques */
	public String toString() {
		String msg = "";
		msg += "id :\t\t" + this._id + "\n";
		msg += "room_name :\t" + this._room_name + "\n";
		msg += "capacity :\t\t" + this._capacity + "\n";
		msg += "area_name :\t" + this._area_name + "\n";
		return msg;
	}
}
