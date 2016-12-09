import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import javax.swing.LookAndFeel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.ActionEvent;

import com.jtattoo.*;

import de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel;

import javax.swing.Action;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.JTextComponent;

import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.concurrent.BrokenBarrierException;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;

import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JCheckBox;

public class MainFrame {
	static MainFrame window;
	private Font F1 = new Font("Digitalino", Font.PLAIN, 18);
	private Font F2 = new Font("Digitalino", Font.PLAIN, 22);
	private Font F3 = new Font("FreePixel", Font.PLAIN, 10);
	private Font F4 = new Font("DS-Digital", Font.PLAIN, 100);
	private Font F5 = new Font("Digitalino", Font.PLAIN, 12);
	private JFrame frmSudokuSolver;
	private final JPanel panel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	public JButton btnBrowse;
	public JButton btnSolve;
	public JButton btnStop;
	public Thread SWS;
	public JCheckBox chckbxDelaySolving;
	public JCheckBox chckbxStepByStep;
	public JLabel lblMs;
	Runnable R;
	Thread Solve;
	public int[] buttonINT;
	public int DelayValue;
	public final JButtonWID[][] button = new JButtonWID[9][9];
	JLabel lblTime = new JLabel("Time");
	public Checker MasterCheck = new Checker();
	public InputParser I1 = new InputParser();
	final JLabel statusLabel = new JLabel("status");
	public int ButtonPressedi;
	public int ButtonPressedj;
	public int Manual = 0;
	String[] Options = { "Manually", "BFS", "DFS", "A*" };// "A* Algorithm",
	public JTextField textField_4;

