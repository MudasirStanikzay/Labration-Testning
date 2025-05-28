import org.example.BookingRepository;
import org.example.BookingSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookingSystemTest {

    private BookingRepository bookingRepository;
    private BookingSystem bookingSystem;

    @BeforeEach
    void setUp() {
        bookingRepository = mock(BookingRepository.class);
        bookingSystem = new BookingSystem(bookingRepository);
    }

    @Test
    void should_store_booking_when_date_is_available() {
        // Arrange
        when(bookingRepository.existsBooking("2025-05-20")).thenReturn(false);
        when(bookingRepository.saveBooking("Kalle", "2025-05-20")).thenReturn(true);

        // Act
        boolean result = bookingSystem.book("Kalle", "2025-05-20");

        // Assert
        assertThat(result).isTrue();
        verify(bookingRepository).saveBooking("Kalle", "2025-05-20");
    }

    @Test
    void should_reject_booking_when_date_is_already_booked() {
        // Arrange
        when(bookingRepository.existsBooking("2025-05-20")).thenReturn(true);

        // Act
        boolean result = bookingSystem.book("Kalle", "2025-05-20");

        // Assert
        assertThat(result).isFalse();
        verify(bookingRepository, never()).saveBooking(anyString(), anyString());
    }

    @Test
    void should_skip_saving_when_date_is_occupied () {
        when(bookingRepository.existsBooking("2025-05-21")).thenReturn(true);
        bookingSystem.book("Anna", "2025-05-21");

        verify(bookingRepository, never()).saveBooking(anyString(), anyString());
    }

    @Test
    void should_invoke_saveBooking_when_date_is_available () {
        when(bookingRepository.existsBooking("2025-05-22")).thenReturn(false);
        bookingSystem.book("Erik", "2025-05-22");

        verify(bookingRepository).saveBooking("Erik", "2025-05-22");
    }

    @Test
    void should_return_success_when_booking_is_saved() {
        when(bookingRepository.existsBooking("2025-05-23")).thenReturn(false);
        when(bookingRepository.saveBooking("Lina", "2025-05-23")).thenReturn(true);

        boolean result = bookingSystem.book("Lina", "2025-05-23");

        assertThat(result).isTrue();
    }

    @Test
    void should_return_failure_when_booking_cannot_be_saved () {
        when(bookingRepository.existsBooking("2025-05-24")).thenReturn(false);
        when(bookingRepository.saveBooking("Olle", "2025-05-24")).thenReturn(false);

        boolean result = bookingSystem.book("Olle", "2025-05-24");

        assertThat(result).isFalse();
    }

    @Test
    void should_accept_null_customer_name_for_booking () {
        when(bookingRepository.existsBooking("2025-05-25")).thenReturn(false);
        when(bookingRepository.saveBooking(null, "2025-05-25")).thenReturn(true);

        boolean result = bookingSystem.book(null, "2025-05-25");

        verify(bookingRepository).saveBooking(null, "2025-05-25");
        assertThat(result).isTrue();
    }

    @Test
    void should_allow_empty_customer_name_for_booking () {
        when(bookingRepository.existsBooking("2025-05-26")).thenReturn(false);
        when(bookingRepository.saveBooking("", "2025-05-26")).thenReturn(true);

        boolean result = bookingSystem.book("", "2025-05-26");

        verify(bookingRepository).saveBooking("", "2025-05-26");
        assertThat(result).isTrue();
    }

    @Test
    void should_reject_booking_when_date_is_null () {
        boolean result = bookingSystem.book("Nina", null);

        verifyNoInteractions(bookingRepository);
        assertThat(result).isFalse();
    }

    // Nytt test: Hantera felaktigt undantag vid bokning
    @Test
    void should_return_failure_when_cancel_booking_fails_in_repository () {
        when(bookingRepository.existsBooking("2025-05-28")).thenReturn(true);
        when(bookingRepository.removeBooking("2025-05-28")).thenReturn(false);

        boolean result = bookingSystem.cancelBooking("2025-05-28");

        assertThat(result).isFalse();
    }

    // Nytt test: Hantera bokning som inte finns
    @Test
    void should_not_cancel_booking_if_it_does_not_exist () {
        // Arrange
        when(bookingRepository.existsBooking("2025-05-29")).thenReturn(false);

        // Act
        boolean result = bookingSystem.cancelBooking("2025-05-29");

        // Assert
        assertThat(result).isFalse();
        verify(bookingRepository, never()).removeBooking("2025-05-29");
    }
}
