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

public class mazeCreator_02 {
	
	static int counter = 1000;
	
	static boolean colorIntTOBoolean(int color) {
		int blue = color & 0xff;
    	int green = (color & 0xff00) >> 8;
    	int red = (color & 0xff0000) >> 16;
    	
    	if ((blue+green+red) < 122) {
    		return true;    		
    	}
    	return false;
	}
	
	static Cube[][] imgToGround(Position p, Position start, Position end) {
		BufferedImage img = null;
	    File f = null;
	    //read image
	    try{
	    	
	    	int iWidth, iHeight, bWidth, bHeight;
	    	
	    	final boolean SOLVE = false;
	    	
	    	if (SOLVE) {
	    		
	    		BufferedImage answerImg = null;
	    	    File answer = null;

	    		answer = new File("C:\\Users\\zvi\\Documents\\TBS\\Mazesplace\\Maze-Creator\\MazeCreator_01\\image.jpg");
	    		answerImg = ImageIO.read(answer);
		    	
		    	iWidth = answerImg.getWidth();
		    	iHeight = answerImg.getHeight();
		    	
		    	bWidth = iWidth/10;
		    	bHeight = iHeight/10;
		    	
		    	System.out.println("height " + bHeight + " width " + bWidth);
	    	}
	    	
	    	
	    	else {

		    	f = new File("C:\\Users\\zvi\\Documents\\TBS\\Mazesplace\\Maze-Creator\\MazeCreator_01\\import.png");
//	    		f = new File("C:\\Users\\zvi\\Desktop\\image.png");
		    	img = ImageIO.read(f);
		    	
		    	iWidth = img.getWidth();
		    	iHeight = img.getHeight();
		    	
		    	bWidth = iWidth/10;
		    	bHeight = iHeight/10;
		    	
		    	System.out.println("height " + bHeight + " width " + bWidth);
	    	}
	    	
	    	p.setCol(bHeight);
	    	p.setRow(bWidth);
	    	start.setCol(bHeight);
	    	start.setRow(bWidth);
	    	end.setCol(bHeight);
	    	end.setRow(bWidth);
	    	
	    	start.setX(-1);
	    	
	    	Cube[][] iGround = new Cube[bWidth][bHeight];
			for (int i = 0; i < bWidth; ++i) {
				for (int j = 0; j < bHeight; j++) {
					boolean color = colorIntTOBoolean(img.getRGB(i*10+5,j*10+5));
					iGround[i][j] = new Cube(color);
					if (color) {
						if (start.getX() == -1){
							p.setY(i);
							p.setX(j);
							start.setY(i);
							start.setX(j);
						}
						end.setY(i);
						end.setX(j);
					}
//					iGround[i][j] = new Cube();
				}
			}
			return iGround;
	    }catch(IOException e){
	    	System.out.println(e);
	    }
	    
	    return new Cube[0][0];
	}
	
	static Cube findStartingPoint(Position p) {
		Cube startingPoint=new Cube();
		
		int side = (int) (Math.random()* 4);
		System.out.println(side);
		if (side == 0) {
			startingPoint.setUp(false);
			p.setX((int) (Math.random()* (p.getCol()-1))); 
			p.setY(0); 
		} else if (side == 1) {
			startingPoint.setRight(false);
			p.setY((int) (Math.random()* (p.getRow()-1))); 
			p.setX(p.getX()); 
		} else if (side == 2) {
			startingPoint.setDown(false);
			p.setX((int) (Math.random()* (p.getCol()-1))); 
			p.setY(p.getY()); 
		} else {
			startingPoint.setLeft(false);
			p.setY((int) (Math.random()*(p.getRow()-1))); 
			p.setX(0); 
		}
		
//		System.out.println(p.getX() + " " + p.getY());
	
		return startingPoint;
	}
	

	
	static Cube nextStepOptions(Position p,Cube ground[][], int level) {
		level--;
		Cube cube = new Cube(false);
		Cube origCube = new Cube(false);
		Position editablePosition = new Position(p);
		
		// dirty code
		if (ground[p.getY()][p.getX()].checkAll(false)) {
			return cube;
		}
//		System.out.println("t= " + p.getT() + "l= " + level);
		
		for (int k = 0; k < 4; k++) {
			
			int x = p.getX() + (k % 2) - (2 * (k / 3));
			int y = p.getY() + ((k + 1) % 2) - (2 * ((3 - k) / 3));

			
			if ( (x < p.getCol()) && (x > -1) && (y < p.getRow()) && (y > -1) ) {
				cube.setSide(k, ground[y][x].checkAll(true));
				origCube.setSide(k, ground[y][x].checkAll(true));
				
				
				if ((cube.getSide(k)) && (level > 0)) {
					editablePosition.setX(x);
					editablePosition.setY(y);
					Cube testCube = new Cube(nextStepOptions(editablePosition, ground, level));
//					System.out.println("cube of x " + x + ", y " + y);
//					testCube.printCube();
					int num = testCube.cubeToInt();

					if (num < 2)
						cube.setSide(k, false);
				}
				
			}
		}
		
		if (cube.checkAll(false)) {
			cube.setAll(origCube);
		}
	
		return cube;
	}
	
