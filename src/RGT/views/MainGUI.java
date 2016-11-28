package RGT.views;

import java.awt.*;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import RGT.common.Action;
import RGT.common.BusinessProcess;
import RGT.common.Option;
import RGT.common.Step;
import RGT.common.SystemFacadeController;
import RGT.common.interfaces.ISystemFacadeController;
import java.awt.*;
public class MainGUI extends JFrame {

	private static final int PRIORITY_SLIDER_MIN = 1;
	private static final int PRIORITY_SLIDER_MAX = 3;
	private static final String GET_ALL_REQUIREMENTS = "Get All Requirements";
	private JPanel contentPaneMain;
	private JTextPane inputDescriptionArea;
	Map<String,String> verbNounPhraseType;
	ISystemFacadeController identifyBPManualController;
	//IGenerateRequirementController generateRequirementController;
	private JTabbedPane tabbedPane;
	public static BusinessProcessTree selectedBusinessProcessTree;
	private JPanel businessProcessPanel;
	private JButton btnAdd;
	private JButton btnDelete;
	private JComboBox<String> businessProcessCombo;
	private JScrollPane scrollPane;
	private JScrollPane requirementScrollPane;
	private JScrollPane addRequirementScrollPane;
	private JSlider prioritySlider;
	private JList<String> requirementList;
	private JButton btnAddRequirement;
	private JTextArea addRequirementPane;
	
	// Menu and its component 
	
	private JMenuBar mnuBar;
	private JMenu mnuFile,mnuEdit,mnuHelp;
	private JMenuItem mnuFileOpen,mnuFileExit;
	private JMenuItem mnuEditCut,mnuEditCopy,mnuEditPaste;
	private JMenuItem mnuHelpAbout;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI frame = new MainGUI();
					frame.setVisible(true);
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainGUI() {
		super("MultiHighlight");
		identifyBPManualController = new SystemFacadeController();
		initComponents();
		createEvents();
	}

