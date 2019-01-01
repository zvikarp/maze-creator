package mazeCreatorPackege_01;

public class Cube {
	private boolean up;
	private boolean down;
	private boolean right;
	private boolean left;

	Cube() {
		setAll(true);
	}
	Cube(boolean val ){
		up = val;
		right =  val;
		down =  val;
		left =  val;
	}
	Cube(Cube cube) {
		up = cube.getUp();
		right = cube.getRight();
		down = cube.getDown();
		left = cube.getLeft();
	}

	boolean getUp() {
		return up;
	}

	boolean getDown() {
		return down;

	}

	boolean getRight() {
		return right;
	}

	boolean getLeft() {
		return left;
	}

	void setUp(boolean val) {
		up = val;
	}

	void setDown(boolean val) {
		down = val;
	}

	void setRight(boolean val) {
		right = val;
	}

	void setLeft(boolean val) {
		left = val;
	}

	void setAll(boolean val) {
		up = val;
		right = val;
		down = val;
		left = val;
	}

	void setAll(Cube cube) {
		up = cube.getUp();
		right = cube.getRight();
		down = cube.getDown();
		left = cube.getLeft();
	}

	void setSide(int i, boolean val) {
		i = i % 4;
		if (i == 0) {
			up = val;
		} else if (i == 1) {
			right = val;
		} else if (i == 2) {
			down = val;
		} else {
			left = val;
		}

	}

	boolean checkAll(boolean val) {
		if (val)
			return (up && down && right && left);
		else
			return (!up && !down && !right && !left);
	}

	boolean getSide(int i) {
		i = i % 4;
		if (i == 0)
			return up;
		if (i == 1)
			return right;
		if (i == 2)
			return down;
		return left;
	}

	int randomSideFromCube(Cube cube) {
		int i = -1;
		if (up)
			i++;
		// if (right) i++;
		if (down)
			i++;
		if (left)
			i++;

		int side = (int) (Math.random() % i);

		for (int j = 0; j < 4; j++) {
			if (!getSide(j))
				side++;
			else if (j == side)
				break;
		}
		return side;
	}
	
	int cubeToInt() {
		int i = 0;
		for (int j = 0; j < 4; j++) {
			if (getSide(j))
				i++;
		}
		return i;
	}

	void printCube() {
		System.out.println("[" + up + " " + right + " " + down + " " + left + "]");
	}
}
