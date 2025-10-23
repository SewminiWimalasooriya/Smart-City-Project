import java.util.*;

public class LocationManager {
    private Graph cityGraph;

    public LocationManager(Graph graph) {
        this.cityGraph = graph;
    }

    // Adds a location; returns false if name empty or already exists
    public boolean createLocation(String name) {
        if (name == null || name.trim().isEmpty())
            return false;
        if (cityGraph.hasLocation(name))
            return false;
        cityGraph.addLocation(name);
        return true;
    }

    // Removes a location; returns true if removed
    public boolean deleteLocation(String name) {
        if (!cityGraph.hasLocation(name))
            return false;
        cityGraph.removeLocation(name);
        return true;
    }

    // Adds an undirected road between two existing locations
    public boolean connectLocations(String a, String b) {
        return cityGraph.addRoad(a, b);
    }

    // Removes road between two locations
    public boolean disconnectLocations(String a, String b) {
        return cityGraph.removeRoad(a, b);
    }

    public Set<String> listLocations() {
        return cityGraph.getAllLocations();
    }

    public List<String> listConnections(String location) {
        return cityGraph.getConnections(location);
    }

}
