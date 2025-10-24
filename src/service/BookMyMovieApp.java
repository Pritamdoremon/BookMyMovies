package service;

import java.util.Scanner;

public class BookMyMovieApp {

    public static void main(String[] args) {
        BookMyMovieSys sys = new BookMyMovieSys();
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to BookMyMovies 🎬");

        while(true) {
            int userId = -1;

            // Login/Register loop
            while(userId == -1) {
                System.out.println("\n1. Register\n2. Login\n3. Exit");
                System.out.print("Choose: ");
                int choice = sc.nextInt();
                sc.nextLine(); // consume newline

                if(choice == 1) {
                    userId = sys.registerUser();
                } else if(choice == 2) {
                    userId = sys.loginUser();
                } else if(choice == 3) {
                    System.out.println("✅ Exiting BookMyMovies. Bye!");
                    return;
                } else {
                    System.out.println("❌ Invalid choice");
                }
            }

            boolean logout = false;
            while(!logout) {
                sys.displayCities();
                System.out.print("Enter City (1-3): ");
                int cityChoice = sc.nextInt();
                sc.nextLine();
                String city = switch(cityChoice) {
                    case 1 -> "Kolkata";
                    case 2 -> "Ranchi";
                    case 3 -> "Delhi";
                    default -> { System.out.println("❌ Invalid choice"); yield ""; }
                };
                if(city.isEmpty()) break;

                sys.displayMovies(city);

                System.out.print("Enter Movie ID to Book: ");
                int movieId = sc.nextInt();
                sc.nextLine();

                System.out.print("Enter Seats (comma separated, e.g., A1,A2): ");
                String seats = sc.nextLine();

                sys.bookSeats(userId, movieId, seats);

                System.out.println("\n1. Logout\n2. Book another movie");
                System.out.print("Choose: ");
                int nextChoice = sc.nextInt();
                sc.nextLine();
                if(nextChoice == 1) {
                    logout = true;
                    System.out.println("✅ Logged out successfully!");
                }
            }
        }
    }
}
