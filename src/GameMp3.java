import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;//jl1.jar

class GameMp3//ר�����ڲ���MP3����
{
	protected String name;
	protected FileInputStream fis=null;
	protected File f=null;
	protected Player player=null;
	protected  boolean initialize = false;
	
	GameMp3(String name)
	{
		this.name=name;
	}
	
	 public void play()
	 {
		switch(name) 
		{
		
		case "ð�յ�1" : f = new File("src\\Mp3\\FunnyRabbit.mp3");break;
		case "ð�յ�2" : f = new File("src\\Mp3\\ð�յ�1.mp3");break;
		
		default:System.out.println("Mp3·������");
		}
		try {
			fis = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			player = new Player(fis);
			if(initialize==false)
			{
				player.play();
				initialize=true;
			}
			else
			{
				player.play();
				System.out.println("replay");
			}
			System.out.println("GameMp3 Position "+player.getPosition());
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}
	 }
	 
	 public void setName(String name)
	 {
		 this.name=name;
		 play();
	 }
}
