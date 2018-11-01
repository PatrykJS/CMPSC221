/**
 *  This file is part of ytd2
 *
 *  ytd2 is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  ytd2 is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  You should have received a copy of the GNU General Public License
 *  along with ytd2.
 *  If not, see <http://www.gnu.org/licenses/>.
 */
package zsk;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Arrays;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.commons.cli.Options;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import zsk.gui.PopupListener;
import zsk.helper.Properties;
import zsk.helper.String_Constants;
import zsk.i18n.Messages;


/**
 * knoedel@section61:~/Development/ytd2/ytd2> echo " *" `egrep -v "(^\s*(\/\*|\*|//)|^\s*$)" src/zsk/*java | wc -l` java code lines && echo -e " *" `egrep "(^\s*(\/\*|\*|//)|^\s*$)" src/zsk/*java | wc -l` empty/comment lines "\n *"
 * 2165 java code lines
 * 778 empty/comment lines 
 *  
 * knoedel@section61:~/Development/ytd2/ytd2> date && uname -a && lsb_release -a && java -version
 * Sun May  3 15:41:23 CEST 2015
 * Linux section61 3.16.7-21-desktop #1 SMP PREEMPT Tue Apr 14 07:11:37 UTC 2015 (93c1539) x86_64 x86_64 x86_64 GNU/Linux
 * LSB Version:    core-2.0-noarch:core-3.2-noarch:core-4.0-noarch:core-2.0-x86_64:core-3.2-x86_64:core-4.0-x86_64:desktop-4.0-amd64:desktop-4.0-noarch:graphics-2.0-amd64:graphics-2.0-noarch:graphics-3.2-amd64:graphics-3.2-noarch:graphics-4.0-amd64:graphics-4.0-noarch
 * Distributor ID: openSUSE project
 * Description:    openSUSE 13.2 (Harlequin) (x86_64)
 * Release:        13.2
 * Codename:       Harlequin
 * openjdk version "1.8.0_45"
 * OpenJDK Runtime Environment (build 1.8.0_45-b14)
 * OpenJDK 64-Bit Server VM (build 25.45-b02, mixed mode)
 * 
 * using Eclipse 3.5/3.6/3.7/4.2/4.3.2 and Oracle Java + Maven
 * Apache SonarQube 4.5
 * TODOs are for Eclipse IDE - Tasks View
 * 
 * tested on GNU/Linux Oracle Java & IBM OpenJDK JRE 1.6-/1.8 64bit, M$-Windows XP 64bit JRE 1.6.0 32&64Bit and M$-Windows 7 32Bit JRE 1.6.0 32Bit, M$-Windows 7 64 Bit JRE 1.7.0 64Bit
 * using Mozilla Firefox 3.6-37, Google Chrome (38), M$-IE (8-11)
 * 
 * 
 * NEW PROBLEMS 2015:
 * - video and audio are sometimes separated in two streams
 * - some videos are filtered and are not playable unchanged (vlc filter Wall 1:1 corrects this)
 * 
 * 
 * source code compliance level is 1.5
 * java files are UTF-8 encoded
 * javac showd no warning
 * SonarQube 4.5.4 LTS .. :)
 *
 * @author Stefan "MrKnödelmann" K.
 *
 */
public class JFCMainClient extends JFrame implements ActionListener, WindowListener, DocumentListener, ChangeListener, DropTargetListener, ItemListener, Observer {

	static JFCMainClient FRAME = null;
	static Thread THREAD_1;
	static Thread THREAD_2;
	static Thread THREAD_3;
	static Thread THREAD_4;
	static Thread THREAD_5;
	static Thread THREAD_6;
	
	private static final long serialVersionUID = 6791957129816930254L;
	private Properties properties;
	private static Boolean QUIT_REQUESTED = false;
		
	private JPanel panel = null;
	private JSplitPane middlePane = null;
	private JTextArea textArea = null;
	private JList<YoutubeUrl> urlList = null;
	private JButton quitButton = null;
	private JButton directoryButton = null;
	private JTextField directoryTextField = null;
	private JTextField textInputField = null;
	private JPopupMenu popup = null;
	private JRadioButton fourkButton = null;
	private JRadioButton hdButton = null;
	private JRadioButton stdButton = null;
	private JRadioButton ldButton = null;
	private JRadioButton mpgButton = null;
	private JRadioButton flvButton = null;
	private JRadioButton webmButton = null;
	private JCheckBox saveConfigCheckBox = null;
	private JCheckBox save3dCheckBox = null;
	 
	public static DefaultListModel<YoutubeUrl> DLM = null;
	
	final static String CONFIG_FILE = "ytd2.config.xml"; 
	static XMLConfiguration CONFIG = null;
	
	static boolean IS_CLI = false;

	static Options CLI_OPTIONS = new Options();
	 
	
	public JFCMainClient(Properties properties) {
		this.properties = properties;
	}
	
	
		
	static void debugOutput (String debugMessage) {
		if (!Properties.IS_DEBUG) {
			// TODO do not empty returns
			return;
		}
		addTextToConsole("#DEBUG " +debugMessage); 
	} 
	
	static void output (String message) {
		addTextToConsole("#INFO " +message); 
	} 
	
	private static void addTextToConsole(String message) {
		try {
			// append() is threadsafe
			FRAME.textArea.append(message +String_Constants.NEWLINE);
			FRAME.textArea.setCaretPosition( FRAME.textArea.getDocument().getLength() );
			FRAME.textInputField.requestFocusInWindow();
			System.out.println(message);
		} catch (NullPointerException npe) {
			// for CLI run only
			System.out.println(message);
		} catch (Exception e) {
			@SuppressWarnings( "unused" ) // for debuging
			String s = e.getMessage();
		}
	} 
	
	/**
	 * quit==exit
	 * 
	 */
	public void windowClosing( WindowEvent e ) {
		shutdownApp();
	} 
		
