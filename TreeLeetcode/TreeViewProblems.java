package TreeLeetcode;


import java.util.*;

public class TreeViewProblems {
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

    class Pair {
        TreeNode node;
        int level;
        public Pair(TreeNode node, int level) {
            this.node = node;
            this.level = level;
        }
    }
    public void findMinMax(TreeNode node, int level, int[] minMax) {
        if(node == null) return;
        minMax[0] = Math.min(level, minMax[0]);
        minMax[1] = Math.max(level, minMax[1]);
        findMinMax(node.left, level - 1, minMax);
        findMinMax(node.right, level + 1, minMax);
    }

    public List<List<Integer>> verticalTraversal(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        List<PriorityQueue<Integer>> check = new ArrayList<>();
        Queue<Pair> q = new ArrayDeque<>();
        q.add(new Pair(root, 0));
        int[] minMax = new int[2];
        findMinMax(root, 0, minMax);
        for(int i=minMax[0]; i<=minMax[1]; i++) {
            res.add(new ArrayList<>());
            check.add(new PriorityQueue<>());
        }
        int shift = -minMax[0];
        while(!q.isEmpty()) {
            int size = q.size();
            while(size-- != 0) {
                Pair p = q.remove();
                check.get(p.level + shift).add(p.node.val);
                if(p.node.left != null) q.add(new Pair(p.node.left, p.level - 1));
                if(p.node.right != null) q.add(new Pair(p.node.right, p.level + 1));
            }
            for(int i=0; i<check.size(); i++) {
                while(check.size() > 0) {
                    res.get(i).add(check.get(i).remove());
                }
            }
        }
        return res;
    }
    /************************  199. Binary Tree Right Side View  ************************************/
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new LinkedList<>();
        if(root == null) return res;
        Deque<TreeNode> mq = new LinkedList<>();
        mq.add(root);
        while(!mq.isEmpty()) {
            int size = mq.size();
            res.add(mq.getLast().val);
            while(size-- != 0) {
                TreeNode node = mq.remove();
                if(node.left != null) mq.add(node.left);
                if(node.right != null) mq.add(node.right);
            }
        }
        return res;
    }

    /************************  Binary Tree Left Side View (Not on Leetcode)  ************************************/
    public List<Integer> leftSideView(TreeNode root) {
        List<Integer> res = new LinkedList<>();
        if(root == null) return res;
        Deque<TreeNode> mq = new LinkedList<>();
        mq.add(root);
        while(!mq.isEmpty()) {
            int size = mq.size();
            res.add(mq.getLast().val);
            while(size-- != 0) {
                TreeNode node = mq.remove();
                if(node.left != null) mq.add(node.left);
                if(node.right != null) mq.add(node.right);
            }
        }
        return res;
    }

    /************************  Bottom View of Binary Tree (GeeksForGeeks)  ************************************/
    public ArrayList <Integer> bottomView(TreeNode root)
    {
        ArrayList<Integer> res = new ArrayList<>();
        Queue<Pair> q = new ArrayDeque<>();
        q.add(new Pair(root, 0));
        int[] minMax = new int[2];
        findMinMax(root, 0, minMax);
        for(int i=minMax[0]; i<=minMax[1]; i++) res.add(0);
        int shift = -minMax[0];
        while(!q.isEmpty()) {
            int size = q.size();
            while(size-- != 0) {
                Pair p = q.remove();
                if(res.get(p.level + shift).equals(0)) res.set(p.level + shift, p.node.val);
                if(p.node.left != null) q.add(new Pair(p.node.left, p.level - 1));
                if(p.node.right != null) q.add(new Pair(p.node.right, p.level + 1));
            }
        }
        return res;
    }

    /************************  Top View of Binary Tree (GeeksForGeeks)  ************************************/
    public ArrayList <Integer> topView(TreeNode root)
    {
        ArrayList<Integer> res = new ArrayList<>();
        Queue<Pair> q = new ArrayDeque<>();
        q.add(new Pair(root, 0));
        int[] minMax = new int[2];
        findMinMax(root, 0, minMax);
        for(int i=minMax[0]; i<=minMax[1]; i++) res.add(0);
        int shift = -minMax[0];
        while(!q.isEmpty()) {
            int size = q.size();
            while(size-- != 0) {
                Pair p = q.remove();
                if(res.get(p.level + shift).equals(0)) res.set(p.level + shift, p.node.val);
                if(p.node.left != null) q.add(new Pair(p.node.left, p.level - 1));
                if(p.node.right != null) q.add(new Pair(p.node.right, p.level + 1));
            }
        }
        return res;
    }

    /************************  Diagonal Traversal of Binary Tree (GeeksForGeeks)  ************************************/
    public ArrayList<Integer> diagonal(TreeNode root)
    {
        ArrayList<Integer> res = new ArrayList<>();
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        while(!queue.isEmpty()) {
            int size = queue.size();
            while(size-- != 0) {
                TreeNode node = queue.remove();
                while(node != null) {
                    res.add(node.val);
                    if(node.left != null) queue.add(node.left);
                    node = node.right;
                }
            }
        }
        return res;
    }
    // Deep Insight of Diagonal Order..........
    /************************  Diagonal Traversal of Binary Tree (Pepcoding)  ************************************/
    public ArrayList<ArrayList<Integer>> diagonalOrder(TreeNode root) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        while(!queue.isEmpty()) {
            int size = queue.size();
            ArrayList<Integer> smallAns = new ArrayList<>();
            while(size-- != 0) {
                TreeNode node = queue.remove();
                while(node != null) {
                    smallAns.add(node.val);
                    if(node.left != null) queue.add(node.left);
                    node = node.right;
                }
            }
            res.add(smallAns);
        }
        return res;
    }
    /************************  Anti Diagonal Traversal of Binary Tree (Pepcoding)  ************************************/
    public ArrayList<ArrayList<Integer>> antiDiagonalOrder(TreeNode root) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        while(!queue.isEmpty()) {
            int size = queue.size();
            ArrayList<Integer> smallAns = new ArrayList<>();
            while(size-- != 0) {
                TreeNode node = queue.remove();
                while(node != null) {
                    smallAns.add(node.val);
                    if(node.right != null) queue.add(node.right);
                    node = node.left;
                }
            }
            res.add(smallAns);
        }
        return res;
    }

    public boolean flipEquiv(TreeNode root1, TreeNode root2) {
        if(root1 == null && root2 == null) return true;
        if(root1 == null) return false;
        if(root2 == null) return false;
        return (root1.val == root2.val) &&
                (flipEquiv(root1.left, root2.left) && flipEquiv(root1.right, root2.right)
                || flipEquiv(root1.left, root2.right) && flipEquiv(root1.right, root2.left));
    }
    /*
    class Pair {
        int maxv = Integer.MIN_VALUE;
        int csum = 0;
        public Pair(int maxv, int csum) {
            this.maxv = maxv;
            this.csum = csum;
        }
    }
    public int max(int... arr) {
        int maxv = arr[0];
        for(int i=1; i<arr.length; i++) maxv = Math.max(maxv, arr[i]);
        return maxv;
    }
    public Pair maxPathSumHelper(TreeNode root) {
        if(root == null) return new Pair(Integer.MIN_VALUE, 0);
        Pair left = maxPathSumHelper(root.left);
        Pair right = maxPathSumHelper(root.right);
        int oneSided = max(left.csum, right.csum) + root.val;
        int maxv = max(left.maxv, right.maxv, oneSided, left.csum + right.csum + root.val, root.val);
        Pair ans = new Pair(maxv, max(oneSided, root.val));
        return ans;
    }
    public int maxPathSum(TreeNode root) {
        return maxPathSumHelper(root).maxv;
    }
    */

    /************************  968. Binary Tree Cameras ************************************/
    // -1 camera required, 0 already covered, 1 I have a camera
    int count = 0;
    public int findMinCamera(TreeNode root) {
        if(root == null) return 0;
        int left = findMinCamera(root.left);
        int right = findMinCamera(root.right);
        if(left == -1 || right == -1){
            count++;
            return 1;
        }
        if(left == 1 || right == 1) return 0;
        return -1;
    }
    public int minCameraCover(TreeNode root) {
        if(findMinCamera(root) == -1) count++;
        return count;
    }

    /************************  1372. Longest ZigZag Path in a Binary Tree ************************************/
    public int max(int... arr) {
        int max = arr[0];
        for(int i=1; i<arr.length; i++) {
            max = Math.max(max, arr[i]);
        }
        return max;
    }

    public int[] find(TreeNode root) {
        int[] ans = new int[3];
        if(root == null) {
            ans[0] = ans[1] = -1;
            ans[2] = Integer.MIN_VALUE;
            return ans;
        }
        int[] left = find(root.left);
        int[] right = find(root.right);
        ans[0] = 1 + right[1];
        ans[1] = 1 + left[0];
        ans[2] = max(left[2], right[2], ans[0], ans[1]);
        return ans;
    }
    public int longestZigZag(TreeNode root) {
        int[] res = find(root);
        return res[2];
    }

    /************************  1376. Time Needed to Inform All Employees ************************************/
    class TreeNode2 {
        int val;
        int time;
        List<TreeNode2> children;
        public TreeNode2(int val, int time) {
            this.time = time;
            this.val = val;
            this.children = new ArrayList<>();
        }
    }
    public int dfs(List<TreeNode2> list, int id) {
        int time = list.get(id).time;
        int maxv = 0;
        for(int i=0; i<list.get(id).children.size(); i++) {
            maxv = Math.max(dfs(list, list.get(id).children.get(i).val), maxv);
        }
        return time + maxv;
    }
    public int numOfMinutes(int n, int headID, int[] manager, int[] informTime) {
        List<TreeNode2> list = new ArrayList<>();
        for(int i=0; i<n; i++) list.add(new TreeNode2(i, informTime[i]));
        for(int i=0; i<manager.length; i++) {
            if(manager[i] == -1) continue;
            list.get(manager[i]).children.add(list.get(i));
        }
        return dfs(list, headID);
    }


    /************************  BST + AVL TREE ************************************/
    /************************  701. Insert into a Binary Search Tree ************************************/
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if(root == null) return new TreeNode(val);
        if(root.val > val)root.left = insertIntoBST(root.left, val);
        else root.right = insertIntoBST(root.right, val);
        return root;
    }

    /************************  450. Delete Node in a BST ************************************/
    public TreeNode getLastLeftNode(TreeNode root) {
        while(root.left != null) root = root.left;
        return root;
    }
    public TreeNode deleteNode(TreeNode root, int key) {
        if(root.val > key) {
            root.left = deleteNode(root.left, key);
        } else if(root.val < key) {
            root.right = deleteNode(root.right, key);
        }
        if(root.val == key) {
            if(root.left == null || root.right == null) {
                return root.left==null?root.right:root.left;
            } else {
                TreeNode max = getLastLeftNode(root.left);
                deleteNode(root.left, max.val);
                max.left = root.left;
                max.right = root.right;
                return max;
            }
        }
        return root;
    }


    /************************  Level order serialize and deserialize (GeeksForGeeks) ************************************/
    static class Node {
        int data;
        Node left, right;
        public Node(int data) {
            this.data = data;
        }
    }
    public void serialize(Node root, ArrayList<Integer> A)
    {
        //code here
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        while(!q.isEmpty()) {
            Node node = q.remove();
            if(node == null) A.add(-1);
            else {
                A.add(node.data);
                q.add(node.left);
                q.add(node.right);

            }
        }
    }

    //Function to deserialize a list and construct the tree.
    public Node deSerialize(ArrayList<Integer> A)
    {
        Queue<Node> q = new ArrayDeque<>();
        int val = A.get(0);
        Node ans = new Node(val);
        q.add(ans);
        for(int i=1; i<A.size();) {
            Node n = q.remove();
            if(A.get(i) == -1) n.left = null;
            else {
                n.left = new Node(A.get(i));
                q.add(n.left);
            }
            i++;
            if(A.get(i) == -1) n.right = null;
            else {
                n.right = new Node(A.get(i));
                q.add(n.right);
            }
            i++;
        }
        return ans;
    }

    /************************  Inorder successor of BST ************************************/
    public void findInorderSuccessor(Node root, Node x, Node[] ans) {
        if(root == null) return;
        if(root.data < x.data) {
            findInorderSuccessor(root.right, x, ans);
        } else {
            ans[0] = root;
            findInorderSuccessor(root.left, x, ans);
        }
    }
    public Node inorderSuccessor(Node root,Node x) {
        Node[] ans = new Node[1];
        findInorderSuccessor(root, x, ans);
        return ans[0];
    }

    public int eliminateMaximum(int[] dist, int[] speed) {
        int[] a = new int[dist.length];
        for(int i=0; i<a.length; i++) {
            a[i] = dist[i]/speed[i];
        }
        Arrays.sort(a);
        int count = 0;
        for(int i=0; i<a.length; i++) {
            if(a[i]>i) break;
            count++;
        }
        return count;
    }

}
