package MIB;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Card extends JPanel{

	private static final long serialVersionUID = 1L;
	private static String[] data = new String[7];
	private static final String[] metadata = {
			"Opis:",
			"Tip:",
			"MTU:",
			"Brzina interfejsa:",
			"Fizicka adresa:",
			"Administrativni status:",
			"Operativni status:"
			};
	public Font dot_font = new Font("Texas", Font.BOLD, 20);
	
	Card(){
		super();
		setLayout(new GridLayout(7,2));	
	}
	
	public String[] getColumns() {
		return metadata;
	}
	
	public JPanel fill(String[] _data) {
		
		for(int i = 0; i < 7; i++) {
			data[i] = _data[i];
			add(new JLabel(metadata[i]));
			if(i == 5 || i == 6) {
				JLabel dot = new JLabel("•");
				dot.setFont(dot_font);
				if(data[i].equals("2"))
					dot.setForeground(Color.red);
				else if(data[i].equals("1"))
					dot.setForeground(Color.green);
				add(dot);
				
			}else
			add(new JLabel(data[i]));
		}
		this.setBorder(BorderFactory.createEmptyBorder(16,6,16,6));
		this.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		setVisible(true);
		return this;		
	}

	public static String[] getData() {
		return data;
	}

	public static String[] getMetadata() {
		return metadata;
	}
	
	
	
	
}
