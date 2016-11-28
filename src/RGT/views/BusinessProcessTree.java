package RGT.views;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import RGT.common.BusinessProcess;
import RGT.common.VerbNounPhrase;

public class BusinessProcessTree extends JTree implements TreeSelectionListener {

	private DefaultTreeModel defaultModel;
	private VerbNounPhrase selectionValue;
	
	/** Creates a new instance of BusinessProcessTree */
	public BusinessProcessTree(BusinessProcess businessProcessValue) {
		super(new DefaultTreeModel(new DefaultMutableTreeNode(businessProcessValue)));
		defaultModel = (DefaultTreeModel) getDefaultTreeModel();
		this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		this.setCellRenderer(new DefaultTreeCellRenderer() {
			ImageIcon bp = new ImageIcon(getClass().getClassLoader().getResource("RGT/resources/bp.png")),
					s = new ImageIcon(getClass().getClassLoader().getResource("RGT/resources/s.png")),
					a = new ImageIcon(getClass().getClassLoader().getResource("RGT/resources/a.png"));
			@Override
			public Component getTreeCellRendererComponent(JTree tree,
					Object value, boolean selected, boolean expanded,
					boolean isLeaf, int row, boolean focused) {
				Component c = super.getTreeCellRendererComponent(tree, value,
						selected, expanded, isLeaf, row, focused);
				switch (((DefaultMutableTreeNode)value).getUserObject().getClass().getSimpleName()) {
					case "BusinessProcess": setIcon(bp); break;
					case "Step": setIcon(s); break;
					case "Action": setIcon(a); break;
				}

				return c;
			}
		});
		
	}

	public void addNode(VerbNounPhrase childValue) {
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) getLastSelectedPathComponent();
		if (selectedNode != null) {
			DefaultMutableTreeNode child = new DefaultMutableTreeNode(childValue);
			defaultModel.insertNodeInto(child, selectedNode, selectedNode.getChildCount());
			defaultModel.reload();
			expandPath(getSelectionPath());
			this.revalidate();
			this.repaint();
			this.updateUI();
		}
	}

	public void editNode(String newValue) {
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) getLastSelectedPathComponent();
		if (selectedNode != null) {
			((VerbNounPhrase)selectedNode.getUserObject()).setValue(newValue);
			expandPath(getSelectionPath());
			this.revalidate();
			this.repaint();
			this.updateUI();
		}
	}

	public void removeNode() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) getLastSelectedPathComponent();
		defaultModel.removeNodeFromParent(node);
		this.revalidate();
		this.repaint();
	}
	public void removeNode(DefaultMutableTreeNode aNode) {
		defaultModel.removeNodeFromParent(aNode);
	}

	@Override
	public void valueChanged(TreeSelectionEvent selectionEvent) {
		if(selectionEvent.getNewLeadSelectionPath() != null) {
			MainGUI.selectedBusinessProcessTree = this;
			selectionValue = (VerbNounPhrase)((DefaultMutableTreeNode)selectionEvent.getNewLeadSelectionPath().getLastPathComponent()).getUserObject();
		}
	}

	public Object getSelectionValue() {
		return selectionValue;
	}

	public void setSelectionValue(VerbNounPhrase selectionValue) {
		this.selectionValue = selectionValue;
	}

}
