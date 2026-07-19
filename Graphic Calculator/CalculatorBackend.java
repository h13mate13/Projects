
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorBackend{






    public static void main(String[] args) {
        menu();
        Frame a=new Frame("Calculator");
        a.setSize(500,500);
        a.setVisible(true);
        String fname;
        fname = JOptionPane.showInputDialog(null,"name?", 10000);
        JOptionPane.showMessageDialog(null,"Hello " + fname);
    }

    //this has to run inside the frame
    static void menu(){
        System.out.println("Welcome to this graphic calculator");
    }


    public void actionPerformed(ActionEvent e) {

    }
}