	public void shutdownApp() {
		// try to save settings to an xml-file
		saveConfigFile();
		
		// running downloads are difficult to terminate (Thread.isInterrupted() does not work here)
		setQuitRequested(true);
		debugOutput("Quitrequested = true"); 
		
		// TODO mayby we use a threadpool here?!
		try {THREAD_1.interrupt();} catch (NullPointerException npe) {} // TODO handle Exception
		try {THREAD_2.interrupt();} catch (NullPointerException npe) {} // TODO handle Exception
		try {THREAD_3.interrupt();} catch (NullPointerException npe) {} // TODO handle Exception
		try {THREAD_4.interrupt();} catch (NullPointerException npe) {} // TODO handle Exception
		try {THREAD_5.interrupt();} catch (NullPointerException npe) {} // TODO handle Exception
		try {THREAD_6.interrupt();} catch (NullPointerException npe) {} // TODO handle Exception
		try {
			try {THREAD_1.join();} catch (NullPointerException npe) {} // TODO handle Exception
			try {THREAD_2.join();} catch (NullPointerException npe) {} // TODO handle Exception
			try {THREAD_3.join();} catch (NullPointerException npe) {} // TODO handle Exception
			try {THREAD_4.join();} catch (NullPointerException npe) {} // TODO handle Exception
			try {THREAD_5.join();} catch (NullPointerException npe) {} // TODO handle Exception
			try {THREAD_6.join();} catch (NullPointerException npe) {} // TODO handle Exception
		} catch (InterruptedException ie) {
			// TODO handle Exception
		}
		
		// in cli-mode we do not exit as the shutdown hookhandler would run circular
		if (window_Mode()) {
			debugOutput("quit."); 
			System.out.println("quit."); 
			System.exit( 0 );
		}
	} 
	
	static synchronized Boolean getQuitRequested() {
		return QUIT_REQUESTED;
	}
	
	private void saveConfigFile() {
		debugOutput("saving properties to file..."); 
		
		if (!window_Mode()) {
			return;
		}
		
		if (properties.isSaveConfig()) {

			// if config wasn't saved before create a new file
			if (CONFIG.getFile() == null) {
				CONFIG.setFile(new File(CONFIG_FILE));
			}

//	TODO	CONFIG.setProperty("http_proxy", this.properties.getProxy()); 
			CONFIG.setProperty("targetfolder", this.properties.getSavefolder());  
			CONFIG.setProperty("videobutton", this.properties.getVideoformat().name());  
			try {
				CONFIG.save();
				// debugoutput will not work here anymore because the GUI does not get updated - output is for CLI-run only
				debugOutput("config file written: " +CONFIG.getBasePath()); 
			} catch (ConfigurationException e) {
				debugOutput("error writing config file: " +CONFIG.getBasePath()); 
			}
		}
	} 
	
	public synchronized static void setQuitRequested(Boolean quitRequested) {
		QUIT_REQUESTED = quitRequested;
	}
	
