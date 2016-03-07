package com.ust;

/**
 * Created by Jude on 3/7/2016.
 */

    import java.io.IOException;

    import com.itextpdf.text.DocumentException;
    import com.itextpdf.text.Phrase;
    import com.itextpdf.text.Rectangle;
    import com.itextpdf.text.pdf.PdfPCell;
    import com.itextpdf.text.pdf.PdfPTable;
    import com.ust.model.ItemBean;
    import com.ust.model.RecieptBean;

public class pdfBean {
        public void CreatePDF(ItemBean[] itemBean, RecieptBean person, double vat, double total)throws DocumentException,IOException{
             String directory
                    ="COMPANYRECIEPT"+ new java.util.Date().getTime()+person.getCustomerName();



        }
    public static PdfPTable createItemTable() throws DocumentException {
        PdfPTable table = new PdfPTable(3);
        Rectangle rect = new Rectangle(523, 770);
        table.setWidthPercentage(new float[]{ 144, 72, 72 }, rect);
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("Table 4"));
        cell.setColspan(3);
        table.addCell(cell);
        /*
        cell = new PdfPCell(new Phrase("Cell with rowspan 2"));
        cell.setRowspan(2);
        table.addCell(cell);
        */
        table.addCell("row 1; cell 1");
        table.addCell("row 1; cell 2");
        table.addCell("row 2; cell 1");
        table.addCell("row 2; cell 2");
        return table;
    }

    }



