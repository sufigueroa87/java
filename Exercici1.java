
package ex1;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

/**
 * Per seleccionar, llistar i esborrar fitxers.
 * Segons els paràmetres introduïts, es durà a terme una acció o una altra,
 * en relació a la gestió de fitxers.
 * @author susanafigueroa
 */
public class Exercici1 {
    
    public static final String ERROR_QUANTITAT_ARGS = "El nombre de parametres no es entre quatre i cinc.";
    public static final String ERROR_PRIMER_PARAMETRE = "El primer parametre no correspon a una carpeta del sistema de fitxers.";
    public static final String ERROR_SEGON_PARAMETRE = "El segon parametre no es ni una 'E' ni una 'L'.";
    public static final String ERROR_TERCER_PARAMETRE = "El tercer parametre no es ni una 'M' ni una 'N'.";
    public static final String ERROR_QUART_PARAMETRE = "El quart parametre no es un numero seguit de 'B', 'K', 'M' o 'G' si el tercer parametre es una 'M'.";
    public static final String ERROR_CINQUE_PARAMETRE = "El cinque parametre no es una H.";
    
    public static void main(String[] args) {
        
        //PAS1: comprovar que els arguments són correctes
        comprovacioArguments(args);
        
        //1_Ruta
        String rutaCarpeta = args[0];
        File carpeta = new File(rutaCarpeta);
        
        //2_Esborrar fitxers o Llistar fitxers i directoris
        String parametre2 = args[1];
                
        //3_Mida del fitxer o Nom del fitxer
        String parametre3 = args[2];
        
        //4_Mida mínima o Extensió dels fitxers
        String parametre4 = args[3];
        
        //5_Hidden (opcional)
        String parametre5 = "";
        
        if (args.length == 5) {
            parametre5 = args[4];
        }
        
        
        File[] llistatFitxersSeleccionats = null;
        
        
        if (args[2].equalsIgnoreCase("N")) {
            
            llistatFitxersSeleccionats = seleccionarFitxersNom(args, carpeta);    
            
        } else if (args[2].equalsIgnoreCase("M")) {
            
            llistatFitxersSeleccionats = seleccionarFitxersMida(args, carpeta);      
            
        }
        
        
        if (args[1].equalsIgnoreCase("L")) {
            
            llistarFitxersSeleccionats(llistatFitxersSeleccionats);
            
        } else if (args[1].equalsIgnoreCase("E")) {
            
            esborrarFitxersSeleccionats(llistatFitxersSeleccionats, carpeta);
            
        }
    }        
    
    
    //MÈTODES MISSATGES D'ERROR
    /**
     * Comprovar que els paràmetres són correctes.
     * @param args cadena d'arguments, que corresponen als paràmetres introduïts.
     */
    private static void comprovacioArguments(String[] args) {
                        
        if (args.length != 4 && args.length != 5) {
            missatgesError(ERROR_QUANTITAT_ARGS);
        }
                
        if (!comprovarRutaCorrecte(args[0])) {
            missatgesError(ERROR_PRIMER_PARAMETRE);
        }
                
        if (!(args[1].equalsIgnoreCase("E") || args[1].equalsIgnoreCase("L"))) {
            missatgesError(ERROR_SEGON_PARAMETRE);
        }
                
        if (!(args[2].equalsIgnoreCase("M") || args[2].equalsIgnoreCase("N"))) {
            System.out.println("hola");
            missatgesError(ERROR_TERCER_PARAMETRE);
        }
                
        if (args[2].equalsIgnoreCase("M")) {
            if (!lletraCorrecte(args[3]) || !validacioNumeros(args[3])) {
            missatgesError(ERROR_QUART_PARAMETRE);
            }
        }
                        
        if (args.length == 5 && !(args[4].equalsIgnoreCase("H"))) {
            missatgesError(ERROR_CINQUE_PARAMETRE);
        }          
    }
    
    
    /**
     * Imprimeix un missatge d'error per informar a l'usuari del problema x
     * en relació al paràmetre introduït. 
     * @param missatgeError missatge que s'imprimirà quan hi hagi un error.
     */
    private static void missatgesError(String missatgeError) {
        System.err.println(missatgeError);
        System.exit(2);
    }
    