	/**
	 * process events of ActionListener
	 * 
	 */
	public void actionPerformed( final ActionEvent e ) {
		if (e.getSource().equals( FRAME.textInputField )) {
			if (!e.getActionCommand().equals( "" )) {  
				if (e.getActionCommand().matches(String_Constants.YOUTUBE_REGEX))
					addYoutubeUrlToList(e.getActionCommand());
				else {
					output(e.getActionCommand());
					parseAndExecuteCliInputCommands(e.getActionCommand().toLowerCase());
				}
			}
			synchronized (FRAME.textInputField) {
				FRAME.textInputField.setText("");				 
			}
			return;
		}
		
		// let the user choose another dir
		if (e.getSource().equals( FRAME.directoryButton )) {
			debugOutput("frame.directorybutton"); 
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setMultiSelectionEnabled(false);
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			synchronized (FRAME.directoryTextField) {
				// we have to set current directory here because it does get lost when fc is lost
				fileChooser.setCurrentDirectory( new File( FRAME.directoryTextField.getText()) );
			}
			debugOutput("current dir: "+fileChooser.getCurrentDirectory().getAbsolutePath()); 
			if (fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			String newDirectory = fileChooser.getSelectedFile().getAbsolutePath();
			// append file.separator if last character is not file.separator (the user choosed a directory other than root)
			newDirectory += (newDirectory.endsWith(System.getProperty("file.separator"))?"":System.getProperty("file.separator"));  
			File file = new File(newDirectory);
			if (file.exists()) {
				if (file.isDirectory()) {
					synchronized (FRAME.directoryTextField) {
						FRAME.directoryTextField.setText( newDirectory );
					}
					this.properties.setSavefolder(newDirectory); 
					debugOutput("new current dir: " +fileChooser.getCurrentDirectory().getAbsolutePath()); 
					output(Messages.getString("INFO_MESSAGE_NEW_DIRECTORY") +fileChooser.getCurrentDirectory().getAbsolutePath()); 
				} else {
					output("not a directory: " +newDirectory); 
				}
			} else {
				output("directory does not exist: " +newDirectory);
			}
			return;
		}
		
		if (isActionForResolutionButtonGroup(e) ) {
			setResolutionPropertiesByAction(e);
			output("trying: " +e.getActionCommand().toUpperCase());
			return;
		}
		
		if (isActionForVideoformatButtonGroup(e)) {
			setVideoformatPropertiesByAction(e);
			output("trying: " +e.getActionCommand().toUpperCase() +"  " +Messages.getString("INFO_MESSAGE_FILES_NAMED_BY_MIME_TYPE"));
			return;
		} 
		
		if (e.getActionCommand().equals( "quit" )) { 
			output(Messages.getString("INFO_MESSAGE_SHUTDOWN_APPLICATION"));
			// seems to have to effect:
			shutdownApp();
			return;
		}
		
		if (e.getActionCommand().equals("paste")) { 
			FRAME.textInputField.paste();
			return;
		}

		if (e.getActionCommand().equals("clear")) { 
			if (FRAME.textInputField.getSelectedText() == null) 
				FRAME.textInputField.selectAll();
			
			FRAME.textInputField.replaceSelection(""); 
			return;
		}
		
		if (e.getActionCommand().equals("debug")) { 
			FRAME.textArea.setFocusable(true);
			FRAME.textArea.setCaretPosition(0);
			FRAME.textArea.selectAll();
			FRAME.textArea.copy();
			FRAME.textArea.select(0,0);
			FRAME.textArea.setFocusable(false);
			FRAME.textArea.copy();
			return;
		}
		debugOutput("unknown action. " +e.getSource().toString());  
		output("unbekannte Aktion"); 
	}
	
	private boolean isActionForVideoformatButtonGroup(ActionEvent e) {
		if (e.getActionCommand().equals(FRAME.mpgButton.getActionCommand()) ||
				e.getActionCommand().equals(FRAME.flvButton.getActionCommand()) || 
				e.getActionCommand().equals(FRAME.webmButton.getActionCommand()) ) {
			return true;
		}
		return false;
	}
	
	private boolean isActionForResolutionButtonGroup(ActionEvent e) {
		if (e.getActionCommand().equals(FRAME.fourkButton.getActionCommand()) || e.getActionCommand().equals(FRAME.hdButton.getActionCommand()) || 
				e.getActionCommand().equals(FRAME.stdButton.getActionCommand()) ||
				e.getActionCommand().equals(FRAME.ldButton.getActionCommand())) {
			return true;
		}
		return false;
	}
	
	public void setVideoformatPropertiesByAction(ActionEvent e) {
		if (e.getActionCommand().equals(FRAME.mpgButton.getActionCommand())) {
			properties.setVideoformat(Videoformat.MPG);
		} else if (e.getActionCommand().equals(FRAME.flvButton.getActionCommand())) {
			properties.setVideoformat(Videoformat.FLV);
		} else if (e.getActionCommand().equals(FRAME.webmButton.getActionCommand())) {
			properties.setVideoformat(Videoformat.WEBM);
		} else {
			throw new NotImplementedException();
		}
	}
	
	public void setResolutionPropertiesByAction(ActionEvent e) {
		if (e.getActionCommand().equals(FRAME.fourkButton.getActionCommand())) {
			properties.setResolution(Resolution.FOUR_K);
		} else if (e.getActionCommand().equals(FRAME.hdButton.getActionCommand())) {
			properties.setResolution(Resolution.LOW_DEFINITION);
		} else if (e.getActionCommand().equals(FRAME.stdButton.getActionCommand())) {
			properties.setResolution(Resolution.HIGH_DEFINITION);
			
		} else if (e.getActionCommand().equals(FRAME.ldButton.getActionCommand())) {
			properties.setResolution(Resolution.STANDARD_DEFINITION);
		} else {
			throw new NotImplementedException();
		}
	}
	
	static void addYoutubeUrlToList(String url) {
		addElementToDLM(new YoutubeUrl(url));
	}	
	
	static void addYoutubeUrlToList( String name, String title) {
		YoutubeUrl youtubeUrl = new YoutubeUrl(name);
		youtubeUrl.setTitle(title);
		addElementToDLM(youtubeUrl);		
	} 
	
	static void addYoutubeUrlToList( String name, String title, String respart ) {
		YoutubeUrl youtubeUrl = new YoutubeUrl(name);
		youtubeUrl.setTitle(title);
		youtubeUrl.setRespart(respart);
		addElementToDLM(youtubeUrl);
	}
	
	private static void addElementToDLM(YoutubeUrl youtubeUrl){
		synchronized (DLM) {
			DLM.addElement(youtubeUrl);
			debugOutput("Add new Element to DLM and notify."); 
			DLM.notify();
		}
	}
	
	/**
	 * process input events
	 * 
	 * @param command
	 */
	protected void parseAndExecuteCliInputCommands(String command) {
		if (command.matches("^(hilfe|help|[h|\\?])")) { 
			printHelpDescriptionToConsole();
		} else if (command.matches("^(v(ersion)?)")) { 
			output(Properties.VERSION);
		} else if (command.matches("^(debug)( on| off| true| false)?")) { 
			if (command.matches(".*(on|true)$")) {
				Properties.IS_DEBUG = true;
			} else if (command.matches(".*(off|false)$")) { 
				Properties.IS_DEBUG = false;
			}
			output("debug: " +Boolean.toString(Properties.IS_DEBUG)); 
		} else if (command.matches("^(ndl)( on| off| true| false)?")) { 
			if (command.matches(".*(on|true)$")) { 
				this.properties.setDownloadDisabled(true);
			} else if (command.matches(".*(off|false)$")) { 
				this.properties.setDownloadDisabled(false);
			}
			output("ndl: " +Boolean.toString(this.properties.isDownloadDisabled())); 
		} else if (command.matches("^(sds)( on| off| true| false)?")) { 
			if (command.matches(".*(on|true)$")) { 
				this.properties.setTryToSaveDiskspace(true);
			} else if (command.matches(".*(off|false)$")) {
				this.properties.setTryToSaveDiskspace(false);
			}
			output("sds: " +Boolean.toString(this.properties.isTryToSaveDiskspace())); 
		} else if (command.matches("^(audio(only)?)( on| off| true| false)?")) { 
			if (command.matches(".*(on|true)$")) {
				this.properties.setAudioOnly(true);
			} else if (command.matches(".*(off|false)$")) { 
				this.properties.setAudioOnly(false);
			}
			output("audioonly: " +Boolean.toString(this.properties.isAudioOnly())); 
		} else if (command.matches("^(quit|exit)")) { 
			shutdownApp();
		} else if (command.matches("^(proxy)( .*)?")) { 
			if (!command.matches("^(proxy)$")) { 
				// we replace "" and '' with <nothing> otherwise it's interpreted as host
				// perhaps some users don't know how to input
				// "proxy[ URL]" with an empty URL ;-)
				String newProxy = command.replaceAll("\"", "").replaceAll("'", "").replaceFirst("proxy ", ""); 
				debugOutput("new proxy: " +newProxy); 
				if (newProxy.matches(String_Constants.PROXY_REGEX)) {
					this.properties.setProxy(newProxy);
				} else {
					output(Messages.getString("ERROR_MESSAGE_PROXY_STRING_FALSE"));
				}
			}
			output("proxy: " +this.properties.getProxy()); 
		} else if (command.matches("^(config|c)$")) { 
			output("config: "); 
		} else if (command.matches("^(id)( .*)?")) { 
			if (command.matches(".*(on|true)$")) 
				this.properties.setSaveIdInFilename(true);
			else if (command.matches(".*(off|false)$")) 
				this.properties.setSaveIdInFilename(false);

			output("id: " +Boolean.toString(this.properties.isSaveIdInFilename())); 
		} else
			output(Messages.getString("INFO_MESSAGE_TRY_HELP"));
	}
	
	/**
	 * @param pane
	 */
	public void addComponentsToPane( final Container pane ) {
		this.panel = new JPanel();

		this.panel.setLayout( new GridBagLayout() );

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets( 5, 5, 5, 5 );
		gbc.anchor = GridBagConstraints.WEST;

		DLM = new DefaultListModel<YoutubeUrl>();
		this.urlList = new JList<YoutubeUrl>( DLM );
		// TODO maybe we add a button to remove added URLs from list?
//		this.userlist.setSelectionMode( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
		this.urlList.setFocusable( false );
		this.textArea = new JTextArea( 2, 2 );
		this.textArea.setEditable( true );
		//this.textarea.setFocusable( false );
		this.textArea.setFocusable( true );

		JScrollPane leftscrollpane = new JScrollPane( this.urlList );
		JScrollPane rightscrollpane = new JScrollPane( this.textArea );
		this.middlePane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, leftscrollpane, rightscrollpane );
		this.middlePane.setOneTouchExpandable( true );
		this.middlePane.setDividerLocation( 150 );

		Dimension minimumSize = new Dimension( 25, 25 );
		leftscrollpane.setMinimumSize( minimumSize );
		rightscrollpane.setMinimumSize( minimumSize );
		
		this.directoryButton = new JButton("", createImageIcon("images/open.png","")); // HACK "/resources/zsk/images/open.png" for Eclipse export runnable jar - maven target install does not yet produces such a file 
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.directoryButton.addActionListener( this );
		this.panel.add( this.directoryButton, gbc );
		
		properties.setSaveConfig(false);
		this.saveConfigCheckBox = new JCheckBox(Messages.getString("INFO_MESSAGE_SAVE_CONFIG")); 
		this.saveConfigCheckBox.setSelected(false);
		
		this.saveConfigCheckBox.addItemListener(this);
		this.panel.add(this.saveConfigCheckBox);
		
		this.saveConfigCheckBox.setEnabled(false);
		
		String fileSeparator = System.getProperty("file.separator"); 

		// TODO check if initial download directory exists
		// assume that at least the users homedir exists
		String homedir = (System.getProperty("user.home") +fileSeparator); 
		if (System.getProperty("user.home").equals("/home/knoedel")) {  
			homedir = "/home/knoedel/YouTube Downloads/ramdisk"; 
		}
		printSystemProperties(homedir);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		this.directoryTextField = new JTextField( homedir, 20+(Properties.IS_DEBUG?48:0) );
		this.directoryTextField.setEnabled( false );
		this.directoryTextField.setFocusable( true );
		this.directoryTextField.addActionListener( this );
		this.panel.add( this.directoryTextField, gbc);
		
		JLabel dirhint = new JLabel(Messages.getString("INFO_MESSAGE_DOWNLOAD_TO_FOLDER")); 

		gbc.gridx = 0;
		gbc.gridy = 1;
		this.panel.add( dirhint, gbc);
		
		debugOutput(String.format("heigth x width: %d x %d",Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height)); 
		
		this.middlePane.setPreferredSize( new Dimension( Toolkit.getDefaultToolkit().getScreenSize().width/3, Toolkit.getDefaultToolkit().getScreenSize().height/4+(Properties.IS_DEBUG?200:0) ) );

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 2;
		gbc.weightx = 2;
		gbc.gridwidth = 2;
		this.panel.add( this.middlePane, gbc );

		// radio buttons for resolution to download
		FRAME.fourkButton = new JRadioButton("4K");
		FRAME.fourkButton.setActionCommand("4k"); 
		FRAME.fourkButton.addActionListener(this); 
		FRAME.fourkButton.setToolTipText("2160p/4320p"); 
		FRAME.hdButton = new JRadioButton("HD"); 
		FRAME.hdButton.setActionCommand("hd"); 
		FRAME.hdButton.addActionListener(this); 
		FRAME.hdButton.setToolTipText("1080p/720p");
		FRAME.stdButton = new JRadioButton("Std");
		FRAME.stdButton.setActionCommand("std");
		FRAME.stdButton.addActionListener(this); 
		FRAME.stdButton.setToolTipText("480p/360p"); 
		FRAME.ldButton = new JRadioButton("LD"); 
		FRAME.ldButton.setActionCommand("ld"); 
		FRAME.ldButton.addActionListener(this); 
		FRAME.ldButton.setToolTipText("< 360p");
		
		setInitialResolutionsToProperties(properties);
		FRAME.fourkButton.setEnabled(true);
		FRAME.stdButton.setSelected(properties.getResolution().equals(Resolution.STANDARD_DEFINITION));
		FRAME.hdButton.setEnabled(true);
		FRAME.ldButton.setEnabled(true);
		
		ButtonGroup resolutionButtongroup = new ButtonGroup();
		resolutionButtongroup.add(FRAME.fourkButton);
		resolutionButtongroup.add(FRAME.hdButton);
		resolutionButtongroup.add(FRAME.stdButton);
		resolutionButtongroup.add(FRAME.ldButton);
		
		JPanel radiopanel = new JPanel(new GridLayout(1,0));
		radiopanel.add(FRAME.fourkButton);
		radiopanel.add(FRAME.hdButton);
		radiopanel.add(FRAME.stdButton);
		radiopanel.add(FRAME.ldButton);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 0;
		gbc.gridwidth = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHEAST;
		this.panel.add( radiopanel, gbc );

		// radio buttons for video format to download
		FRAME.mpgButton = new JRadioButton("MPEG");
		FRAME.mpgButton.setActionCommand("mpg");
		FRAME.mpgButton.addActionListener(this);
		FRAME.mpgButton.setToolTipText("Codec: H.264 MPEG-4"); 
		FRAME.webmButton = new JRadioButton("WEBM");
		FRAME.webmButton.setActionCommand("webm");
		FRAME.webmButton.addActionListener(this); 
		FRAME.webmButton.setToolTipText("Codec: Google/On2's VP8 or Googles WebM");
		FRAME.flvButton = new JRadioButton("FLV"); 
		FRAME.flvButton.setActionCommand("flv");
		FRAME.flvButton.addActionListener(this);
		FRAME.flvButton.setToolTipText("Codec: Flash Video (FLV1)"); 

		ButtonGroup videoformatButtongroup = new ButtonGroup();
		videoformatButtongroup.add(FRAME.mpgButton);
		videoformatButtongroup.add(FRAME.webmButton);
		videoformatButtongroup.add(FRAME.flvButton);

		setInitialVideoFormatsToProperties(properties);
		FRAME.mpgButton.setSelected(properties.getVideoformat().equals(Videoformat.MPG));
		FRAME.mpgButton.setEnabled(true);
		FRAME.webmButton.setEnabled(true);
		FRAME.flvButton.setEnabled(true);
		
		FRAME.save3dCheckBox = new JCheckBox("3D"); 
		FRAME.save3dCheckBox.setToolTipText("stereoscopic video"); 
		FRAME.save3dCheckBox.setSelected(properties.is3DButtonState());
		FRAME.save3dCheckBox.setEnabled(true);
		FRAME.save3dCheckBox.addItemListener(this);
		
		radiopanel = new JPanel(new GridLayout(1,0));
		radiopanel.add(FRAME.save3dCheckBox);
		radiopanel.add(FRAME.mpgButton);
		radiopanel.add(FRAME.webmButton);
		radiopanel.add(FRAME.flvButton);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridheight = 0;
		gbc.gridwidth = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHEAST;
		this.panel.add( radiopanel, gbc );
		
		JLabel hint = new JLabel(Messages.getString("INFO_MESSAGE_DRAG_N_DROP_URL"));

		gbc.fill = 0;
		gbc.gridwidth = 0;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.WEST;
		this.panel.add( hint, gbc );
		
		this.textInputField = new JTextField( 20 );
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 2;
		this.textInputField.setEnabled( true );
		this.textInputField.setFocusable( true );
		this.textInputField.addActionListener( this );
		this.textInputField.getDocument().addDocumentListener(this);
		this.panel.add( this.textInputField, gbc );
		
		
		//...where the GUI is constructed:
	    //Create the popup menu.
	    this.popup = new JPopupMenu();
	    JMenuItem menuItem = new JMenuItem(Messages.getString("INFO_MESSAGE_PAST_CLIPBOARD_INTO_TEXTFIELD"));
	    menuItem.setActionCommand("paste"); 
	    menuItem.addActionListener(this);
	    this.popup.add(menuItem);
	    menuItem = new JMenuItem(Messages.getString("INFO_MESSAGE_CLEAR_TEXTFIELD"));
	    menuItem.setActionCommand("clear"); 
	    menuItem.addActionListener(this);
	    this.popup.add(menuItem);
	    this.popup.addSeparator();
	    menuItem = new JMenuItem(Messages.getString("INFO_MESSAGE_COPY_MESSAGE"));
	    menuItem.setActionCommand("debug"); 
	    menuItem.addActionListener(this);
	    this.popup.add(menuItem);
	    
	    //Add listener to components that can bring up popup menus.
	    MouseListener popupListener = new PopupListener(this.popup);
	    this.textInputField.addMouseListener(popupListener);
	    this.textArea.addMouseListener(popupListener);
	    this.panel.addMouseListener(popupListener);
	    this.urlList.addMouseListener(popupListener);
		
		this.quitButton = new JButton( "" ,createImageIcon("images/exit.png",""));  // HACK "/resources/zsk/images/exit.png" for Eclipse export runnable jar - maven target install does not yet produces such a file
		gbc.gridx = 2;
		gbc.gridy = 5;
		gbc.gridwidth = 0;
		this.quitButton.addActionListener( this );
		this.quitButton.setActionCommand( "quit" ); 
		this.quitButton.setToolTipText( "Exit." ); 

		this.panel.add( this.quitButton, gbc );

		pane.add( this.panel );
		addWindowListener( this );
		
		FRAME.setDropTarget(new DropTarget(this, this));
		FRAME.textArea.setTransferHandler(null); // otherwise the dropped text would be inserted 

	} 
	
