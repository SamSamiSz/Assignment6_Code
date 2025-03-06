package org.example.Amazon;

import org.example.Amazon.Cost.PriceRule;
import org.example.Amazon.Cost.ItemType;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class AmazonUnitTest {

    @Test
    @DisplayName("Unit Test: calculate() returns correct aggregated price")
    public void testCalculatePrice() {
        // Create a dummy ShoppingCart that returns a fixed list of items
        ShoppingCart dummyCart = new ShoppingCart() {
            @Override
            public void add(Item item) {
                // No operation needed for this test
            }
            @Override
            public List<Item> getItems() {
                // Return a fixed list; using OTHER to represent a book
                return List.of(new Item(ItemType.OTHER, "Dummy Book", 3, 10.0));
            }
            @Override
            public int numberOfItems() {
                return 1;
            }
        };

        // Create a dummy PriceRule that sums up (quantity * pricePerUnit)
        PriceRule dummyRule = items -> items.stream()
                .mapToDouble(item -> item.getQuantity() * item.getPricePerUnit())
                .sum();

        // Instantiate Amazon with the dummy implementations
        Amazon amazon = new Amazon(dummyCart, List.of(dummyRule));

        // Expected total: 3 * 10.0 = 30.0
        double expectedPrice = 30.0;
        double actualPrice = amazon.calculate();
        assertEquals(expectedPrice, actualPrice, "Calculated price should be the sum of (quantity * pricePerUnit).");
    }
}
