
public class AVLTree implements AVLInterface {
	BSTNode<Integer> root;
	int numElements;
	
	public AVLTree() {
		root = null;
		numElements = 0;
	}
	
	public int numElements() {
		return numElements;
	}
	
	public boolean isEmpty() {
		return numElements == 0;
	}
	
	public void insert(int element) throws DuplicatedKeyException {
		BSTNode<Integer> newNode = new BSTNode<Integer>(element);
		
		if(isEmpty()) {
			root = newNode;
		}
		else {
			insert(newNode, root);		
		}
		numElements++;
	}
	
	private void insert(BSTNode<Integer> newNode, BSTNode<Integer> referenceNode) throws DuplicatedKeyException {
		if(newNode.getElement() > referenceNode.getElement()) {
			if(referenceNode.getRight() == null) {
				referenceNode.setRight(newNode);
				newNode.setFather(referenceNode);
				verifyBalance(referenceNode);
			}
			else {
				insert(newNode,referenceNode.getRight());
			}
		}
		else if (newNode.getElement() < referenceNode.getElement()){
			if(referenceNode.getLeft() == null) {
				referenceNode.setLeft(newNode);
				newNode.setFather(referenceNode);
				verifyBalance(referenceNode);
			}
			else {
				insert(newNode,referenceNode.getLeft());
			}
		}
		else
			throw new DuplicatedKeyException();
	}
	
	public void verifyBalance(BSTNode<Integer> referenceNode) {
		setBalance(referenceNode);
		
		if(referenceNode.getBalance() > 1) {
			if(height(referenceNode.getLeft().getLeft()) >= 
					height(referenceNode.getLeft().getRight())) {
				referenceNode = rightRotation(referenceNode);
			}
			else {
				referenceNode = doubleRightRotation(referenceNode);
			}
		}
		
		else if(referenceNode.getBalance() < -1) {
			if(height(referenceNode.getRight().getRight()) >=
					height(referenceNode.getRight().getLeft())) {
				referenceNode = leftRotation(referenceNode);
			}
			else {
				referenceNode = doubleLeftRotation(referenceNode);
			}
		}
		
		if(referenceNode.getFather() == null)
			root = referenceNode;
		else
			verifyBalance(referenceNode.getFather());
	}
	
	private BSTNode<Integer> leftRotation(BSTNode<Integer> referenceNode){
		
		BSTNode<Integer> right = referenceNode.getRight();
		right.setFather(referenceNode.getFather());
		referenceNode.setRight(right.getLeft());
		
		try {
			referenceNode.getRight().setFather(referenceNode);
		} catch(NullPointerException e) {}
		
		right.setLeft(referenceNode);
		referenceNode.setFather(right);
		
		BSTNode<Integer> grandfather = right.getFather();
		if(grandfather != null) {
			if (grandfather.getLeft() == referenceNode)
				grandfather.setLeft(right);
			else
				grandfather.setRight(right);
		}
		
		setBalance(referenceNode);
		setBalance(right);
		
		return right;
	}
	
	private BSTNode<Integer> rightRotation(BSTNode<Integer> referenceNode){
		BSTNode<Integer> left = referenceNode.getLeft();
		left.setFather(referenceNode.getFather());
		referenceNode.setLeft(left.getRight());
		
		try {
			referenceNode.getLeft().setFather(referenceNode);
		} catch(NullPointerException e) {}
		
		left.setRight(referenceNode);
		referenceNode.setFather(left);
		
		BSTNode<Integer> grandfather = left.getFather();
		if(grandfather != null) {
			if (grandfather.getLeft() == referenceNode)
				grandfather.setLeft(left);
			else
				grandfather.setRight(left);
		}
		
		setBalance(referenceNode);
		setBalance(left);
		
		return left;
	}
	
	private BSTNode<Integer> doubleLeftRotation(BSTNode<Integer> referenceNode){
		referenceNode.setRight(rightRotation(referenceNode.getRight()));
		return leftRotation(referenceNode);
	}
	
	private BSTNode<Integer> doubleRightRotation(BSTNode<Integer> referenceNode){
		referenceNode.setLeft(leftRotation(referenceNode.getLeft()));
		return rightRotation(referenceNode);
	}
	
	private int height(BSTNode<Integer> referenceNode) {
		if(referenceNode == null)
			return -1;
		
		else if(referenceNode.getLeft() == null && referenceNode.getRight() == null) //sem filhos
		{
			return 0;
		}
		
		else if(referenceNode.getRight() == null) //s� tem esquerda
		{
			return 1 + height(referenceNode.getLeft());
		}
		
		else if(referenceNode.getLeft() == null) //s� tem direita
		{
			return 1 + height(referenceNode.getRight());
		}
		
		else //tem esquerda e direita
		{
			int leftHeight = height(referenceNode.getLeft());
			int rightHeight = height(referenceNode.getRight());
			
			return (leftHeight > rightHeight) ?
				leftHeight + 1 : rightHeight + 1;
		}
	}
	
