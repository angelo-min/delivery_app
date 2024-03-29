package boundary;

import control.GestioneFoodDelivery;
import exception.OperationException;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class BoundaryClienteRegistrato {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        boolean exit = false;

        while(!exit){
            System.out.println("Cliente registrato");
            System.out.println("1 ricerca ristorante");
            System.out.println("2 ricerca pietanza");
            System.out.println("3 ordina");
            System.out.println("4 esci");

	        String operation = scanner.nextLine();
	
	        switch (operation){
	            case "3":
	                effettuaOrdine();
	                break;
	            case "4":
	                exit = true;
	                break;
	            default:
	                System.out.println("operazione non disponibile");
	                break;
	        }
        }

        System.out.println("esco dall'applicazione");
        
    }

    /**
     * Permette all'utente registrato di ordinare diverse pietanze con numero di porzioni relativi presso un ristorante selezionato
     */
    private static void effettuaOrdine(){
        GestioneFoodDelivery gestioneFoodDelivery = GestioneFoodDelivery.getInstance();
        String username = null;
        String ristorante = null;
        ArrayList<String> pietanze = new ArrayList<String>();
        String numeroCarta = null;
        
        boolean inputValido = false;
        ArrayList<String> resultList = null;
        String conferma = null;

        try{
        	while(!inputValido) {
	            System.out.println("inserire username");
	            username = scanner.nextLine();
	            if(username.length() > 30) {
	            	System.out.println("Nome utente troppo lungo");
	            }else {
	            	inputValido = true;
	            }
        	}
            inputValido = false;
            
            while(!inputValido) {
	            System.out.println("inserire il ristorante da cui si vuole prenotare");
	            ristorante = scanner.nextLine();
	            if(ristorante.length() > 50) {
	            	System.out.println("Nome ristorante troppo lungo");
	            }else {
	            	inputValido = true;
	            }
            }
            inputValido = false;
            
            String next = null;
            String quantity = null;
            while(!inputValido){
                //lettura di tutte le pietanze
                //interruzione del ciclo quando la "line is empty"
            	System.out.println("inserire una pietanze da ordinare... se non vuoi ordinare nient'altro vai a capo");
                next = scanner.nextLine();
                if(next.isEmpty()) {
                   inputValido = true;
                }else if (!Pattern.matches("[ a-zA-Z]+", next) || next.length() > 50) {
                	System.out.println("Nome pietanza errato... inserire pietanze di lunghezza massima 50 e composte da sole lettere");
                }else {
                	try {
                		System.out.println("Inserire il numero di porzioni: ");
                        quantity = scanner.nextLine();
                		int quant = Integer.parseInt(quantity);
                		while(quant <= 0) {
                			System.out.println("Numero porzioni non corretto ... inserire un numero > 0");
                			quant = Integer.parseInt(scanner.nextLine());
                		}
                		for(int i=0; i<quant; i++) {
                			pietanze.add(next);
                		}
                	}catch(NumberFormatException e) {
                		System.out.println("Errore... inserire un numero di porzioni valido");
                	}
                }
            }
            inputValido = false;
            
           	resultList = gestioneFoodDelivery.processaOrdine(username, ristorante, pietanze);
            
            System.out.println("Prezzo: " + resultList.get(0) + " euro");
            System.out.println("Digita 's' per confermare o 'n' per annullare..");
            conferma = scanner.nextLine();
            
            if(!conferma.toLowerCase().equals("s")){
                System.out.println("ordine annullato");
                return;
            }
            //se il cliente non ha annullato l'ordine continua l'esecuzione del programma
            
            //inserimento carta di credito
            //il cliente deve essere registrato per aver richiesto di effettuare un ordine e nella registrazione deve aver inserito una carta di credito
            System.out.println("Trovata carta di credito registrata per l'utente: " + username);
            System.out.println("Digita 's' per confermare l'utilizzo della carta ****-****-****-" + resultList.get(1).substring(resultList.get(1).length()-4));
            System.out.println("Altrimenti inserisci qualsiasi altro carattere");
            conferma = scanner.nextLine();
            if(conferma.toLowerCase().equals("s")){
                inputValido=true;
            }
            while(!inputValido){
                System.out.println("inserire il numero di carta: ");
                numeroCarta = scanner.nextLine();
                //elimino tutti i caratteri della stringa numero carta che non sono numerici (\\D è il regex per eliminare i "non digit" characters)
                if(numeroCarta.replaceAll("\\D", "").length() != 16){
                    System.out.println("Errore inserimento carta, inserire solo i 16 caratteri numerici della carta");
                }else {
                	inputValido = true;
                }
            }
            inputValido = false;
            
            System.out.println("pagamento in corso");
            TimeUnit.SECONDS.sleep(3);
            System.out.println("Pagamento effettuato");
            System.out.println();
            
            gestioneFoodDelivery.concludiOrdine(username);
            
            System.out.println("Ordine completato");
            System.out.println();
            
            System.out.println("Se sei interessato ad un altro ordine procedi dal menu, altrimenti esci");
        } catch (OperationException e) {
        	System.out.println(e.getMessage());
            System.out.println("Riprovare");
        }catch (Exception e) {
            System.out.println("Errore inatteso, riprovare ....");
        }
    }
}
