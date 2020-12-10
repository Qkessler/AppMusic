package umu.tds.componente;

import java.util.Vector;
public class CargadorCanciones implements IBuscadorCanciones{
	private Canciones canciones;
	private Vector<CancionesListener> cancionesListener = new Vector<>();
	
	public void setArchivoCanciones(String archivo) {
		Canciones cancionesAnterior = this.canciones; 
		Canciones cancionesNuevo = MapperCancionesXMLtoJava.cargarCanciones(archivo);
		this.canciones = cancionesNuevo;
		if (!cancionesNuevo.equals(cancionesAnterior)) {
			CancionesEvent event = new CancionesEvent(this, cancionesAnterior, cancionesNuevo);
			notificarCambio(event);
		}
	}
	
	public Canciones getArchivoCanciones() {
		return canciones;
	}
	
	public synchronized void addCancionesListener(CancionesListener listener) {
		cancionesListener.addElement(listener);
	}
	
	public synchronized void removeCancionesListener(CancionesListener listener) {
		cancionesListener.removeElement(listener);
	}
	
	@SuppressWarnings("unchecked")
	private void notificarCambio(CancionesEvent event) {
		Vector<CancionesListener> lista;
		synchronized (this) {
			lista=(Vector<CancionesListener>) cancionesListener.clone();
		}
		for (CancionesListener cl : lista) {
			cl.nuevasCanciones(event);
		}
	}
}
