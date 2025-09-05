package com.jitesh.pixiverse.controllers;

import com.jitesh.pixiverse.dto.TextGenerationReqDTO;
import com.jitesh.pixiverse.services.GhibliArtService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@RestController
@RequestMapping("/api/v1/pixiverse")
@CrossOrigin("*")
public class GenerationController {


    private final GhibliArtService ghibliArtService;

    @Autowired
    public GenerationController(GhibliArtService ghibliArtService) {
        this.ghibliArtService = ghibliArtService;
    }

    @PostMapping(value = "/generate", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateGhibliArt(@RequestParam("image") MultipartFile image, @RequestParam("prompt") String prompt) {
        try {
            byte[] imgBytes = ghibliArtService.createGhibliArt(image, prompt);
            System.out.println("Generated");
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imgBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value = "/generate/text", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateGhibliArtFromText(@RequestBody TextGenerationReqDTO req) {
        try {
            byte[] image = ghibliArtService.createGhibliArtFromText(
                    req.getPrompt(),
                    req.getStyle()
            );
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(image);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
