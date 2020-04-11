
public class DuplicatedKeyException extends Exception {
	public DuplicatedKeyException() {
		System.out.println("DuplicatedKeyException: you cannot add equal keys to the tree");
	}
}
