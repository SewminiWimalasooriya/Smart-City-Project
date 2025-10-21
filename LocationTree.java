import java.util.*;

public class LocationTree {
    private TreeNode root;

    private static class TreeNode {
        String location;
        TreeNode left, right;

        TreeNode(String location) {
            this.location = location;
        }
    }

    // Public API: add and remove
    public void addLocation(String location) {
        root = insert(root, location);
    }

    private TreeNode insert(TreeNode node, String location) {
        if (location == null) return node;
        if (node == null) return new TreeNode(location);
        if (location.compareTo(node.location) < 0)
            node.left = insert(node.left, location);
        else if (location.compareTo(node.location) > 0)
            node.right = insert(node.right, location);
        return node;
    }

    public void removeLocation(String location) {
        root = delete(root, location);
    }

    private TreeNode delete(TreeNode node, String location) {
        if (node == null) return null;
        if (location.compareTo(node.location) < 0)
            node.left = delete(node.left, location);
        else if (location.compareTo(node.location) > 0)
            node.right = delete(node.right, location);
        else {
            // node to delete found
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            node.location = minValue(node.right);
            node.right = delete(node.right, node.location);
        }
        return node;
    }

    private String minValue(TreeNode node) {
        while (node.left != null) node = node.left;
        return node.location;
    }

    // Return sorted list of locations (in-order traversal)
    public List<String> getInOrderList() {
        List<String> list = new ArrayList<>();
        inOrderTraversal(root, list);
        return list;
    }

    private void inOrderTraversal(TreeNode node, List<String> list) {
        if (node == null) return;
        inOrderTraversal(node.left, list);
        list.add(node.location);
        inOrderTraversal(node.right, list);
    }
}
