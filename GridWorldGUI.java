/**
 * Created by vincentyu on 5/28/17.
 */
import javax.swing.*;
import java.awt.*;

public class GridWorldGUI extends JFrame {
    private JLabel labels[][];
    private JButton buttons[];
    private Grid grid;

    public GridWorldGUI() {
        super("GridWorld");
        Container c = getContentPane();

        //Ask user for size of simulation
        String theSize;
        //Check if user inputs a valid size, loop until valid one is inputted
        boolean valid = false;
        do {
            theSize = JOptionPane.showInputDialog("What is your preferred size for simulation?");
            try {
                Integer.parseInt(theSize);
                valid = true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "That is not a valid size. It must be an integer. Please try again.", "Error!", JOptionPane.ERROR_MESSAGE);
                valid = false;
            }
        } while(valid == false);
        int size = Integer.parseInt(theSize);
        //Init grid of size n
        grid = new Grid(size);
        //Instantiate labels
        labels = new JLabel[grid.GRID_NUM_ROWS][grid.GRID_NUM_COLS];

        //GridLayout of nxn size for labels
        c.setLayout(new GridLayout(grid.GRID_NUM_ROWS, grid.GRID_NUM_COLS));
        for(int i=0; i<grid.GRID_NUM_ROWS; i++)
        {
            for(int j=0; j<grid.GRID_NUM_COLS; j++)
            {
                labels[i][j] = new JLabel("" + i + "," + j, JLabel.CENTER);
                c.add(labels[i][j]);
            }
        }

        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args)
    {
        GridWorldGUI gui = new GridWorldGUI();
    }
}