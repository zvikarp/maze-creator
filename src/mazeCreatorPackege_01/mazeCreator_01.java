package mazeCreatorPackege_01;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
//import java.awt.Font;
//import java.awt.FontMetrics;
//import java.awt.geom.Ellipse2D;
//import java.awt.GradientPaint;
//import java.awt.BasicStroke;
//import java.util.*;

public class mazeCreator_01 {
	
	static int counter = 1000;
	
	static Cube findStartingPoint(Position p) {
		Cube startingPoint=new Cube();
		
		int side = (int) (Math.random()* 4);
		System.out.println(side);
		if (side == 0) {
			startingPoint.setUp(false);
			p.setX((int) (Math.random()* p.getX())); 
			p.setY(0); 
		} else if (side == 1) {
			startingPoint.setRight(false);
			p.setY((int) (Math.random()* p.getY())); 
			p.setX(p.getX()); 
		} else if (side == 2) {
			startingPoint.setDown(false);
			p.setX((int) (Math.random()* p.getX())); 
			p.setY(p.getY()); 
		} else {
			startingPoint.setLeft(false);
			p.setY((int) (Math.random()* p.getY())); 
			p.setX(0); 
		}
		
		System.out.println(p.getX() + " " + p.getY());
	
		return startingPoint;
	}
	
//	static Cube nextStepOptions(Position p,Cube ground[][]) {
//		Cube cube = new Cube(ground[p.getY()][p.getX()]);
//	
//		System.out.println("printing cube: "); 
//		if(cube!=null)
//		cube.printCube();
//	
//		if (cube.getUp()) {
//			if (p.getY() == 0)
//				cube.setUp(false);
//			else {
//				cube.setUp(ground[p.getY() - 1][p.getX()].checkAll(true) && (p.getY() != 0));
//	//			if (cube.getUp()) {
//	//				cube.setUp((ground[p.getY() - 1][p.getX()+1].checkAll(true)) || (ground[p.getY() - 1][p.getX()-1].checkAll(true)));
//	//			}
//				}
//		}
//		if (cube.getRight()) {
//			if (p.getX() == p.getCol()-1)
//				cube.setRight(false);
//			else {
//				cube.setRight(ground[p.getY()][p.getX() + 1].checkAll(true) && (p.getX() != p.getCol()-1));
//	//			if (cube.getRight()) {
//	//				cube.setRight((ground[p.getY() + 1][p.getX()+1].checkAll(true)) || (ground[p.getY() - 1][p.getX()+1].checkAll(true)));
//	//			}
//			}
//		}
//		if (cube.getDown()) {
//			if (p.getY() == p.getRow()-1)
//				cube.setDown(false);
//			else {
//				cube.setDown(ground[p.getY() + 1][p.getX()].checkAll(true) && (p.getY() != p.getRow()-1));
//	//			if (cube.getDown()) {
//	//				cube.setDown((ground[p.getY() + 1][p.getX()+1].checkAll(true)) || (ground[p.getY() + 1][p.getX()-1].checkAll(true)));
//	//			}
//			}
//		}
//		if (cube.getLeft()) {
//			if (p.getX() == 0)
//				cube.setLeft(false);
//			else {
//				cube.setLeft(ground[p.getY()][p.getX() - 1].checkAll(true) && (p.getX() != 0));
//				if (cube.getDown()) {
//				cube.setLeft((ground[p.getY() + 1][p.getX()-1].checkAll(true)) || (ground[p.getY() - 1][p.getX()+1].checkAll(true)));
//				}
//			}
//		}
//		
//		return cube;
//	}
	
