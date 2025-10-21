import java.util.*;

public class SmartCityApp {
    private Graph cityGraph = new Graph();
    private LocationManager locationManager = new LocationManager(cityGraph);
    private LocationTree locationTree = new LocationTree();
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        SmartCityApp app = new SmartCityApp();
        app.initializeSystem();
        app.showMainMenu();
    }

    private void initializeSystem() {
        // sample data
        String[] sampleLocations = { "Central Station", "City Mall", "Tech Park", "Green Park" };
        for (String loc : sampleLocations) {
            cityGraph.addLocation(loc);
            locationTree.addLocation(loc);
        }
        cityGraph.addRoad("Central Station", "City Mall");
        cityGraph.addRoad("City Mall", "Tech Park");
        cityGraph.addRoad("Tech Park", "Green Park");
        cityGraph.addRoad("Green Park", "Central Station");
        System.out.println("Sample data loaded successfully!\n");
    }

    private void showMainMenu() {
        while (true) {
            System.out.println("=== SMART CITY ROUTE PLANNER ===");
            System.out.println("1. Add New Location");
            System.out.println("2. Remove Location");
            System.out.println("3. Add Road Between Locations");
            System.out.println("4. Remove Road");
            System.out.println("5. Display All Connections (BFS/DFS)");
            System.out.println("6. Display All Locations (Tree View)");
            System.out.println("7. Display Location Connections");
            System.out.println("8. Exit");
            System.out.print("Choose an option (1-8): ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    handleAddLocation();
                    break;
                case "2":
                    handleRemoveLocation();
                    break;
                case "3":
                    handleAddRoad();
                    break;
                case "4":
                    handleRemoveRoad();
                    break;
                case "5":
                    handleDisplayConnections();
                    break;
                case "6":
                    handleDisplayTree();
                    break;
                case "7":
                    handleDisplayLocationConnections();
                    break;
                case "8":
                    System.out.println("Thank you for using Smart City Route Planner!");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }

            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }

    private void handleAddLocation() {
        System.out.print("Enter new location name: ");
        String location = scanner.nextLine().trim();
        if (location.isEmpty()) {
            System.out.println("Location name cannot be empty!");
            return;
        }
        if (locationManager.createLocation(location)) {
            locationTree.addLocation(location);
            System.out.println("Location '" + location + "' added to system!");
        } else {
            System.out.println("Could not add location (it may already exist).");
        }
    }

    private void handleRemoveLocation() {
        System.out.print("Enter location name to remove: ");
        String location = scanner.nextLine().trim();
        if (locationManager.deleteLocation(location)) {
            locationTree.removeLocation(location);
            System.out.println("Location '" + location + "' removed from system!");
        } else {
            System.out.println("Location not found.");
        }
    }

    private void handleAddRoad() {
        printAllLocations();
        System.out.print("Enter first location: ");
        String a = scanner.nextLine().trim();
        System.out.print("Enter second location: ");
        String b = scanner.nextLine().trim();
        if (!cityGraph.hasLocation(a) || !cityGraph.hasLocation(b)) {
            System.out.println("One or both locations not found!");
            return;
        }
        if (a.equals(b)) {
            System.out.println("Cannot connect a location to itself!");
            return;
        }
        boolean ok = locationManager.connectLocations(a, b);
        System.out.println(ok ? "Road added." : "Failed to add road (maybe already exists).");
    }

    private void handleRemoveRoad() {
        System.out.print("Enter first location: ");
        String a = scanner.nextLine().trim();
        System.out.print("Enter second location: ");
        String b = scanner.nextLine().trim();
        boolean ok = locationManager.disconnectLocations(a, b);
        System.out.println(ok ? "Road removed." : "No such road existed.");
    }

    private void handleDisplayConnections() {
        System.out.println("\n=== DISPLAY CONNECTIONS ===");
        System.out.println("1. Breadth-First Search (BFS)");
        System.out.println("2. Depth-First Search (DFS)");
        System.out.print("Choose traversal method (1-2): ");
        String choice = scanner.nextLine().trim();
        if (choice.equals("1"))
            cityGraph.displayConnectionsBFS();
        else if (choice.equals("2"))
            cityGraph.displayConnectionsDFS();
        else
            System.out.println("Invalid choice!");
    }

    private void handleDisplayTree() {
        List<String> list = locationTree.getInOrderList();
        if (list.isEmpty())
            System.out.println("No locations in tree");
        else {
            System.out.println("\n=== LOCATIONS (SORTED) ===");
            for (String s : list)
                System.out.println("- " + s);
        }
    }

    private void handleDisplayLocationConnections() {
        System.out.print("Enter location name: ");
        String location = scanner.nextLine().trim();
        if (!cityGraph.hasLocation(location)) {
            System.out.println("Location not found!");
            return;
        }
        List<String> connections = locationManager.listConnections(location);
        System.out.println("\nConnections from " + location + ":");
        if (connections.isEmpty())
            System.out.println("No connections");
        else
            for (String conn : connections)
                System.out.println("- " + conn);
    }

    private void printAllLocations() {
        Set<String> locations = locationManager.listLocations();
        if (locations.isEmpty())
            System.out.println("No locations available.");
        else {
            System.out.println("\n=== ALL LOCATIONS ===");
            for (String location : locations)
                System.out.println("- " + location);
        }
    }
}
