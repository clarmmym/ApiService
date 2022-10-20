import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.List;


public class Main {
    public static void main(String[] args) throws RuntimeException {
        User user = new User("Tenali Ramakrishna", "tenalil.ramakrishna@15ce.com", "male", "active");
        System.out.println(user);

        TheHttpClient httpClient = new TheHttpClient("https://gorest.co.in/public/v2/users");
        HttpResponse<String> response;

        // Create record on the remote API with 'Post' uses object mapper
        System.out.println("Try to create record on the remote API with 'Post' uses object mapper");
        response = httpClient.post(Utils.userToJsonUsesObjectMapper(user));

        int statusCode1 = response.statusCode();
        System.out.println("Request was successful. Status code: " + statusCode1);


        // Delete previous record to run the program many times, there was no error about existing record
        System.out.println("Deleting previous record\n");
        httpClient.delete(
                (Utils.jsonToUserUsesObjectMapper("[" + response.body() + "]"))
                        .get(0).getId());



        // Change user data
        user.setName("Tenali");
        user.setEmail("tenaraki.ramakrishna@15ce.com");

        // Create record on the remote API with 'Post' uses json object
        System.out.println("Try to create record on the remote API with 'Post' uses json object");
        response = httpClient.post(Utils.userToJsonUsesJsonObject(user));

        int statusCode = response.statusCode();

        System.out.println("Request was successful. Status code: " + statusCode);


        // Delete previous record to run the program many times, there was no error about existing record
        System.out.println("Deleting previous record\n");
        httpClient.delete(
                (Utils.jsonToUserUsesObjectMapper("[" + response.body() + "]"))
                        .get(0).getId());


        // Getting all users
        System.out.println("Getting all users");
        response = httpClient.get();
        System.out.println("Request was successful. Status code: " + response.statusCode() + "\n");


        List<User> usersObjMap = Utils.jsonToUserUsesObjectMapper(response.body());
        List<User> usersJsnObj = Utils.jsonToUserUsesJsonObject(response.body());

        System.out.println("Parsing users using object mapper: " + usersObjMap + "\n");
        System.out.println("Parsing users json object: " + usersJsnObj + "\n");
        System.out.println("Are they equals? " + (usersObjMap.size() == usersJsnObj.size() && new HashSet<>(usersObjMap).containsAll(usersJsnObj)));
    }
}
