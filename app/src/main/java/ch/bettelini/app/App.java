package ch.bettelini.app;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Color;
import java.awt.Font;

public class App extends JFrame {

	public static void main(String[] args) {
		new App();
	}
	
	public App() {
		super("AES 128/192/256-bits ECB PKCS#5/PKCS#7 Encryption/Decryption");

		Font calibri = new Font("Calibri", Font.PLAIN, 14);

		JLabel keyLabel = new JLabel("Secret Key");
		keyLabel.setBounds(60, 5, 100, 30);
		keyLabel.setFont(calibri);
		keyLabel.setForeground(Color.WHITE);

		JTextField keyField = new JTextField();
		keyField.setBounds(15, 35, 150, 40);
		keyField.setFont(calibri);
		keyField.setBackground(Color.GRAY);
		keyField.setForeground(Color.WHITE);
		keyField.setBorder(null);

		JLabel keyOptionsLabel = new JLabel("Encoding");
		keyOptionsLabel.setBounds(15, 85, 100, 30);
		keyOptionsLabel.setFont(calibri);
		keyOptionsLabel.setForeground(Color.WHITE);

		JRadioButton keyOption1 = new JRadioButton("Plain Text", true);
		JRadioButton keyOption2 = new JRadioButton("Base64");
		JRadioButton keyOption3 = new JRadioButton("Hex");
		
		keyOption1.setBounds(15, 110, 100, 30);
		keyOption2.setBounds(15, 140, 100, 30);
		keyOption3.setBounds(15, 170, 100, 30);

		keyOption1.setBackground(Color.DARK_GRAY);
		keyOption2.setBackground(Color.DARK_GRAY);
		keyOption3.setBackground(Color.DARK_GRAY);

		keyOption1.setForeground(Color.WHITE);
		keyOption2.setForeground(Color.WHITE);
		keyOption3.setForeground(Color.WHITE);

		ButtonGroup keyGroup = new ButtonGroup();
		keyGroup.add(keyOption1);
		keyGroup.add(keyOption2);
		keyGroup.add(keyOption3);

		JLabel inputLabel = new JLabel("Input");
		inputLabel.setBounds(270, 5, 100, 30);
		inputLabel.setFont(calibri);
		inputLabel.setForeground(Color.WHITE);

		JTextField inputField = new JTextField();
		inputField.setBounds(215, 35, 150, 40);
		inputField.setFont(calibri);
		inputField.setBackground(Color.GRAY);
		inputField.setForeground(Color.WHITE);
		inputField.setBorder(null);

		JLabel inputOptionsLabel = new JLabel("Encoding");
		inputOptionsLabel.setBounds(215, 85, 100, 30);
		inputOptionsLabel.setFont(calibri);
		inputOptionsLabel.setForeground(Color.WHITE);

		JRadioButton inputOption1 = new JRadioButton("Plain Text", true);
		JRadioButton inputOption2 = new JRadioButton("Base64");
		JRadioButton inputOption3 = new JRadioButton("Hex");
		
		inputOption1.setBounds(215, 110, 100, 30);
		inputOption2.setBounds(215, 140, 100, 30);
		inputOption3.setBounds(215, 170, 100, 30);

		inputOption1.setBackground(Color.DARK_GRAY);
		inputOption2.setBackground(Color.DARK_GRAY);
		inputOption3.setBackground(Color.DARK_GRAY);

		inputOption1.setForeground(Color.WHITE);
		inputOption2.setForeground(Color.WHITE);
		inputOption3.setForeground(Color.WHITE);

		ButtonGroup inputGroup = new ButtonGroup();
		inputGroup.add(inputOption1);
		inputGroup.add(inputOption2);
		inputGroup.add(inputOption3);

		JLabel outputLabel = new JLabel("Output");
		outputLabel.setBounds(470, 5, 100, 30);
		outputLabel.setFont(calibri);
		outputLabel.setForeground(Color.WHITE);

		JTextArea outputField = new JTextArea();
		outputField.setBounds(415, 35, 300, 40);
		outputField.setFont(calibri);
		outputField.setBackground(Color.GRAY);
		outputField.setForeground(Color.WHITE);
		outputField.setBorder(null);
		outputField.setEnabled(false);

		JLabel outputOptionsLabel = new JLabel("Encoding");
		outputOptionsLabel.setBounds(415, 85, 100, 30);
		outputOptionsLabel.setFont(calibri);
		outputOptionsLabel.setForeground(Color.WHITE);

		JRadioButton outputOption1 = new JRadioButton("Base64", true);
		JRadioButton outputOption2 = new JRadioButton("Hex");
		
		outputOption1.setBounds(415, 110, 100, 30);
		outputOption2.setBounds(415, 140, 100, 30);

		outputOption1.setBackground(Color.DARK_GRAY);
		outputOption2.setBackground(Color.DARK_GRAY);

		outputOption1.setForeground(Color.WHITE);
		outputOption2.setForeground(Color.WHITE);

		ButtonGroup outputGroup = new ButtonGroup();
		outputGroup.add(outputOption1);
		outputGroup.add(outputOption2);

		add(keyLabel);
		add(keyField);
		add(keyOptionsLabel);
		add(keyOption1);
		add(keyOption2);
		add(keyOption3);

		add(inputLabel);
		add(inputField);
		add(inputOptionsLabel);
		add(inputOption1);
		add(inputOption2);
		add(inputOption3);

		add(outputLabel);
		add(outputField);
		add(outputOptionsLabel);
		add(outputOption1);
		add(outputOption2);

		getContentPane().setBackground(Color.DARK_GRAY);
		setSize(/*600*/1000, 425);
		setResizable(false);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		// AES Interaction

		AESHandler handler = new AESHandler();

		Runnable update = () -> {
			outputField.setText(handler.getOutput());
		};

		inputField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) { keyChange(); }

			@Override
			public void removeUpdate(DocumentEvent e) { keyChange(); }

			@Override
			public void changedUpdate(DocumentEvent e) {}

			public void keyChange() {
				handler.setInput(inputField.getText());
				update.run();
			}
			
		});

		keyField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) { inputChange(); }

			@Override
			public void removeUpdate(DocumentEvent e) { inputChange(); }

			@Override
			public void changedUpdate(DocumentEvent e) {}

			public void inputChange() {
				handler.setKey(keyField.getText());
				update.run();
			}

		});

		keyOption1.addActionListener   (e -> { handler.setKeyEncoding(0);    update.run(); });
		keyOption2.addActionListener   (e -> { handler.setKeyEncoding(1);    update.run(); });
		keyOption3.addActionListener   (e -> { handler.setKeyEncoding(2);    update.run(); });
		inputOption1.addActionListener (e -> { handler.setInputEncoding(0);  update.run(); });
		inputOption2.addActionListener (e -> { handler.setInputEncoding(1);  update.run(); });
		inputOption3.addActionListener (e -> { handler.setInputEncoding(2);  update.run(); });
		outputOption1.addActionListener(e -> { handler.setOutputEncoding(0); update.run(); });
		outputOption2.addActionListener(e -> { handler.setOutputEncoding(1); update.run(); });
	}

}