

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Snake{
	boolean left = true;
	boolean up,right,down;
	double x,y;
	int speed;
	int width=10;
	int height=10;
	int len = 100; //定义蛇头保存多少个坐标值
	int snakelen = 2; //蛇的初始长度
	int[][] pos = new int[100][2]; //蛇头走过的所有坐标
	int[][] newpos = new int[1][2]; //保存蛇头每次变化后的最新坐标
	
	
	public void drawSelf(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		//填充正方形，蛇头为红色
		g.fillRect((int)x, (int)y, 10, 10);
		g.setColor(Color.BLACK);
		for (int i = 1; i < snakelen; i++) {
			g.fillRect(pos[i][0], pos[i][1], 10, 10);
		}
		g.setColor(c);
		
		// 允许穿过边界
		if(left) {
			if (x <=15 ) {
				x=478;	
			}
			x -= speed;
			newpos[0][0] = (int)x;
		}
		if (right) {
			if (x >= 478) {
				x = 15;
			}
			x += speed;
			newpos[0][0] = (int)x;
		}
		if (up) {
			if (y <= 32) {
				y=479;
			}
			y -= speed;
			newpos[0][1] = (int)y;
		}
		
		if (down) {
			if (y >= 479) {
				y=32;
			}
			y += speed;
			newpos[0][1] = (int)y;
		}
		g.setColor(c);
		//中间变量，用来重组pos数组
		int[][] tmp = new int[len][2];
		System.arraycopy(newpos, 0, tmp, 0, 1);

		for (int s = 1; s < len; s++) {
			tmp[s][0]=pos[s-1][0];
			tmp[s][1]=pos[s-1][1];
		}

		for (int s = 0; s < len; s++) {
			pos[s][0]=tmp[s][0];
			pos[s][1]=tmp[s][1];
		}

	}
	//重载
	public Snake (double x, double y,int speed) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		pos[0][0] = (int)x;
		pos[0][1] = (int)y;
		newpos[0][0] = (int)x;
		newpos[0][1] = (int)y;
	}
	//用户碰撞检测
	public Rectangle getRect() {
		return new Rectangle((int)x, (int)y, width, height);
	}
	
	// 改变方向， 不允许在同一直线上掉头
	public void addDirection(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			if(! right) {
				left = true;
				up = false;
				right = false;
				down = false;
			}
			break;
		case KeyEvent.VK_UP:
			if(! down) {
				left = false;
				up = true;
				right = false;
				down = false;
			}
			break;
		case KeyEvent.VK_RIGHT:
			if(! left) {
				left = false;
				up = false;
				right = true;
				down = false;
			}
			break;
		case KeyEvent.VK_DOWN:
			if(! up) {
				left = false;
				up = false;
				right = false;
				down = true;
			}
			break;
		case KeyEvent.VK_SPACE:
			if(MyGameFrame.space)
				MyGameFrame.space = false;
			else
	            MyGameFrame.space = true;

            break;
		default:
			break;
		}
	}
}
