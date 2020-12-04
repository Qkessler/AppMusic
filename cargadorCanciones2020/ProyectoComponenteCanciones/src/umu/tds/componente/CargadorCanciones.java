package umu.tds.componente;

import java.util.Vector;
public class CargadorCanciones implements IBuscadorCanciones{
	private String archivoCanciones;
	private Vector<CancionesListener> cancionesListener = new Vector<>();
	
	public void setArchivoCanciones(String archivo) {
		this.archivoCanciones = archivo;
	}
	
	public synchronized void addCancionesListener(CancionesListener listener) {
		cancionesListener.addElement(listener);
	}
	
	public synchronized void removeCancionesListener(CancionesListener listener) {
		cancionesListener.removeElement(listener);
	}
}
