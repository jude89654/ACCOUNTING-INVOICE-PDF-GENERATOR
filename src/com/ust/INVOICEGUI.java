package com.ust;

import com.toedter.calendar.JDateChooser;
import com.ust.model.BeanFactory;
import com.ust.model.ItemBean;
import com.ust.model.RecieptBean;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;


/**
 * Created by Jude on 3/6/2016.
 */
public class INVOICEGUI extends JFrame {

    //para hanggang dalawang decimal lang
    private static NumberFormat formatter = new DecimalFormat("#.00");
    private JPanel InvoiceMainPanel;
    private JTextField customerTextField;
    private JTextField addressTextField;
    private JTextField itemNameTextField;
    private JTextField quantityTextField;
    private JTextField priceTextField;

    //nice joke :D
    private JScrollPane BoomPanes;
    private JButton ADDITEMButton;
    private JButton DELETEITEMButton;


    //private JTable table1;
    //private JLabel QtyLabel;
    private JLabel priceLabel;
    private JButton GeneratePDFTextField;
    private JLabel statusLabel;
    //private JLabel TotalLabel;
    private JTextField vatTextField;
    private JLabel vatStatus;
    private JTextField companyNametextField;
    private JTextField recieptNumberTextField;
    private JLabel totalAmountLabel;
    private JLabel vatAmountLabel;
    private JLabel amountLabel;
    private JButton chooseLogoButton;
    private JLabel logoPathLabel;
    private JButton removeLogoButton;


    private JPanel DateSelectionPanel;
    private JDateChooser DateChooser;
    private JButton CURRENTDATEButton;
    List<Object[]> list = new ArrayList<Object[]>();
    File logo = null;

    DefaultTableModel modelo;

    public INVOICEGUI() {
        super("SALES INVOICE");
        super.setMaximumSize(new Dimension(640, 480));
        modelo = new DefaultTableModel();
        modelo.addColumn("ITEM NAME");
        modelo.addColumn("PRICE");
        modelo.addColumn("QTY");
        modelo.addColumn("AMOUNT");


        JTable myTable = new JTable(modelo);
        //set quantity column width
        myTable.getColumnModel().getColumn(2).setMaxWidth(60);
        myTable.getColumnModel().getColumn(3).setMinWidth(100);
        myTable.getColumnModel().getColumn(3).setMaxWidth(200);

        //paggawa ng renderer ng text para sa table
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);//gawing center
        myTable.setDefaultRenderer(String.class, centerRenderer);

        //paglagay ng mga renderer para sa table
        myTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        myTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        myTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        myTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

        //fixed table size
        myTable.setPreferredScrollableViewportSize(new java.awt.Dimension(250, 200));
        BoomPanes.setViewportView(myTable);

        setContentPane(InvoiceMainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);


