package com.ust;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


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
                    if(itemNameTextField.getText().trim()!=""|
                            quantityTextField.getText().trim()!=null|
                            priceLabel.getText().trim()!=null
                            ) {
                        String itemName=itemNameTextField.getText().trim();
                        int quantity=Integer.parseInt(quantityTextField.getText().trim());
                        double price=Double.parseDouble(priceTextField.getText().trim());
                        double amount=quantity*price;
                        modelo.addRow(new Object[]{quantity,itemName,price,amount});
                        totalize(myTable,TotalLabel);
                    }
                }
                catch (Exception s){
                    statusLabel.setText("INVALID INPUT");
                    s.printStackTrace();
                }
            }
        });

        //for the DeleteButton
        DELETEITEMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(myTable.getSelectedRow()>=0){
                    modelo.removeRow(myTable.getSelectedRow());
                    totalize(myTable,TotalLabel);
                    statusLabel.setText("ROW REMOVED");
                }else{
                    statusLabel.setText("NO SELECTED ROWS FOR DELETION");
                }
            }
        });
    }

    public static void main(String args[]){

        //For pampaganda lang, I like purple kasi e
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

        //initialize gui
        new INVOICEGUI();
    }

    public static void totalize(JTable table,JLabel label){
        int total=0;
        for(int x=0;x<table.getRowCount();x++){
            total+=Double.parseDouble(""+table.getValueAt(x,3));

        }
        label.setText(""+total);
    }
}
