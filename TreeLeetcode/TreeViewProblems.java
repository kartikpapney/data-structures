package TreeLeetcode;

import com.sun.source.tree.Tree;

import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class TreeViewProblems {
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
}
