package my.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;

/**
 * This is the main frame for main interface
 *
 * @author Gan Wei Cai
 * @author Wei Jun Shong
 * @author Goh Deng Vee
 */
public class MainFrame extends MenuItem implements ActionListener {
    ArrayList<Country> countriesList;
    private JButton add, edit, search, clear, delete, enlarge;
    private JTextField dateField, casesField, searchField, deathField, recoveryField;
    private JComboBox comboBox;
    private JLabel label1, label2, label3, label4, label5, label6;
    private JTable table;
    private DefaultTableModel model;
    private int row, column;


    /**
     * This constructor inherits the constructor of parent class
     */
    public MainFrame() {
        countriesList = new ArrayList<>();

        setButton();
        setLabel();
        setTextField();
        setComboBox();
        setTable();
        loadData();
        setFrame();

    }

    /**
     * This method is to set the Frame.
     */
    private void setFrame() {
        setTitle("Home");
        setBounds(0, 0, 650, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);
        setResizable(false);
    }

    /**
     * This method to set the Button.
     */
    private void setButton() {
        add = new JButton("Add");
        add.setBounds(50, 220, 100, 40);
        add.addActionListener(this);
        add(add);
        edit = new JButton("Edit");
        edit.setBounds(200, 220, 100, 40);
        edit.addActionListener(this);
        add(edit);
        delete = new JButton("Delete");
        delete.setBounds(350,220,100,40);
        delete.addActionListener(this);
        add(delete);
        clear = new JButton("Clear");
        clear.setBounds(500,220,100,40);
        clear.addActionListener(this);
        add(clear);
        search = new JButton("Search");
        search.setBounds(310,310,100,40);
        search.addActionListener(this);
        add(search);

        enlarge = new JButton("Enlarge");
        enlarge.setBounds(420, 310, 100, 40);
        enlarge.addActionListener(this);
        add(enlarge);

    }

    /**
     * This method is the set the label in frame.
     */
    private void setLabel() {
        Font font1 = new Font("Arial", PROPERTIES, 16);
        label1 = new JLabel("Date:");
        label1.setBounds(30, 20, 100, 40);
        label1.setFont(font1);
        add(label1);
        label2 = new JLabel("Country:");
        label2.setBounds(30, 80, 100, 40);
        label2.setFont(font1);
        add(label2);
        label3 = new JLabel("Cases:");
        label3.setBounds(30, 140, 100, 40);
        label3.setFont(font1);
        add(label3);
        label4 = new JLabel("Please enter the country to search:");
        label4.setBounds(30, 190, 250, 200);
        label4.setFont(font1);
        add(label4);
        label5 = new JLabel("Death:");
        label5.setBounds(330, 20, 100, 40);
        label5.setFont(font1);
        add(label5);
        label6 = new JLabel("Recovery:");
        label6.setBounds(330, 80, 100, 40);
        label6.setFont(font1);
        add(label6);
    }

    /**
     * This method set Text Field in the frame.
     */
    private void setTextField() {
        dateField = new JTextField();
        dateField.setBounds(100, 20, 200, 40);
        add(dateField);
        casesField = new JTextField();
        casesField.setBounds(100, 140, 200, 40);
        add(casesField);
        searchField = new JTextField();
        searchField.setBounds(50, 310, 250, 40);
        add(searchField);
        deathField = new JTextField();
        deathField.setBounds(400, 20, 200, 40);
        add(deathField);
        recoveryField = new JTextField();
        recoveryField.setBounds(400, 80, 200, 40);
        add(recoveryField);
    }

    /**
     * This method is to set the combo box in frame.
     */
    private void setComboBox() {
        comboBox = new JComboBox((new String[]{"Malaysia", "Indonesia", "China", "Singapore", "Thailand"}));
        comboBox.setBounds(100, 80, 200, 40);
        add(comboBox);
    }

    /**
     * This method is to set the table in the frame.
     */
    private void setTable() {
        table = new JTable();
        Object[] columns = {"Date", "Country", "Cases"};
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
        pane.setBounds(10, 380, 620, 230);
        add(pane);
    }

    /**
     * This method to load the data in text file.
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
            Object[] objs = {countriesList.get(i).date, countriesList.get(i).countryName, countriesList.get(i).cases};
            model.addRow(objs);
        }

    }

    /**
     * This method to write the data in text file and table.
     */
    private void writeData() {
        try (FileWriter f = new FileWriter("Covid19.txt")) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < countriesList.size(); i++) {
                sb.append(countriesList.get(i).date + "," + countriesList.get(i).countryName + "," + countriesList.get(i).cases + "," + countriesList.get(i).death + "," + countriesList.get(i).recovery + "-");
            }
            f.write(sb.toString());
            f.close();
        } catch (IOException e) {
            return;
        }
        model.setRowCount(0);//reset data model
        for (int i = 0; i < countriesList.size(); i++) {
            Object[] objs = {countriesList.get(i).date, countriesList.get(i).countryName, countriesList.get(i).cases};
            model.addRow(objs);
        }
    }

    /**
     * This method is to clear the field.
     */
    private void clearField() {
        dateField.requestFocus();
        dateField.setText("");
        casesField.setText("");
        deathField.setText("");
        recoveryField.setText("");
        comboBox.setSelectedIndex(0);
    }


    /**
     * This is the overides method to the button action.
     *
     * @param e = button action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == add) {
            String date = dateField.getText();
            String country = comboBox.getSelectedItem().toString();
            try {
                int cases = Integer.parseInt(casesField.getText());
                int death = Integer.parseInt(deathField.getText());
                int recovery = Integer.parseInt(recoveryField.getText());
                countriesList.add(new Country(date, country, cases, death, recovery));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please input a number");
            }

            writeData();
            clearField();
        }
        if (e.getSource() == delete) {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this, "Delete this data", "Delete", dialogButton);
            if (dialogResult == 0) {

                try {
                    countriesList.remove(row);
                } catch (IndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null, "Nothing to be deleted, Please choose again!", "Record couldn't found", dialogButton);
                }

                clearField();
                writeData();
            } else {
            }
        }
        if (e.getSource() == edit) {
            String editDate = dateField.getText();
            try {
                countriesList.get(row);
                String editCountry = comboBox.getSelectedItem().toString();
                countriesList.get(row).date = editDate;
                countriesList.get(row).countryName = editCountry;

                try {
                    int editCases = Integer.parseInt(casesField.getText());
                    countriesList.get(row).cases = editCases;
                    writeData();
                    clearField();
                } catch (NumberFormatException exp) {
                    JOptionPane.showMessageDialog(null, "Please input a number");
                }
            } catch (IndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(null, "Please choose the data");
            }

        }
        if (e.getSource() == clear) {
            clearField();
        }
        if (e.getSource() == search) {
            TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
            table.setRowSorter(sorter);
            String text = searchField.getText();
            if(text.length() == 0) {
                sorter.setRowFilter(null);
            } else {
                try {
                    sorter.setRowFilter(RowFilter.regexFilter(text));
                } catch(PatternSyntaxException pse) {
                }
            }
        }

        if (e.getSource() == enlarge) {
            new Cases();
            this.dispose();
        }
    }
}