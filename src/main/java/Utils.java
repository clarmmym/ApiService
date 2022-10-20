import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static String userToJsonUsesObjectMapper(User user) {
        String jsonObj;

        try {
            jsonObj = new ObjectMapper().writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return jsonObj;
    }

    public static String userToJsonUsesJsonObject(User user) {
        return new JSONObject(user).toString();
    }

    public static List<User> jsonToUserUsesObjectMapper(String json) {
        try {
            return new ObjectMapper().readValue(json, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<User> jsonToUserUsesJsonObject(String js) {
        JSONArray jsnArray = new JSONArray(js);
        List<User> userList = new ArrayList<>();

        for (var jsObj : jsnArray) {
            JSONObject jsonObject = (JSONObject) jsObj;
            User user = new User();
            user.setId(jsonObject.getInt("id"));
            user.setName(jsonObject.getString("name"));
            user.setEmail(jsonObject.getString("email"));
            user.setGender(jsonObject.getString("gender"));
            user.setStatus(jsonObject.getString("status"));
            userList.add(user);
        }

        return userList;
    }
}
