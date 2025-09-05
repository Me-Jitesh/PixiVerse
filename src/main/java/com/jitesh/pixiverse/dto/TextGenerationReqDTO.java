package com.jitesh.pixiverse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextGenerationReqDTO {

    private String prompt;
    private String style;
}
