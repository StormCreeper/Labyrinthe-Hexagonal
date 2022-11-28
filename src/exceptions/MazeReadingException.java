package exceptions;

public class MazeReadingException extends Exception {
	
	private static final long serialVersionUID = 2402331259296389686L;

	public MazeReadingException(String file, int line, String msg) {
		super("At line " + line + " in file " + file + " : " + msg);
	}

}
