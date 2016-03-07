package com.ust;

/**
 * Created by Jude on 3/7/2016.
 */

    import java.io.File;
    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.net.MalformedURLException;

    import com.itextpdf.text.*;
    import com.itextpdf.text.pdf.PdfPCell;
    import com.itextpdf.text.pdf.PdfPTable;
    import com.itextpdf.text.pdf.PdfWriter;
    import com.ust.model.ItemBean;
    import com.ust.model.RecieptBean;

public class pdfBean {
        public static void CreatePDF(ItemBean[] itemBean, RecieptBean person,
                                     double subAmount, double vat, double total, double vatPercentage
        , File logo)throws DocumentException,IOException{
             String directory
                    =person.getCompanyName()+ new java.util.Date().getTime()+person.getCustomerName()+".pdf";
            //init the document
            Document document = new Document();
            //open the instance of document
            PdfWriter.getInstance(document, new FileOutputStream(directory));
            // step 3
            document.open();

            Image image;
            try {
                image = Image.getInstance(logo.getAbsolutePath());
                image.scaleToFit(100,100);
                image.setAlignment(Image.MIDDLE);
                document.add(image);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Chunk chunk = new Chunk(person.getCompanyName().toUpperCase()+" SALES INVOICE");
            //document.add(chunk);

            Paragraph paragraph = new Paragraph();
            paragraph.add(person.getCompanyName().toUpperCase()+" SALES INVOICE");
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);

            paragraph = new Paragraph();
            paragraph.add("RECIEPT NO:"+person.getRecieptNumber());
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph);

            paragraph = new Paragraph();
            paragraph.add("CUSTOMER NAME:"+person.getCustomerName());
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);

            paragraph = new Paragraph();
            paragraph.add("CUSTOMER ADDRESS:"+person.getCustomerName());
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
        Rectangle rect = new Rectangle(500,500);
        table.setWidthPercentage(new float[]{ 144, 72, 72,72 },rect);
        PdfPCell cell;
        //gawa ng HEADER

      
        /*
        cell = new PdfPCell(new Phrase("Cell with rowspan 2"));
        cell.setRowspan(2);
        table.addCell(cell);
        */
        table.addCell("ITEM NAME");
        table.addCell("PRICE");
        table.addCell("QTY");
        table.addCell("TOTAL");
        for(ItemBean item:items) {

            table.addCell(item.getName());
            table.addCell(""+item.getPrice());
            table.addCell(""+item.getQuantity());
            table.addCell(""+item.getTotal());
        }
        cell = new PdfPCell();
        cell.setColspan(2);
        table.addCell(cell);
        table.addCell("AMOUNT DUE");
        table.addCell(""+subAmount);


        cell = new PdfPCell();
        cell.setColspan(2);
        table.addCell(cell);
        table.addCell("VAT("+vatPercentage+"%)");
        table.addCell(""+vat);

        cell = new PdfPCell();
        cell.setColspan(2);
        table.addCell(cell);
        table.addCell("TOTAL AMOUNT");
        table.addCell(""+total);


        return table;
    }

    }



