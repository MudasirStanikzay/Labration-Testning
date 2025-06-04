package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.*;

public class ShoppingCartTest {

    @Test
    void should_create_empty_cart_initially() {
        ShoppingCart cart = new ShoppingCart();
        assertThat(cart.getItems()).isEmpty();
    }

    @Test
    void should_add_item_to_cart() {
        ShoppingCart cart = new ShoppingCart();

        cart.addItem("Banana", 5.0, 2);

        assertThat(cart.getItems()).hasSize(1);
        Item item = cart.getItems().get(0);
        assertThat(item.getName()).isEqualTo("Banana");
        assertThat(item.getPrice()).isEqualTo(5.0);
        assertThat(item.getQuantity()).isEqualTo(2);
    }

    @Test
    void should_increase_quantity_when_adding_same_item_again() {
        ShoppingCart cart = new ShoppingCart();

        cart.addItem("Banana", 5.0, 2);
        cart.addItem("Banana", 5.0, 3);

        assertThat(cart.getItems()).hasSize(1);
        Item item = cart.getItems().get(0);
        assertThat(item.getName()).isEqualTo("Banana");
        assertThat(item.getQuantity()).isEqualTo(5);
    }

    @Test
    void should_remove_item_from_cart_by_name() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Apple", 10.0, 1);
        cart.addItem("Banana", 5.0, 2);

        cart.removeItem("Apple");

        assertThat(cart.getItems()).hasSize(1);
        assertThat(cart.getItems().get(0).getName()).isEqualTo("Banana");
    }

    @Test
    void should_calculate_total_price_of_items_in_cart() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Apple", 10.0, 2);   // 20.0
        cart.addItem("Banana", 5.0, 3);   // 15.0

        double total = cart.calculateTotal();

        assertThat(total).isEqualTo(35.0);
    }

    @Test
    void should_apply_discount_to_total_price() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Apple", 10.0, 2);   // 20.0
        cart.addItem("Banana", 5.0, 2);   // 10.0

        double discountedTotal = cart.applyDiscount(25); // 25% rabatt

        assertThat(discountedTotal).isEqualTo(22.5); // (20+10)*0.75 = 22.5
    }

    @Test
    void should_return_zero_when_cart_is_empty() {
        ShoppingCart cart = new ShoppingCart();

        double total = cart.calculateTotal();

        assertThat(total).isEqualTo(0.0);
    }

    @Test
    void should_not_add_item_with_zero_quantity() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Apple", 10.0, 0);

        assertThat(cart.getItems()).isEmpty();
    }

    @Test
    void should_not_add_item_with_negative_price() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Apple", -5.0, 2); // Negativt pris

        assertThat(cart.getItems()).isEmpty(); // Förväntar oss att inget läggs till
    }
}
