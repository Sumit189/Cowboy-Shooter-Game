import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


class Listener extends MouseAdapter{
		public void mouseClicked(MouseEvent e){
			if(BouncingBall.bullet_fire==false && !BouncingBall.won){
				if(BouncingBall.startup_screen==true){
					BouncingBall.startup_screen=false;
				}
				BouncingBall.Bullet_count--;
			   if(BouncingBall.Bullet_count<0){
				   BouncingBall.Bullet_count=0;
				   BouncingBall.game_over.setVisible(true);
			   }
			   else{
				BouncingBall.bullet_fire=true;
				BouncingBall.Bullet_remaining.setText("Bulltes: "+BouncingBall.Bullet_count);
				BouncingBall.show_popup=false;
				if(BouncingBall.gamemusic && BouncingBall.Bullet_count>=0){
					try {
						BufferedInputStream audioFile =new BufferedInputStream(BouncingBall.bBall.getResourceAsStream("/res/gunshot.wav"));
						AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
						Clip clip = AudioSystem.getClip();
						clip.open(audioInputStream);
						clip.start();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			   }
			}
			else if(BouncingBall.won){
				BouncingBall.winner.setVisible(true);
			}
		}
}

class Button_Handler implements ActionListener{
	public static JCheckBox c1,c2;
	public static Boolean selected_BG=true;
	public static Boolean selected_GM=true;
	public static JFrame jf2;
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("Save")){
			if(c1.isSelected()){
				BouncingBall.clip.stop();
				BouncingBall.clip.start();
				BouncingBall.bgmusic=true;
				selected_BG=true;
			}
			else{
				BouncingBall.clip.stop();
				BouncingBall.bgmusic=false;
				selected_BG=false;
			}
			if(c2.isSelected()){
				BouncingBall.gamemusic=true;
				selected_GM=true;
			}
			else{
				BouncingBall.gamemusic=false;
				selected_GM=false;
			}
			jf2.dispose();
		}
		if(e.getActionCommand().equals("Settings")){
			jf2 = new JFrame("Settings");
			jf2.setLayout(null);
			JLabel l1 = new JLabel("Sound Settings");
			JLabel l2 = new JLabel("Made by: Sumit Paul");
			jf2.add(l1);
			jf2.add(l2);
			c1=new JCheckBox("Background Music");
			c1.setSelected(selected_BG);
       		jf2.add(c1);
        	c1.setBounds(80,60,150,20);
       		c1.setBackground(Color.WHITE);
			c1.setForeground(Color.BLACK);

			c2=new JCheckBox("Game Music");
			c2.setSelected(selected_GM);
			jf2.add(c2);
			c2.setBounds(80,90,150,20);
			c2.setBackground(Color.WHITE);
			c2.setForeground(Color.BLACK);
			
			JButton save = new JButton("Save");
			Button_Handler button_Handler = new Button_Handler();
			save.addActionListener(button_Handler);
			jf2.add(save);
			save.setBackground(Color.WHITE);
			save.setBounds(100,125,100,30);

			l1.setBounds(110,30,100,20);
			l2.setBounds(160,165,130,20);
			jf2.getContentPane().setBackground(Color.WHITE);
			jf2.setSize(300,230);
            jf2.setResizable(false);
            jf2.setLocationRelativeTo(BouncingBall.f);
			jf2.setVisible(true);
		}
		if(e.getActionCommand().equals("Restart")){
			BouncingBall.Bullet_count=6;
			BouncingBall.point=0;
			BouncingBall.ball_speed=8;
			BouncingBall.target=20;
			BouncingBall.level=1;
			BouncingBall.score.setText("Score: "+BouncingBall.point);
			BouncingBall.Bullet_remaining.setText("Bullets: "+BouncingBall.Bullet_count);
			BouncingBall.Next_target.setText("Target: "+BouncingBall.target);
			BouncingBall.Level.setText("Level: "+BouncingBall.level);
			BouncingBall.game_over.setVisible(false);
			BouncingBall.winner.setVisible(false);
			BouncingBall.move_ball=true;
			BouncingBall.bullet_fire=false;
			BouncingBall.ballx=450;
			BouncingBall.bally=400;
			BouncingBall.won=false;
			if(BouncingBall.bgmusic){
			try{
				BufferedInputStream audioFile = new BufferedInputStream(BouncingBall.bBall.getResourceAsStream("/res/theme.wav"));
				AudioInputStream audioInputStream1 = AudioSystem.getAudioInputStream(audioFile);
				BouncingBall.clip = AudioSystem.getClip();
				BouncingBall.clip.open(audioInputStream1);
				BouncingBall.clip.start();
				BouncingBall.clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}	
		}
	}
	if(e.getActionCommand().equals("Exit")){
		System.exit(0);
	}
 }
} 


