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

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
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

    /************************  653. Two Sum IV - Input is a BST (Important)  ************************************/
//    public boolean findTarget(TreeNode root, int k) {
//
//    }

    /************************  606. Construct String from Binary Tree  ************************************/
    public String tree2str(TreeNode t) {
        StringBuilder strb = new StringBuilder();
        helperTree2Str(t, strb);
        return strb.toString().substring(1, strb.length() - 1);
    }
    public void helperTree2Str(TreeNode root, StringBuilder strb) {
        if(root == null) {
            strb.append("()");
            return;
        } else if(root.left == null && root.right == null) {
            strb.append("(").append(root.val).append(")");
            return;
        }
        strb.append("(");
        strb.append(root.val);
        helperTree2Str(root.left, strb);
        if(root.right != null) helperTree2Str(root.right, strb);
        strb.append(")");
    }

    /************************  530. Minimum Absolute Difference in BST
                            * 783. Minimum Distance Between BST Nodes ************************************/

    Integer val = null;
    int minv = Integer.MAX_VALUE;
    public int getMinimumDifference(TreeNode root) {
        helperGetMinimumDifference(root);
        return minv;
    }
    public void helperGetMinimumDifference(TreeNode root) {
        if(root == null) return;
        helperGetMinimumDifference(root.left);
        if(val == null) val = root.val;
        else minv = Math.min(root.val - val, minv);
        helperGetMinimumDifference(root.right);
    }

    /************************  257. Binary Tree Paths  ************************************/
    // One more way first create an integer arraylist then append it to list of strings.

    public void helperBinaryTreePaths(TreeNode root, List<String> res, String asf) {
        if(root.left == null && root.right == null) res.add(asf + root.val);
        if(root.left != null) helperBinaryTreePaths(root.left, res, asf + root.val + "->");
        if(root.right != null) helperBinaryTreePaths(root.right, res, asf + root.val + "->");
    }
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> res = new ArrayList<>();
        helperBinaryTreePaths(root, res, "");
        return res;
    }

    /************************  100. Same Tree  ************************************/

    public boolean isSameTree(TreeNode p, TreeNode q) {
        return p == null ? q==null :
                        (q != null
                        && (p.val == q.val)
                        && isSameTree(p.left, q.left)
                        && isSameTree(p.right, q.right));
    }

    /************************  563. Binary Tree Tilt  ************************************/
    int sum = 0;
    public int findTilt(TreeNode root) {
        helperFindTilt(root);
        return sum;
    }
    public int helperFindTilt(TreeNode root) {
        if(root == null) return 0;
        int left = helperFindTilt(root.left);
        int right = helperFindTilt(root.right);
        sum+=Math.abs(left - right);
        return root.val + left + right;
    }
    /************************  235. Lowest Common Ancestor of a Binary Search Tree  ************************************/

//  Will not work in case 1 child is present and other is not present + Basic approach.. This approach can work for even Binary Tree
    /*
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null) return null;
        if(root == p || root == q) return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if(left != null && right != null) return root;
        return left == null?right:left;
    }
    */

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode curr = root;
        while(curr != null) {
            if(curr.val < p.val && curr.val < q.val) curr = curr.right;
            else if(curr.val > p.val && curr.val > q.val) curr = curr.left;
            else return curr;
        }
        return curr;
    }

    /************************  235. Lowest Common Ancestor of a Binary Search Tree  ************************************/
    public int sumOfLeftLeaves(TreeNode root) {
        return helperSumOfLeftLeaves(root, false);
    }
    public int helperSumOfLeftLeaves(TreeNode root, boolean check) {
        if(root == null) return 0;
        if(root.left == null && root.right == null) return check?root.val:0;
        return helperSumOfLeftLeaves(root.left, true) + helperSumOfLeftLeaves(root.right, false);
    }

    /************************  173. Binary Search Tree Iterator  ************************************/

    public class BSTIterator {
        ArrayDeque<TreeNode> queue;
        public BSTIterator(TreeNode root) {
            queue = new ArrayDeque<>();
            addAllSmaller(root);
        }

        public void addAllSmaller(TreeNode root) {
            while(root != null) {
                queue.addFirst(root);
                root = root.left;
            }
        }

        public int next() {
            TreeNode node = queue.remove();
            addAllSmaller(node.right);
            return node.val;
        }

        public boolean hasNext() {
            return queue.size() != 0;
        }
    }

    /************************  814. Binary Tree Pruning  ************************************/
//    Basic approach
//    public TreeNode pruneTree(TreeNode root) {
//        boolean ans = helperPruneTree(root);
//        if(!ans && root.val != 1) return null;
//        return root;
//    }
//    public boolean helperPruneTree(TreeNode root) {
//        if(root == null) return false;
//        boolean left = helperPruneTree(root.left);
//        boolean right = helperPruneTree(root.right);
//        if(!left) root.left = null;
//        if(!right) root.right = null;
//        return root.val == 1 || left || right;
//    }

    public TreeNode pruneTree(TreeNode root) {
        if(root == null) return null;
        root.left = pruneTree(root.left);
        root.right = pruneTree(root.right);
        return root.val == 1 || root.left != null || root.right != null?root:null;
    }


    /************************  865. Smallest Subtree with all the Deepest Nodes
     * 1123. Lowest Common Ancestor of Deepest Leaves ************************************/

