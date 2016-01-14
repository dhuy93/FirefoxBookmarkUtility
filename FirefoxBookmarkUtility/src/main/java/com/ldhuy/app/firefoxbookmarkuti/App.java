package com.ldhuy.app.firefoxbookmarkuti;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.gson.Gson;
import com.ldhuy.app.firefoxbookmarkuti.controller.SingleController;
import com.ldhuy.app.firefoxbookmarkuti.gui.TableView;
import com.ldhuy.app.firefoxbookmarkuti.gui.ToolBar;
import com.ldhuy.app.firefoxbookmarkuti.gui.TreeView;
import com.ldhuy.app.firefoxbookmarkuti.model.Bookmark;
import com.ldhuy.app.firefoxbookmarkuti.model.BookmarkDTO;
import com.ldhuy.app.firefoxbookmarkuti.model.MenuCommand;
import com.ldhuy.app.firefoxbookmarkuti.model.TableModel;
import com.ldhuy.app.firefoxbookmarkuti.model.TreeViewModel;
import com.ldhuy.app.firefoxbookmarkuti.service.BookmarkService;

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
	 * The toolbar
	 */
	private ToolBar toolbar;

	/**
	 * Controller that synchronises displayed data in tree view and table view
	 */
	private SingleController singleController;

	/**
	 * The root node of the bookmark tree
	 */
	private BookmarkDTO root;

	private final int WIDTH = 1200;
	private final int HEIGHT = 800;

	/**
	 * The selected folder in the tree view
	 */
	// public static BookmarkDTO selectedContainer;

	/**
	 * The menu bar
	 */
	private JMenuBar menuBar;

	/**
	 * File menu
	 */
	private JMenu fileMenu;

	/**
	 * File chooser
	 */
	private JFileChooser fileChooser;

	public App() {
		this.root = null;
		// App.selectedContainer = root;
		prepareGUI();
		singleController = new SingleController(this.tableView, this.treeView, this.toolbar);
		// ttSync.init(this.root);
		this.treeView.addObserver(singleController);
		this.tableView.addObserver(singleController);
		this.toolbar.addObserver(singleController);
	}

	private void prepareGUI() {
		// main frame
		mainFrame = new JFrame("Firefox Bookmark Utility");
		mainFrame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		mainFrame.setLayout(new BorderLayout());
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});

		// Table view
		this.tableView = new TableView();

		// tree view
		this.treeView = new TreeView();

		// Split pane
		this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.treeView.getMainPanel(),
				this.tableView.getMainPanel());
		this.splitPane.setDividerLocation(250);

		// Init toolbar
		this.toolbar = new ToolBar();

		// Menu
		prepareMenuBar();

		mainFrame.add(this.splitPane, BorderLayout.CENTER);
		mainFrame.add(this.toolbar.getMainPanel(), BorderLayout.NORTH);
		mainFrame.setJMenuBar(menuBar);
		mainFrame.setLocation(50, 50);
		mainFrame.pack();

		// Initialize file chooser
		this.fileChooser = new JFileChooser(System.getProperty("user.dir"));
		FileNameExtensionFilter extFilter = new FileNameExtensionFilter("JSON files", "JSON");
		fileChooser.setFileFilter(extFilter);
	}

	private void prepareMenuBar() {
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");

		JMenuItem openMenuItem = new JMenuItem(MenuCommand.OPENFILE.getValue(), KeyEvent.VK_O);
		openMenuItem.setActionCommand(MenuCommand.OPENFILE.getValue());
		openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		JMenuItem saveMenuItem = new JMenuItem(MenuCommand.SAVEFILE.getValue(), KeyEvent.VK_S);
		saveMenuItem.setActionCommand(MenuCommand.SAVEFILE.getValue());
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));

		MenuItemListener menuItemListener = new MenuItemListener();

		openMenuItem.addActionListener(menuItemListener);

		fileMenu.add(openMenuItem);
		menuBar.add(fileMenu);

	}

	public void show() {
		mainFrame.setVisible(true);
	}

	public static void main(String[] args) {
		App fbu = new App();
		fbu.show();
	}

	private static BookmarkDTO parseJsonFile(String path) {
		String line = null;
		StringBuilder sb = new StringBuilder();

		FileReader fr;
		try {
			fr = new FileReader(path);
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
		BookmarkDTO result = gson.fromJson(sb.toString(), BookmarkDTO.class);

		return result;
	}

	private class MenuItemListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String actionCmd = e.getActionCommand();
			System.out.println(actionCmd + " menu selected");

			if (actionCmd.equals(MenuCommand.OPENFILE.getValue())) {
				int returnValue = fileChooser.showOpenDialog(mainFrame);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					String filePath = fileChooser.getSelectedFile().getPath();
					BookmarkDTO parsedJson = parseJsonFile(filePath);
					root = parsedJson;
					singleController.init(parsedJson);
					toolbar.getSearchBox().setEnabled(true);
					System.out.println(filePath);
					Map<Long, Bookmark> result = BookmarkService.find(root.toBookmark(), "google");
					System.out.println(result.size());
				}
			}
		}

	}

}