class BouncingBall extends Canvas{
	private static int x = 0, a = 0;
	public static boolean up = true;
	public static boolean move_ball=true;
	public static int ballx=450;
	public static int bally=400;
	public static int bulletx=20;
	public static int bullety=320;
	public static boolean bullet_fire=false;
	public static boolean bulletf=true;
	public static int point=0;
	public static int init_x=450;
	public static boolean score_update=true;
	public static int target = 20;
	public static int level = 1;
	public static int ball_speed=8;
	public static int area_x, area_y;
	public static JLabel score;
	public static JLabel gun;
	public static Image img,gun_img,bullet_img,ball_img;
	public static BufferedImage bf;
	public static JPanel detail_panel,bottom_buttons1,bottom_buttons2;
	public static JLabel Bullet_remaining;
	public static JLabel Next_target;
	public static JLabel Level;
	public static Integer Bullet_count=6;
	public static JDialog game_over,winner;
	public static JLabel game_over_msg,winning_msg;
	public static JButton restart, exit,restart2,exit2;
	public static boolean level_changed=false;
	public static int consecutive_hit=0;
	public static Font pop_font=new Font("Fugaz one", Font.PLAIN,22);
	public static String popup_msg="default";
	public static Boolean show_popup=false;
	public static Boolean hit=false;
	public static Clip clip;
	public static AudioInputStream audioInputStream1;
	public static Boolean startup_screen=true;
	public static Class bBall;
	public static Boolean bgmusic=true;
	public static Boolean gamemusic=true;
	public static Boolean won=false;
	public static JFrame f;
	
