import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Dictionnaire {
    private ArrayList<String> tab;


    //Constructeur
    public Dictionnaire(){
        tab= new ArrayList<String>(); ;
    }


    //méthode qui va charger le dictionnaire avec les mots contenus dans le fichier texte
    public void lectureDictionnaire(String fileName){
        try {
            File file=new File(fileName);
            //System.out.println("0");
            FileReader fileReader = new FileReader(file);
            //System.out.println("1");
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            //System.out.println("2");
            String line;

            while ((line=bufferedReader.readLine()) != null) {
                tab.add(line);
                //System.out.println("3");
            }
            System.out.println("Sortie de la boucle");
            fileReader.close();
        }
        catch (IOException e) {
            System.err.println("Erreur");
        }
        int taille=tab.size();
        System.out.println(taille);
    }


    //méthode qui affiche tous les mots contenus dans la structure interne tab de notre dictionnaire
    public void afficherDictionnaire(){
        for(String mot:tab){
            System.out.println(mot+"\n");
        }
    }


    //méthode qui retourne false si le mot n'est pas dans le dictionnaire 
    //on considerera qu'il est mal orthographié
    public boolean existe(String motTest){
        for(String mot:tab){
            if (mot.equalsIgnoreCase(motTest)){
                return true;
            }
        }
        return false;
    }


    //méthode qui calcule la distance entre deux mots
    public static int distance(String s1, String s2){
        int edits[][]=new int[s1.length()+1][s2.length()+1];
        for(int i=0;i<=s1.length();i++)
            edits[i][0]=i;
        for(int j=1;j<=s2.length();j++)
            edits[0][j]=j;
        for(int i=1;i<=s1.length();i++){
            for(int j=1;j<=s2.length();j++){
                int u=(s1.charAt(i-1)==s2.charAt(j-1)?0:1);
                edits[i][j]=Math.min(
                        edits[i-1][j]+1,
                        Math.min(
                                edits[i][j-1]+1,
                                edits[i-1][j-1]+u
                        )
                );
            }
        }
        return edits[s1.length()][s2.length()];
    }

    //méthode qui renvoie le tableau des 5 mots les plus proches d'un mot mal orthographie
    //on suppose que la méthode est appelé quand un mot mal orthographié est détecté
    //(donc pas besoin de tester dans cette méthode si le mot existe)
    public String[] rechercheMotsProches(String motTest){
        String[] motsProches= new String[5];
        

            //on crée un ArrayList tabDistances pour répertorier les distances de tous les mots du dictionnaire
            //tabDistances[i] contient la distance entre le mot testé et le mot d'indice i de notre dictionnaire
            ArrayList<Integer> tabDistances = new ArrayList<Integer>();
            int distance;
            for(int i = 0; i < tab.size(); i++) {
                distance = distance(motTest, tab.get(i));
                tabDistances.add(distance);
            }

            //on va avoir besoin de trier les distances pour trouver les 5 plus petites valeurs
            //mais il faut conserver les indices correspondants
            //pour cela on construit un tableau à deux dimensions qui associe chaque indice de mot à sa distance avec le mot testé
            //on trie ensuite ce tableau en fonction des distances uniquement
            int[][] tabDistancesIndices= new int[tabDistances.size()][2];
            for (int i=0; i<tabDistances.size();i++){
                tabDistancesIndices[i][0]=tabDistances.get(i);
                tabDistancesIndices[i][1]=i;
            }
            
            Arrays.sort(tabDistancesIndices, (a, b) -> Integer.compare(a[0], b[0]));
            //le mot le plus proche du mot testé est désormais a la pemière ligne de notre tableau a 2D
            int indice0 = tabDistancesIndices[0][1];
            int indice1 = tabDistancesIndices[1][1];
            int indice2 = tabDistancesIndices[2][1];
            int indice3 = tabDistancesIndices[3][1];
            int indice4 = tabDistancesIndices[4][1];

            //on recupere les 5 mots les plus proches grace a leur indice dans le dictionnaire
            motsProches[0]= tab.get(indice0);
            motsProches[1]= tab.get(indice1);
            motsProches[2]= tab.get(indice2);
            motsProches[3]= tab.get(indice3);
            motsProches[4]= tab.get(indice4);
        
        return motsProches;
    }
}
