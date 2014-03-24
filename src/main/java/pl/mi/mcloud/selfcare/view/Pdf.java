/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.mi.mcloud.selfcare.view;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.vaadin.server.StreamResource.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 *
 * @author bor
 */
/**
     * This class creates a PDF with the iText library. This class implements
     * the StreamSource interface which defines the getStream method.
     */
    public class Pdf implements StreamSource {
        private final ByteArrayOutputStream os = new ByteArrayOutputStream();

        public Pdf() {
            Document document = null;

            try {
                document = new Document(PageSize.A4, 50, 50, 50, 50) {};
                PdfWriter.getInstance(document, os);
                document.open();

                document.add(new Paragraph("This is some content for the sample PDF!"));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (document != null) {
                    document.close();
                }
            }
        }

        @Override
        public InputStream getStream() {
            // Here we return the pdf contents as a byte-array
            return new ByteArrayInputStream(os.toByteArray());
        }
        
        
    }
