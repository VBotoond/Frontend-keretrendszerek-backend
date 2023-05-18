package hu.pte.frontendkeretrendszerekbackend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StarWarsDto {

    private String nev;

    private String bolygo;

    private String faj;

    private Integer like;
}