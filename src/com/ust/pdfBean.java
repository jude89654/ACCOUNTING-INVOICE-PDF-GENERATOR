package com.ust;

/**
 * Created by Jude on 3/7/2016.
 */

    import java.io.File;
    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.net.MalformedURLException;
    import java.text.DecimalFormat;
    import java.text.NumberFormat;

    import com.itextpdf.text.*;
    import com.itextpdf.text.pdf.PdfPCell;
    import com.itextpdf.text.pdf.PdfPTable;
    import com.itextpdf.text.pdf.PdfWriter;
    import com.ust.model.ItemBean;
    import com.ust.model.RecieptBean;

public class pdfBean {
    private static NumberFormat formatter = new DecimalFormat("#.00");
        public static void CreatePDF(ItemBean[] itemBean, RecieptBean person,
                                     double subAmount, double vat, double total, double vatPercentage
        , File logo)throws DocumentException,IOException{
             String directory
                    =person.getCompanyName()+ new java.util.Date().getDate()+person.getCustomerName()+".pdf";
            //init the document
            Document document = new Document();
            //open the instance of document
            PdfWriter.getInstance(document, new FileOutputStream(directory));
            // step 3
            document.open();


            PdfPTable logoAndCompanyTable;
            if(logo!=null){
               logoAndCompanyTable = new PdfPTable(2);}
                else
                {
                    logoAndCompanyTable = new PdfPTable(1);}

                Image image;
            Font font = FontFactory.getFont("Arial",30);
            Chunk chunk = new Chunk();
            Paragraph para = new Paragraph();
            para.setFont(font);
            para.setAlignment(Element.ALIGN_CENTER);


            try {
                if(logo!=null) {
                    PdfPCell sel= new PdfPCell();

                    image = Image.getInstance(logo.getAbsolutePath());
                    image.scaleToFit(100, 100);
                    para.add(new Chunk(image,0,0));

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Chunk chunk = new Chunk(person.getCompanyName().toUpperCase()+" SALES INVOICE");
            //document.add(chunk);



            Phrase x = new Phrase(person.getCompanyName().toUpperCase());
            x.setFont(font);
            para.add(x);

            document.add(para);
            //logoAndCompanyTable.addCell(x);


            document.add(logoAndCompanyTable);
            Paragraph paragraph = new Paragraph();
            paragraph = new Paragraph();
            paragraph.add("SALES INVOICE");
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);


            paragraph = new Paragraph();
            paragraph.add("RECEIPT NO.: "+person.getRecieptNumber());
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph);

            paragraph = new Paragraph();
            paragraph.add("CUSTOMER NAME: "+person.getCustomerName());
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);

            paragraph = new Paragraph();
            paragraph.add("CUSTOMER ADDRESS: "+person.getCustomerAddress());
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);

            paragraph = new Paragraph();
            paragraph.add("   ");
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);




            PdfPTable table = createItemTable(itemBean,subAmount,vat,total,vatPercentage);
            document.add(table);
            document.close();

        }
    public static PdfPTable createItemTable(ItemBean[] items,double subAmount, double vat, double total, double vatPercentage) throws DocumentException {
        PdfPTable table = new PdfPTable(4);
        Rectangle rect = new Rectangle(600,600);
        table.setWidthPercentage(new float[]{ 144, 72, 72,72 },rect);
        PdfPCell cell;
        //gawa ng HEADER


        /*
        cell = new PdfPCell(new Paragraph("Cell with rowspan 2"));
        cell.setRowspan(2);
        table.addCell(cell);
        */
        table.addCell("ITEM NAME");
        table.addCell("PRICE");
        table.addCell("QTY");
        table.addCell("TOTAL");
        for(ItemBean item:items) {


            PdfPCell cells = new PdfPCell(new Paragraph(item.getName()));
            cells.setHorizontalAlignment(Element.ALIGN_CENTER);


            table.addCell(cells);

            cells = new PdfPCell(new Paragraph(formatter.format(item.getPrice())));
            cells.setHorizontalAlignment(Element.ALIGN_CENTER);


            table.addCell(cells);

            cells = new PdfPCell(new Paragraph(""+item.getQuantity()));
            cells.setHorizontalAlignment(Element.ALIGN_CENTER);


            table.addCell(cells);


            cells = new PdfPCell(new Paragraph(formatter.format(item.getTotal())));
            cells.setHorizontalAlignment(Element.ALIGN_CENTER);

            table.addCell(cells);

        }
        cell = new PdfPCell();
        cell.setColspan(2);
        table.addCell(cell);
        table.addCell("AMOUNT DUE");

        cell = new PdfPCell(new Paragraph(formatter.format(subAmount)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(cell);



        cell = new PdfPCell();
        cell.setColspan(2);
        table.addCell(cell);
        table.addCell("VAT("+vatPercentage+"%)");

        cell = new PdfPCell(new Paragraph(formatter.format(vat)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);


        table.addCell(cell);

        cell = new PdfPCell();
        cell.setColspan(2);
        table.addCell(cell);
        table.addCell("TOTAL AMOUNT");


        cell = new PdfPCell(new Paragraph(formatter.format(total)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);



        return table;
    }

    }



