package net.hackatom.services;

import lombok.SneakyThrows;
import net.hackatom.entity.Attachment;
import net.hackatom.repositories.AttachmentRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    AttachmentRepository attachmentRepository;

    @PostConstruct
    public void init() {
        sources.put(500L, "classpath:files/npp_schema.pdf");
        sources.put(600L, "classpath:files/cat.pdf");
    }

    public byte[] getImage(Long id) {
        return attachmentRepository.findById(id).orElseGet(() -> getImageFromPdf(id)).getFile();
    }

    @SneakyThrows
    @Cacheable(value = "schemaCache", key = "#id")
    public Attachment getImageFromPdf(Long id) {
        String base64Str = null;
        PDDocument document = PDDocument.load(ResourceUtils.getFile(Optional.of("classpath:files/npp_schema.pdf").orElseThrow(
                () -> new RuntimeException("No file found!")
        )));
        List<PDPage> pageList = document.getDocumentCatalog().getAllPages();
        List<BufferedImage> pageImages = new ArrayList<>();
        for (PDPage page : pageList) {
            pageImages.add(page.convertToImage());
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(pageImages.get(0), "png", baos);
        System.out.println("Conversion complete");
        document.close();
        Attachment attachment = new Attachment();
        attachment.setFile(baos.toByteArray());
        return attachmentRepository.save(attachment);
    }
}