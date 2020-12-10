package umu.tds.componente;

import java.util.EventObject;

@SuppressWarnings("serial")
public class CancionesEvent extends EventObject{
	protected Canciones nuevasCanciones, anteCanciones;
	
	public CancionesEvent(Object fuente, Canciones anterior, Canciones nuevo) {
		super(fuente);
		this.nuevasCanciones = nuevo;
		this.anteCanciones = anterior;
	}
	
	public Canciones getNuevasCanciones() {
		return nuevasCanciones;
	}
	
	public Canciones getAnteCanciones() {
		return anteCanciones;
	}
	
	
}
