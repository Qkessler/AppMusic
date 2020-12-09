package umu.tds.apps;

import java.awt.EventQueue;
import java.util.Scanner;

import umu.tds.apps.persistence.SongAdapterTDS;
import umu.tds.apps.ui.LoginView;

public class App{
	public static void main(final String[] args){
		Scanner sc=new Scanner(System.in);
		int command = sc.nextInt();
		switch(command) {
		case 0: 
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
			break;
		case 1:
			SongAdapterTDS.getInstance().cleanDB();
			break;
		case 2:
			System.out.println(System.getProperty("os.name"));
			System.out.println(System.getProperty("user.dir"));
		}
		sc.close();
		
	}
}
