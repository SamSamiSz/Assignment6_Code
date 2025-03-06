package org.example.Amazon;

import org.example.Amazon.Cost.PriceRule;
import org.example.Amazon.Cost.ItemType;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class AmazonIntegrationTest {

    private static Database db;
    private static ShoppingCartAdaptor cartAdaptor;
    private static Amazon amazon;

    @BeforeAll
    public static void setupClass() {
        // Initialize the database and shopping cart adaptor
        db = new Database();
        cartAdaptor = new ShoppingCartAdaptor(db);
        // Create a dummy price rule that calculates total price as (quantity * pricePerUnit)
        PriceRule dummyRule = items -> items.stream()
                .mapToDouble(item -> item.getQuantity() * item.getPricePerUnit())
                .sum();
        amazon = new Amazon(cartAdaptor, List.of(dummyRule));
    }

    @BeforeEach
    public void setup() {
        // Reset the database so tests don't interfere with each other
        db.resetDatabase();
    }

    @AfterAll
    public static void tearDown() {
        // Clean up the database connection
        db.close();
    }

    @Test
    @DisplayName("Integration Test: Add item and calculate price")
    public void testAddItemAndCalculate() {
        // Use ItemType.OTHER to represent a book
        Item item = new Item(ItemType.OTHER, "Test Book", 2, 25.0);
        amazon.addToCart(item);

        List<Item> items = cartAdaptor.getItems();
        assertFalse(items.isEmpty(), "Shopping cart should not be empty after adding an item.");

        // Expected price: 2 * 25.0 = 50.0
        double expectedPrice = 50.0;
        double actualPrice = amazon.calculate();
        assertEquals(expectedPrice, actualPrice, "Calculated price should match the expected total.");
    }
}