        //for the addButton
        ADDITEMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //checheck kung may laman ang mga textfield
                    if (itemNameTextField.getText().trim() != "" &
                            quantityTextField.getText().trim() != null &
                            priceLabel.getText().trim() != null &
                            vatTextField.getText().trim() != null
                            ) {


                        String itemName = itemNameTextField.getText().trim();
                        int quantity = Integer.parseInt(quantityTextField.getText().trim());
                        double price = Double.parseDouble(priceTextField.getText().trim());
                        double vatPercent = Double.parseDouble(vatTextField.getText().trim());

                        //pag negative bawal, tunguna negative ang presyo, batuhin pa ang pera sa customer
                        if (quantity <= 0 | price <= 0 | vatPercent < 0) throw new Exception();


                        double total = price * quantity;


                        modelo.addRow(new Object[]{itemName, price, quantity, total});
                        totalize(myTable, amountLabel, vatAmountLabel, totalAmountLabel, Double.parseDouble(vatTextField.getText().trim()));

                        quantityTextField.setText("");
                        itemNameTextField.setText("");
                        priceTextField.setText("");


                        statusLabel.setText("PRODUCT ADDED");
                    }
                } catch (Exception s) {
                    statusLabel.setText("INVALID INPUT");
                    s.printStackTrace();
                }
            }
        });

        //for the DeleteButton
        DELETEITEMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    //checheck kung may pinili na rows
                    if (myTable.getSelectedRow() >= 0) {
                        modelo.removeRow(myTable.getSelectedRow());
                        totalize(myTable, amountLabel, vatAmountLabel, totalAmountLabel, Double.parseDouble(vatTextField.getText().trim()));
                        statusLabel.setText("ROW REMOVED");
                    } else {
                        statusLabel.setText("NO ROWS TO DELETE");
                    }
                } catch (Exception ex) {
                    vatStatus.setText("INVALID VAT VALUE!");
                }
            }
        });

        //for the vat, when you change the vat, you change lahat, joke yung total lang
        vatTextField.getDocument().addDocumentListener(new DocumentListener() {

            public void changedUpdate(DocumentEvent e) {
                try {
                    totalize(myTable, amountLabel, vatAmountLabel, totalAmountLabel, Double.parseDouble(vatTextField.getText().trim()));
                    vatStatus.setText("VAT UPDATED");
                } catch (Exception ex) {
                    vatStatus.setText("INVALID VAT VALUE!");
                }
            }

            public void removeUpdate(DocumentEvent e) {
                try {
                    totalize(myTable, amountLabel, vatAmountLabel, totalAmountLabel, Double.parseDouble(vatTextField.getText().trim()));
                    vatStatus.setText("VAT UPDATED");
                } catch (Exception ex) {
                    vatStatus.setText("INVALID VAT VALUE!");
                }
            }

            public void insertUpdate(DocumentEvent e) {
                try {
                    totalize(myTable, amountLabel, vatAmountLabel, totalAmountLabel, Double.parseDouble(vatTextField.getText().trim()));
                    vatStatus.setText("VAT UPDATED");
                } catch (Exception ex) {
                    vatStatus.setText("INVALID VAT VALUE!");
                }
            }
        });


        GeneratePDFTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //check kung empty
                if (DateChooser.getDate()!=null
                        &companyNametextField.getText().trim() != ""
                        & customerTextField.getText().trim() != "" &
                        addressTextField.getText().trim() != "" &
                        recieptNumberTextField.getText().trim() != "") {
                    try {
                        //dito ilalagay ang mga laman ng  table
                        ArrayList<ItemBean> itemArray = new ArrayList<ItemBean>();

                        //ito yung mga personal info
                        RecieptBean recieptBean = BeanFactory.createBean(companyNametextField.getText()
                                , customerTextField.getText(),
                                addressTextField.getText(),
                                recieptNumberTextField.getText());

                        double amountdue = 0;
                        double total = 0;
                        double vat = 0;
                        //dito na nilalagay kada row
                        for (int x = 0; x < myTable.getRowCount(); x++) {
                            amountdue += Double.parseDouble("" + myTable.getValueAt(x, 3));
                            itemArray.add(BeanFactory.createBean("" + myTable.getValueAt(x, 0)
                                    , Double.parseDouble("" + myTable.getValueAt(x, 1))
                                    , Integer.parseInt("" + myTable.getValueAt(x, 2))
                                    , Double.parseDouble("" + myTable.getValueAt(x, 3))));
                        }

                        vat = amountdue * (Double.parseDouble(vatTextField.getText().trim()) / 100);
                        total = vat + amountdue;

                        //paggawa ng pdf method.
                        pdfBean.CreatePDF(itemArray.toArray(new ItemBean[itemArray.size()]), recieptBean, amountdue, vat, total,
                                Double.parseDouble(vatTextField.getText()), logo,DateChooser.getDate());

                        statusLabel.setText("PDF GENERATED");
                    } catch (Exception ae) {
                        statusLabel.setText("ERROR IN GENERATING PDF");
                        ae.printStackTrace();
                    }
                }else{
                    statusLabel.setText("SOME FIELDS ARE MISSING INPUT");
                }
            }

        });

        chooseLogoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                //for specific file extensions lang tinatanggap
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "JPG & GIF Images", "jpg", "gif");
                chooser.setFileFilter(filter);


                int returnVal = chooser.showOpenDialog(INVOICEGUI.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    logo = chooser.getSelectedFile();
                    //This is where a real application would open the file.
                    // log.append("Opening: " + file.getName() + "." + newline);
                    logoPathLabel.setText("LOGOPATH:" + logo.getAbsolutePath());
                } else {
                    statusLabel.setText("CHOOSE LOGO CANCELLED");
                    logoPathLabel.setText("LOGO PATH:");
                }
            }


        });
        removeLogoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logo = null;
                logoPathLabel.setText("LOGO PATH:");
                statusLabel.setText("REMOVED LOGO");
            }
        });


        //para sa pagkuha ng currentDate
        CURRENTDATEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DateChooser.setDate(new GregorianCalendar().getTime());
                System.out.println(DateChooser.getDate());
            }
        });
    }

    public static void main(String args[]) {

        //For pampaganda lang, I like purple :D
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                System.out.println(info.getName());
                if ("CDE/Motif".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //initialize gui
        new INVOICEGUI();
    }

    //update total vat
    public static void totalize(JTable table, JLabel amountdueLabel, JLabel vatLabel, JLabel totalamountlabel, double vatPercent) throws Exception {

        double amountdue = 0;
        double total = 0;
        double vat = 0;
        for (int x = 0; x < table.getRowCount(); x++) {
            amountdue += Double.parseDouble("" + table.getValueAt(x, 3));
        }

        //bawal ang negative na vat
        if (vatPercent < 0) throw new Exception();
        vat = amountdue * (vatPercent / 100);
        total = vat + amountdue;

        System.out.println(amountdue + " " + total + " " + vat);
        amountdueLabel.setText(formatter.format(amountdue));
        vatLabel.setText(formatter.format(vat));
        totalamountlabel.setText(formatter.format(amountdue + vat));


    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

