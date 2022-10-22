package main;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class WordGame implements ActionListener, KeyListener {
	private JFrame frame;
	private JTextField text_field;
	private JButton[] letter_buttons;
	private HashMap<String, Integer> letter_index_map;
	private ArrayList<String> word_list;
	private String word;
	
	public WordGame() {
		this.frame = new JFrame("Word Game");
		this.text_field = new JTextField();
		this.letter_buttons = new JButton[26];
		this.letter_index_map = new HashMap<String, Integer>();
		this.word_list = new ArrayList<String>();
		readWordsFromFile();
		this.word = selectWord();
	}

	public void playGame() {
		this.createGame();
	}
	
	private void createGame() {
		int x = 25, y = 25;
		JLabel lb_1 = new JLabel("Welcome to Word Game");
		
		char[] letters = {'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P',
				'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C',
				'V', 'B', 'N', 'M'};
		for (int i = 0; i < 26; i++) {
			this.letter_buttons[i] = new JButton(String.valueOf(letters[i]));
			this.letter_index_map.put(String.valueOf(letters[i]), i);
			this.letter_buttons[i].addActionListener(this);
		}
		
		lb_1.setBounds(x, y, 300, 25); y += 50;
		this.text_field.setBounds(x, y, 525, 25); y += 50;
		this.text_field.setText(createGuessWord());
		this.text_field.addKeyListener(this);
		this.text_field.setEditable(false);
		
		int i = 0;
		for (; i < 10; i++) {
			this.letter_buttons[i].setBounds(x - 5 + 55*i, y, 50, 25);
		}
		y += 30;
		for (; i < 19; i++) {
			this.letter_buttons[i].setBounds(x + 25 + 55*(i - 10), y, 50, 25);
		}
		y += 30;
		for (; i < 26; i++) {
			this.letter_buttons[i].setBounds(x + 55 + 55*(i - 19), y, 50, 25);
		}
		y += 50;
		
		this.frame.add(lb_1);
		this.frame.add(this.text_field);
		
		for (i = 0; i < 26; i++) {
			this.frame.add(this.letter_buttons[i]);
		}
		
		this.frame.setSize(600, 300);
		this.frame.setLayout(null);
		this.frame.setVisible(true);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void readWordsFromFile() {
		try{
			File word_file = new File("../res/words.txt");
			Scanner scanner = new Scanner(word_file);
			while (scanner.hasNextLine()) {
				this.word_list.add(scanner.nextLine());
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("Words file not found");
		}
	}

	private String selectWord() {
		if (this.word_list.size() > 0) {
			Random random = new Random();
			return this.word_list.get(random.nextInt(this.word_list.size()));
		}
		else {
			return "ERROR FILE NOT FOUND";
		}
	}

	private String createGuessWord() {
		String tmp = "";
		for (int i = 0; i < this.word.length(); i++) {
			if (this.word.substring(i, i + 1).equals(" ")) {
				tmp += " ";
			}else {
				tmp += "*";
			}
		}
		return tmp;
	}
	
	private boolean checkLetter(String letter) {
		for (int i = 0; i < this.word.length(); i++) {
			if (this.word.substring(i, i + 1).equals(letter)) {
				return true;
			}
		}
		return false;
	}
	
	private void changeGuessWord(String letter) {
		for (int i = 0; i < this.word.length(); i++) {
			if (this.word.substring(i, i + 1).equals(letter)) {
				String tmp = this.text_field.getText();
				String guess_word = tmp.substring(0, i) + letter + tmp.substring(i + 1);
				this.text_field.setText(guess_word);
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton tmp = (JButton)e.getSource();
		if(checkLetter(tmp.getText())) {
			changeGuessWord(tmp.getText());
		}
		tmp.setEnabled(false);
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {
		char c = e.getKeyChar();
		if ((c >= 'a' && c <= 'z') || c >= 'A' && c <= 'Z') {
			String text = (Character.toString(c)).toUpperCase();
			if(checkLetter(text)) {
				changeGuessWord(text);
			}
			this.letter_buttons[this.letter_index_map.get(text)].setEnabled(false);
		}
    }
}
