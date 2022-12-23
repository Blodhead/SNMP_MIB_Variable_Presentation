package MIB;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;


public class GUI extends JFrame implements ItemListener, Runnable{
   
	private static JScrollPane scroll;
	private static JPanel main_panel = new JPanel();
	
    private static Card[] cards = new Card[10];
    private static JLabel[] routers = new JLabel[3];
	private Protocol_info info;
	private int max = 0;
	
	public Font ruter_font = new Font("Texas", Font.BOLD, 20);
	public Font interface_font = new Font("Texas", Font.BOLD, 18);
	
	private JPanel R = new JPanel(new GridLayout(1, 3));
	private JPanel app = new JPanel(new GridLayout(1, 3));
	
	public GUI() {
		super();
		setTitle("MIB Interface Variable Lookup");
		setSize(1500, 600); 
		
		try {
			for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		}catch(Exception e) {}
		
		
		main_panel.setLayout(new BorderLayout());
		
		for(int i = 0; i < 3; i++) {
			routers[i] = new JLabel("Router " + (i+1));
			routers[i].setFont(ruter_font);
			routers[i].setVerticalAlignment(JLabel.CENTER);
			routers[i].setHorizontalAlignment(JLabel.CENTER);
			routers[i].setBorder(BorderFactory.createEmptyBorder(0,0,1,1));
			routers[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			R.add(routers[i]);
		}
		scroll = new JScrollPane(main_panel);
		add(scroll);
		main_panel.add(R, BorderLayout.NORTH);
		main_panel.add(app, BorderLayout.CENTER);
		
		app.setBorder(BorderFactory.createEmptyBorder(0,0,1,1));
		app.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		info = new Protocol_info();
		
		for(int i = 0; i < 3; i++)
		try {
			if(max < info.getInfo(i).length) max = info.getInfo(i).length;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		//reloadData();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private JPanel[][] window = new JPanel[3][1];
	
	private void reloadData() {
		String[][] data = new String[10][];
		int item_counter = 0;
		for(int i = 0; i < 3; i++) {
			
			try {
				data = info.getInfo(i);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			window[i] = new JPanel[1];
			window[i][0] = new JPanel();
			
			cards = new Card[data.length];
			window[i][0].setLayout(new GridLayout(max*2,1));
			
			for(int j = 0; j < data.length; j++) {
				
				cards[j] = new Card();
				
				
				JLabel inter = new JLabel("Interface " + (j+1));
				inter.setFont(interface_font);
				window[i][0].add(inter);
				
				JPanel temp = new JPanel();
				temp = cards[j].fill(data[j]);
				window[i][0].add(temp);
				
				inter.setVerticalAlignment(JLabel.CENTER);
				inter.setHorizontalAlignment(JLabel.CENTER);
				inter.setBorder(BorderFactory.createEmptyBorder(8,6,-1,6));
				inter.setBorder(BorderFactory.createLineBorder(Color.BLUE));				
				item_counter = item_counter + 1;
			}
			
			while(item_counter < max) {
				window[i][0].add(new JLabel(" "));
				window[i][0].add(new JLabel(" "));
				item_counter++;
			}

		}

		app.removeAll();
		
		for(int i = 0; i < 3; i++) {
			app.add(window[i][0]);
		}
		

		setVisible(true);
		repaint();
		validate();

	}

	@Override
	public void run() {
		while(true) {
			try {
				reloadData();
				setVisible(true);
				revalidate();
				repaint();
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}
}

