import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.*;

public class Fenetre extends JFrame implements MouseListener, ItemListener {

    private final JTextArea textArea;
    private final JLabel label;

    private final JComboBox<String> comboBox;

    public Fenetre() {
        label = new JLabel("Hello World! Hello IFT1025!");

        textArea = new JTextArea(20, 30);
        textArea.addMouseListener(this);

        String[] availableFonts = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getAvailableFontFamilyNames(Locale.CANADA);

        comboBox = new JComboBox<>(availableFonts);
        comboBox.addItemListener(this);

        JPanel north = new JPanel(new FlowLayout());
        JPanel center = new JPanel(new FlowLayout());
        JPanel south = new JPanel(new FlowLayout());

        north.add(label);
        center.add(textArea);
        south.add(comboBox);

        add(north, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);

        JMenuBar menuBar = new JMenuBar();  

        JMenu fileMenu = new JMenu("Fichier"); 
        JMenu dictionaryMenu = new JMenu("Dictionaire"); 
        JMenu verifyMenu = new JMenu("Verifier"); 

        JMenuItem openItem = new JMenuItem("Ouvrir");
  
        openItem.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent ev) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
            int result = fileChooser.showOpenDialog(openItem);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    Scanner myReader = new Scanner(selectedFile);
                    String input = "";
                    while (myReader.hasNextLine()) {
                      String data = myReader.nextLine();
                      input = input + data;
                      input += "\n";
                    }
                    myReader.close();
                    textArea.setText(input);

                  } catch (FileNotFoundException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                  }

                System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            }
          }
        });

        fileMenu.add(openItem);
        fileMenu.add(new JMenuItem("Enregistrer"));

        menuBar.add(fileMenu);
        menuBar.add(dictionaryMenu);
        menuBar.add(verifyMenu);



        setJMenuBar(menuBar);
        setTitle("TITLE IN CLASSE");
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        String s = String.format("Mouse Clicked: (x, y) = (%d, %d)", e.getX(), e.getY());

    }

    @Override
    public void mousePressed(MouseEvent e) {
        String s = String.format("Mouse Pressed: (x, y) = (%d, %d)", e.getX(), e.getY());

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        String s = String.format("Mouse Released: (x, y) = (%d, %d)", e.getX(), e.getY());


    }

    @Override
    public void mouseEntered(MouseEvent e) {
        String s = "Mouse Entered";

    }

    @Override
    public void mouseExited(MouseEvent e) {
        String s = "Mouse Exited";

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        String fontName = Objects.requireNonNull(comboBox.getSelectedItem()).toString();

        Font font = new Font(fontName, Font.BOLD, 12);

        label.setFont(font);
    }
}
