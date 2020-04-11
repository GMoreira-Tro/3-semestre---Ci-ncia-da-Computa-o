
public interface AVLInterface {
	public boolean isEmpty();
	public int numElements();
	public void insert(int element) throws DuplicatedKeyException;
	public void remove(int element);
	public boolean binarySearch (int element);
}
