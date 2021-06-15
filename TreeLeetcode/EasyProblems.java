package TreeLeetcode;

import com.sun.source.tree.Tree;

import java.util.*;

//  Definition for a binary tree node.
class TreeNode {
          int val;
          TreeNode left;
          TreeNode right;
          TreeNode() {}
          TreeNode(int val) { this.val = val; }
          TreeNode(int val, TreeNode left, TreeNode right) {
              this.val = val;
              this.left = left;
              this.right = right;
      }
}

class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
};

public class EasyProblems {
    /************************  938. Range Sum of BST  ************************************/

    // 0ms
    public int rangeSumBST(TreeNode root, int low, int high) {
        if(root == null) return 0;
        if(root.val >= low && root.val <= high)
            return root.val + rangeSumBST(root.left, low, high) + rangeSumBST(root.right, low, high);
        if(root.val < low) return rangeSumBST(root.right, low, high);
        return rangeSumBST(root.left, low, high);
    }

    /************************  617. Merge Two Binary Trees  ************************************/

    // Traditional approach 1ms
    public TreeNode mergeTrees01(TreeNode root1, TreeNode root2) {
        if(root1 == null && root2 == null) return null;
        TreeNode root = new TreeNode();
        if(root1 != null && root2 != null) {
            root.val += (root1.val + root2.val);
            root.left = mergeTrees01(root1.left, root2.left);
            root.right = mergeTrees01(root1.right, root2.right);
        } else if(root1 != null) {
            root.val += root1.val;
            root.left = mergeTrees01(root1.left, null);
            root.right = mergeTrees01(root1.right, null);
        } else {
            root.val += root2.val;
            root.left = mergeTrees01(null, root2.left);
            root.right = mergeTrees01(null, root2.right);
        }
        return root;
    }

    // Better Approach 0ms
    public TreeNode mergeTrees02(TreeNode root1, TreeNode root2) {
        if(root1 == null) return root2;
        if(root2 == null) return root1;
        root1.val+=root2.val;
        root1.left = mergeTrees02(root1.left, root2.left);
        root1.right = mergeTrees02(root1.right, root2.right);
        return root1;
    }

    /************************  897. Increasing Order Search Tree (Important)  ************************************/
//    Tough One
//    897. Increasing Order Search Tree
    public TreeNode increasingBSTHelper(TreeNode head, TreeNode tail) {
        if(head == null) return tail;

//        Get the new head using inorder traversal
        TreeNode newHead = increasingBSTHelper(head.left, head);
//        Set the left side as null
        head.left = null;
        head.right = increasingBSTHelper(head.right, tail);
        return newHead;
    }
    public TreeNode increasingBST(TreeNode root) {
        return increasingBSTHelper(root, null);
    }

    /************************  589. N-ary Tree Preorder Traversal ************************************/
    public List<Integer> preorder(Node root) {
        if(root == null) return new ArrayList<>();
        ArrayList<Integer> res = new ArrayList<>();
        res.add(root.val);
        for(int i=0; i<root.children.size(); i++) {
            res.addAll(preorder(root.children.get(i)));
        }
        return res;
    }


//    Using Static

//    List<Integer> res = new ArrayList<>();
//    public List<Integer> preorder(Node root) {
//        if(root == null) return res;
//        res.add(root.val);
//        for(int i=0; i<root.children.size(); i++) {
//            preorder(root.children.get(i));
//        }
//        return res;
//    }

    /************************  590. N-ary Tree Postorder Traversal ************************************/
    public List<Integer> postorder(Node root) {
        if(root == null) return new ArrayList<>();
        ArrayList<Integer> res = new ArrayList<>();
        for(int i=0; i<root.children.size(); i++) {
            res.addAll(postorder(root.children.get(i)));
        }
        res.add(root.val);
        return res;
    }


//    Using Static
//    List<Integer> res = new ArrayList<>();
//    public List<Integer> postorder(Node root) {
//        if(root == null) return res;
//        for(int i=0; i<root.children.size(); i++) {
//            postorder(root.children.get(i));
//        }
//        res.add(root.val);
//        return res;
//    }

    /************************  700. Search in a Binary Search Tree ************************************/
//    Recursive Approach

//    public TreeNode searchBST(TreeNode root, int val) {
//        if(root == null || root.val == val) return root;
//        TreeNode left = searchBST(root.left, val);
//        return left != null ? left : searchBST(root.right, val);
//    }

//    Iterative Approach

    public TreeNode searchBST(TreeNode root, int val) {
        while(root != null && root.val != val) root = root.val > val?root.left:root.right;
        return root;
    }

