package busroutelab2;
import java.util.Scanner;
public class BusRouteLab2 {
    
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of stops on the route: ");
        int numStops = scanner.nextInt();

        System.out.print("Enter the bus's seating capacity: ");
        int seatingCapacity = scanner.nextInt();
        scanner.nextLine();

        String[] stopNames = new String[numStops];
        int[] boardingPassengers = new int[numStops];
        int[] alightingPassengers = new int[numStops];
        int[] occupancyAfterStop = new int[numStops];

        int currentPassengers = 0;
        int overCapacityCount = 0;

        System.out.println("***** Enter Stop Data & Route Progress *****");

        for (int i = 0; i < numStops; i++) {
            System.out.println("Stop #" + (i + 1) + ":");
            System.out.print("Stop name: ");
            stopNames[i] = scanner.nextLine();

            System.out.print("Passengers boarding: ");
            boardingPassengers[i] = scanner.nextInt();

            if (boardingPassengers[i] > seatingCapacity) {
                System.out.printf("  [Warning] Boarding passengers (%d) exceeds the bus capacity (%d)!%n", 
                        boardingPassengers[i], seatingCapacity);
            }
            
            int availablePassengers = currentPassengers + boardingPassengers[i];

            while (true) {
                System.out.print("Passengers alighting: ");
                int alighting = scanner.nextInt();

                if (alighting < 0) {
                    System.out.println("[Error] Alighting passengers cannot be negative. Please enter again.");
                } else if (alighting > availablePassengers) {
                    System.out.printf("  [Error] Cannot alight %d passengers! There are only %d passengers on the bus. Please enter again.%n", 
                            alighting, availablePassengers);
                } else {
                    alightingPassengers[i] = alighting;
                    break;
                }
            }
            scanner.nextLine();

            currentPassengers = availablePassengers - alightingPassengers[i];
            occupancyAfterStop[i] = currentPassengers;

            System.out.printf("  -> Current passengers on the bus: %d%n", currentPassengers);

            if (currentPassengers > seatingCapacity) {
                System.out.printf("  [Warning] Bus is over capacity after %s! (%d/%d)%n", 
                        stopNames[i], currentPassengers, seatingCapacity);
                overCapacityCount++;
            }
        }

        System.out.println("***** All Stops Summary *****");
        System.out.printf("%-16s | %-10s | %-10s | %-15s%n", "Stop Name", "Boarding", "Alighting", "Current Occupancy");
        System.out.println("-----------------------------------------------------");
        for (int i = 0; i < numStops; i++) {
            System.out.printf("%-16s | %-10d | %-10d | %-15d%n",
                    stopNames[i],
                    boardingPassengers[i],
                    alightingPassengers[i],
                    occupancyAfterStop[i]);
        }
        System.out.println("-----------------------------------------------------");

        System.out.println("***** Route Statistics *****");

        int maxBoarding = -1;
        String busiestStop = "";
        for (int i = 0; i < numStops; i++) {
            if (boardingPassengers[i] > maxBoarding) {
                maxBoarding = boardingPassengers[i];
                busiestStop = stopNames[i];
            }
        }
        System.out.printf("Busiest stop (highest boarding): %s (%d passengers)%n", busiestStop, maxBoarding);

        int totalOccupancy = 0;
        for (int occ : occupancyAfterStop) {
            totalOccupancy += occ;
        }
        double averageOccupancy = (double) totalOccupancy / numStops;
        System.out.printf("Average bus occupancy across all stops: %.2f%n", averageOccupancy);

        System.out.println("Number of stops exceeding bus capacity: " + overCapacityCount);

        int finalOccupancy = occupancyAfterStop[numStops - 1];
        if (finalOccupancy != 0) {
            System.out.printf("Warning: %d passengers still on the bus after the final stop! please check your data.%n",
                    finalOccupancy);
        } else {
            System.out.println("Route completed: All passengers have exited the bus successfully.");
        }
    }
}