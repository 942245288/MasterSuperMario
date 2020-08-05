import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class Fungus extends GameCreature 
{


	protected static Map<String,Image> obj_imgs =  new HashMap<String,Image>();//������ܷŵ�������
	protected boolean hit=false;
	protected int XSPE=2,YSPE=15,yadd=3,xspe=0,ysep=0;
	private int d_time=1;
	private boolean initialize = false;
	
	static 
	{
		imgs = new Image []
				{
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/fungus1.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/fungus2.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/fungus3.png"))
				};
		obj_imgs.put("FR1", imgs[0]);
		obj_imgs.put("FR2", imgs[1]);
		obj_imgs.put("FS3", imgs[2]);
	}
	
	Fungus(int x, int y,Dirction move_dir,GameClient gc) {
		super(x, y, gc);
		if(move_dir==Dirction.L) xspe=-XSPE;
		else xspe=XSPE;
		obj_w=35;
		obj_h=35;
		all_w=35;
		all_h=35;
	}

	public void draw(Graphics g) {
		super.draw(g);
		//���δ��ʼ�����������ڻ��������һ�飬Ϊ����ǰ��ϵͳ��ͼƬ��Դ���ص��ڴ���
		//����Ϸʱ��ɼӿ�ͼƬ�ļ����ٶ�
		if(initialize==false)
		{
			for(int i=0;i<imgs.length;i++)
			{
				g.drawImage(imgs[i],-200,-200,null);
			}
			initialize=true;
		}
		Image img = null;
		if(hit==false)
		{
			if(d_time<=3)
			{
				img=obj_imgs.get("FR1");
				d_time++;
			}
			else if(d_time>3&&d_time<=6)
			{
				img=obj_imgs.get("FR2");
				if(d_time==6)
				d_time=1;
				else 
				d_time++;
			}
		}
		else if(hit==true)
		{
			all_h=20;
			img=obj_imgs.get("FS3");
		}
		g.drawImage(img, x, y, null);
		
		Color c = g.getColor();
		g.drawImage(img, x, y, null);
		g.setColor(Color.black);
		g.fillOval(x,y,5,5);
		g.fillOval(x+all_w,y,5,5);
		g.fillOval(x,y+all_h,5,5);
		g.setColor(c);
		
		touchWithObjs();
	}

	public void move() {
		super.move();
		if(draw==false) return;
		xMove();
		yMove();
	}

	protected void xMove() {
		if(hit==true) return;
		x+=xspe;
	}

	protected void yMove() {
		if(act==Action.UNSTAND)
		{
			yspe+=yadd;
		}
		else if (act==Action.STAND)
		{
			yspe=1;
		}
		y+=yspe;
	}

	public void touchWithHero(Hero hero) {
		super.touchWithHero(hero);
		if(draw==false||hero.live==false) return;
		if(hero.getNextRectangle().intersects(getNextRectangle())
			||hero.getARectangle(hero.x+1, hero.y,hero.hero_w,hero.hero_h).intersects(getNextRectangle())
			||hero.getARectangle(hero.x-1,hero.y,hero.hero_w,hero.hero_h).intersects(getNextRectangle())
				)
		{
			if(hero.y+hero.hero_h<=y)
			{
				touchhero=Action.BUNT;
			}
			else if(hero.x<=x)
			{
				touchhero=Action.LTOUCH;
			}
			else if(hero.x+hero.hero_w>=x+all_w)
			{
				touchhero=Action.RTOUCH;
			}
		}
		else
		{
			touchhero=Action.UNTOUCH;
		}
		action(gc.player1);
	}
	
	protected void action(Hero hero) {
		super.action();
		if(touchhero==Action.BUNT)
		{
			hero.yspe=-hero.Y_ADD;
			if(hit==true)
			{
				disappear();
			}
			hit=true;
			
			//�����Ч
			new GameAudio("���").start();
		}
		else if(touchhero==Action.LTOUCH)
		{
			hero.die();
		}
		else if(touchhero==Action.RTOUCH)
		{
			hero.die();
		}
	}
	protected void touchWithObjs() 
	{
		if(draw==false||available==false) return;
		GameObject obj1=null;
		GameObject obj2=null;
		for(int i=0;i<objs.size();i++)
		{
			GameObject obj=null;
			obj = objs.get(i);
			if((obj.draw==true&&getNextRectangle().intersects(obj.getRectangle())==true
					&&(obj!=obj1&&obj!=obj2&&obj!=this))||obj.throughCheck(this)
					||((x+xspe>obj.x&&x+xspe<obj.x+obj.all_w&&y>obj.y&&y<obj.y+obj.all_h)||(x+all_w+xspe>obj.x&&x+all_w+xspe<obj.x+obj.all_w&&y>obj.y&&y<obj.y+obj.all_h)))
			{
				if((x+xspe>obj.x&&x+xspe<obj.x+obj.all_w&&y>obj.y&&y<obj.y+obj.all_h)||(x+all_w+xspe>obj.x&&x+all_w+xspe<obj.x+obj.all_w&&y>obj.y&&y<obj.y+obj.all_h))
				{//��Խ������2
					if(obj.y>=y) return;
					if(xspe>=0)
					{
						x=obj.x-all_w;
					}
					else
					{
						x=obj.x+obj.all_w;
					}
				//	System.out.println("FU��Խ������1");
				}
				if(obj.throughCheck(this))//��Խ������2
				{
					if(obj.y>=y) return;
					x-=xspe;
					y-=yspe;
				//	System.out.println("FU��Խ������2");
				}
				if(obj1==null)
				{
					obj1=obj;
					//System.out.println("FU obj1  "+obj.getRectangle());
				}
				else if(obj1!=null)
				{
					obj2=obj;
					//System.out.println("FU obj2  "+obj.getRectangle());
				}
				if(obj1!=null&&obj2!=null)
				{
					//System.out.println("FU ������Ҫһ���µ�obj��   "+obj.getRectangle()+" obj1 "+obj1.getRectangle()+" obj2 "+obj2.getRectangle());
				}
			}
		}
		
		if(obj1!=null&&obj2==null)//ֻ��һ��������mario�Ӵ�ʱ
		{
			if(y<=obj1.y)
			{
				y=obj1.y-all_h;
				act=Action.STAND;
			}
			else
			{
				act=Action.UNSTAND;
			}
			
			if(x>=obj1.x+obj1.all_w&&xspe<0)
			{
				touch=Action.LTOUCH;
				x=obj1.x+obj1.all_w+XSPE;	
				xspe=XSPE;
			}
			else if(x+all_w<=obj1.x&&xspe>0)
			{
				touch=Action.RTOUCH;
				x=obj1.x-all_w-XSPE;
				xspe=-XSPE;
			}
			else if(y>=obj1.y+obj1.all_h&&yspe<0)
			{
				touch=Action.BUNT;
			}
			else
			{
				touch=Action.UNTOUCH;
			}
			//if(touch!=Action.UNTOUCH)
			//System.out.println("FU��һ�����弴����ײ "+act+" "+touch+" x "+x+" xspe "+xspe+" y "+y);
		}
		else if(obj1!=null&&obj2!=null)//������������mario�Ӵ�ʱ
		{
			int ground=0;//�ҳ���Ϊ������������Ϊǽ������

			if(x+all_w>obj1.x&&x<obj1.x+obj1.all_w&&obj1.y>y)
			{
				ground=1;
			}
			else if(x+all_w>obj2.x&&x<=obj2.x+obj2.all_w&&obj2.y>y)
			{
				ground=2;
			}
			else
			{
				System.out.println("������������mario��");
				return ;
			}
			//����ǽ����Ĵ���
			GameObject obj_wall=null,obj_ground=null;
			if(ground==1)
			{
				obj_wall=obj2;
				obj_ground=obj1;
			}
			else if(ground==2)
			{
				obj_wall=obj1;
				obj_ground=obj2;
			}
			
			//����������ײʱ��ֱ�������
			if(x<obj_ground.x+obj_ground.all_w||x+all_w>obj_ground.x) //վ�ڵ������
			{
				act=Action.STAND;
				y=obj_ground.y-all_h;
			}
			else
			{
				act=Action.UNSTAND;
			}
			
			//��������ײʱˮƽ�������
			if(xspe<0)
			{
				touch=Action.LTOUCH;
				x=obj_wall.x+obj_wall.all_w+XSPE;	
				xspe=XSPE;
			}
			else if(xspe>0)
			{
				touch=Action.RTOUCH;
				x=obj_wall.x-all_w-XSPE;
				xspe=-XSPE;
			}
			else
			{
				touch=Action.UNTOUCH;
			}
			
			//if(touch!=Action.UNTOUCH)
			System.out.println("FU�����������弴����ײ     "+act+" "+touch+" "+touchhero+" xspe "+xspe+" yspe "+yspe);
		 
		}
		else if(obj1==null&obj2==null)
		{
			act=Action.UNSTAND;
			touch=Action.UNTOUCH;
			//System.out.println("FUû�����弴����ײ "+act+" "+touch+" x "+x+" xspe "+xspe+" y "+y+" yspe "+yspe);
		}
	}

}