    /************************  1022. Sum of Root To Leaf Binary Numbers ************************************/

//    0ms
    public int sumRootToLeaf(TreeNode root) {
        return helperSumRootToLeaf(root, 0, 0);
    }
    public int helperSumRootToLeaf(TreeNode root, int level, int asf) {
        if(root == null) return 0;
        if(root.left == null && root.right == null) return ((asf<<1) | root.val);
        return helperSumRootToLeaf(root.left, level+1, (asf<<1)|root.val)
                + helperSumRootToLeaf(root.right, level+1, (asf<<1)|root.val);
    }

    /************************  559. Maximum Depth of N-ary Tree ************************************/
//    0ms
    public int maxDepth(Node root) {
        if(root == null) return 0;
        int depth = 1;
        for(int i=0; i<root.children.size(); i++) {
            depth = Math.max(1 + maxDepth(root.children.get(i)), depth);
        }
        return depth;
    }
    /************************  104. Maximum Depth of Binary Tree ************************************/
    public int maxDepth(TreeNode root) {
        return root == null?0:1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    public boolean isUnivalTree(TreeNode root) {
        return root == null || ((root.left == null || (root.val == root.left.val))
                && (root.right == null || root.right.val == root.val)
                && isUnivalTree(root.left) && isUnivalTree(root.right));
    }

    /************************  226. Invert Binary Tree ************************************/
    public TreeNode invertTree(TreeNode root) {
        if(root == null) return null;
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
        return root;
    }

    /************************  94. Binary Tree Inorder Traversal ************************************/
    public List<Integer> inorderTraversal(TreeNode root) {
        if(root == null) return new ArrayList<>();
        ArrayList<Integer> res = new ArrayList<>(inorderTraversal(root.left));
        res.add(root.val);
        res.addAll(inorderTraversal(root.right));
        return res;
    }

    /************************  637. Average of Levels in Binary Tree ************************************/
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> res = new ArrayList<>();
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        while(!queue.isEmpty()) {
            double sum = 0;
            int size = queue.size();
            for(int i=0; i<size; i++) {
                TreeNode child = queue.remove();
                if(child.left != null) queue.add(child.left);
                if(child.right != null) queue.add(child.right);
                sum+=child.val;
            }
            res.add(sum/size);
        }
        return res;
    }

    /************************  872. Leaf-Similar Trees  ************************************/

//    General Approach
    /*
    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        getLeafNodes(root1, a);
        getLeafNodes(root2, b);
        if(a.size() != b.size()) return false;
        for(int i=0; i<a.size(); i++) if(!a.get(i).equals(b.get(i))) return false;
        return true;
    }
    public void getLeafNodes(TreeNode root, List<Integer> res) {
        if(root == null) return;
        if(root.left == null && root.right == null) res.add(root.val);
        getLeafNodes(root.left, res);
        getLeafNodes(root.right, res);
    }
    */

//    Space Optimized approach
    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        Stack<TreeNode> a = new Stack<>(), b = new Stack<>();
        a.add(root1);
        b.add(root2);
        while (!a.isEmpty() && !b.isEmpty()) {
            if(dfs(a) != dfs(b)) return false;
        }
        return a.size() == b.size();
    }
    public int dfs(Stack<TreeNode> st) {
        while(true) {
            TreeNode node = st.pop();
            if(node.left != null) st.add(node.left);
            if(node.right != null) st.add(node.right);
            if(node.left == null && node.right == null) return node.val;
        }
    }

    /************************  108. Convert Sorted Array to Binary Search Tree  ************************************/
    public TreeNode sortedArrayToBST(int[] nums) {
        return helperSortedArrayToBST(nums, 0, nums.length);
    }
    public TreeNode helperSortedArrayToBST(int[] nums, int start, int end) {
        if(start == end) return null;
        int mid = start + (end - start)/2;
        TreeNode node = new TreeNode(nums[mid]);
        node.left = helperSortedArrayToBST(nums, start, mid);
        node.right = helperSortedArrayToBST(nums, mid+1, end);
        return node;
    }


    /************************  145. Binary Tree Postorder Traversal  ************************************/
    public List<Integer> postorderTraversal(TreeNode root) {
        if(root == null) return new ArrayList<>();
        List<Integer> res = postorderTraversal(root.left);
        res.addAll(postorderTraversal(root.right));
        res.add(root.val);
        return res;
    }


    /************************  144. Binary Tree Preorder Traversal  ************************************/
    public List<Integer> preorderTraversal(TreeNode root) {
        if(root == null) return new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        res.add(root.val);
        res.addAll(preorderTraversal(root.left));
        res.addAll(preorderTraversal(root.right));
        return res;
    }

    /************************  144. Binary Tree Preorder Traversal  ************************************/
}
