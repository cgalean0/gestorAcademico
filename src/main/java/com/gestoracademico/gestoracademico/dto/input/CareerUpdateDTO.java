package com.gestoracademico.gestoracademico.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CareerUpdateDTO {
    private String name;
    private int duration;
}
