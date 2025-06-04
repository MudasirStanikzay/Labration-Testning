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

}

