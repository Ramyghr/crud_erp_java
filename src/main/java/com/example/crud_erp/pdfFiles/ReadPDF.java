package com.example.crud_erp.pdfFiles;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class ReadPDF {
    public static void main(String[] args) throws IOException {
        File pdfFile = new File(".\\data\\sample.pdf");
        PDDocument document=PDDocument.load(pdfFile);
        PDFTextStripper pdfStripper=new PDFTextStripper();
        String text=pdfStripper.getText(document);
        document.close();
        System.out.println(text);

    }
}
