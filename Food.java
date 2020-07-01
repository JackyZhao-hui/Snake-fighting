

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

// 食物
public class Food {
	int x ;
	int y ;
	int width = 10;
	int height = 10;
	//重载
	public Food(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	//用户碰撞检测
	public Rectangle getRect() {
		return new Rectangle((int)x, (int)y, width, height);
	}
	
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.GREEN);
		g.fillOval((int)x, (int)y, width, height);
		g.setColor(c);
	}

}