package com.jitesh.pixiverse.dto;

import lombok.Data;

@Data
public class TextGenerationReqDTO {

    private String prompt;
    private String style;
}
