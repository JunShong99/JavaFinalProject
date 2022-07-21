package my.example;

import javax.imageio.ImageIO;
import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is to enlarge data.
 *
 * @author Gan Wei Cai
 * @author Goh Deng Vee
 * @author Wei Jun Shong
 **/
public class Cases extends MenuItem implements Image {
    ArrayList<Country> countriesList;
    private JButton add, edit, search, clear, delete, enlarge;
    private JTextField dateField, casesField, searchField;
    private JComboBox comboBox;
    private JLabel label1, label2, label3, label4;
    private JTable table;
    private DefaultTableModel model;
    private int row, column;

    /**
     * This constructor inherits the constructor of parent class
     */
    public Cases() {
        countriesList = new ArrayList<>();
        setTable();
        loadData();


        JButton button2 = new JButton("Back");
        button2.setBounds(0, 305, 100, 50);
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainFrame();
                dispose();
            }
        });
        add(button2);

        image();


        setLayout(null);
        setTitle("Covid-19 update");
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    /**
     * This method to set the table in the frame.
     */
    private void setTable() {
        table = new JTable();
        Object[] columns = {"Date", "Country", "Cases", "Death", "Recovery"};
        model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        table.setModel(model);
        table.setBackground(Color.LIGHT_GRAY);
        table.setForeground(Color.black);
        Font font = new Font("Arial", 1, 14);
        table.setFont(font);
        table.setRowHeight(20);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                row = table.getSelectedRow();
                column = table.getColumnCount();
                dateField.setText(model.getValueAt(row, 0).toString());
                casesField.setText(model.getValueAt(row, 2).toString());
                String country = model.getValueAt(row, 1).toString();
                for (int i = 0; i < (comboBox.getItemCount()); i++) {
                    if (comboBox.getItemAt(i).equals(country)) {
                        comboBox.setSelectedIndex(i);
                    }
                }
            }
        });
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(0, 5, 985, 300);
        add(pane);
    }

    /**
     * This method is the method to set the background image.
     */
    public void image() {
        BufferedImage img = null;

        try {
            img = ImageIO.read(this.getClass().getResource("/image1.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageIcon icon1 = new ImageIcon(img);
        JLabel label1 = new JLabel();
        label1.setIcon(icon1);
        label1.setBounds(0, 305, 1000, 132);
        add(label1);


        try {
            img = ImageIO.read(this.getClass().getResource("/image2.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.GRAY));
        ImageIcon icon2 = new ImageIcon(img);
        JLabel label2 = new JLabel();
        label2.setIcon(icon2);
        label2.setBounds(299, 305, 1000, 132);
        add(label2);
    }

    /**
     * This method is the method to load the data.
     */
    private void loadData() {
        try {
            File file = new File("Covid19.txt"); //create file
            file.createNewFile();//if not exit
            FileReader f = new FileReader("Covid19.txt");
            StringBuffer sb = new StringBuffer();
            while (f.ready()) {
                char c = (char) f.read();
                if (c == '-') {
                    String countryarray[] = sb.toString().split(",");
                    countriesList.add(new Country(countryarray[0], countryarray[1], Integer.parseInt(countryarray[2]), Integer.parseInt(countryarray[3]), Integer.parseInt(countryarray[4])));
                    sb = new StringBuffer();
                } else {
                    sb.append(c);
                }
            }
        } catch (IOException e) {
            return;
        }
        model.setRowCount(0);//reset data model
        for (int i = 0; i < countriesList.size(); i++) {
            Object[] objs = {countriesList.get(i).date, countriesList.get(i).countryName, countriesList.get(i).cases, countriesList.get(i).death, countriesList.get(i).recovery};
            model.addRow(objs);
        }

    }

}

/**
 * This class is to create the menu bar to the frame.
 *
 * @author Wei Jun Shong
 * @author Gan Wei Cai
 * @author Goh Deng Vee
 */
class MenuItem extends JFrame {

    /**
     * This constructor inherits the constructor of parent class
     * This constructor shows all the item in the menubar.
     */
    public MenuItem() {

        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);

        JMenu file = new JMenu("File");
        menubar.add(file);
        JMenuItem open = new JMenuItem("Open");
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new File("c:");
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.open(file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        JMenuItem settings = new JMenuItem("Settings");
        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Runtime.getRuntime().exec("cmd /c start control");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        JMenuItem print = new JMenuItem("Print");
        print.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printFileAction();
            }
        });
        JMenuItem exit = new JMenuItem("Exit");
        file.add(open);
        file.add(settings);
        file.add(print);
        file.add(exit);

        JMenu edit = new JMenu("Edit");
        menubar.add(edit);
        JMenuItem cut = new JMenuItem("Cut");
        JMenuItem copy = new JMenuItem("Copy");
        JMenuItem paste = new JMenuItem("Paste");
        JMenuItem delete = new JMenuItem("Delete");
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(delete);

        JMenu help = new JMenu("Help");
        menubar.add(help);
        JMenuItem about = new JMenuItem("About");
        help.add(about);


        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * This method is the method to set print file.
     */
    private void printFileAction() {

        JFileChooser fileChooser = new JFileChooser("/Covid19.txt");
        int state = fileChooser.showOpenDialog(this);
        if (state == fileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
            DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
            PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
            PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
            PrintService service = ServiceUI.printDialog(null, 200, 200, printService, defaultService, flavor, pras);

            if (service != null) {
                try {
                    DocPrintJob job = service.createPrintJob();
                    FileInputStream fis = new FileInputStream(file);
                    DocAttributeSet das = new HashDocAttributeSet();
                    Doc doc = new SimpleDoc(fis, flavor, das);
                    job.print(doc, pras);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}