	public void setInitialVideoFormatsToProperties(Properties properties) {
		properties.setVideoformat(Videoformat.MPG);
		properties.set3DButtonState(false);
	}
	
	public void setInitialResolutionsToProperties(Properties properties) {
		properties.setResolution(Resolution.STANDARD_DEFINITION);
	}
	
	private ImageIcon createImageIcon(String path, String description) {
	    URL imgURL = getClass().getResource(path);
	    if (imgURL != null) {
	        return new ImageIcon(imgURL, description);
	    } 
	    System.err.println("Couldn't find file: " + path); 
	    return null; // TODO do not return NULL
	}
	
	private void printSystemProperties(String homeDirectoryPath) {
		debugOutput("user.home: " +System.getProperty("user.home") +"  homedir: " +homeDirectoryPath);
		debugOutput("os.name: " +System.getProperty("os.name")); 
		debugOutput("os.arch: " +System.getProperty("os.arch")); 
		debugOutput("os.version: " +System.getProperty("os.version"));
		debugOutput("Locale.getDefault: " +Locale.getDefault().toString());
	}
	
	
	private Thread createThreadWithDownloadThreadAndStarts() {
		DownloadThread downloadThread = createObservedDownloadThread();
		Thread thread = new Thread(downloadThread);
		thread.start();
		return thread;
	}
	
