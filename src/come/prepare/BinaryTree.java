package come.prepare;

import com.sun.source.tree.Tree;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Binary tree implementation
 * Traversals : Pre , Post and Inorder , recursive and non-recursive
 * Depth of a node , root to node edges
 * height of a tree is same as depth of a tree , number od edges  root to deepest leaf node .
 */
public class BinaryTree {
    public static void main(String[] str) {
        int[] values = {4,2,5,3,7,9,11,45,50,58};
        TreeImpl tree = new TreeImpl(values, false);
         // System.out.println(tree.root.value + "," + tree.root.left + "," + tree.root.right);
         //System.out.println(tree.root.value);
         //tree.inOrdertraversal(tree.root);
        //tree.postOrdertraversal(tree.root);
         //tree.inOrderTraversalWhileLoop(tree.root);
        //tree.preOrderTraversalWhileLoop(tree.root);
        //tree.postOrderTraversalWhileLoop(tree.root);
        System.out.println(tree.height(tree.root,0));
        System.out.println(tree.height-1);
         //tree.preOrdertraversal(tree.root);
         //System.out.println(tree.root.right.value);
         tree.testCases();
    }
}

class TreeImpl {

    public void testCases() {
        assert (root.left.value == 2);
        assert (root.left.right.value == 3);
        assert (root.right.value == 5);
    }

     public TreeNode root;

    public TreeImpl(int[] values, boolean isRecursiveMode) {
         for (int value : values) {
             //System.out.println(value);
             if (isRecursiveMode) {
                 add(value);
             } else {
                 addWithWhileLoop(root, value);
             }
         }
     }

    /**
     *  add elements using whille loop
     * @param current
     * @param value
     */
   public void addWithWhileLoop(TreeNode current, int value) {
        // set root node
       if (current == null) {
           current = new TreeNode();
           current.value = value;
           this.root = current;
           return;
       };
        while (true) {
            if ( value < current.value) {
                if (current.left == null) {
                    current.left = new TreeNode();
                    current.left.value = value;
                    break;
                }
                current = current.left;
            }
            if (value >= current.value) {
                if (current.right == null) {
                    current.right = new TreeNode();
                    current.right.value = value;
                    break;
                }
                current = current.right;
            }
        }
   }

    /**
     *  add elements recursively
     * @param current
     * @param value
     * @return
     */
    public TreeNode addRecursive(TreeNode current, int value) {
            if (current == null) {
                current = new TreeNode();
                current.value = value;
                return current;
            }
           if (value < current.value) {
                current.left =  addRecursive(current.left, value);
                //System.out.println("added to left : " + value);
           }
           if (value > current.value) {
                current.right = addRecursive(current.right, value);
               //System.out.println("added to right : " + value);

           }
           return current;
    }

    public void add(int value) {
        root = addRecursive(root, value);
        //System.out.println("root value : " + root.value);
    }

    public void inOrdertraversal(TreeNode current){
        if (current == null) {
            return;
        }
        inOrdertraversal(current.left);
        System.out.println(current.value); //  first left
        inOrdertraversal(current.right);
    }

    public void preOrdertraversal(TreeNode current){
        if (current == null) {
            return;
        }
        System.out.println(current.value); //  first node , left and right
        preOrdertraversal(current.left);
        preOrdertraversal(current.right);
    }

    public void postOrdertraversal(TreeNode current){
        if (current == null) {
            return;
        }
        //System.out.println("NODE : " + current.value);
        postOrdertraversal(current.left);
        postOrdertraversal(current.right);
        System.out.println(current.value); //  first left , right and then node.
    }


    int height = 0;
    public int height(TreeNode current, int depth) {
        // use any of traversal logic , lets use inorder
        if (current == null) {
            return depth ;
        }
        // one level added
        depth++;
        depth = height(current.left, depth) ;
        if (height < depth) {
            this.height = depth;
        }
        depth = height(current.right, depth);
        // visited both the side
        return depth-1;

    }

    /*
        binary tree while loop implementation
     */
    public void preOrderTraversalWhileLoop(TreeNode current) {
        Stack<TreeNode> stack = new Stack<>();
        while(true) {
            if (current == null) {
                // reached left most node
                if (stack.isEmpty()) {
                    break;
                }
                // get the last node
                current = stack.pop();
                current = current.right;
                continue;
            }
            System.out.println(current.value);
            stack.push(current);
            current = current.left;
        }
    }

    /*
        binary tree post order is different , we first push right nodes and left node to process left first ....
     */
    public void postOrderTraversalWhileLoop(TreeNode current) {
        Stack<TreeNode> stack = new Stack<>();
        ArrayList<TreeNode> list = new ArrayList();
        stack.push(current);
        while(!stack.isEmpty()) {
            current = stack.peek();
            // if leaf node print
            if (current.left == null && current.right ==null) {
                // get the last node
                current = stack.pop();
                System.out.println(current.value);
                continue;
            }
            // push right nodes first to process after left nodes .
            if (current.right != null) {
                stack.push(current.right);
                // marking this as visited
                current.right = null;
            }
            if (current.left !=null) {
                stack.push(current.left);
                // marking this as visited
                current.left = null;
            }
        }
    }

    public void inOrderTraversalWhileLoop(TreeNode current) {
        Stack<TreeNode> stack = new Stack<>();
        while(!stack.isEmpty() & current !=null ) {
            if (current == null) {
                // reached left most node
                // get the last node
                current = stack.pop();
                System.out.println(current.value);
                current = current.right;
                continue;
            }
            stack.push(current);
            current = current.left;
        }
    }
}

// Tree structure
class TreeNode {
    int value ;
    TreeNode left;
    TreeNode right;
}
