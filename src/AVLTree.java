import java.util.ArrayList;
import java.util.Date;

interface DelegatedCompareTo<T> {
	int compareTo(Object element, Object other);
}

interface DelegatedString<T> {
	String delegatedString(T element); 
}

interface DelegatedDate<T> {
	Date delegatedDate(T element);
}

interface DelegatedAttribute<T, E> {
	Comparable<E> delegatedAttribute(T element);
}

public class AVLTree<T> implements AVLInterface<T> {
	BSTNode<T> root;
	int numElements;
	DelegatedCompareTo<T> delegate;
	
	public AVLTree() {
		root = null;
		numElements = 0;
		delegate = (Object element, Object other) -> {
			return ((Comparable<T>)element).compareTo((T) other);
		};
	}
	
	public AVLTree(DelegatedCompareTo<T> delegate) {
		root = null;
		numElements = 0;
		this.delegate = delegate;
	}
	
	public int numElements() {
		return numElements;
	}
	
	public boolean isEmpty() {
		return numElements == 0;
	}
	
	public void insert(T element) throws DuplicatedKeyException {
		BSTNode<T> newNode = new BSTNode<T>(element);
		
		if(isEmpty()) {
			root = newNode;
		}
		else {
			insert(newNode, root);		
		}
		numElements++;
	}
	