	static void print(Cube ground[][],Position p) {
		for (int i = 0; i < p.getRow(); i++) {
			for (int j = 0; j < p.getCol(); j++) {
//				System.out.print( (ground[i][j].getUp()?1:0) + "");
//				System.out.print( (ground[i][j].getRight()?1:0) + "");
//				System.out.print( (ground[i][j].getDown()?1:0) + "");
//				System.out.print( (ground[i][j].getLeft()?1:0) + "  ");
			}
//			System.out.println("\n" );
		}
	}
	

	
	static void step(Position p,Cube ground[][]) {
//		System.out.println( " ");
//		p.print();
		Cube options = nextStepOptions(p, ground, 3);
//		p.print();
//		options.printCube();
		
		int point=0;
		for(int i=0 ;i<4;i++) {
			if(options.getSide(i))
			point++;	
		}
	
		if (options.checkAll(false)) {
//			System.out.println ("got stuck!" );
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
	
//		System.out.println("p.getY " + p.getY() + ", p.getX " + p.getX()) ;
		ground[p.getY()][p.getX()].setSide((point+2)%4, false);
		//print(ground,p);
		step(p, ground);
	
	}
	
	

	static void build(Cube[][] ground, Position p, Position start, Position end, int margin, int pWidth, int lWidth, Color pColor, Color lColor) {
		try {
			
			int width = p.getRow()*pWidth*lWidth + 2*margin + lWidth;
			int height = p.getCol()*pWidth*lWidth + 2*margin + lWidth;
			System.out.println(Integer.MAX_VALUE);
			System.out.println(start.getX() + " " + start.getY());
			// create the board
		    BufferedImage maze = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		    Graphics2D board = maze.createGraphics();
		
		    // set the background color to be white
		    board.setPaint(pColor);
		    board.fillRect(0, 0, width, height);
		    
		    // set the paint brush to be black
		    board.setPaint(lColor);
		
		    // add the start and end points
		    board.fillRect(start.getY()*pWidth+margin+2, start.getX()*pWidth+margin+2, pWidth-3, pWidth-3);
		    board.fillRect(end.getY()*pWidth+margin+2, end.getX()*pWidth+margin+2, pWidth-3, pWidth-3);
		    
		    // draw a square around the maze
//		    board.drawLine(margin,margin,pWidth*p.getRow()+margin,margin);
//		    board.drawLine(margin, margin, margin, pWidth*p.getCol()+margin);
//		    board.drawLine(pWidth*p.getRow()+margin, margin, pWidth*p.getRow()+margin, pWidth*p.getCol()+margin); // useless line
//		    board.drawLine(margin, pWidth*p.getCol()+margin, pWidth*p.getRow()+margin, pWidth*p.getCol()+margin); // useless line
		
		    for (int i=0 ;i<p.getRow();i++) {
		        for (int j=0 ;j<p.getCol();j++) {
		        	if(ground[i][j].getLeft()) { // get up
		      			board.drawLine(pWidth*i+margin, pWidth*j+margin,pWidth*(i+1)+margin,pWidth*j+margin);
		        	}
		        	if(ground[i][j].getDown()) { // get right
		        		board.drawLine(pWidth*(i+1)+margin,pWidth*j+margin,pWidth*(i+1)+margin,pWidth*(j+1)+margin);
		        	}
		        	if(ground[i][j].getRight()) { // get down
		      			board.drawLine(pWidth*i+margin, pWidth*(j+1)+margin,pWidth*(i+1)+margin,pWidth*(j+1)+margin);
		        	}
		        	if(ground[i][j].getUp()) { // get left
		        		board.drawLine(pWidth*i+margin, pWidth*j+margin, pWidth*i+margin, pWidth*(j+1)+margin);
		        	}
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
	
	public static Cube[][] copyGround (Cube[][] ground) {
		int width = ground.length;
		int height = ground[0].length;
		Cube[][] copyGround = new Cube[width][height];
		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < height; j++) {
				copyGround[i][j] = new Cube(ground[i][j].checkAll(true));
			}
		}
		
		return copyGround;
	}
	
	public static Cube[][] flipGround (Cube[][] ground, Cube[][] copyGround) {
		int width = ground.length;
		int height = ground[0].length;
		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < height; j++) {
				if (copyGround[i][j].checkAll(false))
				ground[i][j].setAll(true);
			}
		}
		
		return ground;
	}

	public static void main(String[] args) {
		
		
		/* START SETTINGS */
		
		// set board width
		//int bWidth = 0; - set in the imgToGround function
		
		// set board height
		//int bHeight = 0; - set in the imgToGround function
		
		// set path width
		int pWidth = 3;
		
		// set line width
		int lWidth = 1;
		
		// set page margin
		int margin = 2;
		
		// set path color
		Color pColor = new Color(75, 0, 130);
		
		// set line color
		Color lColor = Color.yellow;
		
		/* END SETTINGS */
		
		
		Position p= new Position(0, 0, 0, 0);
		Position start = new Position(0, 0, 0, 0);
		Position end = new Position(0, 0, 0, 0);
		
		Cube[][] ground = imgToGround(p, start, end);
		
		Cube[][] copyGround = copyGround(ground);
		
		step(p, ground);
		
		for (int i = 0; i < 50; i++) {
			
			p.setX(start.getX());
			p.setY(start.getY());
			
			for (int y = 0; y < p.getRow(); y++) {
				for (int x = 0; x < p.getCol(); x++) {
					if (ground[y][x].checkAll(true))
						continue;
					if (ground[y][x].checkAll(false)) {
						continue;
					}
					p.setX(x);
					p.setY(y);
					step(p, ground);
				}
			}
		}
		
//		ground = flipGround(ground, copyGround);
//		
//		for (int i = 0; i < 50; i++) {
//			
//			p.setX(start.getX());
//			p.setY(start.getY());
//			
//			for (int y = 0; y < p.getRow(); y++) {
//				for (int x = 0; x < p.getCol(); x++) {
//					if (ground[y][x].checkAll(true))
//						continue;
//					if (ground[y][x].checkAll(false)) {
//						continue;
//					}
//					p.setX(x);
//					p.setY(y);
//					step(p, ground);
//				}
//			}
//		}
		
		build(ground, p, start, end, margin, pWidth, lWidth, pColor, lColor);
		
		System.out.println("finished!!!" );
		
	}

}