    public static void main(String[] args){
		BouncingBall bouncingBall = new BouncingBall();
		bBall = bouncingBall.getClass();
		if(bgmusic){
		try {
			BufferedInputStream audioFile = new BufferedInputStream(bBall.getResourceAsStream("/res/theme.wav"));
			AudioInputStream audioInputStream1 = AudioSystem.getAudioInputStream(audioFile);
			clip = AudioSystem.getClip();
			clip.open(audioInputStream1);
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception ex) {
			ex.printStackTrace();
		}	
	}

   f = new JFrame("Cowboy Shooter");

	//menu bar
	JMenuBar jmb = new JMenuBar();
	jmb.setBackground(Color.WHITE);
	JMenuItem settings = new JMenuItem("Settings");
	jmb.add(settings);
	jmb.setBackground(Color.WHITE);
	settings.setBackground(Color.WHITE);
	f.setJMenuBar(jmb);

	area_x=800;
	area_y=600;
	Button_Handler button_Handler = new Button_Handler();

	detail_panel= new JPanel(null);
	score = new JLabel("Score: "+point);
	Bullet_remaining = new JLabel("Bullets: "+Bullet_count);
	Next_target = new JLabel("Target: "+target);
	Level = new JLabel("Level: "+level);
	game_over = new JDialog(f,"Game Over");
	winner = new JDialog(f,"Winner");

	Image g_over=Toolkit.getDefaultToolkit().getImage(bBall.getResource("/res/gameover_img.png")); 
	JLabel g_over_img=new JLabel("",SwingConstants.CENTER);
	g_over_img.setIcon(new ImageIcon(g_over.getScaledInstance(150,150, Image.SCALE_DEFAULT)));
	game_over.getContentPane().setBackground(Color.WHITE);

	Image w_over=Toolkit.getDefaultToolkit().getImage(bBall.getResource("/res/winner_img.jpg")); 
	JLabel w_over_img=new JLabel("",SwingConstants.CENTER);
	w_over_img.setIcon(new ImageIcon(w_over.getScaledInstance(300,350, Image.SCALE_DEFAULT)));
	winner.getContentPane().setBackground(Color.WHITE);
	
	restart = new JButton("Restart");
	restart.setBackground(Color.WHITE);
	restart.setForeground(Color.BLACK);
	exit = new JButton("Exit");
	exit.setBackground(Color.WHITE);
	exit.setForeground(Color.BLACK);

	restart2 = new JButton("Restart");
	restart2.setBackground(Color.WHITE);
	restart2.setForeground(Color.BLACK);
	exit2 = new JButton("Exit");
	exit2.setBackground(Color.WHITE);
	exit2.setForeground(Color.BLACK);

	restart.addActionListener(button_Handler);
	exit.addActionListener(button_Handler);
	restart2.addActionListener(button_Handler);
	exit2.addActionListener(button_Handler);
	settings.addActionListener(button_Handler);

	game_over.add(g_over_img);
	winner.add(w_over_img);

	bottom_buttons1 = new JPanel();
	bottom_buttons1.setBackground(Color.WHITE);
	bottom_buttons1.add(restart);
	bottom_buttons1.add(exit);
	
	bottom_buttons2 = new JPanel();
	bottom_buttons2.add(restart2);
	bottom_buttons2.add(exit2);


	winner.add(bottom_buttons2,BorderLayout.SOUTH);
	game_over.add(bottom_buttons1,BorderLayout.SOUTH);


	game_over.setSize(300,200);
	winner.setSize(300,420);
	game_over.setLocationRelativeTo(f);
	winner.setLocationRelativeTo(f);
	gun_img = Toolkit.getDefaultToolkit().getImage(bBall.getResource("/res/cowboy.png")); 
	bullet_img = Toolkit.getDefaultToolkit().getImage(bBall.getResource("/res/bullet.png")); 
	ball_img = Toolkit.getDefaultToolkit().getImage(bBall.getResource("/res/ball.png")); 
	
	gun = new JLabel();
	gun.setBackground(Color.RED);
	gun.setOpaque(true);
	Canvas c = new BouncingBall();
	f.setLayout(null);
	f.add(detail_panel);
	f.add(c);
	gun.setBounds(0,312,150,80);
	c.setBounds(0,0,area_x,area_y);
	Bullet_remaining.setBackground(new Color(255,255,255));
	score.setBackground(new Color(255,255,255));
	score.setBackground(new Color(255,255,255));
	Next_target.setBackground(new Color(255,255,255));
	Level.setBackground(new Color(255,255,255));
	score.setOpaque(true);
	Listener l = new Listener();
	c.addMouseListener(l);
	img = Toolkit.getDefaultToolkit().getImage(bBall.getResource("/res/gamebg.jpg"));
	detail_panel.setBounds(30,18,720,50);
	detail_panel.add(Bullet_remaining);
	Bullet_remaining.setBounds(10,10,100,20);
	detail_panel.add(score);
	score.setBounds(220,10,100,20);
	detail_panel.add(Next_target);
	Next_target.setBounds(420,10,100,20);
	detail_panel.add(Level);
	Level.setBounds(610,10,100,20);
	detail_panel.setBackground(new Color(255,255,255));

	Bullet_remaining.setFont(new Font("Fugaz one",0,17));
	score.setFont(new Font("Fugaz one",0,17));
	Next_target.setFont(new Font("Fugaz one",0,17));
	Level.setFont(new Font("Fugaz one",0,17));

	f.setSize(area_x,area_y);
	game_over.setResizable(false);
	winner.setResizable(false);
	f.setResizable(false);
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	f.setLocationRelativeTo(null);
	f.getContentPane().setBackground(new Color(255,255,255));
	f.setVisible(true);
	}
    private BouncingBall(){
	Thread t = new Thread(()->{ 
								while(true){
								if(move_ball){
									if(level>5 && won==false){
										move_ball=false;
										bullet_fire=false;
										clip.stop();
										if(bgmusic){
										try {
											BufferedInputStream audioFile = new BufferedInputStream(bBall.getResourceAsStream("/res/win.wav"));
											AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
											Clip hclip = AudioSystem.getClip();
											hclip.open(audioInputStream);
											hclip.start();
										} catch (Exception ex) {
											ex.printStackTrace();
										}
									}
										won=true;
										popup_msg="WINNER";
										Level.setText("Level: 5");
										winner.setVisible(true);
										
									}
			                    if(up){    
									 x++;  
									 if(x == 330){ 
										 up = false;
										}}
				     			else{
									x--;   
									if(x == 0){
										up = true;
									}}
								}
			                      try{
									  Thread.sleep(ball_speed);}
			                      catch(Exception e){}	
								  repaint();
							}
			        });
	   t.start();

	Thread bullet = new Thread(()->{  
								int newx;
								int random_value;
								int random_add_sub;
								while(bulletf){
								//level
								if(point>=target && bullet_fire){
									level+=1;
									level_changed=true;
									Random rand = new Random();
									
									 	//chnage x with level
										 random_value=rand.nextInt(10)+40;
										 random_add_sub=rand.nextInt(2)+1;
										 if(random_add_sub<0){
											random_add_sub=-random_add_sub;
										 }
										 if(random_add_sub==1){
						
											if(random_value+ballx>780){
														newx=ballx+random_value;
														newx=newx-area_x;
														ballx+=newx;
											}
											else{
														ballx+=random_value;
													}
										}
										else{
											if(ballx-random_value<250){
												newx=ballx-random_value;
												newx=240-newx;
												ballx-=newx;
											}
											else{
												ballx-=random_value;
											}
										}
								}
								
								if(a<700 && bullet_fire){
									  a=a+5;
									  int ball_outer_edge=ballx;
									  int bullet_point=bulletx+38;

									  if(((bullet_point==ball_outer_edge)||(bullet_point<ball_outer_edge+10 && bullet_point>ball_outer_edge-10))){
										if(consecutive_hit==2){
											if(gamemusic){
											try {
												BufferedInputStream audioFile = new BufferedInputStream(bBall.getResourceAsStream("/res/bonus.wav"));
												AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
												Clip hclip = AudioSystem.getClip();
												hclip.open(audioInputStream);
												hclip.start();
											} catch (Exception ex) {
												ex.printStackTrace();
											}
										}
											popup_msg="Bullet +1";
											show_popup=true;
											consecutive_hit=0;
											Bullet_count+=1;
											Bullet_remaining.setText("Bullet: "+Bullet_count);
										}
										if(bullety-15==bally){
											if(score_update){
												score_update=false;
												point+=20;
												if(hit==true){
												consecutive_hit+=1;
												}
												if(Bullet_count>0){
													move_ball=false;
												}
												hit=true;
												ball_img = Toolkit.getDefaultToolkit().getImage(bBall.getResource("/res/explode.png"));
												if(gamemusic){
												try {
													BufferedInputStream audioFile = new BufferedInputStream(bBall.getResourceAsStream("/res/hit.wav"));
													AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
													Clip hclip = AudioSystem.getClip();
													hclip.open(audioInputStream);
													hclip.start();
												} catch (Exception ex) {
													ex.printStackTrace();
												} 
											}
											}
											score.setText("Score: "+point);
										
										}
										else if(bally<=bullety && bally>=bullety-19){
											if(score_update){
												score_update=false;
												point+=10;
												if(hit==true){
												consecutive_hit+=1;
												}
												if(Bullet_count>0){
													move_ball=false;
												}
												hit=true;
												if(gamemusic){
												try {
													BufferedInputStream audioFile = new BufferedInputStream(bBall.getResourceAsStream("/res/hit.wav"));
													AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
													Clip hclip = AudioSystem.getClip();
													hclip.open(audioInputStream);
													hclip.start();
												} catch (Exception ex) {
													ex.printStackTrace();
												}
											}
												ball_img = Toolkit.getDefaultToolkit().getImage(bBall.getResource("/res/explode.png")); 
											}
											score.setText("Score: "+point);
										}
										else if(bally<=bullety-21 && bally>=bullety-35){
											if(score_update){
												score_update=false;
												point+=10;
												if(hit==true){
												consecutive_hit+=1;
												}
												if(Bullet_count>0){
													move_ball=false;
												}
												hit=true;
												ball_img = Toolkit.getDefaultToolkit().getImage(bBall.getResource("/res/explode.png"));
												if(gamemusic){ 
												try {
													BufferedInputStream audioFile = new BufferedInputStream(bBall.getResourceAsStream("/res/hit.wav"));
													AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
													Clip hclip = AudioSystem.getClip();
													hclip.open(audioInputStream);
													hclip.start();
												} catch (Exception ex) {
													ex.printStackTrace();
												}
											}
											}
											score.setText("Score: "+point);
											
										}
										else{
											hit=false;
										}
										
									  }
		
								}
								
								else{
									ball_img = Toolkit.getDefaultToolkit().getImage(bBall.getResource("/res/ball.png")); 
									score_update=true;
									bullet_fire=false;
									a = 0;
									  if(Bullet_count<=0 && point!=target && consecutive_hit!=2){
										  
										clip.stop();
										  if(move_ball){
											move_ball=false;
											game_over.setVisible(true);
											bullet_fire=false;
											if(bgmusic){
												try {
													BufferedInputStream audioFile = new BufferedInputStream(bBall.getResourceAsStream("/res/gameover.wav"));
													AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
													Clip hclip = AudioSystem.getClip();
													hclip.open(audioInputStream);
													hclip.start();
												} catch (Exception ex) {
													ex.printStackTrace();
												}
											}
												}
												}
											else{
												move_ball=true;
											}
								}
									if(level_changed){
										consecutive_hit=0;
										popup_msg="Level: "+level;
										show_popup=true;
										if(level == 2){
											ball_speed-=1;
											Bullet_count=6;
											target+=30;
										}
										else if(level == 3){
											ball_speed-=1;
											Bullet_count=5;
											target+=30;
										}
										else if(level == 4){
											ball_speed-=2;
											Bullet_count=5;
											target+=40;
										}
										else if(level == 5 ){
											ball_speed=3;
											Bullet_count=3;
											target+=30;
										}
										
										score.setText("Score: "+point);
										Level.setText("Level: "+level);
										Next_target.setText("Target: "+target);
										Bullet_remaining.setText("Bullet: "+Bullet_count);
										level_changed=false;
									}
			                    try{
									Thread.sleep(3);
								}
			                    catch(Exception e){}	
								repaint();
							}
					});
	   bullet.start();
	}
	public void update(Graphics g) {
		paint(g);
	}
    public void paint(Graphics g){	 
		bf=new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_RGB);
		try{
		animate(bf.getGraphics());
		g.drawImage(bf, 0, 0, null);
		}
		catch(Exception e){}
	  }
	  
	  public void animate(Graphics g){
		super.paint(g); 
		
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(),this);	
		g.drawImage(gun_img, -80, 141, 380, 420,this);	  
		bally=400-x;

		
		g.setColor(Color.BLACK);
		g.setColor(Color.RED);
		g.drawImage(ball_img, ballx, bally,50 ,50, this);


		//Bullet
		if(bullet_fire){
		bulletx=a+235;
		bullety=312;
		g.drawImage(bullet_img, bulletx, bullety, 30, 5,this);	  
		
		}
		g.setColor(Color.WHITE);
		g.fillRoundRect(20, 10, 750, 60, 20,30 );

		if(show_popup){
		g.fillRoundRect(340, 150, 120, 50, 20,30 );
		g.setColor(Color.BLACK);
		g.setFont(pop_font);
		g.drawString(popup_msg, 357, 180);
		}

		if(startup_screen){
			g.setColor(Color.WHITE);
			g.setFont(pop_font);
			g.drawString("Click To Start Playing", 280, 530);
		}

	}	
}