	private void setBalance(BSTNode<Integer> referenceNode) {
		referenceNode.setBalance(height(referenceNode.getLeft()) - 
				height(referenceNode.getRight()));
	}
	
	public String toString() {
		if (isEmpty()) {
			return "Empty tree";
		}
		else {
			return makeString(root, "");
		}
		
	}
	
	private String makeString(BSTNode<Integer> referenceNode, String s) {
		s += referenceNode.getElement();
		if (referenceNode.getLeft() != null) {
			String tabs = numberOfTabs(referenceNode.getLeft());			
			s += "\n" +  tabs + "left ";
			s = makeString(referenceNode.getLeft(), s);
		}
		
		if (referenceNode.getRight() != null) {
			String tabs = numberOfTabs(referenceNode.getRight());
			s += "\n" + tabs + "right ";
			s =  makeString(referenceNode.getRight(), s);
		}
			return s;
	}
	
	private String numberOfTabs(BSTNode<Integer> tabsNode) {
		String tabs = "";
		while(tabsNode.getFather() != null) {
			tabs += "  ";
			tabsNode = tabsNode.getFather();
		}
		return tabs;
	}
	
	public boolean binarySearch(int element) {
		return binarySearch(element, root);
	}
	
	private boolean binarySearch(int element, BSTNode<Integer> referenceNode) {
		do {
			if (element == referenceNode.getElement())
				return true;
			else if (element > referenceNode.getElement())
				referenceNode = referenceNode.getRight();
			else
				referenceNode = referenceNode.getLeft();
		} while (referenceNode != null);
		
		return false;
	}
	
	private BSTNode<Integer> binarySearchNode(int element, BSTNode<Integer> referenceNode) {
		
		do {
			if (element == referenceNode.getElement())
				return referenceNode;
			else if (element > referenceNode.getElement())
				referenceNode = referenceNode.getRight();
			else
				referenceNode = referenceNode.getLeft();
		} while (referenceNode != null);
		
		return null;
	}
	
	public void remove(int element) {
		BSTNode<Integer> node;
		
		if(isEmpty())
			node = null;
		else
			node = binarySearchNode(element, root);
		if(node != null) {
			removeNode(node);
			numElements--;
		}
		else
			System.out.println("Element not found!");
	}
	
	private void removeNode(BSTNode<Integer> node) {
		boolean isLeftFather = false;
		BSTNode<Integer> left = node.getLeft();
		BSTNode<Integer> right = node.getRight();
		BSTNode<Integer> father = node.getFather();
		
		try {
			if(father.getLeft() == node)
				isLeftFather = true;
		}
		catch(NullPointerException e) {}
		
		if(left == null && right == null) {
			if(father == null)
				root = null;
			else {
				if(isLeftFather)
					father.setLeft(null);
				else
					father.setRight(null);
			}
		}
		
		else {
			if(left == null) {
				if(father != null) {
					if(isLeftFather) 
						father.setLeft(right);
					else
						father.setRight(right);
				}
				else
					root = right;
				
				right.setFather(father);
			}
			else if(right == null) {
				if(father != null) {
					if(isLeftFather) 
						father.setLeft(left);
					else
						father.setRight(left);
				}
				else
					root = left;
				
				left.setFather(father);
			}
			else {
				if(father != null) {
					if(isLeftFather) {
						father.setLeft(left);
						BSTNode<Integer> mostRightNode = mostRightNode(father.getLeft());
						mostRightNode.setRight(right);
						right.setFather(mostRightNode);
						verifyBalance(node.getFather());
					}
					else {
						father.setRight(right);
						BSTNode<Integer> mostLeftNode = mostLeftNode(father.getRight());
						mostLeftNode.setRight(left);
						left.setFather(mostLeftNode);
						verifyBalance(node.getFather());
					}
				}
				else {
					root = left;
					BSTNode<Integer> mostRightNode = mostRightNode(left);
					mostRightNode.setRight(right);
					right.setFather(mostRightNode);
					verifyBalance(left);
				}
			}
			
		}
	}
	
	private BSTNode<Integer> mostRightNode(BSTNode<Integer> referenceNode){
		if(referenceNode.getRight() == null)
			return referenceNode;
		else
			return mostRightNode(referenceNode.getRight());
	}
	
	private BSTNode<Integer> mostLeftNode(BSTNode<Integer> referenceNode){
		if(referenceNode.getLeft() == null)
			return referenceNode;
		else
			return mostRightNode(referenceNode.getLeft());
	}
}