	private DownloadThread createObservedDownloadThread() {
		DownloadThread downloadThread = new DownloadThread(this.properties);
		downloadThread.addObserver(this);
		
		return downloadThread;
	}
	
	
	public void windowActivated( WindowEvent e ) {
			setFocusToTextField();
	} 
	
	private void setFocusToTextField() {
		this.textInputField.requestFocusInWindow();
	} 

	public void windowClosed( WindowEvent e ) {
	}

	public void windowDeactivated( WindowEvent e ) {
	}

	public void windowDeiconified( WindowEvent e ) {
	}

	public void windowIconified( WindowEvent e ) {
	}

	public void windowOpened( WindowEvent e ) {
	}
	
	/*
	 * "Movement!...signals clean. Range 20 meters." (Hudson)
	 */
	public void processComponentEvent(ComponentEvent e) {
		switch (e.getID()) {
		case ComponentEvent.COMPONENT_MOVED:
			break;
		case ComponentEvent.COMPONENT_RESIZED:
			FRAME.middlePane.setDividerLocation(FRAME.middlePane.getWidth() / 3);
			break;
		case ComponentEvent.COMPONENT_HIDDEN:
			break;
		case ComponentEvent.COMPONENT_SHOWN:
			break;
		}
	} 
	
	/**
	 * main entry point
	 * 
	 * @param args
	 */
	public void start(final String[] args) {
				
		try { // load from file
			CONFIG = new XMLConfiguration(CONFIG_FILE);
			output("configuration loaded from file: " +CONFIG.getBasePath()); 
			// create empty config entries?
		} catch (ConfigurationException e1) {
			output("configuration could not be load from file: " +CONFIG_FILE +" ..creating new one.");  
			CONFIG = new XMLConfiguration();
		}
		
		if (isCLI(args)) {
			Runtime.getRuntime().addShutdownHook(new Thread() {
			    public void run() { 
			    	debugOutput("shutdown hook handler. (cli only)"); 
			    	if (!window_Mode()) {
			    		shutdownApp();
			    	}
			    	debugOutput("shutdown hook handler. end run()"); 
			    }
			});
			
			IS_CLI = true;
			runCLI(args);			
		} else {
			try {
				UIManager.setLookAndFeel( "javax.swing.plaf.metal.MetalLookAndFeel" ); 
			} catch (UnsupportedLookAndFeelException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			} catch (InstantiationException ex) {
				ex.printStackTrace();
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
			} catch (java.lang.InternalError ie) {
				System.err.println(ie.getMessage());
				printHelp();
				System.exit(1);
			}

			try {
				javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
					public void run() {
						try {
							initializeStartUp();
						}  catch (java.awt.HeadlessException he) {
							System.err.println(he.getMessage());
							printHelp();
							System.exit(1);
						}
					}
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	} 
	
	private static boolean isCLI(String[] args) {
		return args.length<0?true:false;
	}
		
	private void runCLI(String[] arguments) {
		DLM = new DefaultListModel<YoutubeUrl>();
		int numberOfUrls = 0;
		String[] parameters;
		
		try {
			parameters = saveOptionsAndReturnParameters(arguments, properties);
		} catch (ArrayIndexOutOfBoundsException aioob) {
			output(Messages.getString("ERROR_MESSAGE_NOT_ENOUGH_PARAMETERS"));
			parameters = arguments;
		}
		
		if (parameters.length < arguments.length) {
			numberOfUrls = addUrlsToDownloadList(parameters);
		}
		
		if (numberOfUrls > 0) {
			initializeStartUp();
		} else {
			printHelp();
		}
	} 
	
	public int addUrlsToDownloadList(String[] parameters) {
		int countUrls = 0;
		for (int i = 0; i < parameters.length; i++) {
			String parameter = parameters[i];
			if (isParameterUrl(parameter)) {
				addYoutubeUrlToList(parameter);
				debugOutput("adding URL: " +parameter);
				countUrls++;
				continue;
			}
			debugOutput("wrong URL: " +parameters[i]); 
			output(String.format("URL: %d",i) +Messages.getString("ERROR_MESSAGE_DONT_LOOK_AN_URL") +parameters[i]);
		}
		return countUrls;
	}
	
	public boolean isParameterUrl(String parameter) {
		return parameter.matches(String_Constants.YOUTUBE_REGEX +".*"); 
	}
	
	public String[] saveOptionsAndReturnParameters(String[] args, Properties properties) throws ArrayIndexOutOfBoundsException {
		int numberOfOptins=0;
							
		for (int i=0;i<args.length;i++) {
			if (args[i].toLowerCase().matches("(--)?h(elp|ilfe)?")) { 
				printHelp();
				return args; // dont load any videos if --help is submitted
			}			
			if (isArgumentResolutionOption(args[i])) {
				saveResolution(args[i], properties);
				numberOfOptins++;
			}
			if (isArgumentVideoformatOption(args[i])) { 
				saveVideoformat(args[i], properties); 
				numberOfOptins++;
			}
		} 		
		return separateParametersFromOptions(args, numberOfOptins);
	} 
	
	public boolean isArgumentVideoformatOption(String argument) {
		return argument.matches(("-f|-w"));
	}
	
	public void saveVideoformat(String argument, Properties properties) {
		if (argument.equals("-f")) { 
			properties.setVideoformat(Videoformat.FLV);
			debugOutput("parameter -f"); 
		} 
		if (argument.equals("-w")) { 
			properties.setVideoformat(Videoformat.WEBM);
			debugOutput("parameter -w"); 
		}		
	}
	
	public boolean isArgumentResolutionOption(String argument) {
		return argument.matches(("-l|-h"));
	}
	
	public void saveResolution(String argument, Properties properties) {
		if (argument.equals("-l")) { 
			properties.setResolution(Resolution.LOW_DEFINITION);
			debugOutput("parameter -l"); 
		} 
		if (argument.equals("-h")) { 
			properties.setResolution(Resolution.HIGH_DEFINITION);
			debugOutput("parameter -h"); 
		}		
	}
		
	public String[] separateParametersFromOptions(String[] args, int numberOfOptions) {
		return Arrays.copyOfRange(args, numberOfOptions, args.length);
	}
	
	private void initializeStartUp() {
		this.properties.setProxy(System.getenv("http_proxy"));
		if (IS_CLI) {
			this.properties.setSaveIdInFilename(true);
			setHomedirectoryToSavefolder(this.properties); 
			printApplicationInformation();
		} else {
			initalizeFRAME();
			this.properties.setSavefolder(FRAME.directoryTextField.getText());
			printApplicationInformation();
			printHelpDescriptionToConsole();
		}	
		initializeDownloadThreads();		
	} 
	
	private void setHomedirectoryToSavefolder(Properties properties) {
		if (properties.getSavefolder()=="") { 
			String homedir = readHomedirectoryFromSystem();
			properties.setSavefolder(homedir); 
		}
	}
	
	private String readHomedirectoryFromSystem() {
		String homedir = System.getProperty("user.dir");  
		if (!homedir.endsWith(System.getProperty("file.separator"))){ 
			homedir += System.getProperty("file.separator"); 
		}
		return homedir;
	}
	
	private void initalizeFRAME() {
		setDefaultLookAndFeelDecorated(false);
		FRAME = new JFCMainClient(this.properties);
		FRAME.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		FRAME.addComponentsToPane(FRAME.getContentPane());
		FRAME.pack();
		FRAME.setVisible( true );
	}
	
	private void initializeDownloadThreads() {
		// lets respect the upload limit of google (youtube)
		// downloading is faster than viewing anyway so don't start more than six threads and don't play around with the URL-strings please!!!
		THREAD_1 = createThreadWithDownloadThreadAndStarts();
		if (!Properties.IS_DEBUG) {
			THREAD_2 = createThreadWithDownloadThreadAndStarts();
			THREAD_3 = createThreadWithDownloadThreadAndStarts();
			THREAD_4 = createThreadWithDownloadThreadAndStarts();
			THREAD_5 = createThreadWithDownloadThreadAndStarts();
			THREAD_6 = createThreadWithDownloadThreadAndStarts();
		}
	}

	static void printHelp() {
		System.out.println(Properties.VERSION);
		System.out.println("Usage: \n"); 
		System.out.println("java -jar runytd2.jar <Q> <F> ['youtube-url' ['youtube-url']]\n"); 
		System.out.println("<Q> = [-l|-h]   for video quality (low, high) - defaults to standard if <Q> is omitted"); 
		System.out.println("<F> = [-m]      for video format (mpeg) - defaults to mpeg if <F> is omitted"); 
		System.out.println("<F> = [-w]      for video format (webm) - defaults to mpeg if <F> is omitted"); 
		System.out.println("<F> = [-f]      for video format (flv)  - defaults to mpeg if <F> is omitted"); 
		// TODO we implement that later - some testing is still needed
		// formatter.printHelp("java -jar runytd2.jar", sCLIOptions, true);		
		System.out.println(""); 
		System.out.println(Messages.getString("INFO_MESSAGE_DONT_FORGET_WRAP_URL"));
		System.out.println(""); 
		System.out.println(Messages.getString("INFO_MESSAGE_PROJECT"));
		System.out.println(""); 
	} 
	
	public void changedUpdate(DocumentEvent e) {
		checkInputFieldForYoutubeUrl();
	}

	public void insertUpdate(DocumentEvent e) {
		checkInputFieldForYoutubeUrl();
	} 

	public void removeUpdate(DocumentEvent e) {
		checkInputFieldForYoutubeUrl();
	}

	/**
	 * check if a youtube-URL was pasted or typed in
	 * if yes cut it out and send it to the URLList to get processed by one of the threads
	 * 
	 * the user can paste a long string containing many youtube-URLs .. but here is work to do because we have to erase the string(s) that remain(s)
	 */
	void checkInputFieldForYoutubeUrl() {
		String inputText = FRAME.textInputField.getText();
		inputText = replaceWatchAndPlaylistFromInput(inputText);
		String inputTextWithoutUrl =  trimFirstUrlFromInput(inputText);
		// if nothing could be replaced we have to yt-URL found
		if (inputText.equals(inputTextWithoutUrl)){
			return;
		}
		// starting at index 0 because szYTREGEX should start with ^ 
		// if szYTREGEX does not start with ^ then you have to find the index where the match is before you can cut out the URL 
		String youtubeUrl = inputText.substring(0, inputText.length()-inputTextWithoutUrl.length());
		debugOutput("input: " +inputText +" url: " +youtubeUrl);
		addYoutubeUrlToList(youtubeUrl);
		inputText = inputText.substring(youtubeUrl.length()); 		
		final String outputText = clearIfShorterAsYoutubeUrl(inputText);
		
		//TODO Caution there is a hidden recursion, must refactored
		updateFRAMEtextInputField(outputText);
	}
	
	private String replaceWatchAndPlaylistFromInput(String input) {
		input = input.replaceAll(String_Constants.WATCH_REGEX, "/watch?v=");  
		input = input.replaceAll(" ", "");  
		input = input.replaceAll(String_Constants.PLAYLIST_REGEX, "/watch?v="); 
		return input;
	}
	
	private String trimFirstUrlFromInput(String input) {
		return input.replaceFirst(String_Constants.YOUTUBE_REGEX, "");
	}
	
	private String clearIfShorterAsYoutubeUrl(String inputText) {
		if (inputText.length() < String_Constants.SHORTEST_YOUTUBE_URL.length()){ 
			inputText = ""; 
		}
		return inputText;
	}
	
	private void updateFRAMEtextInputField(final String outputText) {
		Thread worker = new Thread() {
            public void run() {
            	synchronized (FRAME.textInputField) {
            		FRAME.textInputField.setText(outputText);
				}
            }
        };
        SwingUtilities.invokeLater(worker);
	}
			
	/**
	 * processing event of dropping a HTTP URL, YT-Video Image or plain text (URL) onto the frame
	 * 
	 * seems not to work with M$-IE (8,9) - what a pity! (at least the video image drop does not work, url seems to work though)
	 */
	public void drop(DropTargetDropEvent dropTargetDropEvent) {
		Transferable transferable = dropTargetDropEvent.getTransferable();
		DataFlavor[] dataFlavors = transferable.getTransferDataFlavors();
		String str = ""; 
			
		debugOutput("DataFlavors found: " +dataFlavors.length); 
		
		for (DataFlavor dataFlavor : dataFlavors) {
			
			if (dataFlavor.isFlavorTextType()) {
				try {
					dropTargetDropEvent.acceptDrop(dropTargetDropEvent.getDropAction());
				} catch (Throwable t) {
					t.printStackTrace();
				}
				try {
					if (transferable.getTransferData(dataFlavor) instanceof InputStreamReader) {
						debugOutput("Text-InputStream"); 
						BufferedReader textreader = new BufferedReader((Reader) transferable.getTransferData(dataFlavor));
						String line = ""; 
						try {
							while (line != null) {
								line = textreader.readLine();
								if (line != null) {
									str += line;
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							textreader.close();
						}
						str = str.replaceAll("<[^>]*>", ""); // remove HTML tags, especially a hrefs - ignore HTML characters like &szlig; (which are no tags)  
					} else if (transferable.getTransferData(dataFlavor) instanceof InputStream) {
						
						InputStream inputStream = (InputStream) transferable.getTransferData(dataFlavor);
						BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
						
						String readDataFromStream = readDataFromBufferedInputStream(bufferedInputStream);
						
						debugOutput("Byte-InputStream: read from Inputstream: " +readDataFromStream); 
					} else {
						str = transferable.getTransferData(dataFlavor).toString();
					}
				} catch (IOException ioe) {
					ioe.printStackTrace();
				} catch (UnsupportedFlavorException ufe) {
					ufe.printStackTrace();
				}

				debugOutput("drop event text: " +str +" (" +dataFlavor.getMimeType() +") ");   
				// insert text into textfield - almost the same as user drops text/url into this field
				// except special characaters -> from http://de.wikipedia.org/wiki/GNU-Projekt („GNU is not Unix“)(&bdquo;GNU is not Unix&ldquo;)
				// two drops from same source .. one time in textfield and elsewhere - maybe we change that later?!
				if (str.matches(String_Constants.YOUTUBE_REGEX +"(.*)")) { 
					synchronized (FRAME.textInputField) {
						FRAME.textInputField.setText(str +FRAME.textInputField.getText());
					}
					debugOutput("breaking for-loop with str: " +str); 
					break;
				}
			} else {
				debugOutput("drop event unknown type: " +dataFlavor.getHumanPresentableName()); 
			}
		} 
		dropTargetDropEvent.dropComplete(true);
	} 
	
	private String readDataFromBufferedInputStream(BufferedInputStream bufferedInputStream) throws IOException{
		
		int readByte = bufferedInputStream.read();
		String stringOfCharakters = ""; 
		
		while (readByte != -1) {
			if (readByte != 0) {
				stringOfCharakters += new Character((char) readByte).toString();
			}
			readByte = bufferedInputStream.read();
		} 
		return stringOfCharakters;
	}
		
	public void update(Observable o, Object arg) {
		addTextToConsole((String)arg);
	}

	public void itemStateChanged(ItemEvent itemEvent) {
		if (itemEvent.getSource()==this.saveConfigCheckBox) {
			if (itemEvent.getStateChange()==ItemEvent.SELECTED) {
				properties.setSaveConfig(true);
				output(Messages.getString("INFO_MESSAGE_CONFIG_SAVE_ON_EXIT")); 
			} else {
				properties.setSaveConfig(false);
				output(Messages.getString("INFO_MESSAGE_CONFIG_DONT_SAVE_ON_EXIT")); 
			}		
		}
		if (itemEvent.getSource()==this.save3dCheckBox){
			if (itemEvent.getStateChange()==ItemEvent.SELECTED) { 
				properties.set3DButtonState(true);
				output(Messages.getString("INFO_MESSAGE_TRY_3D")); 
			} else {
				properties.set3DButtonState(false);
				output(Messages.getString("INFO_MESSAGE_TRY_2D")); 
			}
		}
	} 
	
	public void stateChanged(ChangeEvent e) {
	}


	public void dragEnter(DropTargetDragEvent dtde) {
	}


	public void dragOver(DropTargetDragEvent dtde) {
	}


	public void dropActionChanged(DropTargetDragEvent dtde) {
	}


	public void dragExit(DropTargetEvent dte) {
	}
	
	static synchronized YoutubeUrl getFirstDownloadableUrlFromList() { 
		
		for (int i = 0; i < DLM.getSize(); i++) {
			if (isElementWaitingForDownload(DLM.get(i))) {
				return DLM.get(i);
			}
		}
		throw new DLMException("There are no downloadable urls in DLM-List"); 
	} 
	
	private static boolean isElementWaitingForDownload(YoutubeUrl youtubeUrl) {
		if (youtubeUrl.isDownloading()) {
			return false;
		}
		return true;
	}
	
	static void updateYoutubeUrlInList( YoutubeUrl youtubeUrl ) {
		synchronized (DLM) {
			try {
				int idx = DLM.indexOf(youtubeUrl);
				DLM.setElementAt(youtubeUrl, idx);
			} catch (IndexOutOfBoundsException ioobe) {
				debugOutput(ioobe.getMessage());
			}
		}
	} 
	
	static void removeYoutubeUrlFromList(YoutubeUrl youtubeUrl) {
		synchronized (DLM) {
			try {
				int i = DLM.indexOf( youtubeUrl );
				DLM.remove( i );
			} catch (IndexOutOfBoundsException ioobe) {
				debugOutput(ioobe.getMessage());
			}
		}
	} 
	
	private void printHelpDescriptionToConsole() {
		output(String_Constants.COMMAND_DEBUG + Messages.getString("SHOW_DEBUG_COMMAND_DESCRIPTION"));
		output(String_Constants.COMMAND_HELP + Messages.getString("SHOW_HELP_COMMAND_DESCRIPTION"));
		output(String_Constants.COMMAND_ID + Messages.getString("SHOW_ID_COMMAND_DESCRIPTION"));
		output(String_Constants.COMMAND_NO_DOWNLOAD + Messages.getString("SHOW_NO_DOWNLOAD_COMMAND_DESCRIPTION"));
		output(String_Constants.COMMAND_PROXY + Messages.getString("SHOW_PROXY_COMMAND_DESCRIPTION") +" - [http[s]://]proxyhost:proxyport");
		output(String_Constants.COMMAND_QUIET + Messages.getString("SHOW_QUIET_COMMAND_DESCRIPTION"));
		output(String_Constants.COMMAND_SAVE_SPACE + Messages.getString("SHOW_SAVE_DISKSPACE_DESCRIPTION"));
		output(String_Constants.COMMAND_AUDIO_ONLY + Messages.getString("SHOW_AUDIO_ONLY_COMMAND_DESCRIPTION"));
		output(String_Constants.COMMAND_VERSION +Messages.getString("SHOW_VERSION_COMMAND_DESCRIPTION")); 
	}

	private void printApplicationInformation(){		
		output("------------------------------"); 
		output("Project :\t\tYTD2 " +Properties.VERSION +" " +Properties.PROJECT_URL); 
		output("author :\t\t" +Properties.AUTHOR); 
		output("version :\t\t" +Properties.VERSION); 
		output("modus :\t\t" +(Properties.IS_DEBUG?"DEBUG ":""));
		output("http_proxy :\t\t" +this.properties.getProxy());
		output(Messages.getString("LABEL_DOWNLOAD_FOLDER") +this.properties.getSavefolder());
		output(""); 
		output(Messages.getString("INFO_MESSAGE_PROJECT"));
		output("");
	}
	
	private boolean window_Mode() {
		return (FRAME != null);
	}
} 