import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

import java.io.*;

class NotepadClone extends JFrame implements ActionListener {
    JScrollPane jp;
    JMenuBar jm;
    JMenu fileMenu;
    JMenu editMenu;
    JMenu viewMenu;
    JMenuItem jmi1;
    JMenuItem jmi2;
    JMenuItem jmi3;
    JMenuItem jmi4;
    JMenuItem jmi5;
    JMenuItem jmi6;
    JMenuItem jmi7;
    JMenuItem jmi8;
    JMenuItem jmi9;
    JMenuItem jmi10;
    JMenuItem jmi11;
    JTextArea ja;
    File currFile;
    float currFontSize = 12.0f;
    boolean theme = true;
    private javax.swing.Timer autosaveTimer;

    public NotepadClone() {

        this.setTitle("Notepad Clone");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(true);

        ja = new JTextArea();
        jp = new JScrollPane(ja, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        jm = new JMenuBar();

        fileMenu = new JMenu("File");
        jmi1 = new JMenuItem("New");
        jmi2 = new JMenuItem("Open");
        jmi3 = new JMenuItem("Save");
        jmi4 = new JMenuItem("Save As");
        jmi5 = new JMenuItem("Exit");

        fileMenu.add(jmi1);
        fileMenu.add(jmi2);
        fileMenu.add(jmi3);
        fileMenu.add(jmi4);
        fileMenu.add(jmi5);

        jmi1.addActionListener(this);
        jmi2.addActionListener(this);
        jmi3.addActionListener(this);
        jmi4.addActionListener(this);
        jmi5.addActionListener(this);

        editMenu = new JMenu("Edit");
        jmi6 = new JMenuItem("Cut");
        jmi7 = new JMenuItem("Copy");
        jmi8 = new JMenuItem("Paste");
        jmi11 = new JMenuItem("Change Theme");
        editMenu.add(jmi6);
        editMenu.add(jmi7);
        editMenu.add(jmi8);
        editMenu.add(jmi11);

        jmi6.addActionListener(this);
        jmi7.addActionListener(this);
        jmi8.addActionListener(this);

        viewMenu = new JMenu("View");
        jmi9 = new JMenuItem("Zoom In");
        jmi10 = new JMenuItem("Zoom Out");
        viewMenu.add(jmi9);
        viewMenu.add(jmi10);

        jmi9.addActionListener(this);
        jmi10.addActionListener(this);

        jm.add(fileMenu);
        jm.add(editMenu);
        jm.add(viewMenu);

        autosaveTimer = new javax.swing.Timer(3000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                autosave();
            }
        });

        this.setJMenuBar(jm);

        this.add(jp);

        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent obj) {
        if (obj.getSource() == jmi1) {
            ja.setText("");
            currFile = null;
        } else if (obj.getSource() == jmi2) {

            JFileChooser jfc = new JFileChooser();
            int returnVal = jfc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = jfc.getSelectedFile();
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    ja.read(reader, null);
                    currFile = file;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (obj.getSource() == jmi3) {
            if (currFile != null) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(currFile))) {
                    ja.write(writer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                JFileChooser jfc = new JFileChooser();
                int returnVal = jfc.showSaveDialog(this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = jfc.getSelectedFile();
                    try {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                        ja.write(bw);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (obj.getSource() == jmi4) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    file.createNewFile();
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        ja.write(writer);
                        currFile = file;
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (obj.getSource() == jmi5) {
            System.exit(0);
        } else if (obj.getSource() == jmi6) {
            ja.cut();
        } else if (obj.getSource() == jmi7) {
            ja.copy();
        } else if (obj.getSource() == jmi8) {
            ja.paste();
        } else if (obj.getSource() == jmi9) {
            currFontSize += 2.0;
            ja.setFont(ja.getFont().deriveFont(currFontSize));
        } else if (obj.getSource() == jmi10) {
            currFontSize -= 2.0;
            ja.setFont(ja.getFont().deriveFont(currFontSize));
        } else if (obj.getSource() == jmi11) {
            if (theme) {
                this.setBackground(Color.black);
            } else {
                this.setBackground(Color.white);
            }
            theme = !theme;
        }
    }

    public void autosave() {
        if (currFile != null) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(currFile));
                ja.write(bw);
            } catch (Exception e) {

            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NotepadClone());
    }
}
