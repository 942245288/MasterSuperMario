import java.awt.Graphics;


class ImageMap extends ObjectMap
{



	ImageMap(int num, GameClient gc) {
		super(num, gc);
	}

	public void draw(Graphics g) 
	{
		super.draw(g);
	}

	protected void makeObj() 
	{
		if(num==1)
		{
			//�����Ǳ�
			objs.add(new Tower(3100,300,gc));
			
			System.out.println("Num 1 ͼƬ�Ѿ���ʼ���� ");
		}
		num=0;
	}
}
