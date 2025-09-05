package com.jitesh.pixiverse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextToImageRequestDTO {

    private List<TextPrompt> text_prompts;
    private double cfg_scale = 7;
    private int height = 1024;
    private int width = 1024;
    private int steps = 30;
    private String style_preset;

    public TextToImageRequestDTO(String txt, String style) {
        this.text_prompts = List.of(new TextPrompt(txt));
        this.style_preset = style;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TextPrompt {
        private String text;
    }
}
