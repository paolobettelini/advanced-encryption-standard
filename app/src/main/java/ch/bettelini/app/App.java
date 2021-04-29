package ch.bettelini.app;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


import java.awt.Color;
import java.awt.Font;

public class App extends JFrame {
	
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(() -> {
			new App().setVisible(true);
		});
	}

	@SuppressWarnings("unchecked")
	public App() {
		super("AES Encryption/Decryption");

		Font calibri = new Font("Calibri", Font.PLAIN, 14);

		JLabel keyLabel = new JLabel("Secret Key");
		keyLabel.setFont(calibri);
		keyLabel.setForeground(Color.WHITE);
		
		JTextField keyField = new JTextField();
		keyField.setFont(calibri);
		keyField.setBackground(Color.GRAY);
		keyField.setForeground(Color.WHITE);
		keyField.setBorder(null);
		
		JLabel keyOptionsLabel = new JLabel("Encoding");
		keyOptionsLabel.setFont(calibri);
		keyOptionsLabel.setForeground(Color.WHITE);
		
		JRadioButton keyOption1 = new JRadioButton("Plain Text", true);
		JRadioButton keyOption2 = new JRadioButton("Base64");
		JRadioButton keyOption3 = new JRadioButton("Hex");
		
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
		inputLabel.setFont(calibri);
		inputLabel.setForeground(Color.WHITE);
		
		JTextField inputField = new JTextField();
		inputField.setFont(calibri);
		inputField.setBackground(Color.GRAY);
		inputField.setForeground(Color.WHITE);
		inputField.setBorder(null);
		
		JLabel inputOptionsLabel = new JLabel("Encoding");
		inputOptionsLabel.setFont(calibri);
		inputOptionsLabel.setForeground(Color.WHITE);
		
		JRadioButton inputOption1 = new JRadioButton("Plain Text", true);
		JRadioButton inputOption2 = new JRadioButton("Base64");
		JRadioButton inputOption3 = new JRadioButton("Hex");
		
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
		outputLabel.setFont(calibri);
		outputLabel.setForeground(Color.WHITE);
		
		JTextField outputField = new JTextField();
		outputField.setFont(calibri);
		outputField.setBackground(Color.GRAY);
		outputField.setForeground(Color.WHITE);
		outputField.setBorder(null);
		
		JLabel outputOptionsLabel = new JLabel("Encoding");
		outputOptionsLabel.setFont(calibri);
		outputOptionsLabel.setForeground(Color.WHITE);
		
		JRadioButton outputOption1 = new JRadioButton("PlainText");
		JRadioButton outputOption2 = new JRadioButton("Base64", true);
		JRadioButton outputOption3 = new JRadioButton("Hex");
		
		outputOption1.setBackground(Color.DARK_GRAY);
		outputOption2.setBackground(Color.DARK_GRAY);
		outputOption3.setBackground(Color.DARK_GRAY);
		
		outputOption1.setForeground(Color.WHITE);
		outputOption2.setForeground(Color.WHITE);
		outputOption3.setForeground(Color.WHITE);
		
		ButtonGroup outputGroup = new ButtonGroup();
		outputGroup.add(outputOption1);
		outputGroup.add(outputOption2);
		outputGroup.add(outputOption3);
		
		JLabel actionLabel = new JLabel("Action");
		actionLabel.setForeground(Color.WHITE);
		actionLabel.setFont(calibri);
		
		JRadioButton encryption = new JRadioButton("Encryption", true);
		JRadioButton decryption = new JRadioButton("Decryption");
		
		encryption.setForeground(Color.WHITE);
		decryption.setForeground(Color.WHITE);
		
		encryption.setBackground(Color.DARK_GRAY);
		decryption.setBackground(Color.DARK_GRAY);
		
		ButtonGroup actionGroup = new ButtonGroup();
		actionGroup.add(encryption);
		actionGroup.add(decryption);
		
		JLabel paddingLabel = new JLabel("Padding");
		paddingLabel.setFont(calibri);
		paddingLabel.setForeground(Color.WHITE);
		
		JComboBox<String> paddingBox = new JComboBox<>(new String[] {
			"PKCS#7",
			"ANSI X9.23",
			"ISO 10126",
			"ISO/IEC 7816-4"
		});
		paddingBox.setBackground(Color.GRAY);
		paddingBox.setForeground(Color.WHITE);
		
		JLabel modeLabel = new JLabel("Mode");
		modeLabel.setFont(calibri);
		modeLabel.setForeground(Color.WHITE);
		
		JComboBox<String> modeBox = new JComboBox<>(new String[] {
			"CBC",
			"ECB"
		});
		modeBox.setBackground(Color.GRAY);
		modeBox.setForeground(Color.WHITE);
		
		JLabel ivLabel = new JLabel("Initialization Vector");
		ivLabel.setFont(calibri);
		ivLabel.setForeground(Color.WHITE);
		
		JTextField ivField = new JTextField();
		ivField.setFont(calibri);
		ivField.setBackground(Color.GRAY);
		ivField.setForeground(Color.WHITE);
		ivField.setBorder(null);
		ivField.setText("AAAAAAAAAAAAAAAAAAAAAA==");

		JLabel ivOptionsLabel = new JLabel("Encoding");
		ivOptionsLabel.setFont(calibri);
		ivOptionsLabel.setForeground(Color.WHITE);
		
		JRadioButton ivOption1 = new JRadioButton("PlainText");
		JRadioButton ivOption2 = new JRadioButton("Base64", true);
		JRadioButton ivOption3 = new JRadioButton("Hex");
		
		ivOption1.setBackground(Color.DARK_GRAY);
		ivOption2.setBackground(Color.DARK_GRAY);
		ivOption3.setBackground(Color.DARK_GRAY);
		
		ivOption1.setForeground(Color.WHITE);
		ivOption2.setForeground(Color.WHITE);
		ivOption3.setForeground(Color.WHITE);
		
		ButtonGroup ivGroup = new ButtonGroup();
		ivGroup.add(ivOption1);
		ivGroup.add(ivOption2);
		ivGroup.add(ivOption3);
		
		keyLabel.setBounds(15, 5, 100, 30);
		keyField.setBounds(15, 35, 200, 40);
		keyOptionsLabel.setBounds(15, 85, 100, 30);
		keyOption1.setBounds(15, 110, 100, 30);
		keyOption2.setBounds(15, 140, 100, 30);
		keyOption3.setBounds(15, 170, 100, 30);
		
		actionLabel.setBounds(15, 225, 100, 30);
		encryption.setBounds(15, 250, 100, 30);
		decryption.setBounds(15, 280, 100, 30);

		outputLabel.setBounds(15, 335, 100, 30);
		outputField.setBounds(15, 365, 705, 40);
		outputOptionsLabel.setBounds(15, 415, 100, 30);
		outputOption1.setBounds(15, 440, 100, 30);
		outputOption2.setBounds(15, 470, 100, 30);
		outputOption3.setBounds(15, 500, 100, 30);
		
		inputLabel.setBounds(265, 5, 100, 30);
		inputField.setBounds(265, 35, 200, 40);
		inputOptionsLabel.setBounds(265, 85, 100, 30);
		inputOption1.setBounds(265, 110, 100, 30);
		inputOption2.setBounds(265, 140, 100, 30);
		inputOption3.setBounds(265, 170, 100, 30);

		modeLabel.setBounds(265, 225, 125, 30);
		modeBox.setBounds(265, 250, 100, 30);

		ivLabel.setBounds(520, 5, 200, 30);
		ivField.setBounds(520, 35, 200, 40);
		ivOptionsLabel.setBounds(520, 85, 100, 30);
		ivOption1.setBounds(520, 110, 100, 30);
		ivOption2.setBounds(520, 140, 100, 30);
		ivOption3.setBounds(520, 170, 100, 30);
		
		paddingLabel.setBounds(520, 225, 100, 30);
		paddingBox.setBounds(520, 250, 125, 30);
		
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
		add(outputOption3);
		
		add(actionLabel);
		add(encryption);
		add(decryption);
		
		add(paddingLabel);
		add(paddingBox);
		
		add(modeLabel);
		add(modeBox);
		
		add(ivLabel);
		add(ivField);
		add(ivOptionsLabel);
		add(ivOption1);
		add(ivOption2);
		add(ivOption3);
		
		getContentPane().setBackground(Color.DARK_GRAY);
		setSize(750, 575);
		setResizable(false);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// GUI/AESHandler Interaction
		
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
		
		ivField.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void insertUpdate(DocumentEvent e) { ivChange(); }
			
			@Override
			public void removeUpdate(DocumentEvent e) { ivChange(); }
			
			@Override
			public void changedUpdate(DocumentEvent e) {}
			
			public void ivChange() {
				handler.setIV(ivField.getText());
				update.run();
			}
			
		});
		
		paddingBox.addActionListener(e -> {
			handler.setPadding((String)((JComboBox<String>)e.getSource()).getSelectedItem());
			
			update.run();
		});
		
		modeBox.addActionListener(e -> {
			String mode = (String)((JComboBox<String>)e.getSource()).getSelectedItem();
			
			handler.setMode(mode);
			
			ivField.setEnabled(mode.equals("CBC"));
			
			update.run();
		});
		
		keyOption1.addActionListener   (e -> { handler.setKeyEncoding(0);    update.run(); });
		keyOption2.addActionListener   (e -> { handler.setKeyEncoding(1);    update.run(); });
		keyOption3.addActionListener   (e -> { handler.setKeyEncoding(2);    update.run(); });
		inputOption1.addActionListener (e -> { handler.setInputEncoding(0);  update.run(); });
		inputOption2.addActionListener (e -> { handler.setInputEncoding(1);  update.run(); });
		inputOption3.addActionListener (e -> { handler.setInputEncoding(2);  update.run(); });
		outputOption1.addActionListener(e -> { handler.setOutputEncoding(0); update.run(); });
		outputOption2.addActionListener(e -> { handler.setOutputEncoding(1); update.run(); });
		outputOption3.addActionListener(e -> { handler.setOutputEncoding(2); update.run(); });
		ivOption1.addActionListener    (e -> { handler.setIvEncoding(0);     update.run(); });
		ivOption2.addActionListener    (e -> { handler.setIvEncoding(1);     update.run(); });
		ivOption3.addActionListener    (e -> { handler.setIvEncoding(2);     update.run(); });
		encryption.addActionListener   (e -> { handler.setEncryption();	     update.run(); });
		decryption.addActionListener   (e -> { handler.setDecryption();	     update.run(); });
	}
	
}
