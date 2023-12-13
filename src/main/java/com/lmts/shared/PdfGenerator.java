
package com.lmts.shared;

import com.lmts.model.TicketHistory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;

public class PdfGenerator {
    public static File generateTicketPDF(TicketHistory ticket) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 700); // Set starting position

                contentStream.showText("Ticket ID: " + ticket.getId());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Music Name: " + ticket.getMusicName());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Quantity: " + ticket.getQuantity());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Total Price: " + ticket.getTotalPrice());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Date: " + ticket.getDate());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Time: " + ticket.getTime());

                contentStream.endText();
            }

            File pdfFile = new File("ticket.pdf");
            document.save(pdfFile);
            return pdfFile;
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
            return null;
        }
    }
}

