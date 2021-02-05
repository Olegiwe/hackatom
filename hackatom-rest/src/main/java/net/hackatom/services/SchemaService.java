package net.hackatom.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service

public class SchemaService {

    private final ConcurrentHashMap<Long, String> sources = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        sources.put(500L, "classpath:files/npp_schema.pdf");
        sources.put(600L, "classpath:files/cat.pdf");
    }

    @Cacheable(value = "schemaCache", key = "#id")
    public String getImageFromPdf(Long id) {
        String base64Str = null;
        try {
            PDDocument document = PDDocument.load(ResourceUtils.getFile(Optional.ofNullable(sources.get(id)).orElseThrow(
                    () -> new RuntimeException("No file found!")
            )));
            List<PDPage> pageList = document.getDocumentCatalog().getAllPages();
            List<BufferedImage> pageImages = new ArrayList<>();
            for (PDPage page : pageList) {
                BufferedImage image = page.convertToImage();
                pageImages.add(page.convertToImage());
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(pageImages.get(0), "png", baos);
            System.out.println("Conversion complete");
            base64Str = Base64.getEncoder().encodeToString(baos.toByteArray());
            document.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return base64Str;
    }
}