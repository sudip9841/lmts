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
                // Set font and size for the title
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 24);

                // Title
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 750); // Set starting position
                contentStream.showText("Music Event Ticket");
                contentStream.endText();

                // Set font and size for other text elements
                contentStream.setFont(PDType1Font.HELVETICA, 14);

                // Ticket Details in Grid (3 columns)
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 700); // Set starting position

                contentStream.showText("Ticket ID: " + ticket.getId());
                contentStream.newLineAtOffset(200, 0);
                contentStream.showText("Music Name: " + ticket.getMusicName());
                contentStream.newLineAtOffset(200, 0);
                contentStream.showText("Quantity: " + ticket.getQuantity());
                contentStream.newLineAtOffset(-400, -20);
                contentStream.showText("Total Price: $" + ticket.getTotalPrice());
                contentStream.newLineAtOffset(200, 0);
                contentStream.showText("Date: " + ticket.getDate());
                contentStream.newLineAtOffset(200, 0);
                contentStream.showText("Time: " + ticket.getTime());

                contentStream.endText();
            }

            File pdfFile = new File("music_ticket.pdf");
            document.save(pdfFile);
            return pdfFile;
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
            return null;
        }
    }
}
