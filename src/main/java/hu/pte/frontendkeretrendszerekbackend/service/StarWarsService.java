package hu.pte.frontendkeretrendszerekbackend.service;

import hu.pte.frontendkeretrendszerekbackend.dto.StarWarsDto;
import hu.pte.frontendkeretrendszerekbackend.model.StarWars;
import org.springframework.stereotype.Service;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class StarWarsService {


    private String filePath = "src/main/resources/sw.json";

    public StarWars createCharter(StarWarsDto starWarsDto) {
        try {
            // Read the existing JSON data from the file
            String jsonData = new String(Files.readAllBytes(Paths.get(filePath)));

            // Parse the JSON data into a JSON array
            JSONArray jsonArray = new JSONObject(jsonData).getJSONArray("sw");

            // Generate a new unique ID for the new record
            Random random = new Random();
            int newId = 100000000 + random.nextInt(900000000);

            // Create a new JSON object for the new record
            JSONObject newRecord = new JSONObject();
            newRecord.put("id", newId);
            newRecord.put("nev", starWarsDto.getNev());
            newRecord.put("bolygo", starWarsDto.getBolygo());
            newRecord.put("faj", starWarsDto.getFaj());
            newRecord.put("likes", 0);

            // Add the new record to the JSON array
            jsonArray.put(newRecord);

            // Update the JSON data with the modified array
            JSONObject updatedJson = new JSONObject();
            updatedJson.put("sw", jsonArray);

            // Write the updated JSON data back to the file
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(updatedJson.toString());
            fileWriter.close();

            // Return the created StarWars object
            return new StarWars(newId, starWarsDto.getNev(), starWarsDto.getBolygo(), starWarsDto.getFaj(), 0);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any errors that occur during the process
            return null;
        }

    }

    public List<StarWars> getAllCharacters() {
        try {
            // Read the existing JSON data from the file
            String jsonData = new String(Files.readAllBytes(Paths.get(filePath)));

            // Parse the JSON data into a JSON array
            JSONArray jsonArray = new JSONObject(jsonData).getJSONArray("sw");

            // Convert the JSON array to a list of StarWars objects
            List<StarWars> characters = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonCharacter = jsonArray.getJSONObject(i);
                StarWars character = new StarWars(
                        jsonCharacter.getInt("id"),
                        jsonCharacter.getString("nev"),
                        jsonCharacter.getString("bolygo"),
                        jsonCharacter.getString("faj"),
                        jsonCharacter.getInt("likes")
                );
                characters.add(character);
            }

            return characters;
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any errors that occur during the process
            return null;
        }
    }

    public StarWars getCharacterById(int id) {
        try {
            // Read the existing JSON data from the file
            String jsonData = new String(Files.readAllBytes(Paths.get(filePath)));

            // Parse the JSON data into a JSON array
            JSONArray jsonArray = new JSONObject(jsonData).getJSONArray("sw");

            // Find the character with the specified ID
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonCharacter = jsonArray.getJSONObject(i);
                if (jsonCharacter.getInt("id") == id) {
                    return new StarWars(
                            jsonCharacter.getInt("id"),
                            jsonCharacter.getString("nev"),
                            jsonCharacter.getString("bolygo"),
                            jsonCharacter.getString("faj"),
                            jsonCharacter.getInt("likes")
                    );
                }
            }

            return null; // Character not found
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any errors that occur during the process
            return null;
        }
    }

    public StarWars updateCharacterLikes(int id, int likes) {
        try {
            // Read the existing JSON data from the file
            String jsonData = new String(Files.readAllBytes(Paths.get(filePath)));

            // Parse the JSON data into a JSON array
            JSONArray jsonArray = new JSONObject(jsonData).getJSONArray("sw");

            // Find the character with the specified ID and update its likes
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonCharacter = jsonArray.getJSONObject(i);
                if (jsonCharacter.getInt("id") == id) {
                    jsonCharacter.put("likes", likes);

                    // Update the JSON data with the modified character
                    JSONObject updatedJson = new JSONObject();
                    updatedJson.put("sw", jsonArray);

                    // Write the updated JSON data back to the file
                    FileWriter fileWriter = new FileWriter(filePath);
                    fileWriter.write(updatedJson.toString());
                    fileWriter.close();

                    return new StarWars(
                            jsonCharacter.getInt("id"),
                            jsonCharacter.getString("nev"),
                            jsonCharacter.getString("bolygo"),
                            jsonCharacter.getString("faj"),
                            likes
                    );
                }
            }

            return null; // Character not found
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any errors that occur during the process
            return null;
        }
    }

    public boolean deleteCharacter(int id) {
        try {
            // Read the existing JSON data from the file
            String jsonData = new String(Files.readAllBytes(Paths.get(filePath)));

            // Parse the JSON data into a JSON array
            JSONArray jsonArray = new JSONObject(jsonData).getJSONArray("sw");

            // Find the character with the specified ID and remove it from the array
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonCharacter = jsonArray.getJSONObject(i);
                if (jsonCharacter.getInt("id") == id) {
                    jsonArray.remove(i);

                    // Update the JSON data with the modified array
                    JSONObject updatedJson = new JSONObject();
                    updatedJson.put("sw", jsonArray);

                    // Write the updated JSON data back to the file
                    FileWriter fileWriter = new FileWriter(filePath);
                    fileWriter.write(updatedJson.toString());
                    fileWriter.close();

                    return true; // Character deleted successfully
                }
            }

            return false; // Character not found
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any errors that occur during the process
            return false;
        }
    }

    public StarWars updateCharacter(int id, StarWarsDto starWarsDto) {
        try {
            // Read the existing JSON data from the file
            String jsonData = new String(Files.readAllBytes(Paths.get(filePath)));

            // Parse the JSON data into a JSON array
            JSONArray jsonArray = new JSONObject(jsonData).getJSONArray("sw");

            // Find the character with the specified ID and update its data
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonCharacter = jsonArray.getJSONObject(i);
                if (jsonCharacter.getInt("id") == id) {
                    // Update the character's data with the values from the DTO
                    jsonCharacter.put("nev", starWarsDto.getNev());
                    jsonCharacter.put("bolygo", starWarsDto.getBolygo());
                    jsonCharacter.put("faj", starWarsDto.getFaj());

                    // Update the JSON data with the modified array
                    JSONObject updatedJson = new JSONObject();
                    updatedJson.put("sw", jsonArray);

                    // Write the updated JSON data back to the file
                    FileWriter fileWriter = new FileWriter(filePath);
                    fileWriter.write(updatedJson.toString());
                    fileWriter.close();

                    // Convert the updated JSON object to a StarWars object
                    return convertJsonToStarWars(jsonCharacter);
                }
            }

            return null; // Character not found
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any errors that occur during the process
            return null;
        }
    }

    private StarWars convertJsonToStarWars(JSONObject jsonCharacter) {
        int id = jsonCharacter.getInt("id");
        String nev = jsonCharacter.getString("nev");
        String bolygo = jsonCharacter.getString("bolygo");
        String faj = jsonCharacter.getString("faj");
        int likes = jsonCharacter.getInt("likes");

        return new StarWars(id, nev, bolygo, faj, likes);
    }
}
