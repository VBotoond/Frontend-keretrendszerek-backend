package hu.pte.frontendkeretrendszerekbackend.controller;

import hu.pte.frontendkeretrendszerekbackend.dto.StarWarsDto;
import hu.pte.frontendkeretrendszerekbackend.model.StarWars;
import hu.pte.frontendkeretrendszerekbackend.service.StarWarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class HomePageController {

    @Autowired
    private final StarWarsService starWarsService;

    public HomePageController(StarWarsService starWarsService) {
        this.starWarsService = starWarsService;
    }


    // Create
    @PostMapping("/create")
    public ResponseEntity<StarWars> createCharacter(@RequestBody StarWarsDto starWarsDto) {
        StarWars createdCharacter = starWarsService.createCharter(starWarsDto);
        return new ResponseEntity<>(createdCharacter, HttpStatus.CREATED);
    }

    // Read all
    @GetMapping("/getAll")
    public ResponseEntity<List<StarWars>> getAllCharacters() {
        List<StarWars> characters = starWarsService.getAllCharacters();
        return new ResponseEntity<>(characters, HttpStatus.OK);
    }

    // Update likes
    @PutMapping("/updateLike/{id}")
    public ResponseEntity<StarWars> updateCharacterLikes(@PathVariable int id, @RequestBody StarWarsDto starWarsDto) {
        StarWars updatedCharacter = starWarsService.updateCharacterLikes(id, starWarsDto.getLike());

        if (updatedCharacter != null) {
            return new ResponseEntity<>(updatedCharacter, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update likes
    @PutMapping("/update/{id}")
    public ResponseEntity<StarWars> updateCharacter(@PathVariable int id, @RequestBody StarWarsDto starWarsDto) {
        StarWars updatedCharacter = starWarsService.updateCharacter(id, starWarsDto);
        if (updatedCharacter != null) {
            return new ResponseEntity<>(updatedCharacter, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // Delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<List<StarWars>> deleteCharacter(@PathVariable int id) {
        boolean deleted = starWarsService.deleteCharacter(id);
        if (deleted) {
            List<StarWars> newSW = starWarsService.getAllCharacters();
            return new ResponseEntity<>(newSW, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
