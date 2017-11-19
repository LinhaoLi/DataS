import static org.junit.Assert.*;

import org.junit.Test;

import textbook.LinkedBinaryTree;
import textbook.Position;

public class test {
	
	public LinkedBinaryTree<String> ComplexTree(){
		LinkedBinaryTree<String> tree;
		tree = new LinkedBinaryTree<String>();
		Position<String> root = tree.addRoot("-");
		Position<String> l1l= tree.addLeft(root, "+");
		Position<String> l1r= tree.addRight(root, "+");
		Position<String> l21= tree.addLeft(l1l, "b");
		Position<String> l22= tree.addRight(l1l, "+");
		Position<String> l31= tree.addLeft(l22, "4");
		Position<String> l32= tree.addRight(l22, "5");
		Position<String> l23= tree.addLeft(l1r, "1");
		Position<String> l24= tree.addRight(l1r, "2");
		return tree;
	}
	
	
	@Test
	public void testtree2prefix() {
		LinkedBinaryTree<String> tree;
		tree = Assignment.prefix2tree("* - 1 + b 3 d");
		assertEquals("* - 1 + b 3 d", Assignment.tree2prefix(tree));
		
		tree = Assignment.prefix2tree("+ 1 - 2 3");
		assertEquals("+ 1 - 2 3", Assignment.tree2prefix(tree));
		
		//tree = Assignment.prefix2tree("");
		//assertEquals(null, Assignment.tree2prefix(tree));
	}
	
	@Test
	public void testisPrefix() {
		
		String testString = new String("* - 1 + b 3 d");
		assertEquals(true, Assignment.isPrefix(testString));
		testString = new String("* - 1 + 3 d");
		assertEquals(false, Assignment.isPrefix(testString));
		testString = new String("* - 1 b 3 d");
		assertEquals(false, Assignment.isPrefix(testString));
		
	}
	
	@Test
	public void testtree2infix() {
		LinkedBinaryTree<String> tree;
		tree = Assignment.prefix2tree("* - 1 + b 3 d");
		assertEquals("((1-(b+3))*d)", Assignment.tree2infix(tree));
		tree = Assignment.prefix2tree("+ 1 - 2 3");
		assertEquals("(1+(2-3))", Assignment.tree2infix(tree));
		tree = Assignment.prefix2tree("1");
		assertEquals("1", Assignment.tree2infix(tree));
		
	}
	
	@Test
	public void testsimplify() {
		LinkedBinaryTree<String> tree;
		tree = Assignment.prefix2tree("* - 1 2");
		assertEquals("((1-(b+3))*d)", Assignment.simplify(tree));
		tree = Assignment.prefix2tree("+ 1 - 2 3");
		assertEquals("(1+(2-3))", Assignment.tree2infix(tree));
		tree = Assignment.prefix2tree("1");
		assertEquals("1", Assignment.tree2infix(tree));
		
	}
	
	@Test
	public void testisArithmeticExpression() {
		LinkedBinaryTree<String> tree;
		tree = new LinkedBinaryTree<String>();
		Position<String> root = tree.addRoot("-");
		Position<String> l1l= tree.addLeft(root, "+");
		Position<String> l1r= tree.addRight(root, "2");//+ to 2
		Position<String> l21= tree.addLeft(l1l, "b");
		Position<String> l22= tree.addRight(l1l, "+");
		Position<String> l31= tree.addLeft(l22, "4");
		Position<String> l32= tree.addRight(l22, "5");
		//Position<String> l23= tree.addLeft(l1r, "1");
		//Position<String> l24= tree.addRight(l1r, "2");

		assertEquals(true, Assignment.isArithmeticExpression(tree));
		
		tree = new LinkedBinaryTree<String>();
		Position<String> root2 = tree.addRoot("-");
		Position<String> l1l2= tree.addLeft(root2, "+");
		Position<String> l1r2= tree.addRight(root2, "1");
		assertEquals(false, Assignment.isArithmeticExpression(tree));
	}
	
	@Test
	public void testSingleArithmeticExpression() {
		LinkedBinaryTree<String> tree;
		tree = new LinkedBinaryTree<String>();
		Position<String> root = tree.addRoot("");
		assertEquals(false, Assignment.isArithmeticExpression(tree));
	}
}