//    Brute Force approach
    public TreeNode lcaDeepestLeaves(TreeNode root) {
        List<List<TreeNode>> res = new ArrayList<>();
        find(root, new ArrayList<>(), res, maxDepth(root));
        int idx = 0;
        loop:
        while(idx < res.get(0).size()) {
            for (List<TreeNode> re : res) {
                if (re.get(idx).val != res.get(0).get(idx).val) break loop;
            }
            idx++;
        }
        return res.get(0).get(idx-1);
    }

    public void find(TreeNode node, List<TreeNode> curr, List<List<TreeNode>> res, int k) {
        if (node != null) {
            if(node.left == null && node.right == null) {
                if(k == 0) {
                    List<TreeNode> bc = new ArrayList<>(curr);
                    bc.add(node);
                    res.add(bc);
                }
            } else {
                curr.add(node);
                find(node.left, curr, res, k-1);
                find(node.right, curr, res, k-1);
                curr.remove(curr.size() - 1);
            }
        }

    }
//  Good Approach
    class Pair {
        TreeNode node;
        int depth;
        public Pair(TreeNode node, int depth) {
            this.node = node;
            this.depth = depth;
        }
    }
    public Pair lcaHelper(TreeNode node, int depth) {
        if(node == null) return new Pair(null, depth);
        Pair left = lcaHelper(node.left, depth+1);
        Pair right = lcaHelper(node.right, depth+1);
        if(left.depth == right.depth) return new Pair(node, left.depth);
        return left.depth>right.depth?left:right;
    }
    public TreeNode subtreeWithAllDeepest(TreeNode root) {
        Pair p = lcaHelper(root, 0);
        return p.node;
    }

    /************************  1026. Maximum Difference Between Node and Ancestor  ************************************/

    int ans = 0;
    public int maxAncestorDiff(TreeNode root) {
        find(root, root.val, root.val);
        return ans;
    }
    public void find(TreeNode root, int min, int max) {
        if(root == null) return;
        int mn = Math.min(min, root.val);
        int mx = Math.max(max, root.val);
        ans = Math.max(ans, mx - mn);
        find(root.left, mn, mx);
        find(root.right, mn, mx);
    }

    /************************  508. Most Frequent Subtree Sum  ************************************/
    HashMap<Integer, Integer> map = new HashMap<>();
    public int[] findFrequentTreeSum(TreeNode root) {
        find(root);
        int maxFreq = Integer.MIN_VALUE;
        int count = 0;
        for(Map.Entry<Integer, Integer> mp : map.entrySet()) {

            if(mp.getValue() > maxFreq) {
                maxFreq = mp.getValue();
                count = 1;
            } else if(mp.getValue() == maxFreq) {
                count++;
            }
        }
        int[] ans = new int[count];
        int idx = 0;
        for(Map.Entry<Integer, Integer> mp : map.entrySet()) {
            if(mp.getValue() == maxFreq) {
                ans[idx++] = mp.getKey();
            }
        }
        return ans;
    }
    public int find(TreeNode root) {
        if(root == null) return 0;
        int left = find(root.left);
        int right = find(root.right);
        int csum = root.val + left + right;
        map.put(csum, map.getOrDefault(csum, 0) + 1);
        return csum;
    }
    /************************  1448 Count Good Nodes in Binary Tree  ************************************/
    public int goodNodes(TreeNode root) {
        return countGoodNodes(root, Integer.MIN_VALUE);
    }
    public int countGoodNodes(TreeNode root, int max) {
        return root==null ? 0:root.val>=max ? 1
                + countGoodNodes(root.left, root.val)
                + countGoodNodes(root.right, root.val):countGoodNodes(root.left, max)
                + countGoodNodes(root.right, max);
    }

//    public int distributeCoins(TreeNode root) {
//
//    }

    List<TreeNode> res;
    HashSet<Integer> set;
    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        res = new ArrayList<>();
        set = new HashSet();
        for(int val : to_delete) set.add(val);
        set.add(0);
        delete(new TreeNode(0, root, null));
        return res;
    }
    public TreeNode delete(TreeNode root) {
        if(root == null) return null;
        root.left = delete(root.left);
        root.right = delete(root.right);
        if(set.contains(root.val)) {
            if(root.left != null) res.add(root.left);
            if(root.right != null) res.add(root.right);
            return null;
        }
        return root;
    }

//    public int distributeCoins(TreeNode root) {
//
//    }

    /************************  919. Complete Binary Tree Inserter  ************************************/
    class CBTInserter {
        // This will store the treenodes
        List<TreeNode> arr;
        public CBTInserter(TreeNode root) {
            arr = new ArrayList<>();
            arr.add(root);
            for(int i=0; i<arr.size(); i++) {
                if(arr.get(i).left != null) arr.add(arr.get(i).left);
                if(arr.get(i).right != null) arr.add(arr.get(i).right);
            }
        }
        public int insert(int v) {
            // Finding the parent
            TreeNode parent = arr.get((arr.size() - 1)/2);
            TreeNode node = new TreeNode(v);
            if(parent.left == null) parent.left = node;
            else parent.right = node;
            arr.add(node);
            return parent.val;
        }

        public TreeNode get_root() {
            return arr.get(0);
        }
    }


    public boolean isSubPath(ListNode head, TreeNode root) {
        if(head == null) return true;
        if(root == null) return false;
        boolean res = isSubPath(head, root.left) || isSubPath(head, root.right);
        if(head.val == root.val) res |= (isSubPath(head.next, root.left) || isSubPath(head.next, root.right));
        return res;
    }


    String answer = "";
    public String smallestFromLeaf(TreeNode root) {
        find(root, new StringBuilder());
        return answer;
    }

    public void find(TreeNode root, StringBuilder asf) {
        if(root == null) {
            if(answer.equals("")) answer = asf.toString();
            else if(answer.compareTo(asf.toString()) > 0) answer = asf.toString();
            return;
        }
        asf.insert(0, (char)(root.val + 'a'));
        find(root.left,  asf);
        find(root.right, asf);
        asf.deleteCharAt(0);
    }
}
