package umu.tds.apps;

import java.awt.EventQueue;

import umu.tds.apps.ui.LoginView;

public class App{
	public static void main(final String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginView ventana = new LoginView();
					ventana.mostrarVentana();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
