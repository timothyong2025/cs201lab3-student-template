import java.util.*;

public class Q1Test {
     public static void main(String[] args) {

          String result, traversal1, traversal2, traversal3;

          traversal1 = "1-2";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "3-2-1";
          traversal3 = "2-3-1";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "3-1-2-5-4";
          traversal2 = "2-1-4-5-3";
          traversal3 = "1-2-3-4-5";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Preorder, Traversal 2 - Postorder, Traversal 3 - Inorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "10-20-30-40-50";
          traversal2 = "20-10-30-40-50";
          traversal3 = "10-50-40-30-20";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();
     } 

      // write your codes here
     public static String verify(String traversal1, String traversal2, String traversal3){
          //break down traversal 1, 2 and 3 into arrays, seperating them by dashes
          long[][] t = new long[3][];
          try {
               t[0] = toArray(traversal1);
               t[1] = toArray(traversal2);
               t[2] = toArray(traversal3);
          } catch (NumberFormatException e) {
               return "Invalid traversals";
          }

          // check if length is incorrect
          int n = t[0].length;
          if (t[1].length != n || t[2].length != n) {
               return "Invalid traversals";
          }

          //figure out what is inorder (if all elements are ascending), and then figure out what is postorder and preorder from starting and ending elements
          for (int in = 0; in < 3; in++) {
               if (!isAscending(t[in])) {
                    continue;
               }
               for (int pre = 0; pre < 3; pre++) {
                    if (pre == in) {
                         continue;
                    }
                    //works out which traversal is expected to be the postorder
                    int post = 3 - in - pre;

                    // preorder starts with the root, postorder ends with it
                    if (t[pre][0] != t[post][n - 1]) {
                         continue;
                    }

                    if (matches(t[in], t[pre], t[post])) {
                         String[] names = new String[3];
                         names[in] = "Inorder";
                         names[pre] = "Preorder";
                         names[post] = "Postorder";
                         return "Traversal 1 - " + names[0] + ", Traversal 2 - " + names[1] + ", Traversal 3 - " + names[2];
                    }
               }
          }
          return "Invalid traversals";
     }

     private static long[] toArray(String traversal) {
          String[] parts = traversal.split("-");
          long[] values = new long[parts.length];
          for (int i = 0; i < parts.length; i++) {
               values[i] = Long.parseLong(parts[i].trim());
          }
          return values;
     }

     private static boolean isAscending(long[] values) {
          for (int i = 1; i < values.length; i++) {
               if (values[i - 1] >= values[i]) {
                    return false;
               }
          }
          return true;
     }

     private static class Node {
          long value;
          Node left, right;

          Node(long value) {
               this.value = value;
          }
     }

     // rebuild the BST from the preorder, then check its inorder and postorder match the other two
     // everything here is O(n) and uses loops instead of recursion, so deep trees can't overflow the stack
     private static boolean matches(long[] in, long[] pre, long[] post) {
          Node root = buildFromPreorder(pre);
          if (root == null) {
               return false;
          }

          // iterative inorder, compared against in as we go
          Deque<Node> stack = new ArrayDeque<>();
          Node current = root;
          int i = 0;
          while (current != null || !stack.isEmpty()) {
               while (current != null) {
                    stack.push(current);
                    current = current.left;
               }
               current = stack.pop();
               if (current.value != in[i++]) {
                    return false;
               }
               current = current.right;
          }

          // iterative postorder: visiting root, right, left gives postorder backwards
          stack.push(root);
          int j = post.length - 1;
          while (!stack.isEmpty()) {
               Node node = stack.pop();
               if (node.value != post[j--]) {
                    return false;
               }
               if (node.left != null) stack.push(node.left);
               if (node.right != null) stack.push(node.right);
          }
          return true;
     }

     // builds the BST in O(n) using a stack, returns null if pre isn't a valid BST preorder
     private static Node buildFromPreorder(long[] pre) {
          Deque<Node> stack = new ArrayDeque<>();
          Node root = new Node(pre[0]);
          stack.push(root);
          // once we go right of a node, every later value has to be bigger than it
          long lower = Long.MIN_VALUE;
          boolean hasLower = false;

          for (int i = 1; i < pre.length; i++) {
               long value = pre[i];
               if (hasLower && value <= lower) {
                    return null;
               }
               Node node = new Node(value);
               Node parent = null;
               // pop every node smaller than value, the last one popped is value's parent
               while (!stack.isEmpty() && stack.peek().value < value) {
                    parent = stack.pop();
               }
               if (parent != null) {
                    parent.right = node;
                    lower = parent.value;
                    hasLower = true;
               } else if (stack.peek().value == value) {
                    return null; // duplicate
               } else {
                    stack.peek().left = node;
               }
               stack.push(node);
          }
          return root;
     }

}
