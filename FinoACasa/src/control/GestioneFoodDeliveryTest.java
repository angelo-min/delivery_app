package control;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import exception.OperationException;

public class GestioneFoodDeliveryTest {

	@Test
	public void testProcessaOrdine1() {
		GestioneFoodDelivery gFD = GestioneFoodDelivery.getInstance();
		ArrayList<String> pietanze = new ArrayList<String>();
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> expected = new ArrayList<String>();
		
		expected.add(0, "12.0");
		expected.add(1, "0234-5678-9876-3421");
		pietanze.add("Gnocchi alla sorrentina");
		try {
			result = gFD.processaOrdine("angelo00", "La scialuppa", pietanze);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		assertEquals(expected, result);
	}
	
	@Test
	public void testProcessaOrdine2() {
		GestioneFoodDelivery gFD = GestioneFoodDelivery.getInstance();
		String expected = "utente non registrato";
		String result = null;
		ArrayList<String> pietanze = new ArrayList<String>();
		try {
			pietanze.add("Gnocchi alla sorrentina");
			gFD.processaOrdine("angelo", "La scialuppa", pietanze);
		}catch(OperationException e) {
			result = e.getMessage();
		}
		assertEquals(expected, result);
	}

	@Test
	public void testProcessaOrdine3() {
		GestioneFoodDelivery gFD = GestioneFoodDelivery.getInstance();
		String expected = "La pietanza Gnocchi non e' disponibile";
		String result = null;
		ArrayList<String> pietanze = new ArrayList<String>();
		try {
			pietanze.add("Gnocchi");
			gFD.processaOrdine("angelo00", "La scialuppa", pietanze);
		}catch(OperationException e) {
			result = e.getMessage();
		}
		assertEquals(expected, result);
	}
	
	@Test
	public void testProcessaOrdine4() {
		GestioneFoodDelivery gFD = GestioneFoodDelivery.getInstance();
		ArrayList<String> pietanze = new ArrayList<String>();
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> expected = new ArrayList<String>();
		
		expected.add(0, "28.0");
		expected.add(1, "0234-5678-9876-3421");
		pietanze.add("Gnocchi alla sorrentina");
		pietanze.add("Risotto alla pescatora");
		try {
			result = gFD.processaOrdine("angelo00", "La scialuppa", pietanze);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		assertEquals(expected, result);
	}
	
	@Test
	public void testProcessaOrdine5() {
		GestioneFoodDelivery gFD = GestioneFoodDelivery.getInstance();
		String expected = "Ristorante non disponibile per la consegna";
		String result = null;
		ArrayList<String> pietanze = new ArrayList<String>();
		try {
			pietanze.add("Gnocchi alla sorrentina");
			gFD.processaOrdine("angelo00", "La masardona", pietanze);
		}catch(OperationException e) {
			result = e.getMessage();
		}
		assertEquals(expected, result);
	}
	
}
