package mazeCreatorPackege_01;

public class Position {
	private	int col;
	private	int row;
	private	int x;
	private	int y;
	private	int px;
	private	int py;
	private int t = 0;
	
	public Position (int col, int row, int y,int x) {
		this.col=col;
		this.row=row;
		this.x=x;
		this.y=y;
	}
	
	public Position (Position position) {
		this.col = position.getCol();
		this.row = position.getRow();
		this.x = position.getX();
		this.y = position.getY();
		this.px = position.getPX();
		this.py = position.getPY();
		this.t = position.getT();
	}
	
	public int getCol() {
		return col;
	}
	public int getRow() {
		return row;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getPX() {
		return px;
	}
	public int getPY() {
		return py;
	}
	public int getT() {
		return t;
	}
	public void setY(int y) {
		this.y=y;
	}
	public void setX(int x) {
		this.x=x;
	}
	public void setPY(int py) {
		this.py=py;
	}
	public void setPX(int px) {
		this.px=px;
	}
	public void setCol(int col) {
		this.col=col;
	}
	public void setRow(int row) {
		this.row=row;
	}
	public void setT(int t) {
		this.t=t;
	}
	public void turnT(boolean t) {
		if (t)
			this.t--;
		else
			this.t++;
	}
	public boolean t3() {
		if ((t > 2) || (t < -2))
			return false;
		return true;
	}
	
	public void print() {
		System.out.println("row: " + row + ", col: " + col + ", x: " + x + ", y: " + y);
	}
}