	private void insert(BSTNode<T> newNode, BSTNode<T> referenceNode) throws DuplicatedKeyException {
		if(delegate.compareTo((Object)newNode.getElement(),referenceNode.getElement()) > 0) {
			if(referenceNode.getRight() == null) {
				referenceNode.setRight(newNode);
				newNode.setFather(referenceNode);
				verifyBalance(referenceNode);
			}
			else {
				insert(newNode,referenceNode.getRight());
			}
		}
		else if (delegate.compareTo((Object)newNode.getElement(),referenceNode.getElement()) < 0){
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
	
	public void insertButCanDuplicateKeys(T element) {
		BSTNode<T> newNode = new BSTNode<T>(element);
		
		if(isEmpty()) {
			root = newNode;
		}
		else {
			insertButCanDuplicateKeys(newNode, root);		
		}
		numElements++;
	}
	
	private void insertButCanDuplicateKeys(BSTNode<T> newNode, BSTNode<T> referenceNode) {
		if(delegate.compareTo((Object)newNode.getElement(),referenceNode.getElement()) >= 0) {
			if(referenceNode.getRight() == null) {
				referenceNode.setRight(newNode);
				newNode.setFather(referenceNode);
				verifyBalance(referenceNode);
			}
			else {
				insertButCanDuplicateKeys(newNode,referenceNode.getRight());
			}
		}
		else {
			if(referenceNode.getLeft() == null) {
				referenceNode.setLeft(newNode);
				newNode.setFather(referenceNode);
				verifyBalance(referenceNode);
			}
			else {
				insertButCanDuplicateKeys(newNode,referenceNode.getLeft());
			}
		}
	}
	
	public void verifyBalance(BSTNode<T> referenceNode) {
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
	
	private BSTNode<T> leftRotation(BSTNode<T> referenceNode){
		
		BSTNode<T> right = referenceNode.getRight();
		right.setFather(referenceNode.getFather());
		referenceNode.setRight(right.getLeft());
		
		try {
			referenceNode.getRight().setFather(referenceNode);
		} catch(NullPointerException e) {}
		
		right.setLeft(referenceNode);
		referenceNode.setFather(right);
		
		BSTNode<T> grandfather = right.getFather();
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
	
	private BSTNode<T> rightRotation(BSTNode<T> referenceNode){
		BSTNode<T> left = referenceNode.getLeft();
		left.setFather(referenceNode.getFather());
		referenceNode.setLeft(left.getRight());
		
		try {
			referenceNode.getLeft().setFather(referenceNode);
		} catch(NullPointerException e) {}
		
		left.setRight(referenceNode);
		referenceNode.setFather(left);
		
		BSTNode<T> grandfather = left.getFather();
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
	
	private BSTNode<T> doubleLeftRotation(BSTNode<T> referenceNode){
		referenceNode.setRight(rightRotation(referenceNode.getRight()));
		return leftRotation(referenceNode);
	}
	
	private BSTNode<T> doubleRightRotation(BSTNode<T> referenceNode){
		referenceNode.setLeft(leftRotation(referenceNode.getLeft()));
		return rightRotation(referenceNode);
	}
	
	private int height(BSTNode<T> referenceNode) {
		if(referenceNode == null)
			return -1;
		
		else if(referenceNode.getLeft() == null && referenceNode.getRight() == null) //sem filhos
		{
			return 0;
		}
		
		else if(referenceNode.getRight() == null) //só tem esquerda
		{
			return 1 + height(referenceNode.getLeft());
		}
		
		else if(referenceNode.getLeft() == null) //só tem direita
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
	
	private void setBalance(BSTNode<T> referenceNode) {
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
	
	private String makeString(BSTNode<T> referenceNode, String s) {
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
	
	private String numberOfTabs(BSTNode<T> tabsNode) {
		String tabs = "";
		while(tabsNode.getFather() != null) {
			tabs += "  ";
			tabsNode = tabsNode.getFather();
		}
		return tabs;
	}
	
	public boolean binarySearch(Comparable<T> element) {
		return binarySearch(element, root);
	}
	
	private boolean binarySearch(Comparable<T> element, BSTNode<T> referenceNode) {
		do {
			if (delegate.compareTo(element,referenceNode.getElement()) == 0)
				return true;
			else if (delegate.compareTo(element,referenceNode.getElement()) > 0)
				referenceNode = referenceNode.getRight();
			else
				referenceNode = referenceNode.getLeft();
		} while (referenceNode != null);
		
		return false;
	}
	
	public Comparable<T> binarySearchElement(Comparable<T> element) {
		return binarySearchElement(element, root);
	}
	
	private Comparable<T> binarySearchElement(Comparable<T> element, BSTNode<T> referenceNode) {
		do {
			if (delegate.compareTo(element,referenceNode.getElement()) == 0)
				return element;
			else if (delegate.compareTo(element,referenceNode.getElement()) > 0)
				referenceNode = referenceNode.getRight();
			else
				referenceNode = referenceNode.getLeft();
		} while (referenceNode != null);
		
		return null;
	}
	
	public <E> boolean binarySearchByAttribute(E element, DelegatedAttribute<T, E> del) {
		return binarySearchByAttribute(element, root, del);
	}
	
	private <E> boolean binarySearchByAttribute(E element, BSTNode<T> referenceNode,
			DelegatedAttribute<T, E> del) {
		do {
			if (del.delegatedAttribute(referenceNode.getElement()).compareTo(element) == 0)
				return true;
			else if (del.delegatedAttribute(referenceNode.getElement()).compareTo(element) < 0)
				referenceNode = referenceNode.getRight();
			else
				referenceNode = referenceNode.getLeft();
		} while (referenceNode != null);
		
		return false;
	}
	
	public <E> T binarySearchElementByAttribute(E element, DelegatedAttribute<T, E> del) {
		return binarySearchElementByAttribute(element, root, del);
	}
	
	private <E> T binarySearchElementByAttribute(E element, BSTNode<T> referenceNode
			, DelegatedAttribute<T, E> del) {
		do {
			//System.out.println(del.delegatedAttribute(referenceNode.getElement()));
			if (del.delegatedAttribute(referenceNode.getElement()).compareTo(element) == 0)
				return referenceNode.getElement();
			else if (del.delegatedAttribute(referenceNode.getElement()).compareTo(element) < 0)
				referenceNode = referenceNode.getRight();
			else
				referenceNode = referenceNode.getLeft();
		} while (referenceNode != null);
		
		return null;
	}
	
	private BSTNode<T> binarySearchNode(T element, BSTNode<T> referenceNode) {
		
		do {
			if (delegate.compareTo(element,referenceNode.getElement()) == 0)
				return referenceNode;
			else if (delegate.compareTo(element,referenceNode.getElement()) > 0)
				referenceNode = referenceNode.getRight();
			else
				referenceNode = referenceNode.getLeft();
		} while (referenceNode != null);
		
		return null;
	}
	
	private <E> BSTNode<T> binarySearchNodeByAttribute(E element, BSTNode<T> referenceNode,
			DelegatedAttribute<T, E> del) {
		
		do {
			if (del.delegatedAttribute(referenceNode.getElement()).compareTo(element) == 0)
				return referenceNode;
			else if (del.delegatedAttribute(referenceNode.getElement()).compareTo(element) < 0)
				referenceNode = referenceNode.getRight();
			else
				referenceNode = referenceNode.getLeft();
		} while (referenceNode != null);
		
		return null;
	}
	
	public void remove(T element) {
		BSTNode<T> node;
		
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
	
	public <E> void removeByAttribute(E element, DelegatedAttribute<T, E> del) {
		BSTNode<T> node;
		
		if(isEmpty())
			node = null;
		else
			node = binarySearchNodeByAttribute(element, root, del);
		if(node != null) {
			removeNode(node);
			numElements--;
		}
		else
			System.out.println("Element not found!");
	}
	
	private void removeNode(BSTNode<T> node) {
		BSTNode<T> left = node.getLeft();
		BSTNode<T> right = node.getRight();
		BSTNode<T> father = node.getFather();
		
		if(left == null && right == null) {
			if(father != null)
			{
				boolean isLeftFather = (father.getLeft() == node);
				if(isLeftFather)
					father.setLeft(null);
				else
					father.setRight(null);
				verifyBalance(father);
			}
		}
		
		else {
			if(left == null) {
				if(father != null) {
					boolean isLeftFather = (father.getLeft() == node);
					if(isLeftFather) {
						father.setLeft(right);
						right.setFather(father);
					}
					else {
						father.setRight(right);
						right.setFather(father);
					}
				}
				else {
					root = right;
					right.setFather(null);
				}
				verifyBalance(right);
			}
			else if(right == null) {
				if(father != null) {
					boolean isLeftFather = (father.getLeft() == node);
					if(isLeftFather) {
						father.setLeft(left);
						left.setFather(father);
					}
					else {
						father.setRight(left);
						left.setFather(father);
					}
				}
				else {
					root = left;
					left.setFather(null);
				}
				verifyBalance(left);
			}
			else {
				if(father != null) {
					boolean isLeftFather = (father.getLeft() == node);
					if(isLeftFather) {
						father.setLeft(left);
						left.setFather(father);
						BSTNode<T> mostRightNode = mostRightNode(father.getLeft());
						mostRightNode.setRight(right);
						right.setFather(mostRightNode);
						verifyBalance(node.getFather());
					}
					else {
						father.setRight(right);
						right.setFather(father);
						BSTNode<T> mostLeftNode = mostLeftNode(father.getRight());
						mostLeftNode.setLeft(left);
						left.setFather(mostLeftNode);
						verifyBalance(node.getFather());
					}
				}
				else {
					root = left;
					left.setFather(null);
					BSTNode<T> mostRightNode = mostRightNode(left);
					mostRightNode.setRight(right);
					right.setFather(mostRightNode);
					verifyBalance(left);
				}
			}
			
		}
		node = null;
	}
	
	private BSTNode<T> mostRightNode(BSTNode<T> referenceNode){
		if(referenceNode.getRight() == null)
			return referenceNode;
		else
			return mostRightNode(referenceNode.getRight());
	}
	
	private BSTNode<T> mostLeftNode(BSTNode<T> referenceNode){
		if(referenceNode.getLeft() == null)
			return referenceNode;
		else
			return mostRightNode(referenceNode.getLeft());
	}
	
	public ArrayList<T> toArrayList() {
		try {
		ArrayList<T> list = toArrayList(new ArrayList<T>(), root);
		 list.sort(null);
		 return list;
		}
		catch (NullPointerException e) {
			return new ArrayList();
		}
	}
	
	private ArrayList<T> toArrayList(ArrayList<T> list, BSTNode<T> referenceNode){
		list.add(referenceNode.getElement());
		
		if (referenceNode.getLeft() != null) {	
			 toArrayList(list,referenceNode.getLeft());
		}
		
		if (referenceNode.getRight() != null) {
			toArrayList(list,referenceNode.getRight());
		}
			return list;
	}
	
	public ArrayList<T> toArrayList(DelegatedString<T> delegate, String string) {
		try {
			ArrayList<T> list = toArrayList(new ArrayList<T>(), root, delegate, string);
			return list;
		}
		catch (NullPointerException e) {
			return new ArrayList();
		}
	}
	
	private ArrayList<T> toArrayList(ArrayList<T> list, BSTNode<T> referenceNode, 
			DelegatedString<T> delegate, String string) {
		
		String referenceString = delegate.delegatedString(referenceNode.getElement());
		System.out.println(referenceString);
		int compareInt = referenceString.compareTo(string);
		
		boolean startWith = referenceString.startsWith(string);
		if (startWith) {
			list.add(referenceNode.getElement());
		}
			
		if (referenceNode.getLeft() != null) {
			if(referenceString.compareTo(string) > 0) {
				toArrayList(list,referenceNode.getLeft(),delegate,string);
			}
		}
		
		if (referenceNode.getRight() != null) {
			if (startWith || compareInt < 0) {
				 toArrayList(list,referenceNode.getRight(), delegate, string);
			}
		}
		
		return list;
	}
	
	public ArrayList<T> toArrayList(DelegatedDate<T> delegate, Date startDate, Date endDate) {
		try {
			ArrayList<T> list = toArrayList(new ArrayList<T>(), root, delegate, startDate, endDate);
			return list;
		}
		catch (NullPointerException e) {
			return new ArrayList();
		}
	}
	
	private ArrayList<T> toArrayList(ArrayList<T> list, BSTNode<T> referenceNode, 
			DelegatedDate<T> delegate, Date startDate, Date endDate) {
		
		Date referenceDate = delegate.delegatedDate(referenceNode.getElement());
		
		if (endDate.compareTo(startDate) <= 0)
			throw new IllegalArgumentException();
		
		while (referenceNode != null) {
			
			if (referenceDate.compareTo(startDate) > 0 && referenceDate.compareTo(endDate) < 0) {
				list.add(referenceNode.getElement());
			}
			
			if (referenceDate.compareTo(startDate) >= 0) {
				referenceNode = referenceNode.getRight();
			}
			else {
				referenceNode = referenceNode.getLeft();
			}
		} 
		
		return list;
	}
}
