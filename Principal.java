package gg;

import javax.swing.JFrame;

public class Principal {

    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(530, 260);
        gui.setVisible(true);
    }

}
