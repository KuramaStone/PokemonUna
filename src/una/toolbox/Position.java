package una.toolbox;

public class Position {
	
	private int x, y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void addX(int i) {
		x += i;
	}
	
	public void addY(int i) {
		y += i;
	}
	
	public void subtract(Position a) {
		this.x -= a.x;
		this.y -= a.y;
	}
	
	public void add(Position a) {
		this.x += a.x;
		this.y += a.y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
