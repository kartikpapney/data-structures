package TreeLeetcode;

import com.sun.source.tree.Tree;

import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ConversionProblems {
    class Node {
        Node left, right;
        int data;

        Node(int d) {
            data = d;
            left = right = null;
        }

    }

    /************************  Binary Tree to DLL  ************************************/
//    Node bToDLL(Node root) {
//        if(root == null) return null;
//        Node dummy = new Node(-1);
//        bToDLL_(root, new Node[]{dummy});
//        dummy = dummy.right;
//        dummy.left = null;
//        return dummy;
//    }
//    void bToDLL_(Node root, Node[] prev) {
//        if(root == null) return;
//        bToDLL_(root.left, prev);
//        prev[0].right = root;
//        root.left = prev[0];
//        prev[0] = prev[0].right;
//        bToDLL_(root.right, prev);
//    }
//
//    Node bTreeToClist(Node root) {
//        if(root == null) return null;
//        Node dummy = new Node(-1);
//        bTreeToClist_(root, new Node[]{dummy});
//        dummy = dummy.right;
//        dummy.left = null;
//        Node end = dummy;
//        while(end.right != null) end = end.right;
//        end.right = dummy;
//        dummy.left = end;
//        return dummy;
//    }
//   void bTreeToClist_(Node root, Node[] prev) {
//        if(root == null) return;
//        bTreeToClist_(root.left, prev);
//        prev[0].right = root;
//        root.left = prev[0];
//        prev[0] = prev[0].right;
//        bTreeToClist_(root.right, prev);
//    }

    class Pair {
        TreeNode node;
        int position;
        public Pair(TreeNode node, int position) {
            this.node = node;
            this.position = position;
        }
    }
    public int widthOfBinaryTree(TreeNode root) {
        Queue<Pair> q = new ArrayDeque<>();
        q.add(new Pair(root, 0));
        int max = 1;
        while (!q.isEmpty()) {
            int size = q.size();
            Integer[] minMax = new Integer[2];
            for(int i=0; i<size; i++) {
                Pair p = q.remove();
                if(p.node.left != null) {
                    q.add(new Pair(p.node.left, 2*p.position+1));
                    if(minMax[0] == null) minMax[0] = 2*p.position+1;
                }
                if(p.node.left != null) {
                    q.add(new Pair(p.node.left, 2*p.position+1));
                    minMax[1] = 2*p.position+2;
                }
            }
            if(minMax[0] != null) max = Math.max(max, minMax[1] - minMax[0] + 1);
        }
        return max;
    }
}
