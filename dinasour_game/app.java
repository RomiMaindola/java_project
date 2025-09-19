import javax.swing.*;

public class app {

   static int  fheigth = 350;
    static int fwidth = 750;

    public static void main(String[] args) {

        // Create a frame with title
        JFrame frame = new JFrame("Dinosaur Game");
        // Make the frame visible
        frame.setVisible(false);
        // Set the size of the frame
        frame.setSize(fwidth, fheigth);
        // for  location to center
        frame.setLocationRelativeTo(null);
        // not resizable
        frame.setResizable(false);
        // Set the default close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dgame Dgame= new Dgame();
        //adding jpanel to frame
        frame.add(Dgame);
        frame.pack();
        Dgame.requestFocus();
        // true because frame is added to it
        frame.setVisible(true);

    }
}
