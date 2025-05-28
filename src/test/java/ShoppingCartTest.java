package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.*;

public class ShoppingCartTest {

    @Test
    void should_increase_cart_size_when_item_is_added () {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Apple", 10.0, 1);

        assertThat(cart.getItems()).hasSize(1);
        assertThat(cart.getItems().get(0).getName()).isEqualTo("Apple");
        assertThat(cart.getItems().get(0).getPrice()).isEqualTo(10.0);
        assertThat(cart.getItems().get(0).getQuantity()).isEqualTo(1);
    }

    @Test
    void should_decrease_cart_size_when_item_is_removed () {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Apple", 10.0, 1);
        cart.addItem("Banana", 5.0, 2);

        assertThat(cart.getItems()).hasSize(2);

        cart.removeItem("Apple");

        assertThat(cart.getItems()).hasSize(1);
        assertThat(cart.getItems().get(0).getName()).isEqualTo("Banana");
    }

    @Test
    void  should_calculate_total_price_correctly () {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Apple", 10.0, 1);
        cart.addItem("Banana", 5.0, 2);

        double total = cart.calculateTotal();

        assertThat(total).isEqualTo(20.0);  // (1 * 10) + (2 * 5)
    }

    @Test
    void should_update_quantity_if_item_already_exists () {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Apple", 10.0, 1);
        cart.addItem("Apple", 10.0, 2);

        assertThat(cart.getItems()).hasSize(1);
        assertThat(cart.getItems().get(0).getQuantity()).isEqualTo(3);
    }

    // Nytt test: Applicera rabatt (parametriserat)
    @ParameterizedTest
    @CsvSource({
            "10, 0.9",   // 10% rabatt, förväntad total = 90% av original
            "0, 1.0",    // 0% rabatt, förväntad total = original
            "100, 0.0"   // 100% rabatt, förväntad total = 0
    })
    void should_apply_discount_correctly (double discountPercentage, double expectedMultiplier) {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Apple", 10.0, 1);
        cart.addItem("Banana", 5.0, 2);

        double total = cart.calculateTotal();
        double discountedTotal = cart.applyDiscount(discountPercentage); // Applicera rabatt

        assertThat(discountedTotal).isEqualTo(total * expectedMultiplier); // Kontrollera om rabatten appliceras korrekt
    }

    // Test för att lägga till varor i varukorgen (parametriserat)
    @ParameterizedTest
    @CsvSource({
            "Apple, 10.0, 1",
            "Banana, 5.0, 2",
            "Orange, 7.5, 3"
    })
    void testAddItemToCart(String name, double price, int quantity) {
        ShoppingCart cart = new ShoppingCart();

        cart.addItem(name, price, quantity);

        assertThat(cart.getItems()).hasSize(1);
        assertThat(cart.getItems().get(0).getName()).isEqualTo(name);
        assertThat(cart.getItems().get(0).getPrice()).isEqualTo(price);
        assertThat(cart.getItems().get(0).getQuantity()).isEqualTo(quantity);
    }
}
