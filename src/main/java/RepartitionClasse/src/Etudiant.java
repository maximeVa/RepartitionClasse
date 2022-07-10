package RepartitionClasse.src;

public class Etudiant implements Comparable<Etudiant> {
	private int id;
	private String prenom;
	private String nom;
	private String genre;
	// moyenne sur 100
	private double moyenne;
	private Etudiant premierBinome;
	private Etudiant secondBinome;
	private Etudiant troisiemeBinome;

	public Etudiant getPremierBinome() {
		return premierBinome;
	}

	public void setPremierBinome(Etudiant premierBinome) {
		this.premierBinome = premierBinome;
	}

	public Etudiant getSecondBinome() {
		return secondBinome;
	}

	public void setSecondBinome(Etudiant secondBinome) {
		this.secondBinome = secondBinome;
	}

	public Etudiant getTroisiemeBinome() {
		return troisiemeBinome;
	}

	public void setTroisiemeBinome(Etudiant troisiemeBinome) {
		this.troisiemeBinome = troisiemeBinome;
	}

	public Etudiant(int id, String prenom, String nom, int genre, double moyenne) {
		super();
		this.id = id;
		this.prenom = prenom;
		this.nom = nom;
		if (genre == 1) {
			this.genre = "masc";
		} else {
			this.genre = "fem";
		}
		this.moyenne = moyenne;

		this.premierBinome = null;
		this.secondBinome = null;
		this.troisiemeBinome = null;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public double getMoyenne() {
		return moyenne;
	}

	public void setMoyenne(double moyenne) {
		this.moyenne = moyenne;
	}

	@Override
	public String toString() {
		String toReturn = "Etudiant [id=" + id + ", prenom=" + prenom + ", nom=" + nom + ", genre=" + genre
				+ ", moyenne=" + moyenne;
		if (this.premierBinome != null) {
			toReturn += ", premierBinome=" + this.premierBinome.getId();
		}
		if (this.secondBinome != null) {
			toReturn += ", secondBinome=" + this.secondBinome.getId();
		}
		if (this.troisiemeBinome != null) {
			toReturn += ", troisiemeBinome=" + this.troisiemeBinome.getId();
		}
		
		toReturn += "]";
		return toReturn;
	}

	@Override
	public int compareTo(Etudiant e) {
		// TODO Auto-generated method stub
		int res = Double.compare(e.getMoyenne(), this.getMoyenne());
		if (res == 0) {
			res = e.getNom().compareTo(this.getNom());
		}
		return res;
	}

}