    //1r NIVELL DISSENY DESCENDENT
    /**
     * Esborra els fitxers seleccionats (si el fitxer té permís d'escriptura)
     * que es troben a l'array de tipus File[] que passem per paràmetre d'entrada.
     * S'executa quan el segon paràmetre introduït és la 'e' o la 'E'.
     * @param fitxersSeleccionats fitxers seleccionats per ser esborrats.
     * @param directoriDonEsborrar directori on volem esborrar els fitxers seleccionats.
     */
    private static void esborrarFitxersSeleccionats(File[] fitxersSeleccionats, File directoriDonEsborrar) {
        
        System.out.println("Comença el procés d'esborrat:");
        
        File[] totalFitxersDirectori = directoriDonEsborrar.listFiles();
        
        int countFitxersEsborrats = 0;
        long espaiAlliberat = 0;
                               
        for (int i = 0; i < fitxersSeleccionats.length; i++) {
            for (int j = 0; j < totalFitxersDirectori.length; j++) {
                if(fitxersSeleccionats[i].equals(totalFitxersDirectori[j])){
                    if(fitxersSeleccionats[i].canWrite()){
                        espaiAlliberat = espaiAlliberat + totalFitxersDirectori[j].length();
                        totalFitxersDirectori[j].delete();
                        countFitxersEsborrats++;
                        System.out.println("Fitxer:" + fitxersSeleccionats[i] + " esborrat amb èxit.");
                    } else {
                        System.out.println("Fitxer:" + fitxersSeleccionats[i] + " no té permís d'escriptura.");
                    }
                }
            }
        }
        
        System.out.println(countFitxersEsborrats + " fitxer/s esborrat/s, " + espaiAlliberat + " B alliberats.");
    }
        
    
    /**
     * Permet imprimir els fitxers seleccionats que es troben en l'array
     * de tipus File[] que passem per paràmetre.
     * Dóna informació dels fitxers: Hidden, no Hidden, Directori, no Directori, mida.
     * S'executa quan el segon paràmetre introduït és la 'l' o la 'L'.
     * @param fitxersSeleccionats llistat de fitxers seleccionats per ser llistats.
     */
    private static void llistarFitxersSeleccionats(File[] fitxersSeleccionats) {        
        String nom = "";
        int countFitxers = 0;
        int countDirectoris = 0;
        long totalBytes = 0;
                
        for (int i = 0; i < fitxersSeleccionats.length; i++) {
            
            if (fitxersSeleccionats[i].isDirectory()) {
                nom = fitxersSeleccionats[i].getName() + " (D)"; 
            } else {  
                nom = fitxersSeleccionats[i].getName(); 
            }
                                  
            if (fitxersSeleccionats[i].isHidden() && fitxersSeleccionats[i].isDirectory()) {  
                long midaDirectori = obtenirMidaDirectoriRecursiu(fitxersSeleccionats[i]);
                System.out.println(nom + " (H) " + midaDirectori + " B");
                countDirectoris++;
                totalBytes = totalBytes + fitxersSeleccionats[i].length();
            } else if (fitxersSeleccionats[i].isHidden() && fitxersSeleccionats[i].isFile() ){
                System.out.println(nom + " (H)" + fitxersSeleccionats[i].length() + " B");
                countFitxers++;
                totalBytes = totalBytes + fitxersSeleccionats[i].length();
            } else if (!fitxersSeleccionats[i].isHidden() && fitxersSeleccionats[i].isDirectory()){
                long midaDirectori = obtenirMidaDirectoriRecursiu(fitxersSeleccionats[i]);
                System.out.println(nom + " " + midaDirectori + " B"); 
                countDirectoris++;
                totalBytes = totalBytes + fitxersSeleccionats[i].length();
            } else if (!fitxersSeleccionats[i].isHidden() && fitxersSeleccionats[i].isFile()) {
                System.out.println(nom + " " + fitxersSeleccionats[i].length() + " B"); 
                countFitxers++;
                totalBytes = totalBytes + fitxersSeleccionats[i].length();
            }
        } 
        
        System.out.println(countFitxers + " fitxers i " + countDirectoris + " directoris, " + totalBytes + " B");
        
    }
    
    
    //2n NIVELL DISSENY DESCENDENT
    /**
     * Obtenir tamany d'un directori recursivament.
     * @param directori directori del que volem saber la mida.
     * @return la mida del directori.
     */
    private static long obtenirMidaDirectoriRecursiu(File directori){
        
        long tamanyDirectori = 0;
        
        File[] fitxersDirectori = directori.listFiles();
        
        for (int i = 0; i < fitxersDirectori.length; i++) {
            
            if (fitxersDirectori[i].isFile()) {
                
                tamanyDirectori = tamanyDirectori + fitxersDirectori[i].length();
                
            } else if (fitxersDirectori[i].isDirectory()) {
                
                tamanyDirectori = tamanyDirectori + obtenirMidaDirectoriRecursiu(fitxersDirectori[i]);
                                
            }
        }
        
        return tamanyDirectori;
        
    }
    
    
    //3r NIVELL DISSENY DESCENDENT
    /**
     * Per seleccionar fitxers segons el Nom del tipus de fitxer.
     * El nom del tipus de fitxer el trobem al paràmetre 4 (args[3]) quan el
     * paràmetre 3 (args[2]) és N.
     * @param args llistat dels paràmetres introduïts
     * @param directori directori d'on volem seleccionar els fitxers
     * @return array de fitxers de tipus File seleccionats segons el filtre
     */
    private static File[] seleccionarFitxersNom(String[] args, File directori) {
        
        File[] llistatFitxersSeleccionats = null;
        
        if (args.length == 5 && args[4].equalsIgnoreCase("H")) {
            
            FileFilter filtreSeleccionarTotsFitxers = (File arxiu) ->  arxiu.isFile() && arxiu.getName().endsWith("." + args[3]);
               
            llistatFitxersSeleccionats = directori.listFiles(filtreSeleccionarTotsFitxers);
            
        } else if (args.length == 4) {
                                
            FileFilter filtreSeleccionarFitxersNoOcults = (File arxiu) -> arxiu.isFile() && arxiu.getName().endsWith("." + args[3]) && !arxiu.isHidden();

            llistatFitxersSeleccionats = directori.listFiles(filtreSeleccionarFitxersNoOcults);
            
        }

        return llistatFitxersSeleccionats;
    }
    
    
    /**
     * Per seleccionar fitxers segons la Mida del fitxer.
     * Es seleccionaran els fitxers de mida superior a la introduïda.
     * La mida introduïda la trobem al paràmetre 4 (args[3]) quan el
     * paràmetre 3 (args[2]) és 'M'.
     * @param args llistat dels paràmetres introduïts
     * @param directori directori d'on volem seleccionar els fitxers
     * @return array de fitxers de tipus File seleccionats segons el filtre
     */
    private static File[] seleccionarFitxersMida(String[] args, File directori) {
        
        File[] llistatFitxersSeleccionats = null;
        
        Double midaMinimaIntroduida = midaMinimaBytes(args);
                
        if (args.length == 5 && args[4].equalsIgnoreCase("H")) {
                        
            FileFilter filtreSeleccionarFitxers = (File arxiu) -> {
                double tamanyArxiuBytes = arxiu.length();
                
                return tamanyArxiuBytes > midaMinimaIntroduida;
            };
        
            llistatFitxersSeleccionats = directori.listFiles(filtreSeleccionarFitxers);
           
        } else if (args.length == 4) {
            
            FileFilter filtreSeleccionarFitxers = (File arxiu) -> {
                double tamanyArxiuBytes = arxiu.length();
                
                return tamanyArxiuBytes > midaMinimaIntroduida && !arxiu.isHidden();
            };

            llistatFitxersSeleccionats = directori.listFiles(filtreSeleccionarFitxers);
       
        }

        return llistatFitxersSeleccionats;
    }
    
    
    //4t NIVELL DISSENY DESCENDENT
    /**
     * Transforma la mida mínima introduïda a bytes.
     * La mida mínima introduïda la trobem al paràmetre 4 (args[3]) quan el
     * paràmetre 3 (args[2]) és 'M'.
     * @param args llistat de paràmetres introduïts
     * @return retorna la mida introduïda en bytes
     */
    private static double midaMinimaBytes(String[] args) {
        double midaMinimaEnBytes = 0;
        int longitudCharsMidaIntroduida = args[3].length();
        char unitatMesura = args[3].toUpperCase().charAt(longitudCharsMidaIntroduida-1);
        String cadenaNumerica = "";
        
        for ( int i = 0; i < args[3].length()-1; i++) {
            cadenaNumerica = cadenaNumerica + String.valueOf(args[3].charAt(i));
        }
                
        switch (unitatMesura) {
            case 'B':
                midaMinimaEnBytes = Double.parseDouble(cadenaNumerica);
                break;
            case 'K':
                midaMinimaEnBytes = Double.parseDouble(cadenaNumerica) * 1024;
                break;
            case 'M':
                midaMinimaEnBytes = Double.parseDouble(cadenaNumerica) * 1024 * 1024;
                break;
            case 'G':
                midaMinimaEnBytes = Double.parseDouble(cadenaNumerica) * 1024 * 1024 * 1024;
                break;
            default:
                break;
        }
              
        return midaMinimaEnBytes;
    }

    
    /**
     * Comprova que la lletra de la mida mínima introduïda és correcte.
     * @param cadenaAAnalitzar cadena de la que analitzarem l'últim char
     * @return true si la lletra es correcte, false si és incorrecte          
     */
    private static boolean lletraCorrecte(String cadenaAAnalitzar){
        boolean trobat = false;
        String regex = "[BKMG]";
        int longitudCadena = cadenaAAnalitzar.length();
        char lletraABuscar = cadenaAAnalitzar.toUpperCase().charAt(longitudCadena-1);

        if(Pattern.matches(regex, String.valueOf(lletraABuscar))) {
            trobat = true;
        }
        return trobat;  
    }
    
    
    /**
     * Comprova que el valor numèric de la mída mínima introduïda és correcte.
     * @param cadenaAAnalitzar cadena de la que analitzarem tots els caràcters menys l'últim.
     * @return true si tots els chars són dígits, false si algún char no és un dígit.
     */
    private static boolean validacioNumeros(String cadenaAAnalitzar){
        boolean cadenaNumerica = false;
        String regex = "[0-9]+";
        String midaMinima = "";
        
        for ( int i = 0; i < cadenaAAnalitzar.length()-1; i++) {
            midaMinima = midaMinima + String.valueOf(cadenaAAnalitzar.charAt(i));
        }
        
        if(Pattern.matches(regex, midaMinima)){
            cadenaNumerica = true;
        }
        return cadenaNumerica;
    }
    
    
    //5è NIVELL DISSENY DESCENDENT
    /**
     * Comprova que la ruta introduïda sigui correcte.
     * @param nomRuta la ruta que correspon al directori
     * @return true si és un directori existent, false si no és un directori existent.
     */
    private static boolean comprovarRutaCorrecte(String nomRuta) {
        File nomDirectori = new File(nomRuta);
        boolean rutaCorrecte = false;
        if (nomDirectori.exists() && nomDirectori.isDirectory()) {
            rutaCorrecte = true;
        }
        return rutaCorrecte;
    }
    
}