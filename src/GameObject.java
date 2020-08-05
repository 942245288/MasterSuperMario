import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;

class GameObject 
{
	public int x,y,in_x;
	public int obj_w=28, obj_h=28;
	public int hasrun_x=0;
	public int all_w=0;//�����ȫ����ȣ���ΪĳЩ�������ɶ���ӿ�ƴ�ɣ�����棬ש�飩
	public int all_h=0;//�����ȫ���߶ȣ���ΪĳЩ�������ɶ���ӿ�ƴ�ɣ�����棬ש�飩
	public GameClient gc=null;
	public boolean draw = true;//�Ƿ����
	public boolean available=true;//�Ƿ�����ײ
	protected static Toolkit tk =Toolkit.getDefaultToolkit();
	protected static Random random = new Random();
	protected int ran_num = random.nextInt(10);
	protected static Image [] imgs = null;
	protected Action act=Action.UNSTAND,touch=Action.UNTOUCH,touchhero = Action.UNTOUCH;
	protected Hero player=null;
	
	
	
	GameObject(int x,int y,GameClient gc)
	{
		this.x=x;
		this.y=y;
		this.gc=gc;
		all_w=obj_w;
		all_h=obj_h;
		in_x=x;
	}
	
	public void draw(Graphics g)
	{
		checkAvailable();
		getHasrun();
		touchWithHero(gc.player1);
		move();
	}
	
	protected void getHasrun() {
		this.hasrun_x = gc.player1.hasrun_x;
	}

	/**
	 * ÿ֡���������Ƿ���Ч����Ҫ����
	 */
	protected void checkAvailable() 
	{
		if(available==false) return;
		//�����岻�ڴ�������Ϊ�Ѿ��߹��ĳ���������Ч������)
		if(getRectangle().intersects(new Rectangle(0,0,GameClient.F_W,GameClient.F_H))==false)
		{
			if(x<=-GameClient.F_W/2)
			{
				draw=false;
				available=false;
			}
		}
	}

	public void move()
	{
		if(available==true)
		player = gc.player1;
		{
			if(player.x+player.xspe>Hero.LIM_X2&&player.finish==false)
			{
				x-=player.xspe;
			}
		}
		
	}
	
	public Rectangle getRectangle()
	{
		return new Rectangle(x,y,all_w,all_h);
	}
	
	protected void touchWithHero(Hero hero)
	{
		if(this.draw==false) return ;
	}
	
	/**
	 * ��⵱ǰ�������ɫ�Ƿ���ײ�������ܹ���⴩͸ʽ��ײ�ļ�ⷽʽ
	 * ����״�ص�����ײ��ⷽʽ�������ƶ����죬һ֡��ֱ�Ӵ���������������Ч��
	 * @param hero
	 * @return
	 */
	public boolean throughCheck(Hero hero)
	{
		int x,y,x1,y1;
		int r_x,r_y,r_w,r_h;
		x=hero.x;//��ǰ֡X����
		y=hero.y;//��ǰ֡Y����
		x1=hero.x+hero.xspe;//��һ֡X����
		y1=hero.y+hero.yspe;//��һ֡Y����
		if(x<=x1)//�������ƶ���xΪ�ƶ����ε�x����
		{
			r_x=x;
		}
		else r_x=x1;
		if(y<=y1)//�������ƶ�,
		{
			r_y=y1;
		}
		else r_y=y;
		if(hero.xspe<0) r_w=-hero.xspe;//�����Ⱦ���x��һ֡���ƶ�����
		else r_w=hero.xspe;
		if(hero.yspe<0) r_h=-hero.yspe;//����߶Ⱦ���Y��һ֡���ƶ�����
		else r_h=hero.yspe;
		//�����ƶ����깹��ɵľ��Σ����ڼ����ײ
		Rectangle move_r=new Rectangle(r_x,r_y,r_w,r_h);
		if(move_r.intersects(this.getRectangle()))
		{
			return true;
		}
		else return false;
	}
	
	public boolean throughCheck(GameCreature gc)
	{
		int x,y,x1,y1,r_x,r_y,r_w,r_h;
		x=gc.x;
		y=gc.y;
		x1=gc.x+gc.xspe;
		y1=gc.y+gc.yspe;
		if(x<=x1)
		{
			r_x=x;
		}
		else r_x=x1;
		if(y<=y1)
		{
			r_y=y;
		}
		else r_y=y1;
		if(gc.xspe<0) r_w=-gc.xspe;
		else r_w=gc.xspe;
		if(gc.yspe<0) r_h=-gc.yspe;
		else r_h=gc.yspe;
		Rectangle move_r=new Rectangle(r_x,r_y,r_w,r_h);
		if(move_r.intersects(this.getRectangle()))
		{
			return true;
		}
		else return false;
	}
	
	protected void action()
	{
		
	}
}
