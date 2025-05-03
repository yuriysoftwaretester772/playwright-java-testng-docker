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
 * Example API Test class demonstrating how to use Playwright for API testing
 * with the DummyJSON API (https://dummyjson.com/)
 */
public class ProductAPITest {

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

    @Test(groups = "ProductAPI")
    public void testGetAllProducts() {
        // Make a GET request to fetch all products
        APIResponse response = apiContext.get("/products");

        // Verify status code
        Assert.assertEquals(response.status(), 200, "Expected 200 OK status");

        // Parse response body
        JsonObject jsonResponse = gson.fromJson(response.text(), JsonObject.class);

        // Verify response structure
        Assert.assertTrue(jsonResponse.has("products"), "Response should contain 'products' array");
        Assert.assertTrue(jsonResponse.has("total"), "Response should contain 'total' field");
        Assert.assertTrue(jsonResponse.has("limit"), "Response should contain 'limit' field");

        // Verify there are products returned
        Assert.assertTrue(jsonResponse.getAsJsonArray("products").size() > 0,
                "Products array should not be empty");
    }

    @Test(groups = "ProductAPI")
    public void testGetSingleProduct() {
        // Make a GET request to fetch a specific product (ID 1)
        APIResponse response = apiContext.get("/products/1");

        // Verify status code
        Assert.assertEquals(response.status(), 200, "Expected 200 OK status");

        // Parse response body
        JsonObject product = gson.fromJson(response.text(), JsonObject.class);

        // Verify product details
        Assert.assertEquals(product.get("id").getAsInt(), 1, "Product ID should be 1");
        Assert.assertTrue(product.has("title"), "Product should have a title");
        Assert.assertTrue(product.has("price"), "Product should have a price");
        Assert.assertTrue(product.has("description"), "Product should have a description");
    }

    @Test(groups = "ProductAPI")
    public void testSearchProducts() {
        // Make a GET request to search for products containing "phone"
        APIResponse response = apiContext.get("/products/search?q=phone");

        // Verify status code
        Assert.assertEquals(response.status(), 200, "Expected 200 OK status");

        // Parse response body
        JsonObject jsonResponse = gson.fromJson(response.text(), JsonObject.class);

        // Verify there are search results
        Assert.assertTrue(jsonResponse.getAsJsonArray("products").size() > 0,
                "Should return products matching search term 'phone'");
    }

    @Test(groups = "ProductAPI")
    public void testAddNewProduct() {
        // Create product JSON data
        JsonObject newProduct = new JsonObject();
        newProduct.addProperty("title", "New Test Product");
        newProduct.addProperty("description", "This is a test product created via API");
        newProduct.addProperty("price", 299.99);
        newProduct.addProperty("brand", "Test Brand");
        newProduct.addProperty("category", "electronics");

        // Make a POST request to add a new product
        APIResponse response = apiContext.post("/products/add",
                RequestOptions.create().setData(newProduct.toString()));

        // Verify status code
        Assert.assertEquals(response.status(), 201, "Expected 201 Created status");

        // Parse response body
        JsonObject addedProduct = gson.fromJson(response.text(), JsonObject.class);

        // Verify the product was added with correct details
        Assert.assertEquals(addedProduct.get("title").getAsString(), "New Test Product");
        Assert.assertEquals(addedProduct.get("description").getAsString(),
                "This is a test product created via API");
        Assert.assertEquals(addedProduct.get("price").getAsDouble(), 299.99);

        // DummyJSON assigns an ID to new products
        Assert.assertTrue(addedProduct.has("id"), "Added product should have an ID");
    }

    @Test(groups = "ProductAPI")
    public void testUpdateProduct() {
        // Create update data
        JsonObject updateData = new JsonObject();
        updateData.addProperty("title", "Updated Product Title");

        // Make a PUT request to update product with ID 1
        APIResponse response = apiContext.put("/products/1",
                RequestOptions.create().setData(updateData.toString()));

        // Verify status code
        Assert.assertEquals(response.status(), 200, "Expected 200 OK status");

        // Parse response body
        JsonObject updatedProduct = gson.fromJson(response.text(), JsonObject.class);

        // Verify the product was updated correctly
        Assert.assertEquals(updatedProduct.get("id").getAsInt(), 1, "Product ID should be 1");
        Assert.assertEquals(updatedProduct.get("title").getAsString(), "Updated Product Title");
    }

    @Test(groups = "ProductAPI")
    public void testDeleteProduct() {
        // Make a DELETE request to delete product with ID 1
        APIResponse response = apiContext.delete("/products/1");

        // Verify status code
        Assert.assertEquals(response.status(), 200, "Expected 200 OK status");

        // Parse response body
        JsonObject deletedProduct = gson.fromJson(response.text(), JsonObject.class);

        // Verify the response contains the deleted product info
        Assert.assertEquals(deletedProduct.get("id").getAsInt(), 1, "Deleted product ID should be 1");
        Assert.assertTrue(deletedProduct.has("isDeleted"), "Response should indicate deletion status");
        Assert.assertTrue(deletedProduct.get("isDeleted").getAsBoolean(), "isDeleted should be true");
    }
}