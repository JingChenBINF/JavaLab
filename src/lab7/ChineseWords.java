package lab7;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;


public class ChineseWords extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JTextArea myArea = new JTextArea();
	private JButton cancelButton = new JButton("Cancel");
	private JButton startButton = new JButton("Start");
	private boolean cancel = false;
	private List<String> wordlist = new ArrayList<String>();
	public ChineseWords() throws Exception
	{
		super("Learning Chinese words!");
		setLayout(new BorderLayout());
		getContentPane().add( new JScrollPane( myArea), BorderLayout.CENTER);
		getContentPane().add(getBottomPanel(), BorderLayout.SOUTH);
		setSize(400,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(getMyMenuBar());
		setLocationRelativeTo(null);
		setVisible(true);
	}
	private JMenuBar getMyMenuBar()
	{
		JMenuBar jmenuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');
		jmenuBar.add(fileMenu);
		JMenuItem saveItem = new JMenuItem("Save");
		fileMenu.add(saveItem);
		saveItem.setMnemonic('S');
		saveItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				saveToFile();
			}
		});
		return jmenuBar;
	}
	private void saveToFile()
	{
		JFileChooser jfc = new JFileChooser();
		if (jfc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
		{
			return;
		}
		if (jfc.getSelectedFile() == null)
		{
			return;
		}
		File chosenFile = jfc.getSelectedFile();
		if (jfc.getSelectedFile().exists())
		{
			String message = "File" + jfc.getSelectedFile().getName() + "exits. Overwrite?";
			if (JOptionPane.showConfirmDialog(this, message) != JOptionPane.YES_OPTION)
			{
				return;
			}			
		}
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(chosenFile));
			for (int x = 0; x < wordlist.size();x++)
			{
				writer.write(wordlist.get(x)+"\n");
			}			
			writer.flush();
			writer.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, ex.getMessage(),"Could not write file", JOptionPane.ERROR_MESSAGE);
		}
	}
	public JPanel getBottomPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		startButton.addActionListener(new StartActionListener());
		cancelButton.addActionListener(new CancelActionListener());
		panel.add(startButton);
		panel.add(cancelButton);
		return panel;
	}
	private class CancelActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			cancel = true;
		}
	}
	private class StartActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			cancel = false;
			startButton.setEnabled(false);
			cancelButton.setEnabled(true);
			myArea.setText("Let's start learning Chinese!" + "\n");
			wordlist.add("Let's start learning Chinese!" + "\n");
			new Thread(new StartActionRunnable()).start();
		}
	}
	private class StartActionRunnable implements Runnable
	{
		public void run()
		{
			try
			{
				int words = 0;
				while(! cancel && words < 100)
				{
					words ++;
					String aword = generateaword();
					addToArea(aword);
					wordlist.add(aword);
					Thread.sleep(3000);
					
				}
			}
			catch(Exception ex)
			{
				myArea.setText(ex.getMessage());
				ex.printStackTrace();
			}
			try
			{
				SwingUtilities.invokeLater(new Runnable()
				{
					public void run()
					{
						startButton.setEnabled(true);
						cancelButton.setEnabled(false);
					}
				});
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	public String generateaword() throws Exception
	{
		String line = null;
		List<String> wordlist= new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(new File("/Users/chenjing/Downloads/ChineseEnglishWords.txt")));
		while ((line = reader.readLine()) != null)
		{
			wordlist.add(line);
		}
		reader.close();
		Random random = new Random();
		int r = random.nextInt(wordlist.size());
		String s = wordlist.get(r);
		return s;
	}
	private void addToArea(String s)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run() 
			{
				myArea.append(s + "\n");
			}
		}
		);
	}
	public static void main(String[] args) throws Exception
	{
		new ChineseWords();
	}
	
}
