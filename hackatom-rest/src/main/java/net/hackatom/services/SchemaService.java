package net.hackatom.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SchemaService {

    private static final String PDF_SOURCE = "/files/cat.pdf";

    public byte[] getImage(Long id) {
        try {
            PDDocument document = PDDocument.load(PDF_SOURCE);
            List<PDPage> pageList = document.getDocumentCatalog().getAllPages();
            List<BufferedImage> pageImages = new ArrayList<>();
            for (PDPage page : pageList) {
                BufferedImage image = page.convertToImage();
                pageImages.add(page.convertToImage());
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(pageImages.get(0), "png", baos);
            System.out.println("HOORAY");
            return baos.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "OSHIBKA".getBytes(StandardCharsets.UTF_8);
    }

}
