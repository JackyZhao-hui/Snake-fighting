
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.Random;

/**
 * 游戏的主窗口
 * @author 
 *
 */

public class MyGameFrame  extends  Frame {
	Snake snake  =  new Snake(250,250,10);
	static int count = 0;
	boolean peng = false; //碰撞检测
	static boolean space = false; // 控制游戏开始
	static boolean esc = false;  //控制游戏结束
	Random randx = new Random(); 
	Random randy = new Random();
	Food food = new Food(100,100); //初始化食物
	Date startTime; // 游戏开始时间
	Date endTime; // 游戏结束时间
	
	public void paint(Graphics g) {
		printstart(g);
		if(space) {
			startTime = new Date();
			Color c = g.getColor();
			g.setColor(Color.WHITE); //填充白色背景
			g.fillRect(0, 0, 500, 500);
			g.setColor(c);
			snake.drawSelf(g);
			food.draw(g);
			printsco(g);
			//碰撞检测，判断蛇是否吃到了食物
			//吃到食物后，改变食物出现的位置
			peng = food.getRect().intersects(snake.getRect());
			if(peng) {
				System.out.println("吃到了");
				snake.snakelen +=1;
				count++;
				food.setX(randx.nextInt(400)+40);
				food.setY(randy.nextInt(400)+40);
				System.out.println("food: "+food.getRect().x+" & "+food.getRect().y);
			}
		}
		
		
	}
	
	public void printstart(Graphics g) {
        g.setFont(new Font("黑体", Font.BOLD, 50));
        g.drawString("游戏马上开始!", 100, 200);
        g.setFont(new Font("宋体", Font.ITALIC, 20));
        g.drawString("请按空格键继续", 180, 350);
    }
	
	// 实时分数提示
    public void printsco(Graphics g) {
        Color c = g.getColor();
        g.setFont(new Font("微软雅黑", Font.PLAIN, 10));
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 900, 60);
        g.setColor(Color.WHITE);
        g.drawString("分数: " + count + "分", 30, 52);
        g.drawString("注意游戏时间, 劳逸结合!", 330, 52);
        g.setColor(c);
    }
	
    
    public void addDirection(KeyEvent e) {
    	if(e.getKeyCode() == 27) {
    		System.exit(0);
    	}
    }
    
	class PaintThread extends Thread {
		public void run() {
			while (true) {
				//System.out.println("重画窗口");
				repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//键盘监听
	class KeyMonitor extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			snake.addDirection(e);
			addDirection(e);
		}
		
	}

	/**
	 * 初始化窗口
	 */
	public  void  launchFrame(){
		this.setTitle("贪吃蛇");
		this.setVisible(true);
		this.setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		this.setLocation(1100, 300);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		new PaintThread().start();
		addKeyListener(new KeyMonitor());
	}
	//启动
//	MyGameFrame() {
//		MyGameFrame  f = new MyGameFrame();
//		f.launchFrame();
//	}
	
	private Image offScreenImage = null;
	public void update(Graphics g) {
		if(offScreenImage == null)
			offScreenImage = this.createImage(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);//这是游戏窗口的宽度和高度
		Graphics gOff = offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage, 0, 0, null);
	}   
	
}