	static Cube nextStepOptions(Position p,Cube ground[][], int level) {
		level--;
		Cube cube = new Cube(false);
		Cube origCube = new Cube(false);
		Position editablePosition = new Position(p);
		System.out.println("t= " + p.getT() + "l= " + level);
		
		for (int k = 0; k < 4; k++) {
			
			int x = p.getX() + (k % 2) - (2 * (k / 3));
			int y = p.getY() + ((k + 1) % 2) - (2 * ((3 - k) / 3));
//			System.out.println("k" + k + ", k1 " + ((k % 2) - (2 * (k / 3)) + ", k2 " + (((k + 1) % 2) - (2 * ((3 - k) / 3)))));
//			System.out.println("level " + level + ", x " + x + ", y " + y);
			
			if ( (x < p.getCol()) && (x > -1) && (y < p.getRow()) && (y > -1) ) {
				cube.setSide(k, ground[y][x].checkAll(true));
				origCube.setSide(k, ground[y][x].checkAll(true));
				
				
				if ((cube.getSide(k)) && (level > 0)) {
					editablePosition.setX(x);
					editablePosition.setY(y);
					Cube testCube = new Cube(nextStepOptions(editablePosition, ground, level));
					System.out.println("cube of x " + x + ", y " + y);
					testCube.printCube();
					int num = (testCube).cubeToInt();
//					System.out.println("level " + level + ", num " + num);
					if (num < 2)
						cube.setSide(k, false);
				}
				
			}
		}
		
		if (cube.checkAll(false)) {
			cube.setAll(origCube);
		}
		int turn = p.getT();
		
		if ((turn > 1) || (turn < -1)) {
			if (turn > 1 )
				turn = 1;
			else
				turn = -1;
			System.out.println("--------------------------------------------");
			System.out.println("t= " + p.getT() + " x= " + p.getX() + " px= " + p.getPX() + " y= " + p.getY() + " py= " + p.getPY());
			System.out.println("--------------------------------------------");
			if (p.getPX() == p.getX()) {
				if (p.getPY() > p.getY())
					cube.setSide(4-turn, false);
				else
					cube.setSide(4+turn, false);
			}
			else {
				if (p.getPX() > p.getX())
					cube.setSide(3-turn, false);
				else
					cube.setSide(3+turn, false);
			}
		}

//		if (cube.checkAll(false))
//			return origCube;
		return cube;
	}
	
	static void print(Cube ground[][],Position p) {
		for (int i = 0; i < p.getRow(); i++) {
			for (int j = 0; j < p.getCol(); j++) {
	//			System.out.print( (ground[i][j].checkAll(true)?1:0) + "  ");
				System.out.print( (ground[i][j].getUp()?1:0) + "");
				System.out.print( (ground[i][j].getRight()?1:0) + "");
				System.out.print( (ground[i][j].getDown()?1:0) + "");
				System.out.print( (ground[i][j].getLeft()?1:0) + "  ");
			}
			System.out.println("\n" );
		}
	}
	
//	static int nextOptions(Position p,Cube ground[][]) {
//		
//	int counter=0;
//		
//		Cube optionsUp =new Cube(false);
//		int y=p.getY();
//		if(p.getY()-1>0) {
//			p.setY(p.getY()-1);
//			optionsUp = nextStepOptions(p, ground, 2);
//		}
//		
//		if(optionsUp.checkAll(true)) {
//			counter++;
//		}
//		p.setY(y);
//		Cube optionsDown = new Cube(false);
//		if(p.getY()+1<p.getRow()) {
//			p.setY(p.getY()+1);
//			optionsDown = nextStepOptions(p, ground, 2);
//		}
//		if(optionsDown.checkAll(true)) {
//			counter++;
//		}
//		p.setY(y);
//	
//		int x=p.getX();
//		Cube optionsLeft =new Cube(false);
//	
//		if(p.getX()-1>0) {
//			p.setY(p.getX()-1);
//			optionsLeft = nextStepOptions(p, ground, 1);
//		}
//		
//		if(optionsLeft.checkAll(true)) {
//			counter++;
//		}// the possion 
//		p.setX(x);
//	
//		Cube optionsRight = new Cube(false);
//		if(p.getX()+1<p.getCol()) {
//			p.setX(p.getX()+1);
//			optionsRight = nextStepOptions(p, ground);
//		}
//		if(optionsRight .checkAll(true)) {
//			counter++;
//		}
//		p.setX(x);
//	
//		return counter;
//	}
	
	static void stepOLD(Position p,Cube ground[][]) {
		System.out.println( " ");
		p.print();
		Cube options = nextStepOptions(p, ground, 3);
		p.print();
		options.printCube();
		
		int point=0;
		for(int i=0 ;i<4;i++) {
			if(options.getSide(i))
			point++;	
		}
	
		if (options.checkAll(false)) {
			System.out.println ("got stuck!" );
			return;
		}
		point = (int)(Math.random()*point);
		for (int k = 0; k < 4; k++) {
			if (!options.getSide(k))
				continue;
			else if (point == 0) {
				point = k;
				break;
			}
				
			else 
				point --;
		}
//		System.out.println ("new point is" + point );
	
		ground[p.getY()][p.getX()].setSide(point, false);
	
		if (((p.getPX() == p.getX()) && (point%2 == 0)) ||
			 (p.getPY() == p.getY()) && (point%2 == 1)) {
			
		}
		else if ((p.getPX() > p.getX()) ||
				 (p.getPY() > p.getY())) {
				p.turnT(point < 2);
		}
		else {
				p.turnT(point >= 2);
		}
		
		p.setPX(p.getX());
		p.setPY(p.getY());
		
		if (point == 0)
			p.setY(p.getY()-1);
		else if (point == 1)
			p.setX(p.getX()+1); 
		else if (point == 2)
			p.setY(p.getY()+1) ;
		else if (point == 3)
			p.setX(p.getX()-1); 
	
		System.out.println("p.getY " + p.getY() + ", p.getX " + p.getX()) ;
		ground[p.getY()][p.getX()].setSide((point+2)%4, false);
		//print(ground,p);
		stepOLD(p, ground);
	
	}
	
