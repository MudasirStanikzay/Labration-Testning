package org.example;

public class Main {
    public static void main(String[] args) {
        // Skapa ett BookingRepository
        BookingRepository bookingRepository = new BookingRepository();

        // Skapa BookingSystem och injicera bookingRepository
        BookingSystem bookingSystem = new BookingSystem(bookingRepository);

        // Testa några bokningar
        System.out.println("Försöker boka John Doe den 5 april 2025-04-05");
        boolean booking1 = bookingSystem.book("John Doe", "2025-04-05");
        System.out.println("Bokning lyckades: " + booking1);

        System.out.println("Försöker boka Jane Doe den 5 april 2025-04-05");
        boolean booking2 = bookingSystem.book("Jane Doe", "2025-04-05");
        System.out.println("Bokning lyckades: " + booking2);

        System.out.println("Försöker boka med tomt namn");
        boolean booking3 = bookingSystem.book("", "2025-04-06");
        System.out.println("Bokning lyckades: " + booking3);
    }
}
