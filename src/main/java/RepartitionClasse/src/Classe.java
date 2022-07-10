package RepartitionClasse.src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

public class Classe {
	private int numero;
	private HashSet<Etudiant> listeEtudiant;
	private int nbrMasc = 0;
	private int nbrFem = 0;
	private int nombreEtudiantMax;

	public Classe(int numero, int nombreEtudiantMax) {
		// cr�er une liste de nombreEtudiantMax emplacements
		this.listeEtudiant = new HashSet<Etudiant>(nombreEtudiantMax);
		this.numero = numero;
		this.nombreEtudiantMax = nombreEtudiantMax;

	}

	private void ajouterEtudiant(Etudiant etudiant) {

		this.listeEtudiant.add(etudiant);
		if (etudiant.getGenre().equals("masc")) {
			this.nbrMasc++;
		} else if (etudiant.getGenre().equals("fem")) {
			this.nbrFem++;
		}
	}

	private void retirerEtudiant(Etudiant etudiant) {
		this.listeEtudiant.remove(etudiant);
	}

	public void afficherListeEtudiant() {
		System.out.println(this.listeEtudiant.toString());
	}
	
	public void afficherMoyenne() {
		double moyenne = 0;
		int compteur = 0;
		for (Etudiant etu : listeEtudiant) {
			moyenne += etu.getMoyenne();
			compteur++;
		}
		System.out.println(moyenne/compteur);
	}

	public double getMoyenne(){
		double moyenne = 0;
		int compteur = 0;
		for (Etudiant etu : listeEtudiant) {
			moyenne += etu.getMoyenne();
			compteur++;
		}
		return (moyenne/compteur);
	}

	public TreeSet<Etudiant> faireClasse(TreeSet<Etudiant> listeCompleteEtudiant) {

		int nbrEtudiantComplet = listeCompleteEtudiant.size();
		// check si math.floor est vrmt utile (nrmlt oui)
		int incrementation = (int) Math.floor(nbrEtudiantComplet / (this.nombreEtudiantMax - 1));
		int moitieNombreMaxEtudiant = this.nombreEtudiantMax / 2;

		// cr�er une arraylist pour pouvoir remove et parcourir avec un for et une
		// incr�mentation d�fini
		// et non parcourir tous les elems avc un foreach
		List<Etudiant> listeEtudiantArray = new ArrayList<Etudiant>(listeCompleteEtudiant);
		char symb;
		
		for (int i = 0; i < nbrEtudiantComplet; i += incrementation) {
			Etudiant etu = listeEtudiantArray.get(i);

			if (this.nbrFem >= moitieNombreMaxEtudiant && etu.getGenre().equals("fem")) {
				// limite de sexe "fem" atteint
				// on va prendre un autre �tudiant avec un genre contraire
				etu = ajouterEtudiantGenreContraire("fem", nbrEtudiantComplet, listeEtudiantArray, i);

			} else if (this.nbrMasc >= moitieNombreMaxEtudiant && etu.getGenre().equals("masc")) {
				// limite de sexe "masc" atteint
				// on va prendre un autre �tudiant avec un genre contraire
				etu = ajouterEtudiantGenreContraire("masc", nbrEtudiantComplet, listeEtudiantArray, i);

			} else if (this.listeEtudiant.contains(etu)) {
				// si l'�tudiant est d�ja inscrit dans la classe
				// on va prendre un autre �tudiant
				symb = checkSymb(i, nbrEtudiantComplet);
				int compteur = 1;

				if (symb == '+') {
					etu = listeEtudiantArray.get(i + compteur++);
					while (this.listeEtudiant.contains(etu)) {
						etu = listeEtudiantArray.get(i + compteur++);
					}
				} else {
					etu = listeEtudiantArray.get(i - compteur++);
					while (listeEtudiant.contains(etu)) {
						etu = listeEtudiantArray.get(i - compteur++);
					}
				}
			}
			this.ajouterEtudiant(etu);
			if (etu.getPremierBinome() != null) {
				// ajoute le premier elem de la table d'amis
				// si celui-ci n'est pas encore pr�sent dans la classe
				Etudiant premierBinome = etu.getPremierBinome();
				if (!listeEtudiant.contains(premierBinome)) {
					this.ajouterEtudiant(premierBinome);
				}
			}

		}

		// foreach du hashset pour remove les �tudiants ajout�
		for (Etudiant e : listeEtudiant) {
			listeEtudiantArray.remove(e);
		}

		return new TreeSet<Etudiant>(listeEtudiantArray);

	}

	private Etudiant ajouterEtudiantGenreContraire(String genre, int nbrEtudiantComplet,
			List<Etudiant> listeEtudiantArray, int i) {
		char symb;
		Etudiant etu;
		symb = checkSymb(i, nbrEtudiantComplet);
		int compteur = 1;

		if (symb == '+') {
			etu = listeEtudiantArray.get(i + compteur++);
			while (etu.getGenre().equals(genre) || this.listeEtudiant.contains(etu)) {
				etu = listeEtudiantArray.get(i + compteur++);
			}
		} else {
			etu = listeEtudiantArray.get(i - compteur++);
			while (etu.getGenre().equals(genre) || this.listeEtudiant.contains(etu)) {
				etu = listeEtudiantArray.get(i - compteur++);
			}
		}
		return etu;
	}

	private char checkSymb(int indice, int tailleMax) {
		if (indice >= tailleMax / 2) {
			return '-';
		}
		return '+';

	}

	public int getNbrMasc() {
		return nbrMasc;
	}

	public int getNbrFem() {
		return nbrFem;
	}

	public HashSet<Etudiant> getListeEtudiant() {
		return listeEtudiant;
	}

	public int getNumero() {
		return numero;
	}

	public int getNombreEtudiantMax() {
		return nombreEtudiantMax;
	}
}
