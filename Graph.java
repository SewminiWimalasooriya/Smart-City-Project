import java.util.*;

public class Graph {
    private Map<String, List<String>> adjList;

    public Graph() {
        adjList = new HashMap<>();
    }

    public void addLocation(String location) {
        adjList.putIfAbsent(location, new ArrayList<>());
    }

    public void removeLocation(String location) {
        adjList.remove(location);
        for (List<String> connections : adjList.values()) {
            connections.remove(location);
        }
    }

    public boolean hasLocation(String location) {
        return adjList.containsKey(location);
    }

    public boolean addRoad(String from, String to) {
        if (!hasLocation(from) || !hasLocation(to) || from.equals(to))
            return false;
        if (!adjList.get(from).contains(to))
            adjList.get(from).add(to);
        if (!adjList.get(to).contains(from))
            adjList.get(to).add(from);
        return true;
    }

    public boolean removeRoad(String from, String to) {
        boolean removed = false;
        if (adjList.containsKey(from))
            removed |= adjList.get(from).remove(to);
        if (adjList.containsKey(to))
            removed |= adjList.get(to).remove(from);
        return removed;
    }

    public List<String> getConnections(String location) {
        return new ArrayList<>(adjList.getOrDefault(location, Collections.emptyList()));
    }

    public Set<String> getAllLocations() {
        return new TreeSet<>(adjList.keySet()); // sorted set for predictable order
    }

    // BFS traversal print (keeps output responsibility here for debug/help)
    public void displayConnectionsBFS() {
        if (adjList.isEmpty()) {
            System.out.println("No locations in graph");
            return;
        }

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        for (String location : adjList.keySet()) {
            if (!visited.contains(location)) {
                queue.add(location);
                visited.add(location);

                while (!queue.isEmpty()) {
                    String current = queue.poll();
                    System.out.println("\nLocation: " + current);
                    System.out.print("Connected to: ");

                    List<String> connections = adjList.get(current);
                    if (connections.isEmpty()) {
                        System.out.println("None");
                    } else {
                        for (String neighbor : connections) {
                            System.out.print(neighbor + " ");
                            if (!visited.contains(neighbor)) {
                                queue.add(neighbor);
                                visited.add(neighbor);
                            }
                        }
                        System.out.println();
                    }
                }
            }
        }
    }

    public void displayConnectionsDFS() {
        if (adjList.isEmpty()) {
            System.out.println("No locations in graph");
            return;
        }

        Stack<String> stack = new Stack<>();
        Set<String> visited = new HashSet<>();

        for (String location : adjList.keySet()) {
            if (!visited.contains(location)) {
                stack.push(location);

                while (!stack.isEmpty()) {
                    String current = stack.pop();

                    if (!visited.contains(current)) {
                        visited.add(current);
                        System.out.println("\nLocation: " + current);
                        System.out.print("Connected to: ");

                        List<String> connections = adjList.get(current);
                        if (connections.isEmpty()) {
                            System.out.println("None");
                        } else {
                            for (String neighbor : connections) {
                                System.out.print(neighbor + " ");
                                if (!visited.contains(neighbor)) {
                                    stack.push(neighbor);
                                }
                            }
                            System.out.println();
                        }
                    }
                }
            }
        }
    }
}