	// "Depth First Algorithm", "Manually"
	// };

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new MainFrame();
					window.frmSudokuSolver.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws ParseException
	 */
	public MainFrame() throws ParseException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws ParseException
	 */
	private void initialize() throws ParseException {

		// ////////////////////////////////////////////
		// //Defining the form and it's location...////
		// ////////////////////////////////////////////
		frmSudokuSolver = new JFrame();
		frmSudokuSolver.setIconImage(Toolkit.getDefaultToolkit().getImage(
				MainFrame.class.getResource("/images/Icon.png")));
		frmSudokuSolver.setResizable(false);
		frmSudokuSolver.setTitle("Sudoku Solver");
		frmSudokuSolver.setBounds(100, 100, 815, 550);
		frmSudokuSolver.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSudokuSolver.getContentPane().add(panel);
		try {
			// UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
			UIManager
					.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		} catch (Exception E) {
			// TODO Auto-generated catch block

		}

		final JTextField input = new JTextField();

		panel.setLayout(null);
		// /////////////////////////////////
		buttonINT = new int[81];
		btnStop = new JButton("Stop");
		btnStop.setBounds(628, 356, 97, 25);
		btnStop.setFont(F2);
		panel.add(btnStop);
		btnStop.setVisible(false);
		btnStop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(Manual==0)
				Solve.stop();
				SWS.stop();
				chckbxDelaySolving.setEnabled(true);
				chckbxStepByStep.setEnabled(true);
				textField_4.setEditable(true);
				btnBrowse.setEnabled(true);
				btnSolve.setEnabled(true);
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						btnStop.setVisible(false);
					}
				});
			}
		});
		// /////////////////////////////////
		// ///Adding the status Bar////////
		// /////////////////////////////////
		JPanel statusPanel = new JPanel();
		statusPanel.setBorder((Border) new BevelBorder(BevelBorder.LOWERED));
		frmSudokuSolver.getContentPane().add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setPreferredSize(new Dimension(frmSudokuSolver.getWidth(),
				16));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));

		statusLabel.setFont(F3);
		;
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(statusLabel);
		// ///////////////////////////

		panel.add(input);

		final JComboBox comboBox = new JComboBox(Options);
		comboBox.setBounds(556, 72, 165, 22);
		comboBox.setFont(F1);
		panel.add(comboBox);

		btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser BrowseFile = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"Text files .txt Only", "txt");
				BrowseFile.setFileFilter(filter);
				BrowseFile.setDialogTitle("Browse game input file");
				if (BrowseFile.showOpenDialog(frmSudokuSolver) == JFileChooser.APPROVE_OPTION) {
					statusLabel.setText("getSelectedFile() : "
							+ BrowseFile.getSelectedFile());
					try {
						I1.ReadAndCheckEveryThing(BrowseFile.getSelectedFile()
								.toString(), window, MasterCheck);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Input File Error");
					}
				} else {
					System.out.println("No Selection ");
				}

			}
		});
		btnBrowse.setBounds(556, 122, 97, 25);
		// btnBrowse.setBounds(556, 122, 208, 98);
		btnBrowse.setFont(F1);
		// Image img;
		// try {
		// img =
		// ImageIO.read(MainFrame.class.getResource("/images/Browse.png"));
		// btnBrowse.setIcon(new ImageIcon(img));
		// btnBrowse.setBorder(BorderFactory.createEmptyBorder());
		// btnBrowse.setContentAreaFilled(false);
		// } catch (IOException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }

		panel.add(btnBrowse);
		// /////////////////////////
		// ///////Solve Button + it's handler..////
		// //////////////////////////////////
		btnSolve = new JButton("Solve");
		btnSolve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Runnable SW = new StopWatch(window);
				SWS=new Thread(SW);
				SWS.start();
				Manual = 0;
				btnBrowse.setEnabled(false);
				
				chckbxDelaySolving.setEnabled(false);
				chckbxStepByStep.setEnabled(false);
				textField_4.setEditable(false);
				if (comboBox.getSelectedIndex() == 2) {
					int count = 0;
					for (int i = 0; i < 9; i++) {
						for (int j = 0; j < 9; j++) {
							if (button[i][j].getText().toString().equals(""))
								buttonINT[count] = 0;
							else
								buttonINT[count] = Integer
										.parseInt(button[i][j].getText()
												.toString());
							count++;
						}
					}

					R = new DepthFirstAlgo(window);
					Solve = new Thread(R);
					Solve.start();
					btnStop.setVisible(true);
				} else if (comboBox.getSelectedIndex() == 0)
					Manual = 1;

				else if (comboBox.getSelectedIndex() == 1) {
					int count = 0;
					for (int i = 0; i < 9; i++) {
						for (int j = 0; j < 9; j++) {
							if (button[i][j].getText().toString().equals(""))
								buttonINT[count] = 0;
							else
								buttonINT[count] = Integer
										.parseInt(button[i][j].getText()
												.toString());
							count++;
						}
					}
					R = new BFA(window);
					Solve = new Thread(R);
					Solve.start();
					btnStop.setVisible(true);
				} else if (comboBox.getSelectedIndex() == 3) {
					int count = 0;
					for (int i = 0; i < 9; i++) {
						for (int j = 0; j < 9; j++) {
							if (button[i][j].getText().toString().equals(""))
								buttonINT[count] = 0;
							else
								buttonINT[count] = Integer
										.parseInt(button[i][j].getText()
												.toString());
							count++;
						}
					}
					R = new aStarAlgo(window);
					Solve = new Thread(R);
					Solve.start();
					btnStop.setVisible(true);
				}
				
				
			
				btnBrowse.setEnabled(false);

			}

		});
		btnSolve.setBounds(665, 122, 97, 25);
		btnSolve.setFont(F1);
		panel.add(btnSolve);
		// /////////////////////////////////
		// //////Background grid ;)/////////
		// /////////////////////////////////
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(MainFrame.class
				.getResource("/images/Untitled-3.png")));
		lblNewLabel.setBounds(-9, -36, 827, 561);
		panel.add(lblNewLabel);
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblTime.setText("");
		lblTime.setBounds(563, 388, 220, 86);
		lblTime.setFont(F4);
		panel.add(lblTime);

		JButton btnHowToPlay = new JButton("How to play");
		btnHowToPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String pt1 = "<html><body width='300'><h1>How To Play !</h1>"
						+ "<p><font size='5'>Traditional Sudoku is a 9x9 puzzle grid made up of nine 3x3 regions. Each region, row, and column contains nine cells each.</p>"
						+ "<p><font size='6'>Complete the Sudoku puzzle so that each and every row, column, and region contains the numbers one through nine only once.<br><br>";
				JOptionPane.showMessageDialog(null, pt1);
			}
		});
		btnHowToPlay.setBounds(573, 174, 180, 60);
		btnHowToPlay.setFont(F2);
		panel.add(btnHowToPlay);

		chckbxDelaySolving = new JCheckBox("Delay each step by");
		chckbxDelaySolving.setBounds(516, 313, 152, 25);
		chckbxDelaySolving.setFont(F5);
		chckbxDelaySolving.setVisible(false);
		panel.add(chckbxDelaySolving);
		chckbxDelaySolving.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (chckbxDelaySolving.isSelected()) {
					textField_4.setVisible(true);
					lblMs.setVisible(true);
				} else {
					textField_4.setVisible(false);
					lblMs.setVisible(false);
					DelayValue = 0;
				}
			}
		});

		textField_4 = new JTextField();
		textField_4.setFont(F5);
		textField_4.setBounds(684, 314, 60, 22);
		textField_4.setVisible(false);
		panel.add(textField_4);
		textField_4.setColumns(10);
		textField_4.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub

				if (textField_4.getText().equals(""))
					DelayValue = 0;
				else
					try {
						DelayValue = Integer.parseInt(textField_4.getText());
					} catch (Exception e) {
						SwingUtilities.invokeLater(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								textField_4
										.setText(textField_4.getText()
												.substring(
														0,
														textField_4.getText()
																.length() - 1));
								DelayValue = 0;
							}
						});

					}

			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				if (textField_4.getText().equals(""))
					DelayValue = 0;
				else
					try {
						DelayValue = Integer.parseInt(textField_4.getText());
					} catch (Exception e) {
						SwingUtilities.invokeLater(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								textField_4
										.setText(textField_4.getText()
												.substring(
														0,
														textField_4.getText()
																.length() - 1));
								DelayValue = 0;
							}
						});

					}
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				if (textField_4.getText().equals(""))
					DelayValue = 0;
				else
					try {
						DelayValue = Integer.parseInt(textField_4.getText());
					} catch (Exception e) {
						SwingUtilities.invokeLater(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								textField_4
										.setText(textField_4.getText()
												.substring(
														0,
														textField_4.getText()
																.length() - 1));
								DelayValue = 0;
							}
						});

					}

			}
		});
		chckbxStepByStep = new JCheckBox("Step by step");
		chckbxStepByStep.setBounds(516, 279, 130, 25);
		panel.add(chckbxStepByStep);
		chckbxStepByStep.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (chckbxStepByStep.isSelected()) {
					chckbxDelaySolving.setVisible(true);
					chckbxDelaySolving.setSelected(false);
				} else {
					chckbxDelaySolving.setVisible(false);
					textField_4.setVisible(false);
					lblMs.setVisible(false);
				}
			}
		});
		chckbxStepByStep.setFont(F5);
		lblMs = new JLabel("ms");
		lblMs.setBounds(755, 313, 41, 25);
		lblMs.setFont(F5);
		lblMs.setVisible(false);
		panel.add(lblMs);

		// //////////////////////////////////
		// ///Init of the buttons and there locations.../////
		// //Handlers to add value to solve manually later////
		// ///////////////////////////////////////////////

		int x = 10;
		int y = 10;
		int width = 50;
		int height = 50;
		int rowcount = 9;
		int test = 1;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				button[i][j] = new JButtonWID();
				panel.add(button[i][j]);
				button[i][j].ID = i;
				button[i][j].j = j;
				button[i][j].setFont(F2);
				button[i][j].setForeground(Color.darkGray);
				button[i][j].setBounds(x, y, width, height);

				test++;
				if (i < 3) {
					// //0,1,2 block
					if (j < 3) {
						button[i][j].BlockID = 0;
						MasterCheck.blocksIs[0][MasterCheck.blockscount[0]] = i;
						MasterCheck.blocksjs[0][MasterCheck.blockscount[0]] = j;
						MasterCheck.blockscount[0]++;

					} else if (j >= 3 && j < 6) {
						button[i][j].BlockID = 1;
						MasterCheck.blocksIs[1][MasterCheck.blockscount[1]] = i;
						MasterCheck.blocksjs[1][MasterCheck.blockscount[1]] = j;
						MasterCheck.blockscount[1]++;
					} else {
						button[i][j].BlockID = 2;
						MasterCheck.blocksIs[2][MasterCheck.blockscount[2]] = i;
						MasterCheck.blocksjs[2][MasterCheck.blockscount[2]] = j;
						MasterCheck.blockscount[2]++;
					}
				} else if (i >= 3 && i < 6) {
					// //3,4,5 block
					if (j < 3) {
						button[i][j].BlockID = 3;
						MasterCheck.blocksIs[3][MasterCheck.blockscount[3]] = i;
						MasterCheck.blocksjs[3][MasterCheck.blockscount[3]] = j;
						MasterCheck.blockscount[3]++;
					} else if (j >= 3 && j < 6) {
						button[i][j].BlockID = 4;
						MasterCheck.blocksIs[4][MasterCheck.blockscount[4]] = i;
						MasterCheck.blocksjs[4][MasterCheck.blockscount[4]] = j;
						MasterCheck.blockscount[4]++;
					} else {
						button[i][j].BlockID = 5;
						MasterCheck.blocksIs[5][MasterCheck.blockscount[5]] = i;
						MasterCheck.blocksjs[5][MasterCheck.blockscount[5]] = j;
						MasterCheck.blockscount[5]++;
					}
				} else {
					// //6,7,8 block
					if (j < 3) {
						button[i][j].BlockID = 6;
						MasterCheck.blocksIs[6][MasterCheck.blockscount[6]] = i;
						MasterCheck.blocksjs[6][MasterCheck.blockscount[6]] = j;
						MasterCheck.blockscount[6]++;
					} else if (j >= 3 && j < 6) {
						button[i][j].BlockID = 7;
						MasterCheck.blocksIs[7][MasterCheck.blockscount[7]] = i;
						MasterCheck.blocksjs[7][MasterCheck.blockscount[7]] = j;
						MasterCheck.blockscount[7]++;
					} else {
						button[i][j].BlockID = 8;
						MasterCheck.blocksIs[8][MasterCheck.blockscount[8]] = i;
						MasterCheck.blocksjs[8][MasterCheck.blockscount[8]] = j;
						MasterCheck.blockscount[8]++;
					}
				}
				// button[i][j].setText(String.valueOf(button[i][j].BlockID));
				// button[i][j].setText("0");
				if (j != 2 && j != 5)
					x += 53;
				else
					x += 60;
				rowcount--;

				if (rowcount == 0) {
					if (i != 2 && i != 5)
						y += 50;
					else
						y += 57;
					rowcount = 9;
					x = 10;
				}
				button[i][j].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						// ((JButtonWID) e.getSource()).setText("sasa");
						ButtonPressedi = ((JButtonWID) e.getSource()).ID;
						ButtonPressedj = ((JButtonWID) e.getSource()).j;
						input.requestFocusInWindow();

					}
				});
			}
		}
		input.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				try {
					ButtonPressediInputandParse();
					if (Manual == 1)
						if (MasterCheck.CheckGoal(window)) {
							Manual = 0;
							btnBrowse.setEnabled(true);
							JOptionPane.showMessageDialog(null, "Alf mabrook");
							SWS.stop();
						}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					button[ButtonPressedi][ButtonPressedj].setText("");

					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							input.setText("");
						}
					});
				}
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				try {
					ButtonPressediInputandParse();
					if (Manual == 1)
						if (MasterCheck.CheckGoal(window)) {
							JOptionPane.showMessageDialog(null, "Alf mabrook");
						}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					button[ButtonPressedi][ButtonPressedj].setText("");
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							input.setText("");
						}
					});
				}
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				try {
					ButtonPressediInputandParse();
					if (Manual == 1)
						if (MasterCheck.CheckGoal(window)) {
							JOptionPane.showMessageDialog(null, "Alf mabrook");
						}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					button[ButtonPressedi][ButtonPressedj].setText("");
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							input.setText("");
						}
					});

				}
			}

			public void ButtonPressediInputandParse() throws Exception {

				if (input.getText().isEmpty()) {

				} else if (Integer.parseInt(input.getText()) > 0
						& Integer.parseInt(input.getText()) < 10) {
					button[ButtonPressedi][ButtonPressedj].setText(input
							.getText());

					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							input.setText("");
							if (MasterCheck.CheckBlock(
									button[ButtonPressedi][ButtonPressedj],
									window)) {
								button[ButtonPressedi][ButtonPressedj]
										.setText("");
								JOptionPane.showMessageDialog(frmSudokuSolver,
										"Value is repeated in the block");

							}
							if (MasterCheck.CheckColRow(
									button[ButtonPressedi][ButtonPressedj],
									window)) {
								button[ButtonPressedi][ButtonPressedj]
										.setText("");
								JOptionPane.showMessageDialog(frmSudokuSolver,
										"Value is repeated in row or coloumn");
							}
						}
					});
				} else {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							input.setText("");
						}
					});
				}

			}

		});

	}
}