	/*
	 * This Method contains all of the code for creating and initializing components
	 */
	private void initComponents() {
		setTitle("Requirement Generation Tool");
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainGUI.class.getResource("/RGT/resources/Icon128.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100,1024, 768);
		contentPaneMain = new JPanel();
		contentPaneMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPaneMain);
		
		//setting menu bar 
		mnuBar = new JMenuBar();
		mnuFile = new JMenu("File");
		mnuEdit = new JMenu("Edit");
		mnuHelp = new JMenu("Help");
		
		mnuFileOpen  = new JMenuItem("Open");
		
		mnuFileOpen.setIcon(new ImageIcon("C:\\Users\\Himat\\Downloads\\RGT 1.5\\RGT\\images\\open.jpg"));
	
		
		
		mnuFileExit = new JMenuItem("Exit");
		mnuFileExit.setIcon(new ImageIcon("C:\\Users\\Himat\\Downloads\\RGT 1.5\\RGT\\images\\exit.jpg"));
		mnuEditCut = new JMenuItem(new DefaultEditorKit.CutAction());
		mnuEditCut.setText("Cut");
		
		mnuEditCut.setIcon(new ImageIcon("C:\\Users\\Himat\\Downloads\\RGT 1.5\\RGT\\images\\cut.jpg"));
		
		mnuEditCopy = new JMenuItem(new DefaultEditorKit.CopyAction());
		mnuEditCopy.setText("Copy");
		
		mnuEditCopy.setIcon(new ImageIcon("C:\\Users\\Himat\\Downloads\\RGT 1.5\\RGT\\images\\copy.jpg"));
		
		mnuEditPaste = new JMenuItem(new DefaultEditorKit.PasteAction());
		mnuEditPaste.setText("Paste");
		
		mnuEditPaste.setIcon(new ImageIcon("C:\\Users\\Himat\\Downloads\\RGT 1.5\\RGT\\images\\paste.jpg"));
		mnuHelpAbout = new JMenuItem("About us");
		
		mnuHelpAbout.setIcon(new ImageIcon("C:\\Users\\Himat\\Downloads\\RGT 1.5\\RGT\\images\\about.jpg"));
		mnuFile.add(mnuFileOpen);
		
		mnuFile.add(mnuFileExit);
		
		mnuEdit.add(mnuEditCut);
		mnuEdit.add(mnuEditCopy);
		mnuEdit.add(mnuEditPaste);
		
		mnuHelp.add(mnuHelpAbout);
		
		mnuBar.add(mnuFile);
		mnuBar.add(mnuEdit);
		mnuBar.add(mnuHelp);
		
		this.setJMenuBar(mnuBar);

		// adding listeners for menu item and action code 
		
		mnuFileOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
				
					JFileChooser filechooser = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
					filechooser.setFileFilter(filter);
					int result = filechooser.showOpenDialog(null);
					String desc="";
					String s;
					if(result==filechooser.APPROVE_OPTION)
					{
					
						File f = filechooser.getSelectedFile();
						BufferedReader br = new BufferedReader(new FileReader(f));
						
						while((s=br.readLine())!=null)
						{
							desc=desc + s + "\n";
							
						}
						
						inputDescriptionArea.setText(desc);

					}
				}
				catch(Exception e1)
				{
					
					e1.printStackTrace();
				}
				
			}
		});
		
		mnuFileExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		mnuHelpAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Work in Progess !");
			}
		});
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
/*
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(arg0) -> {
				String selectedNodeValue = selectedBusinessProcessTree.getSelectionValue();
				if(selectedNodeValue != null) {
					identifyBPManualController.deleteSelected(selectedNodeValue);
					syncTreeStructure(identifyBPManualController.getUpdatedBusinessProcesses());
				}
		});

		btnAdd = new JButton("Edit");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String selectedNodeValue = selectedBusinessProcessTree.getSelectionValue();

				String newNodeValue = JOptionPane.showInputDialog(selectedNodeValue);
				if(newNodeValue != null) {
					selectedBusinessProcessTree.editNode(newNodeValue);
					businessProcessPanel.revalidate();
					businessProcessPanel.repaint();
					//update changes
					identifyBPManualController.updateChanges(selectedNodeValue, newNodeValue);
				}
			}
		});
*/
		scrollPane = new JScrollPane();

		GroupLayout gl_contentPaneMain = new GroupLayout(contentPaneMain);
		gl_contentPaneMain.setHorizontalGroup(
				gl_contentPaneMain.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPaneMain.createSequentialGroup()
						.addContainerGap()
						//.addComponent(btnAdd)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						//.addComponent(btnDelete)
						.addGap(489))
				.addGroup(gl_contentPaneMain.createSequentialGroup()
						.addComponent(tabbedPane, 0, 0, Short.MAX_VALUE)
						.addGap(4))
				.addGroup(Alignment.LEADING, gl_contentPaneMain.createSequentialGroup()
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 626, Short.MAX_VALUE)
						.addContainerGap())
				);
		gl_contentPaneMain.setVerticalGroup(
				gl_contentPaneMain.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPaneMain.createSequentialGroup()
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 289, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPaneMain.createParallelGroup(Alignment.BASELINE)
								//.addComponent(btnAdd)
								//.addComponent(btnDelete)
								))
				);

		inputDescriptionArea = new JTextPane();
		scrollPane.setViewportView(inputDescriptionArea);
		businessProcessPanel = new JPanel();
		tabbedPane.addTab("Business Processes", null, businessProcessPanel, null);
		businessProcessPanel.setLayout(new GridLayout(1, 0, 0, 0));

		JPanel requirementPanel = new JPanel();
		tabbedPane.addTab("Requirements", null, requirementPanel, null);

		businessProcessCombo = new JComboBox<String>();
		businessProcessCombo.addItem(GET_ALL_REQUIREMENTS);
		businessProcessCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(businessProcessCombo.getSelectedItem() != null) {
					List<String> listOfRequirements = identifyBPManualController.getRequirements(businessProcessCombo.getSelectedItem().toString());
					addElementsToRequirementList(listOfRequirements);
				}
			}
		});
		requirementScrollPane = new JScrollPane();
		addRequirementScrollPane = new JScrollPane();
		prioritySlider = new JSlider();
		prioritySlider.setPaintLabels(true);
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put( new Integer( PRIORITY_SLIDER_MIN ), new JLabel("Low") );
		labelTable.put( new Integer( PRIORITY_SLIDER_MAX/2 + 1 ), new JLabel("Medium") );
		labelTable.put( new Integer( PRIORITY_SLIDER_MAX ), new JLabel("High") );
		prioritySlider.setLabelTable(labelTable);
		prioritySlider.setMajorTickSpacing(1);
		prioritySlider.setValue(2);
		prioritySlider.setMinimum(1);
		prioritySlider.setMaximum(3);
		prioritySlider.setPaintLabels(true);
		btnAddRequirement = new JButton("Generate Requirement");
		btnAddRequirement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String businessProcess = String.valueOf(businessProcessCombo.getSelectedItem());
				String requirement = addRequirementPane.getText();
				int priority = prioritySlider.getValue();
				try {
					List<String> listOfRequirements = identifyBPManualController.addRequirement(businessProcess, requirement, priority);
					addElementsToRequirementList(listOfRequirements);
				} catch (IllegalArgumentException exception) {
					JOptionPane.showMessageDialog(new Frame(),
							exception.getMessage(),
							"You missed something",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		JLabel lblLow = new JLabel("Low");

		JButton btnSaveRequirements = new JButton("Save Requirements");
		btnSaveRequirements.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame parentFrame = new JFrame();
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify a file to save");   
				int userSelection = fileChooser.showSaveDialog(parentFrame);
				if (userSelection == JFileChooser.APPROVE_OPTION) {
					File fileToSave = fileChooser.getSelectedFile();
					try {
						if(identifyBPManualController.saveRequirements(identifyBPManualController.getRequirements(GET_ALL_REQUIREMENTS), fileToSave)) {
							JOptionPane.showMessageDialog(new Frame(),
									"File saved successfully!",
									"Great",
									JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (IOException e) {
						JOptionPane.showMessageDialog(new Frame(),
								"Error while saving the file. Please try again...",
								"You missed something",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		GroupLayout gl_requirementPanel = new GroupLayout(requirementPanel);
		gl_requirementPanel.setHorizontalGroup(
				gl_requirementPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_requirementPanel.createSequentialGroup()
						.addGroup(gl_requirementPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_requirementPanel.createSequentialGroup()
										.addContainerGap()
										.addGroup(gl_requirementPanel.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_requirementPanel.createSequentialGroup()
														.addComponent(addRequirementScrollPane, GroupLayout.PREFERRED_SIZE, 222, GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED))
												.addGroup(gl_requirementPanel.createParallelGroup(Alignment.LEADING)
														.addComponent(businessProcessCombo, GroupLayout.PREFERRED_SIZE, 219, GroupLayout.PREFERRED_SIZE)
														.addComponent(prioritySlider, GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)))
										.addGroup(gl_requirementPanel.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_requirementPanel.createSequentialGroup()
														.addGap(14)
														.addComponent(requirementScrollPane, GroupLayout.PREFERRED_SIZE, 369, GroupLayout.PREFERRED_SIZE))
												.addGroup(gl_requirementPanel.createSequentialGroup()
														.addGap(18)
														.addComponent(btnAddRequirement)
														.addPreferredGap(ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
														.addComponent(btnSaveRequirements))))
								.addComponent(lblLow))
						.addContainerGap())
				);
		gl_requirementPanel.setVerticalGroup(
				gl_requirementPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_requirementPanel.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_requirementPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_requirementPanel.createSequentialGroup()
										.addComponent(requirementScrollPane, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnSaveRequirements))
								.addGroup(gl_requirementPanel.createSequentialGroup()
										.addComponent(businessProcessCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(addRequirementScrollPane, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_requirementPanel.createParallelGroup(Alignment.BASELINE)
												.addComponent(prioritySlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(btnAddRequirement))))
						.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblLow))
				);
		addRequirementPane = new JTextArea();
		addRequirementScrollPane.setViewportView(addRequirementPane);
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		requirementList = new JList<String>(listModel);
		requirementScrollPane.setViewportView(requirementList);
		requirementPanel.setLayout(gl_requirementPanel);
		contentPaneMain.setLayout(gl_contentPaneMain);
	}
	private void addElementsToRequirementList(List<String> listOfRequirements) {
		DefaultListModel<String> listModel = (DefaultListModel<String>) requirementList.getModel();
		listModel.removeAllElements();
		for(String aRequirement : listOfRequirements) {
			listModel.addElement(aRequirement);
		}
	}
	
	private void setTextColor(Color clr) {
		StyledDocument doc = inputDescriptionArea.getStyledDocument();
	    int start = inputDescriptionArea.getSelectionStart();
	    int end = inputDescriptionArea.getSelectionEnd();
	    if (start > end) { // Backwards selection?
	        int life = start;
	        start = end;
	        end = life;
	    }
	    Style style = inputDescriptionArea.addStyle("", null);
	    StyleConstants.setForeground(style, clr);
	    //style = textPane.getStyle("MyHilite");
	    doc.setCharacterAttributes(start, end - start, style, false);
	    //inputDescriptionArea.setToolTipText("Tooltip");
	}


	/*
	 * This Method contains all the code for creating events
	 */ 
	private void createEvents() {
		inputDescriptionArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent releasedEvent) {
				if (releasedEvent.isPopupTrigger() && inputDescriptionArea.getSelectedText() != null)
					new TextAreaPopUp().show(releasedEvent.getComponent(), releasedEvent.getX(), releasedEvent.getY());	
			}
		});

	}

	public class TextAreaPopUp extends JPopupMenu {
		JMenuItem[] choices;

		public TextAreaPopUp() {
			choices = new JMenuItem[] {
					new JMenuItem("Business Process"),
					new JMenuItem("Step"),
					new JMenuItem("Action")
			};

			for (int i = 0; i < choices.length; i++) {
				final int j = i;
				choices[i].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						popUpProcessor(Option.values()[j]);
					}
				});
				add(choices[i]);
			}
		}

		public void popUpProcessor(Option operationType) {
			if(operationType.equals(Option.BUSINESS_PROCESS)) {
				BusinessProcess bp = identifyBPManualController.userOptionInput(inputDescriptionArea.getSelectedText());
				if(bp != null) {
					BusinessProcessTree businessProcessTree = new BusinessProcessTree(bp);
					businessProcessTree.addTreeSelectionListener(businessProcessTree);
					businessProcessPanel.add(businessProcessTree);
					businessProcessPanel.revalidate();
					businessProcessPanel.repaint();
					businessProcessTree.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent releasedEvent) {
							if (releasedEvent.isPopupTrigger() && selectedBusinessProcessTree.getSelectionValue() != null)
								new BusinessProcessTreePopUp().show(releasedEvent.getComponent(), releasedEvent.getX(), releasedEvent.getY());	
						}
					});
					//syncTreeStructure(identifyBPManualController.getUpdatedBusinessProcesses());
					setTextColor(Color.RED.darker());
				}
				
			}

			if(operationType.equals(Option.STEP) && selectedBusinessProcessTree.getSelectionValue() != null) {
				Step s = identifyBPManualController.userOptionInput(inputDescriptionArea.getSelectedText(), (BusinessProcess)selectedBusinessProcessTree.getSelectionValue());
				if(s != null) {
					selectedBusinessProcessTree.addNode(s);
					//syncTreeStructure(identifyBPManualController.getUpdatedBusinessProcesses());
					setTextColor(Color.GREEN.darker());
				}
			}

			if(operationType.equals(Option.ACTION) && selectedBusinessProcessTree.getSelectionValue() != null) {
				Action a = identifyBPManualController.userOptionInput(inputDescriptionArea.getSelectedText(), (Step)selectedBusinessProcessTree.getSelectionValue());
				if(a != null) {
					selectedBusinessProcessTree.addNode(a);
					//syncTreeStructure(identifyBPManualController.getUpdatedBusinessProcesses());
					setTextColor(Color.BLUE.darker());
				}
			}
		}
	}
	
	class BusinessProcessTreePopUp extends JPopupMenu {
		JMenuItem[] choices;

		public BusinessProcessTreePopUp() {
			choices = new JMenuItem[] {
					new JMenuItem("Edit"),
					new JMenuItem("Delete")
			};

			for (int i = 0; i < choices.length; i++) {
				final int j = i;
				choices[i].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						popUpProcessor(j);
					}
				});
				add(choices[i]);
			}
		}
		
		void popUpProcessor(int x) {
			if (x > 0) {
				//Delete
			} else {
				//Edit
				String newNodeValue = JOptionPane.showInputDialog(selectedBusinessProcessTree.getSelectionValue());
				if(newNodeValue != null) {
					selectedBusinessProcessTree.editNode(newNodeValue);
					//update changes
					//identifyBPManualController.updateChanges(selectedNodeValue, newNodeValue);
				}
			}
		}
	}
/*
	public void syncTreeStructure (Set<IBusinessProcess> businessProcesses) {
		businessProcessPanel.removeAll();
		businessProcessCombo.removeAllItems();
		businessProcessCombo.addItem(GET_ALL_REQUIREMENTS);
		for(IBusinessProcess businessProcess : businessProcesses) {
			businessProcessCombo.addItem(businessProcess.getValue());
			BusinessProcessTree businessProcessTree = new BusinessProcessTree(businessProcess);
			businessProcessTree.addTreeSelectionListener(businessProcessTree);
			DefaultTreeModel treeModel = (DefaultTreeModel) businessProcessTree.getModel();
			DefaultMutableTreeNode parentBP = (DefaultMutableTreeNode) treeModel.getRoot();
			for(IStep step : businessProcess.getSteps()) {
				DefaultMutableTreeNode childStep = new DefaultMutableTreeNode(step);
				parentBP.add(childStep);
				for(IAction action : step.getActions()) {
					DefaultMutableTreeNode childAction = new DefaultMutableTreeNode(action);
					childStep.add(childAction);
				}
			}
			businessProcessPanel.add(businessProcessTree);
		}
		businessProcessPanel.revalidate();
		businessProcessPanel.repaint();
	}*/
}
