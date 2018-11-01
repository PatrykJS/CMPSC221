package zsk.helper;

import zsk.Resolution;
import zsk.Videoformat;


public class Properties {
	
	public static final String VERSION = "V20150609_2340d"; 
	public static final String AUTHOR = "MrKnödelmann";
	public static final String PROJECT_URL = "http://sourceforge.net/projects/ytd2/"; 
	public final static String CONFIG_FILE = "ytd2.config.xml";
	
	// more or less (internal) output
	// set to True or add 'd' after mod-time in szVersion
	public static boolean IS_DEBUG = VERSION.matches("V[0-9]+_[0-9]+d.*");	
	
	private String proxy = "";
	// just report file size of HTTP header - don't download binary data (the video)
	// TODO this State is in reverse logic, should changed in a natural feeling ;)
	private boolean downloadDisabled = IS_DEBUG;	
	// ONLY download audio file, the one with a video stream in it (if available), only applies to adaptive format URLs
	private boolean audioOnly = false;
	// append youtube ID (http://www.youtube.com/watch?v=XY) to filename 
	private boolean saveIdInFilename = false;
	// save diskspace - try to download e.g. 720p before 1080p if HD is set
	private boolean tryToSaveDiskspace = false;
	private String savefolder = "";	

	private boolean _3DButtonState;
	private boolean idButtonState;
	private boolean quitRequested;
	private boolean saveConfig;
	
	private Resolution resolution;
	private Videoformat videoformat;
	
		
	public boolean isSaveConfig() {
		return saveConfig;
	}

	public void setSaveConfig(boolean saveConfig) {
		this.saveConfig = saveConfig;
	}

	public Resolution getResolution() {
		return resolution;
	}

	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}

	public Videoformat getVideoformat() {
		return videoformat;
	}

	public void setVideoformat(Videoformat videoformat) {
		this.videoformat = videoformat;
	}

	public boolean is3DButtonState() {
		return _3DButtonState;
	}

	public void set3DButtonState(boolean _3dButtonState) {
		_3DButtonState = _3dButtonState;
	}

	public boolean isSaveIdInFilename() {
		return saveIdInFilename;
	}

	public void setSaveIdInFilename(boolean saveIdInFilename) {
		this.saveIdInFilename = saveIdInFilename;
	}

	public boolean isTryToSaveDiskspace() {
		return tryToSaveDiskspace;
	}

	public void setTryToSaveDiskspace(boolean saveDiskspace) {
		this.tryToSaveDiskspace = saveDiskspace;
	}

	public boolean isDownloadDisabled() {
		return downloadDisabled;
	}
	
	public boolean isDownloadEnabled() {
		return !downloadDisabled;
	}

	public void setDownloadDisabled(boolean downloadDisabled) {
		this.downloadDisabled = downloadDisabled;
	}

	public boolean isAudioOnly() {
		return audioOnly;
	}

	public void setAudioOnly(boolean audioOnly) {
		this.audioOnly = audioOnly;
	}

	public String getSavefolder() {
		return savefolder;
	}

	public void setSavefolder(String savefolder) {
		this.savefolder = savefolder;
	}

	public String getProxy() {
		return proxy;
	}

	public void setProxy(String proxy) {
		if(proxy==null){
			proxy="";
		}
		this.proxy = proxy;
	}
	
	public boolean isIdButtonState() {
		return idButtonState;
	}

	public void setIdButtonState(boolean idButtonState) {
		this.idButtonState = idButtonState;
	}

	public boolean isQuitRequested() {
		return quitRequested;
	}

	public void setQuitRequested(boolean quitRequested) {
		this.quitRequested = quitRequested;
	}
}
