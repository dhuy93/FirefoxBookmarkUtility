package com.ldhuy.app.firefoxbookmarkuti;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;

import com.google.gson.Gson;
import com.ldhuy.app.firefoxbookmarkuti.gui.ResultView;
import com.ldhuy.app.firefoxbookmarkuti.gui.TreeView;
import com.ldhuy.app.firefoxbookmarkuti.model.Bookmark;
import com.ldhuy.app.firefoxbookmarkuti.model.BookmarkDTO;
import com.ldhuy.app.firefoxbookmarkuti.model.TableModel;
import com.ldhuy.app.firefoxbookmarkuti.model.TreeViewModel;

/**
 * Hello world!
 *
 */
public class App {
	private JFrame mainFrame;
	private ResultView resultView;
	private TreeView treeView;
	private TableModel tableModel;
	private TreeViewModel treeModel;
	private String[] columnNames = { "ID", "Title", "Tags", "Location" };

	public App() {
		prepareGUI();
	}

	private void prepareGUI() {
		mainFrame = new JFrame("Firefox Bookmark Utility");
		mainFrame.setSize(800, 600);
		mainFrame.setLayout(new GridLayout(2, 2));
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		this.tableModel = new TableModel(columnNames);
		resultView = new ResultView(this.tableModel);
		this.treeView = new TreeView();
		mainFrame.add(resultView);
		mainFrame.add(treeView);
	}

	public void setTableData(Object[][] data) {
		resultView.getTableModel().setData(data);
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
		Bookmark bookmark = bookmarkDTO.toBookmark();
		List<BookmarkDTO> list = bookmarkDTO.getChildren();
		Object[][] data = new Object[list.size()][4];
		for (int i = 0; i < list.size(); ++i) {
			data[i][0] = (list.get(i).getId() != null) ? list.get(i).getId() : "";
			data[i][1] = (list.get(i).getTitle() != null) ? list.get(i).getTitle() : "";
			data[i][2] = (list.get(i).getTags() != null) ? list.get(i).getTags() : "";
			data[i][3] = (list.get(i).getUri() != null) ? list.get(i).getUri() : "";
		}

		App fbu = new App();
		fbu.setTableData(data);
		fbu.setTreeData(bookmarkDTO);
		fbu.show();
		System.out.println(System.currentTimeMillis() - t0);
	}
}
