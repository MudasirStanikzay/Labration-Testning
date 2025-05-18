package org.example;

public class BookingSystem {
    private final BookingRepository bookingRepository;

    public BookingSystem(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public boolean book(String customerName, String date) {
        if (date == null) {
            return false;
        }

        if (bookingRepository.existsBooking(date)) {
            return false;
        }

        return bookingRepository.saveBooking(customerName, date);
    }

    public boolean cancelBooking(String date) {
        return false;

    }
}
