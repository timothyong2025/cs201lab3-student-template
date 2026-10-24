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
          String[] traversals = {traversal1, traversal2, traversal3};

          // check if length is incorrect
          int length = traversal1.split("-").length;
          if (traversal2.split("-").length != length || traversal3.split("-").length != length) {
               return "Invalid traversals";
          }

          // try every way of giving the three traversals the roles inorder, preorder and postorder
          for (int in = 0; in < 3; in++) {
               // in a BST, the inorder traversal is always in ascending order
               if (!isAscending(traversals[in])) {
                    continue;
               }
               for (int pre = 0; pre < 3; pre++) {
                    for (int post = 0; post < 3; post++) {
                         // each traversal can only have one role
                         if (pre == in || post == in || post == pre) {
                              continue;
                         }

                         // build the BST by inserting the preorder values one by one
                         Node root = null;
                         for (String value : traversals[pre].split("-")) {
                              root = insert(root, Integer.parseInt(value));
                         }

                         // if the tree's traversals match the ones we were given, we found the answer
                         if (inorder(root).equals(traversals[in])
                                   && preorder(root).equals(traversals[pre])
                                   && postorder(root).equals(traversals[post])) {
                              String[] names = new String[3];
                              names[in] = "Inorder";
                              names[pre] = "Preorder";
                              names[post] = "Postorder";
                              return "Traversal 1 - " + names[0] + ", Traversal 2 - " + names[1] + ", Traversal 3 - " + names[2];
                         }
                    }
               }
          }
          return "Invalid traversals";
     }

     // returns true if every number is bigger than the one before it
     public static boolean isAscending(String traversal) {
          String[] values = traversal.split("-");
          for (int i = 1; i < values.length; i++) {
               if (Integer.parseInt(values[i - 1]) >= Integer.parseInt(values[i])) {
                    return false;
               }
          }
          return true;
     }

     static class Node {
          int value;
          Node left;
          Node right;

          Node(int value) {
               this.value = value;
          }
     }

     // normal BST insert: smaller values go left, bigger values go right
     public static Node insert(Node node, int value) {
          if (node == null) {
               return new Node(value);
          }
          if (value < node.value) {
               node.left = insert(node.left, value);
          } else {
               node.right = insert(node.right, value);
          }
          return node;
     }

     // each traversal returns a string like "1-2-3" so it can be compared to the input directly
     public static String inorder(Node node) {
          if (node == null) {
               return "";
          }
          return join(join(inorder(node.left), "" + node.value), inorder(node.right));
     }

     public static String preorder(Node node) {
          if (node == null) {
               return "";
          }
          return join(join("" + node.value, preorder(node.left)), preorder(node.right));
     }

     public static String postorder(Node node) {
          if (node == null) {
               return "";
          }
          return join(join(postorder(node.left), postorder(node.right)), "" + node.value);
     }

     // puts a dash between two strings, unless one of them is empty
     public static String join(String a, String b) {
          if (a.isEmpty()) {
               return b;
          }
          if (b.isEmpty()) {
               return a;
          }
          return a + "-" + b;
     }

}
