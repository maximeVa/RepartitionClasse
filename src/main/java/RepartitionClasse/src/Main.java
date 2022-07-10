package RepartitionClasse.src;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main{
  public static void main(String[] args) throws IOException {
		// 	final String excelFilePathRead ="C:/Users/ADMIN/Desktop/WS_Eclipse/RepartitionClasseIntelliJ/Classeur1.xlsx";
		//  final String excelFilePathWrite = "C:/Users/ADMIN/Desktop/listeClasse.xlsx";
		final String excelFilePathRead = "A:/PROJET IRL/RepartitionClasse/Classeur1.xlsx";
		final String excelFilePathWrite = "A:/PROJET IRL/RepartitionClasse/LiseClasse.xlsx";

		HashMap<String, Integer> mapPrenomNomToId = new HashMap<>();
		HashMap<Integer, Etudiant> mapIdToEtudiant = new HashMap<>();

		ArrayList<Classe> listeClasse = new ArrayList<>();

	  final int NombrePersonneParClasse = 10;
	  int numClasse = 1;
	  double moyenneGlobale = 0;

	  TreeSet <Etudiant> listeCompleteEtudiant = new TreeSet<>();
	 
	  FileInputStream excelFile = new FileInputStream(excelFilePathRead);
	  Workbook workbook = new XSSFWorkbook(excelFile);

	  //R�cup�ration de la premi�re feuille de calcul du document
	  Sheet dataSheetRead = workbook.getSheetAt(0);

		int compteurCol = 0;
		int compteurLigne = 1;
		int compteur = 0;

		int lastRow =	dataSheetRead.getLastRowNum();
		// Création de tous les étudiants
		while (compteur < lastRow){
			int id = (int) dataSheetRead.getRow(compteurLigne).getCell(compteurCol++).getNumericCellValue();
			String prenom = dataSheetRead.getRow(compteurLigne).getCell(compteurCol++).getStringCellValue();
			String nom = dataSheetRead.getRow(compteurLigne).getCell(compteurCol++).getStringCellValue();
			int genre = (int) dataSheetRead.getRow(compteurLigne).getCell(compteurCol++).getNumericCellValue();
			int moyenne = (int) dataSheetRead.getRow(compteurLigne).getCell(compteurCol).getNumericCellValue();

			Etudiant etu = new Etudiant(id, prenom, nom, genre, moyenne);
			listeCompleteEtudiant.add(etu);
			moyenneGlobale += moyenne;

			compteurLigne++;
			compteurCol = 0;
			compteur++;

			mapPrenomNomToId.put(prenom + nom, id);
			mapIdToEtudiant.put(id, etu);
		}

		// Ajout des binomes de tous les étudiants
		compteurCol = 6;
		compteurLigne = 1;
		compteur = 0;
		while (compteur < lastRow){
			//etudiant
			int id = (int) dataSheetRead.getRow(compteurLigne).getCell(0).getNumericCellValue();

			// binomes
			String premierBinome = dataSheetRead.getRow(compteurLigne).getCell(compteurCol).getStringCellValue();
			int idPremierBinome = mapPrenomNomToId.get(premierBinome.replaceAll(" ", ""));
			Etudiant etuPremierBinome = mapIdToEtudiant.get(idPremierBinome);

			mapIdToEtudiant.get(id).setPremierBinome(etuPremierBinome);

			// En commentaire car actuellement null
			//String secondBinome = dataSheetRead.getRow(compteurLigne).getCell(compteurCol++).getStringCellValue();
			//String troisiemeBinome = dataSheetRead.getRow(compteurLigne).getCell(compteurCol++).getStringCellValue();
			//compteurCol = 7;
			compteurLigne++;
			compteur++;
		}
		System.out.println("\nMoyenneGlobal : " + moyenneGlobale/listeCompleteEtudiant.size());
	  System.out.println("taille liste complete : " + listeCompleteEtudiant.size());

		// *********************
		// CLASSE 1
		// *********************
	  // Cr�ation classes
	  Classe classe1 = new Classe(numClasse++, NombrePersonneParClasse);
	  
	  listeCompleteEtudiant = classe1.faireClasse(listeCompleteEtudiant);

		System.out.println("taille liste complete : " + listeCompleteEtudiant.size());
	  System.out.println("nbr masc: " + classe1.getNbrMasc());
	  System.out.println("nbr fem: " + classe1.getNbrFem());
	  
	  classe1.afficherListeEtudiant();
	  classe1.afficherMoyenne();
		listeClasse.add(classe1);

		// *********************
		// CLASSE 2
		// *********************
		// Cr�ation classes
		Classe classe2 = new Classe(numClasse, NombrePersonneParClasse);

		listeCompleteEtudiant = classe2.faireClasse(listeCompleteEtudiant);

		System.out.println("taille liste complete : " + listeCompleteEtudiant.size());
		System.out.println("nbr masc: " + classe2.getNbrMasc());
		System.out.println("nbr fem: " + classe2.getNbrFem());

		classe2.afficherListeEtudiant();
		classe2.afficherMoyenne();
		listeClasse.add(classe2);

		// fermeture de la lecture
		excelFile.close();

	  // Impression des classes dans le excel
		workbook = new XSSFWorkbook();
		XSSFSheet sheetWrite = (XSSFSheet) workbook.createSheet("Liste Classe");
		sheetWrite.setDefaultColumnWidth(15);

		int rowCount = 0;

		for(Classe cl:listeClasse){
			HashSet<Etudiant> listeEtudiantClasse = cl.getListeEtudiant();
			Row row = sheetWrite.createRow(rowCount++);
			// "Classe n°1"
			Cell cell = row.createCell(0);
			cell.setCellValue("Classe n°" + cl.getNumero());
			// "Nbr Masc : 10"
			cell = row.createCell(1);
			cell.setCellValue("Nbr Masc : " + cl.getNbrMasc());
			// "Nbr Fem : 10"
			cell = row.createCell(2);
			cell.setCellValue("Nbr Fem : " + cl.getNbrFem());
			// "Moyenne : 50"
			cell = row.createCell(3);
			cell.setCellValue("Moyenne : " + cl.getMoyenne());
			// "Nbr Etudiants : 20"
			cell = row.createCell(4);
			cell.setCellValue("Nbr Etudiants Max : " + cl.getNombreEtudiantMax());

			for(Etudiant etu : listeEtudiantClasse){
				row = sheetWrite.createRow(rowCount++);
				cell = row.createCell(1);
				cell.setCellValue(etu.getId());

				cell = row.createCell(2);
				cell.setCellValue(etu.getPrenom());

				cell = row.createCell(3);
				cell.setCellValue(etu.getNom());

				cell = row.createCell(4);
				cell.setCellValue(etu.getMoyenne());

				cell = row.createCell(5);
				cell.setCellValue(etu.getGenre());
			}
			// pour créer une ligne vide
			sheetWrite.createRow(rowCount++);
		}

		//valide les changements et les note dans un nouveau excel
		try {
			//Write the workbook in file system
			FileOutputStream out = new FileOutputStream(excelFilePathWrite);
			workbook.write(out);
			out.close();
			System.out.println("listeClasse.xlsx written successfully on disk.");
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
  }

}


