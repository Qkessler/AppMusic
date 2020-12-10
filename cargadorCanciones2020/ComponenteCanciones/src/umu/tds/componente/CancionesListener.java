package umu.tds.componente;

import java.util.EventListener;
import java.util.EventObject;

public interface CancionesListener extends EventListener{
	public void nuevasCanciones(EventObject e);
}