	static void step(Position p,Cube ground[][]) {

	
		boolean notTuched = true;
		
		p.setPX(p.getX());
		p.setPY(p.getY());
		

		boolean inBoard = false;
		int point = 0;
		while (!inBoard) {
			point = (int)(Math.random()*4);
			inBoard = true;
			if ((point == 0) && (p.getY() > 0) )
				p.setY(p.getY()-1);
			else if ((point == 1) && (p.getX()+1 < p.getCol()))
				p.setX(p.getX()+1); 
			else if ((point == 2) && (p.getY()+1 < p.getRow()))
				p.setY(p.getY()+1);
			else if ((point == 3) && (p.getX() > 0))
				p.setX(p.getX()-1);
			else
				inBoard = false;
			
		}
		
//		System.out.println("c: " + counter + " point " + point + ", p.getY " + p.getY() + ", p.getX " + p.getX()) ;
		notTuched = ground[p.getY()][p.getX()].checkAll(true);
		
		if (notTuched)
			ground[p.getPY()][p.getPX()].setSide(point, false);
		ground[p.getY()][p.getX()].setSide((point+2)%4, false);
		//print(ground,p);
		counter --;
		if (counter > 0)
			step(p, ground);
	
	}

	static void build(Cube[][] ground , Position p) {
		try {
		int width = p.getCol()*10;
		int height = p.getRow()*10;
	
		// create the board
	    BufferedImage maze = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D board = maze.createGraphics();
	
	    // set the background color to be white
	    board.setPaint(Color.white);
	    board.fillRect(0, 0, width, width);
	    
	    // set the paint brash to be black
	    board.setPaint(Color.black);
	
	    for (int i=0 ;i<p.getRow();i++) {
	    	if(ground[0][i].getUp()) {
//	    		System.out.println(10*i+",0,"+10*(i+1)+",0");
	    		  board.drawLine(10*i,0,10*(i+1),0);
	    	}
	    }
//	System.out.println(" ");
	    for (int i=0 ;i<p.getCol();i++) {
	    	if(ground[i][0].getLeft()) {
//	    		System.out.println("0," + 10*i+",0,"+10*(i+1));
	    		  board.drawLine(0, 10*i,0,10*(i+1));
	    	}
	    }
	
	    for (int i=0 ;i<p.getRow();i++) {
	        for (int j=0 ;j<p.getCol();j++) {
//	        	if(ground[i][j].getLeft()) {
//	      			board.drawLine(10*i, 10*(j+1),10*(i+1),10*(j+1));
//	        	}
	        	if(ground[i][j].getDown()) {
	        		board.drawLine(10*(i+1),10*j,10*(i+1),10*(j+1));
	        	}
	        	if(ground[i][j].getRight()) {
	      			board.drawLine(10*i, 10*(j+1),10*(i+1),10*(j+1));
	        	}
//	        	if(ground[i][j].getUp()) {
//	      			board.drawLine(10*i, 10*j,10*i,10*(j+1));
//	        	}
	        }
	    }
	    // draw lines
		ImageIO.write(maze, "PNG", new File("image.png"));
		File file = new File ("C:\\Users\\zvi\\Documents\\TBS\\Mazesplace\\Maze-Creator\\MazeCreator_01\\image.png");
		Desktop desktop = Desktop.getDesktop();
		desktop.open(file);
		}
	    catch (IOException ie) {
	    	System.out.println("fhfghf");
			ie.printStackTrace();
		}
	
	}

	public static void main(String[] args) {
		
		Position p= new Position(100,100, 99, 99);
		Cube[][] ground = new Cube[p.getRow()][p.getCol()];
		for (int i = 0; i < p.getRow(); ++i) {
			for (int j = 0; j < p.getCol(); j++) {
				ground[i][j] = new Cube();
			}
		}
		//p.setY(p.getRaw()-1); 
		//p.setX(p.getCol()-1);

		ground[p.getY()][p.getX()] = findStartingPoint(p);
		step(p, ground);

//		print(ground,p);

		build(ground,p);
		
		System.out.println("finished!!!" + p.getT());
		
	}

}
