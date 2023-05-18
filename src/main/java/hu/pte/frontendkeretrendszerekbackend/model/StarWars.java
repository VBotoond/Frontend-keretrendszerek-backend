package hu.pte.frontendkeretrendszerekbackend.model;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class StarWars {

    private Integer id;

    private String Nev;

    private String Bolygo;

    private String Faj;

    private Integer like;
}
