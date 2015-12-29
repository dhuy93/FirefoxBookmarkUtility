package com.ldhuy.app.firefoxbookmarkuti;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import com.google.gson.Gson;
import com.ldhuy.app.firefoxbookmarkuti.gui.TableView;
import com.ldhuy.app.firefoxbookmarkuti.gui.TreeView;
import com.ldhuy.app.firefoxbookmarkuti.model.Bookmark;
import com.ldhuy.app.firefoxbookmarkuti.model.BookmarkDTO;
import com.ldhuy.app.firefoxbookmarkuti.model.TableModel;
import com.ldhuy.app.firefoxbookmarkuti.model.TreeViewModel;

import javafx.scene.layout.Border;

/**
 * Hello world!
 *
 */
public class App {
	/**
	 * The main frame of the application
	 */
	private JFrame mainFrame;
	
	/**
	 * Split panel contains table view and tree view
	 */
	private JSplitPane splitPane;
	
	/**
	 * The table view
	 */
	private TableView tableView;
	
	/**
	 * The tree view to view folders in a tree structure
	 */
	private TreeView treeView;
	
	/**
	 * Model for table view
	 */
	private TableModel tableModel;
	
	/**
	 * Model for tree view
	 */
	private TreeViewModel treeModel;
	
	/**
	 * Columns of table view
	 */
	private String[] columnNames = { "Type", "Title", "Tags", "Location" };
	
	/**
	 * The root node of the bookmark tree
	 */
	private BookmarkDTO root;
	
	/**
	 * The selected folder in the tree view
	 */
	public static BookmarkDTO selectedContainer;
	

	public App(BookmarkDTO root) {
		this.root = root;
		this.selectedContainer = root;
		prepareGUI();
//		Object[][] data = convertToTableData(root);
		setTableData(new Object[0][0]);
		setTreeData(this.selectedContainer);
	}

	private void prepareGUI() {
		// main frame
		mainFrame = new JFrame("Firefox Bookmark Utility");
		mainFrame.setSize(800, 600);
		mainFrame.setLayout(new BorderLayout());
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		
		// result view
		this.tableModel = new TableModel(columnNames);
		tableView = new TableView(this.tableModel);
		
		// tree view
		this.treeView = new TreeView();
//		this.treeView.setSize(700, 300);
		this.treeView.setTableModel(this.tableModel);
		
		// Split pane
		this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.treeView, this.tableView);
		this.splitPane.setDividerLocation(250);

//		mainFrame.add(treeView, BorderLayout.LINE_START);
//		mainFrame.add(resultView, BorderLayout.CENTER);
		mainFrame.add(this.splitPane, BorderLayout.CENTER);
		mainFrame.pack();
	}

	public void setTableData(Object[][] data) {
		tableView.getTableModel().setData(data);
	}

	public void setTreeData(BookmarkDTO root) {
		this.treeModel = new TreeViewModel(root);
		this.treeView.setModel(this.treeModel);
	}

	public void show() {
		mainFrame.setVisible(true);
	}

	public static void main(String[] args) {
		long t0 = System.currentTimeMillis();

		// Parse input file
		String filename = "res/bookmarks-2015-12-07[formated].json";
		String line = null;
		StringBuilder sb = new StringBuilder();

		FileReader fr;
		try {
			fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Gson gson = new Gson();
		BookmarkDTO bookmarkDTO = gson.fromJson(sb.toString(), BookmarkDTO.class);
		

		App fbu = new App(bookmarkDTO);
		fbu.show();
		System.out.println(System.currentTimeMillis() - t0);
	}
	
}
