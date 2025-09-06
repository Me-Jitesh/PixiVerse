package com.jitesh.pixiverse.services;

import com.jitesh.pixiverse.clients.StabilityAIClient;
import com.jitesh.pixiverse.dto.TextToImageRequestDTO;
import com.jitesh.pixiverse.utilities.ImagePreprocessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class GhibliArtService {

    private final StabilityAIClient stabilityAIClient;
    private final String API_KEY;
    private final String promptEnhance;
    private final String engineId;
    private final String preset;

    @Autowired
    public GhibliArtService(StabilityAIClient stabilityAIClient, @Value("${stability.api.key}") String API_KEY, @Value("${gen.ai.prompt.enhance}") String PromptEnhance, @Value("${gen.ai.engine.id}") String engineId, @Value("${gen.ai.preset}") String preset) {
        this.stabilityAIClient = stabilityAIClient;
        this.API_KEY = API_KEY;
        this.promptEnhance = PromptEnhance;
        this.engineId = engineId;
        this.preset = preset;
    }

    public byte[] createGhibliArt(MultipartFile image, String prompt) {
        try {
            // Save multipart to temp file
            File tempFile = File.createTempFile("upload-", ".png");
            image.transferTo(tempFile);

            // Preprocess to nearest allowed dimension
            File processed = ImagePreprocessor.prepareImage(tempFile);

            // Final enhanced prompt
            String finalPrompt = prompt + promptEnhance;

            // Call Stability API
            return stabilityAIClient.generateImageFromImage(
                    "Bearer " + API_KEY,
                    engineId,
                    processed,
                    finalPrompt,
                    preset
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Ghibli art", e);
        }
    }

    public byte[] createGhibliArtFromText(String prompt, String style) {

        String stylePreset = style.equals("general") ? "anime" : style.replace("_", "-");

        TextToImageRequestDTO reqPayload = new TextToImageRequestDTO(prompt + promptEnhance, stylePreset);

        return stabilityAIClient.generateImageFromText("Bearer " + API_KEY,
                engineId,
                reqPayload);
    }
}
