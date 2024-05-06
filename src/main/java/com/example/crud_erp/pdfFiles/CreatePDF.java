package com.example.crud_erp.pdfFiles;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import com.example.crud_erp.model.Salary;

import java.io.IOException;

public class CreatePDF {
    public static void main(String[] args) throws IOException {
        //CREATE DOCUMENT
        PDDocument document = new PDDocument();
        //CEATE PAGE
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        //CREATE CONTENT STREAM
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        //CONTENT AND SIZE
        contentStream.setFont(PDType1Font.COURIER,14);
        contentStream.beginText();
        contentStream.newLineAtOffset(50, 700);
        contentStream.showText("Welcome to PDF File");
        contentStream.endText();

        contentStream.close();

        document.save(".\\data\\sample.pdf");
        document.close();
        System.out.println("pdf created succesfully !");




    }
}
