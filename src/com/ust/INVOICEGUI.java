package com.ust;

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
import java.text.NumberFormat;import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jude on 3/6/2016.
 */
public class INVOICEGUI extends JFrame {
    private static NumberFormat formatter = new DecimalFormat("#.00");
    private JPanel InvoiceMainPanel;
    private JTextField customerTextField;
    private JTextField addressTextField;
    private JTextField itemNameTextField;
    private JTextField quantityTextField;
    private JTextField priceTextField;
    private JScrollPane BoomPanes;
    private JButton ADDITEMButton;
    private JButton DELETEITEMButton;
    private JTable table1;
    private JLabel QtyLabel;
    private JLabel priceLabel;
    private JButton GeneratePDFTextField;
    private JLabel statusLabel;
    private JLabel TotalLabel;
    private JTextField vatTextField;
    private JLabel vatStatus;
    private JTextField companyNametextField;
    private JTextField recieptNumberTextField;
    private JLabel totalAmountLabel;
    private JLabel vatAmountLabel;
    private JLabel amountLabel;
    private JButton chooseLogoButton;
    List<Object[]> list = new ArrayList<Object[]>();
    File logo=null;

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
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
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
                    if (itemNameTextField.getText().trim() != "" &
                            quantityTextField.getText().trim() != null &
                            priceLabel.getText().trim() != null &
                            vatTextField.getText().trim() != null
                            ) {


                        String itemName = itemNameTextField.getText().trim();
                        int quantity = Integer.parseInt(quantityTextField.getText().trim());
                        double price = Double.parseDouble(priceTextField.getText().trim());
                        double vatPercent = Double.parseDouble(vatTextField.getText().trim());


                        //double vat = price * (vatPercent / 100);
                        double total = price * quantity;


                        modelo.addRow(new Object[]{itemName, price, quantity, total});
                        totalize(myTable, amountLabel, vatAmountLabel, totalAmountLabel, Double.parseDouble(vatTextField.getText().trim()));
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
                if (myTable.getSelectedRow() >= 0) {
                    modelo.removeRow(myTable.getSelectedRow());
                    totalize(myTable, amountLabel, vatAmountLabel, totalAmountLabel, Double.parseDouble(vatTextField.getText().trim()));
                    statusLabel.setText("ROW REMOVED");
                } else {
                    statusLabel.setText("NO ROWS TO DELETE");
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
                if (companyNametextField.getText().trim() != ""
                        & customerTextField.getText().trim() != "" &
                        addressTextField.getText().trim() != "" &
                        recieptNumberTextField.getText().trim() != "") {
                    try{
                    ArrayList<ItemBean> itemArray = new ArrayList<ItemBean>();
                    RecieptBean recieptBean = BeanFactory.createBean(companyNametextField.getText()
                            , customerTextField.getText(),
                            addressTextField.getText(),
                            recieptNumberTextField.getText());

                    double amountdue = 0;
                    double total = 0;
                    double vat = 0;
                    for (int x = 0; x < myTable.getRowCount(); x++) {
                        amountdue += Double.parseDouble("" + myTable.getValueAt(x, 3));
                        itemArray.add(BeanFactory.createBean("" + myTable.getValueAt(x, 0)
                                , Double.parseDouble("" + myTable.getValueAt(x, 1))
                                , Integer.parseInt("" + myTable.getValueAt(x, 2))
                                , Double.parseDouble("" + myTable.getValueAt(x, 3))));
                    }

                    vat = amountdue * (Double.parseDouble(vatTextField.getText().trim()) / 100);
                    total = vat + amountdue;

                    pdfBean.CreatePDF(itemArray.toArray(new ItemBean[itemArray.size()]),recieptBean,amountdue,vat,total,
                            Double.parseDouble(vatTextField.getText()),logo);

                        statusLabel.setText("PDF GENERATED");
                    }catch(Exception ae){
                        statusLabel.setText("ERROR IN GENERATING PDF");
                        ae.printStackTrace();
                    }
                }
            }

        });

        chooseLogoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "JPG & GIF Images", "jpg", "gif");
                chooser.setFileFilter(filter);


                    int returnVal = chooser.showOpenDialog(INVOICEGUI.this);

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        logo = chooser.getSelectedFile();
                        //This is where a real application would open the file.
                       // log.append("Opening: " + file.getName() + "." + newline);
                    } else {
                        statusLabel.setText("CHOOSE LOGO CANCELLED");
                    }
                }


        });
    }

    public static void main(String args[]) {

        //For pampaganda lang, I like purple kasi e
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

    //update rows
    public static void totalize(JTable table, JLabel amountdueLabel, JLabel vatLabel, JLabel totalamountlabel, double vatPercent) {

        double amountdue = 0;
        double total = 0;
        double vat = 0;
        for (int x = 0; x < table.getRowCount(); x++) {
            amountdue += Double.parseDouble("" + table.getValueAt(x, 3));
        }


        vat = amountdue * (vatPercent / 100);
        total = vat + amountdue;

        System.out.println(amountdue + " " + total + " " + vat);
        amountdueLabel.setText(formatter.format(amountdue));
        vatLabel.setText(formatter.format(vat));
        totalamountlabel.setText(formatter.format(amountdue+vat));


    }
}

