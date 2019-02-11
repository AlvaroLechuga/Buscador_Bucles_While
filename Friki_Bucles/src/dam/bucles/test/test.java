package dam.bucles.test;

import dam.bucles.interfaz.FormularioPrincipal;

public class test {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormularioPrincipal().setVisible(true);
            }
        });
    }
    
}
