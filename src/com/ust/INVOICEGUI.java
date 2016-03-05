package com.ust;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



/**
 * Created by Jude on 3/6/2016.
 */
public class INVOICEGUI extends JFrame{
    private JPanel InvoiceMainPanel;
    private JTextField billToTextField;
    private JTextField textField2;
    private JTextField billToAddressTextField;
    private JTextField shipToAddressTextField;
    private JLabel shipToTextField;
    private JTextField itemNameTextField;
    private JTextField textField3;
    private JTextField textField4;
    private JScrollPane BoomPanes;
    private JButton ADDITEMButton;
    private JButton DELETEITEMButton;
    private JTable table1;
    private JLabel QtyTextField;
    private JLabel priceTextField;
    private JButton GeneratePDFTextField;
    List<Object[]> list=new ArrayList<Object[]>();

    DefaultTableModel modelo;
    public INVOICEGUI(){
        super();
        modelo = new DefaultTableModel();
        modelo.addColumn("QTY");
        modelo.addColumn("ITEM NAME");
        modelo.addColumn("PRICE");
        modelo.addColumn("AMOUNT");


        JTable myTable = new JTable(modelo);
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
                try{
                    if(itemNameTextField.getText().trim()!=""! ) {
                        String itemName;
                    int quantity;
                    double price;
                    double amount;
                }
                catch (Exception s){
                    s.printStackTrace();
                }
            }
        });
    }

    public static void main(String args[]){

        //For pampaganda lang
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                System.out.println(info.getName());
                if ("CDE/Motif".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        new INVOICEGUI();
    }
}
