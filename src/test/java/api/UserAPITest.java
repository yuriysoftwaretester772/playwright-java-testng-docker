package api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Example User API Test class demonstrating how to use Playwright for API testing
 * with the DummyJSON API (https://dummyjson.com/)
 */
public class UserAPITest {

    private Playwright playwright;
    private APIRequestContext apiContext;
    private final String BASE_URL = "https://dummyjson.com";
    private final Gson gson = new Gson();

    @BeforeClass(alwaysRun = true)
    public void setup() {
        playwright = Playwright.create();

        // Set up headers and other options for API calls
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");

        // Create the API context
        apiContext = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(BASE_URL)
                .setExtraHTTPHeaders(headers));
    }

    @AfterClass(alwaysRun = true)
    public void teardown() {
        if (apiContext != null) {
            apiContext.dispose();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    @Parameters({"baseApiUrl"})
    @BeforeMethod
    public void setupMethod(@Optional("https://dummyjson.com") String baseApiUrl) {
        // You can use the parameter from testng-smoke.xml or default value
        System.out.println("Using API URL: " + baseApiUrl);
    }

    @Test(groups = "UserAPI")
    public void testGetAllUsers() {
        // Make a GET request to fetch all users
        APIResponse response = apiContext.get("/users");

        // Verify status code
        Assert.assertEquals(response.status(), 200, "Expected 200 OK status");

        // Parse response body
        JsonObject jsonResponse = gson.fromJson(response.text(), JsonObject.class);

        // Verify response structure
        Assert.assertTrue(jsonResponse.has("users"), "Response should contain 'users' array");
        Assert.assertTrue(jsonResponse.has("total"), "Response should contain 'total' field");
        Assert.assertTrue(jsonResponse.has("limit"), "Response should contain 'limit' field");

        // Verify there are users returned
        Assert.assertTrue(jsonResponse.getAsJsonArray("users").size() > 0,
                "Users array should not be empty");
    }

    @Test(groups = "UserAPI")
    public void testGetSingleUser() {
        // Make a GET request to fetch a specific user (ID 1)
        APIResponse response = apiContext.get("/users/1");

        // Verify status code
        Assert.assertEquals(response.status(), 200, "Expected 200 OK status");

        // Parse response body
        JsonObject user = gson.fromJson(response.text(), JsonObject.class);

        // Verify user details
        Assert.assertEquals(user.get("id").getAsInt(), 1, "User ID should be 1");
        Assert.assertTrue(user.has("firstName"), "User should have a firstName");
        Assert.assertTrue(user.has("lastName"), "User should have a lastName");
        Assert.assertTrue(user.has("email"), "User should have an email");
    }

    @Test(groups = "UserAPI")
    public void testSearchUsers() {
        // Make a GET request to search for users
        APIResponse response = apiContext.get("/users/search?q=John");

        // Verify status code
        Assert.assertEquals(response.status(), 200, "Expected 200 OK status");

        // Parse response body
        JsonObject jsonResponse = gson.fromJson(response.text(), JsonObject.class);

        // Verify there are search results
        Assert.assertTrue(jsonResponse.has("users"), "Response should contain 'users' array");
    }

    @Test(groups = "UserAPI")
    public void testAddNewUser() {
        // Create user data
        JsonObject newUser = new JsonObject();
        newUser.addProperty("firstName", "John");
        newUser.addProperty("lastName", "Doe");
        newUser.addProperty("email", "john.doe@example.com");
        newUser.addProperty("age", 32);

        // Make a POST request to add a new user
        APIResponse response = apiContext.post("/users/add",
                RequestOptions.create().setData(newUser.toString()));

        // Verify status code
        Assert.assertEquals(response.status(), 201, "Expected 201 Created status");

        // Parse response body
        JsonObject addedUser = gson.fromJson(response.text(), JsonObject.class);

        // Verify the user was added with correct details
        Assert.assertEquals(addedUser.get("firstName").getAsString(), "John");
        Assert.assertEquals(addedUser.get("lastName").getAsString(), "Doe");
        Assert.assertEquals(addedUser.get("email").getAsString(), "john.doe@example.com");
        Assert.assertEquals(addedUser.get("age").getAsInt(), 32);

        // DummyJSON assigns an ID to new users
        Assert.assertTrue(addedUser.has("id"), "Added user should have an ID");
    }

    @Test(groups = "UserAPI")
    public void testUpdateUser() {
        // Create update data
        JsonObject updateData = new JsonObject();
        updateData.addProperty("email", "updated.email@example.com");

        // Make a PUT request to update user with ID 1
        APIResponse response = apiContext.put("/users/1",
                RequestOptions.create().setData(updateData.toString()));

        // Verify status code
        Assert.assertEquals(response.status(), 200, "Expected 200 OK status");

        // Parse response body
        JsonObject updatedUser = gson.fromJson(response.text(), JsonObject.class);

        // Verify the user was updated correctly
        Assert.assertEquals(updatedUser.get("id").getAsInt(), 1, "User ID should be 1");
        Assert.assertEquals(updatedUser.get("email").getAsString(), "updated.email@example.com");
    }

    @Test(groups = "UserAPI")
    public void testDeleteUser() {
        // Make a DELETE request to delete user with ID 1
        APIResponse response = apiContext.delete("/users/1");

        // Verify status code
        Assert.assertEquals(response.status(), 200, "Expected 200 OK status");

        // Parse response body
        JsonObject deletedUser = gson.fromJson(response.text(), JsonObject.class);

        // Verify the response contains the deleted user info
        Assert.assertEquals(deletedUser.get("id").getAsInt(), 1, "Deleted user ID should be 1");
        Assert.assertTrue(deletedUser.has("isDeleted"), "Response should indicate deletion status");
        Assert.assertTrue(deletedUser.get("isDeleted").getAsBoolean(), "isDeleted should be true");
    }
}