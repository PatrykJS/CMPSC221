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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Vector;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import zsk.helper.Properties;
import zsk.helper.String_Constants;
import zsk.http.PageCrawler;
import zsk.http.SourceCodeUrlTransformer;
import zsk.http.TypeOfUrl;
import zsk.i18n.Messages;

/**
 * http://www.youtube.com/watch?v=Cx6eaVeYXOs					4K (Ultra HD)
 * http://www.youtube.com/watch?v=9QFK1cLhytY					Javatar and .NOT
 * http://www.youtube.com/watch?v=Mt7zsortIXs				 	1080p "Lady Java"
 * http://www.youtube.com/watch?v=WowZLe95WDY					Tom Petty And the Heartbreakers - Learning to Fly (with lyrics)
 * http://www.youtube.com/watch?v=86OfBExGSE0					URZ 720p
 * http://www.youtube.com/watch?v=cNOP2t9FObw 					Blade 360 - 480
 * http://www.youtube.com/watch?v=HvQBrM_i8bU					MZ 1000 Street Fighter
 * http://www.youtube.com/watch?v=5fB_wIP21_Q					KTM 1190 Adventure vs. BMW R 1200GS 1080p (mp4, vorbis - audio)
 * http://www.youtube.com/watch?v=yVpbFMhOAwE					How Linux is build
 * http://www.youtube.com/watch?v=4XpnKHJAok8					Tech Talk: Linus Torvalds on git 
 * http://www.youtube.com/watch?v=5nj77mJlzrc  					BF109 G																																																																																								In lovely memory of my grandpa, who used to fly around the clouds. 
 * http://www.youtube.com/watch?v=I3lq1yQo8OY					Showdown: Air Combat - Me-109																																																																																		http://www.youtube.com/watch?v=yxXBhKJnRR8
 * http://www.youtube.com/watch?v=RYXd60D_kgQ					Me 262 Flys Again!
 * http://www.youtube.com/watch?v=6ejc9_yR5oQ					Focke Wulf 190 attacks Boeing B 17 in 2009 at Hahnweide
 * https://www.youtube.com/watch?v=yNPECkESPbU					Linkin Park feat. Jay-Z - Numb/Encore
 * https://www.youtube.com/watch?v=rfOY8ePOs_0					15 seconds short video
 * 
 * technobase.fm / We Are One! 
 *
 * http://www.youtube.com/watch?v=ZoWB_IYoN60					"An unloaded weapon always shoots the loudest" Col. McQueen - Space 2063
 *
 * ODOT http://sourceforge.net/p/ytd2/bugs/7/ http://www.youtube.com/watch?v=fRVVzXnRsUQ   uses RTMPE (http://en.wikipedia.org/wiki/Protected_Streaming), which ytd2 cannot download atm 
 *
 */
public class DownloadThread extends Observable implements Runnable {
	
	private static final Logger log = Logger.getLogger(DownloadThread.class.getName() ); 
	
	static int THREAD_COUNT = 0;
			
	private Properties properties;
	private PageCrawler crawler;
	private YoutubeUrl youtubeUrl = null;	// main URL (youtube start web page) as object
	private Vector<YoutubeUrl> nextVideoUrls = new Vector<YoutubeUrl>();	// list of URLs from webpage source
	
	private TypeOfUrl urlType = TypeOfUrl.NO_URL; // downloadone() for the 3 webrequest to one video
	
	private boolean noDownload;
	private boolean isInterrupted = false; 	// basically the same as Thread.isInterrupted()
	
	private String url = null;				// main URL (youtube start web page)
	private String title = null;			// will be used as filename
	private String videoUrl = null;			// one video web resource
	private String fileName = null;			// contains the absolute filename
	
	private String contentType = null;
	private String contentLength = null;
	
	private CloseableHttpClient	httpClient = null;
	private HttpGet	request = null;
	
	private BufferedInputStream	binaryReader = null;
    
	
	public DownloadThread(Properties properties) {
		super();
		this.properties = properties;
		this.crawler = new PageCrawler();
	} 
	
	
	public void run() {
		Handler handler = new ConsoleHandler();
		log.addHandler(handler);
		boolean downloadOK = false;
		
		while (!this.isInterrupted) {
			try {
				synchronized (JFCMainClient.DLM) {

					// check for new URLs (if they got pasted faster than threads removing them)
					do{
						JFCMainClient.DLM.wait(1000); 
						log.info("Thread woke up ");
						this.isInterrupted = JFCMainClient.getQuitRequested(); // if quit was pressed while this threads works it would not get the InterruptedException and therefore prevent application shutdown
						
						log.info("URLs remain in list: " +Integer.toString(JFCMainClient.DLM.size()));
						// running in CLI mode?
						if (JFCMainClient.FRAME == null) {
							if (JFCMainClient.DLM.size() == 0) {
								log.info("Thread ran out of work.");
								if (DownloadThread.THREAD_COUNT == 0) {
									// this is the last DownloadThread so shutdown Application as well
									log.severe("all DownloadThreads ended. should shuting down ytd2 but is disabled.");
									// TODO disabled, must refactored, That is a unnecessary dependency to JFCMainClient.class 
	//								JFCMainClient.shutdownApp();
								} else {
									// this is not the last DownloadThread so shutdown this thread only
									this.isInterrupted = true;
									log.info("end this Thread.");
									throw new NullPointerException("end this thread.");
								}
							}
						}
						this.youtubeUrl = JFCMainClient.getFirstDownloadableUrlFromList();
					} while(this.youtubeUrl == null);
					
					this.url = this.youtubeUrl.toString();
					sendMessage(Messages.getString("INFO_MESSAGE_TRY_TO_DOWNLOAD") +this.url);					
				}

				this.youtubeUrl.setDownloading();
				JFCMainClient.updateYoutubeUrlInList(this.youtubeUrl);
				
				this.noDownload = properties.isDownloadDisabled(); // copy ndl-state because this thread should end with a complete file (and report so) even if someone switches to no-download before this thread is finished
				
				String preparedUrlForDownload = crawler.removeXTags(this.youtubeUrl.getUrl());
						
				// download one webresource and show result
				downloadOK = downloadOne(preparedUrlForDownload); 
				this.urlType = TypeOfUrl.NO_URL;
				if (downloadOK && !this.noDownload) {
					sendMessage(Messages.getString("INFO_MESSAGE_DOWNLOAD_COMPLETED") +"\"" +getTitle() +"\" to " +this.getFileName());
				} else {
					sendMessage(Messages.getString("INFO_MESSAGE_NOT_DOWNLOADED") +"\"" +getTitle() +"\""); 
				}
				
				// running in CLI mode?
				// TODO check if else block in history, is doing the same
				if (JFCMainClient.FRAME == null) {
					JFCMainClient.removeYoutubeUrlFromList(this.youtubeUrl);
				} else {
					JFCMainClient.removeYoutubeUrlFromList(this.youtubeUrl); 
				}
			} catch (DLMException e) {
				log.severe(e.getMessage());
			} catch (InterruptedException e) {
				this.isInterrupted = true;
			} catch (NullPointerException npe) {
				npe.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		} 
		log.info("Thread ended");
		DownloadThread.THREAD_COUNT--;
	} 
	
	private void sendMessage(String message){
        setChanged();
        notifyObservers(this.getClass().getName() +Integer.toString(DownloadThread.THREAD_COUNT++) +" :" +message);
	}
	
	private String getTitle() {
		if (this.title != null){
			return this.title;
		}  else {
			return("");
		}
	} 
	
	private String getFileName() {
		if (this.fileName != null) {
			return this.fileName;
		}  else {
			return("");
		}
	} 
	
	private boolean downloadOne(String url) throws IOException {
		
		BufferedReader page = null;
		String statusLine = "";
		boolean isDownloadOK = false;
		boolean isHttpReturnCode_200 = false;
		boolean isHttpReturnCode_204 = false;
		boolean isHttpReturnCode_302 = false;
	
		this.urlType = changeStateBetweenParseOrDownloadUrl(urlType);
				
		if (shouldNotDownloadMore(url)) { 
			return false;
		}
	
		log.info("start");
		CloseableHttpResponse response = requestUrl(url);
		
		try {
			statusLine = response.getStatusLine().toString();
			this.contentType = response.getFirstHeader("Content-Type").getValue();
			this.contentLength = response.getFirstHeader("Content-Length").getValue();
			
			log.info("HTTP response status line: " +statusLine);

			isHttpReturnCode_200 = isHttpReturnCode200(statusLine);
			isHttpReturnCode_204 = isHttpReturnCode204(statusLine);
			isHttpReturnCode_302 = isHttpReturnCode302(statusLine);
			isDownloadOK = isHttpReturnCode_200;
			
			// abort if HTTP response code is != 200, != 302 and !=204 - wrong URL?
			boolean shouldAbortDownload = (!isHttpReturnCode_200 & !isHttpReturnCode_204 & !isHttpReturnCode_302);
			if (shouldAbortDownload) {
				log.severe("StatusCode: " +statusLine +" aborting download for Url: "+url);
				sendMessage("Url is not working, StatusCode: " +statusLine +", abort download of title: \""+this.title +"\"");
				return false;
			}
			if (isHttpReturnCode_204) {
				isDownloadOK = downloadOne(this.nextVideoUrls.get(0).getUrl());
				return isDownloadOK;
			}
			if (isHttpReturnCode_302) { 
				log.info("location from HTTP Header: " +response.getFirstHeader("Location").getValue());
			}
		} catch (NullPointerException npe) {
			// if an IllegalStateException was catched while calling httpclient.execute(httpget) a NPE is caught here because
			// response.getStatusLine() == null
			this.videoUrl = null;
		}
		
		HttpEntity entity = null;
        try {
            entity = response.getEntity();
        } catch (NullPointerException npe) {
        	npe.printStackTrace();
        }
        
        // try to read HTTP response body
        if (entity != null) {
			try {
				if (this.contentType.matches("^text/html(.*)")) {
					page = new BufferedReader(new InputStreamReader(entity.getContent()));
				} else {
					this.binaryReader = new BufferedInputStream( entity.getContent());
				}
			} catch (IllegalStateException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
            try {
            	// test if we got a webpage
            	if (this.contentType.matches("^text/html(.*)")) {
            		isDownloadOK = savePageContent(page);
            	// test if we got the binary content 
            	} else if (this.contentType.matches("(audio|video)/(.*)|application/octet-stream")) {

            		addAdditionalAudiostreamUrlToDownloadList();
            		         		
            		if (properties.isDownloadEnabled()){
            			saveBinaryData(page);
            		} else {
            			reportHeaderInfo(response);
            		}
            	} else { // content-type is not video/
            		isDownloadOK = false;
            		this.videoUrl = null;
            	}
            } catch (IOException ex) {
                try {
					throw ex;
				} catch (IOException e) {
					e.printStackTrace();
				}
            } catch (RuntimeException ex) {
                try {
					throw ex;
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        } 
        
       	this.httpClient.close();

        log.info("done: " +url);
        if (this.videoUrl==null) {
        	this.videoUrl=""; // to prevent NPE
        }
        
    	// enter recursion - download video resource
        if (this.videoUrl.matches(String_Constants.URL_REGEX)) { 	
        	log.info("try to download video from URL: " +this.videoUrl);
        	videoUrl= crawler.removeXTags(videoUrl);
        	isDownloadOK = downloadOne(this.videoUrl);
        } else {
        	// no more recursion - html source has been read
        	// rc==false if video could not downloaded because of some error (like wrong protocol or country restriction)
        	if (!isDownloadOK) {
        		log.info(Messages.getString("DEBUG_MESSAGE_CANT_DOWNLOAD_URL_SEEM_WRONG") +this.url);
        		sendMessage(Messages.getString("ERROR_MESSAGE_TO_FIND_VIDEO_URL"));
        		sendMessage((Messages.getString("INFO_MESSAGE_CONSIDER_TO_REPORT_URL_TO_AUTHOR")) +this.url);
        		this.videoUrl = null;
        	}
        } 
        
        this.videoUrl = null;

    	return isDownloadOK;		
	}
	
	protected CloseableHttpResponse requestUrl(String url) {
		CloseableHttpResponse response = null;
		try {
			this.request = new HttpGet(url);
			tryToAddSecifiedProxy(properties.getProxy());			
			this.httpClient = HttpClients.createDefault();	
			
	        log.info("executing request: " +this.request.getRequestLine().toString());
	        log.info("uri: " +this.request.getURI().toString());
	        response = this.httpClient.execute(this.request);
			
		} catch (ClientProtocolException clientProtocolException) {
			log.log(Level.SEVERE, clientProtocolException.getMessage());
		} catch (UnknownHostException unknownHostException) {
			sendMessage((Messages.getString("ERROR_MESSAGE_CONNECTION_FAILURE")) +unknownHostException.getMessage());
			log.log(Level.SEVERE, unknownHostException.getMessage());
		} catch (IOException ioException) {
			log.log(Level.SEVERE, ioException.getMessage());
		} catch (IllegalStateException illegalStateException) {
			log.log(Level.SEVERE, illegalStateException.getMessage());
		}
		return response;
	}
	
	protected void tryToAddSecifiedProxy(String proxyString) {
		try {
			HttpHost proxy = createSpecifiedProxy(proxyString);
			RequestConfig requestConfig = buildSpecifiedRequestConfig(proxy);
			
			this.request.setConfig(requestConfig);
			
			log.info("Use specified Proxy: " +proxyString);
		} catch(ProxyException e) {
			log.info(e.getMessage());
		}
	}
	
	protected HttpHost createSpecifiedProxy(String proxyString) throws ProxyException {
		if (proxyString == null || proxyString.isEmpty()) {
			throw new ProxyException("Proxy isn't specified, execute Request whithout Proxy.");
		}
		
		proxyString = proxyString.toLowerCase().replaceFirst("http(s?)://", "");
		String hostname = proxyString.replaceFirst(":(.*)", "");
		int port = Integer.parseInt( proxyString.replaceAll("(.*):", ""));
		
		return new HttpHost(hostname, port, "http");
	}
	
	protected RequestConfig buildSpecifiedRequestConfig(HttpHost proxy) {
		Builder requestConfigBuilder = RequestConfig.custom().setProxy(proxy);
		return requestConfigBuilder.build();
	}
	
	public void addAdditionalAudiostreamUrlToDownloadList() {
		if (this.urlType.equals(TypeOfUrl.SOURCECODE_URL)) {
			log.info("last response code==true - download: " +this.nextVideoUrls.get(0).getYoutubeId());
			if (this.nextVideoUrls.get(0).getAudioStreamUrl().isEmpty()) { 
				log.info("download audio stream? no");
			} else {
				// FIXME audio stream has no filename if we add the direct URL to the GUI url list - we should add YTURL objects, not Strings!
				log.info("download audio stream? yes - " +this.nextVideoUrls.get(0).getAudioStreamUrl());
				this.youtubeUrl.setTitle(getTitle());
				JFCMainClient.addYoutubeUrlToList(this.nextVideoUrls.get(0).getAudioStreamUrl(), getTitle(), "AUDIO");
			}
		}   
	}
	
	public boolean shouldNotDownloadMore(String url) {
		if (url==null || url.isEmpty()) {
			log.info("Stopping download because there are no further URLs to process");
			return true; 
		} else if (JFCMainClient.getQuitRequested()) {
			log.info("Stopping download because quit is requested");
			return true; 
		}		
		return false;
	}

	public boolean isHttpReturnCode200(String statusLine) {
		return statusLine.toLowerCase().matches("^(http)(.*)200(.*)");	
	}

	public boolean isHttpReturnCode204(String statusLine) {
		return statusLine.toLowerCase().matches("^(http)(.*)204(.*)");	
	}
	
	public boolean isHttpReturnCode302(String statusLine) {
		return statusLine.toLowerCase().matches("^(http)(.*)302(.*)");	
	}
	
	public boolean savePageContent(BufferedReader page) throws IOException {
		boolean rc = false;
		
		// read html lines one by one and search for java script array of video URLs
		String pageline = ""; 
		String urlEncodedFormats = ""; 
		String adaptiveFormats = "";
		
		while ((pageline = page.readLine()) != null) {

			try {
				// FIXME audio is missing .. video and audio are in separated streams in  adaptive_fmts
				if (this.urlType == TypeOfUrl.WEBPAGE_URL && crawler.hasPagelineSourcecodeUrls(pageline)) { 
					rc = true;
    				HashMap<String, String> sourceCodeVideoUrls = new HashMap<String, String>();
    				
					urlEncodedFormats = crawler.findUrlEncodedFormatedUrls(pageline);
					// TODO must check, in history
					adaptiveFormats = "";
					adaptiveFormats = crawler.findAdaptiveFormatedUrls(pageline);
										
					String sortedline = adaptiveFormats + "," + urlEncodedFormats;
					String[] sourceCodeYoutubeUrls = sortedline.split(",");
					
//					String resolutions = Messages.getString("INFO_MESSAGE_FOUNDED_VIDEO_URL_FOR_RESOLUTION");
					sourceCodeVideoUrls = new SourceCodeUrlTransformer().transformUrls(this.request, sourceCodeYoutubeUrls); 
					
					if (JFCMainClient.FRAME!=null) {
						sendMessage("Founded ITags (Resolutions): " +sourceCodeVideoUrls.keySet().toString());
					}
										
					this.nextVideoUrls.removeAllElements();
					addChoosenSourceCodeUrlsToDownload(sourceCodeVideoUrls);
  
					try {
						if(this.nextVideoUrls.get(0).getUrl()==null) {
							sendMessage(Messages.getString("INFO_MESSAGE_COULDNT_FOUND_VIDEO_URL_FOR_RESOLUTION_TRY_LOWER"));
						}
					} catch (java.lang.ArrayIndexOutOfBoundsException aioob) {
						sendMessage(Messages.getString("INFO_MESSAGE_COULDNT_FOUND_VIDEO_URL_FOR_RESOLUTION_TRY_LOWER")); 
					} 
					
					this.nextVideoUrls = removeEmptyNextVideoUrls(this.nextVideoUrls);
					this.videoUrl = this.nextVideoUrls.get(0).getUrl();
					
					String newTitle = getTitle(); 
					if(properties.isSaveIdInFilename()){
						newTitle = newTitle.concat("."+this.nextVideoUrls.get(0).getYoutubeId());
					}
					if(!this.nextVideoUrls.get(0).getRespart().isEmpty()) { 
						newTitle = newTitle.concat("." +this.nextVideoUrls.get(0).getRespart());
					}
					setTitle(newTitle);
				}
				if (this.urlType.equals(TypeOfUrl.WEBPAGE_URL) && crawler.hasTitleInPageline(pageline)) {
					String title = crawler.extractTitleFromPageline(pageline);
					setTitle(title);
				}
			} catch (NullPointerException npe) {
				npe.printStackTrace();
			}
		} 
		return rc;
	} 	
	
	public Vector<YoutubeUrl> removeEmptyNextVideoUrls(Vector<YoutubeUrl> nextVideoUrls) {
		for (int i = nextVideoUrls.size() - 1; i >= 0; i--) {
			if (nextVideoUrls.get(i).getUrl() == null) {
				nextVideoUrls.remove(i);
			}
		}
		return nextVideoUrls;
	}
		
	public TypeOfUrl changeStateBetweenParseOrDownloadUrl(TypeOfUrl urlType) {
		switch (urlType) {
			case WEBPAGE_URL:
				return TypeOfUrl.SOURCECODE_URL;
			case NO_URL:
				return TypeOfUrl.WEBPAGE_URL;
			case SOURCECODE_URL:
				break;
		}
		return urlType;
	}
		
	private void setTitle(String title) {
		this.title = title;
	} 
	
	public void addChoosenSourceCodeUrlsToDownload(HashMap<String, String> sourcecodeUrls){
		int index = 0;		
		if (this.properties.isAudioOnly()) {
			if (Videoformat.MPG == this.properties.getVideoformat()) {
				this.nextVideoUrls.add(index++, new YoutubeUrl(sourcecodeUrls.get("140"),this.url,"AUDIO")); 
				this.nextVideoUrls.add(index++, new YoutubeUrl(sourcecodeUrls.get("171"),this.url,"AUDIO")); 
			} else {
				this.nextVideoUrls.add(index++, new YoutubeUrl(sourcecodeUrls.get("171"),this.url,"AUDIO")); 
				this.nextVideoUrls.add(index++, new YoutubeUrl(sourcecodeUrls.get("140"),this.url,"AUDIO")); 
			}
		} else if (this.properties.getResolution() != null) {
			switch (this.properties.getResolution()) {
				case FOUR_K: 
					add_ULTRA_HD_Urls(index, sourcecodeUrls);
				case HIGH_DEFINITION: 
					add_HD_Urls(index, sourcecodeUrls);
				case STANDARD_DEFINITION: 
					add_SD_Urls(index, sourcecodeUrls);
				case LOW_DEFINITION:	
					add_LD_Urls(index, sourcecodeUrls);
					break;
				default:
					this.videoUrl = null;
					break;
			}
		}
	}
	
	private void add_ULTRA_HD_Urls(int index, HashMap<String, String> sourceCodeVideoUrls){
		// try 1440p, 2160p and 2304p first, if selected in GUI
		if (Videoformat.MPG == this.properties.getVideoformat()) {
			index = addAllKnownMPEG_ULTRAHD_UrlsToNextVideoUrl(index, sourceCodeVideoUrls);
		}
		if (Videoformat.WEBM == this.properties.getVideoformat()) {
			index = addAllKnownWEBM_ULTRAHD_UrlsToNextVideoUrl(index, sourceCodeVideoUrls);
		}	
	}
	
	private void add_HD_Urls(int index, HashMap<String, String> sourceCodeVideoUrls) {
		// try 1080p/720p in selected format first. if it's not available than the other format will be used 
		if (Videoformat.MPG == this.properties.getVideoformat()) {
			index=addAllKnownMPEG_HD_UrlsToNextVideoUrl(index,sourceCodeVideoUrls);
		}						
		if (Videoformat.WEBM == this.properties.getVideoformat()) {
			index=addAllKnownWEBM_HD_UrlsToNextVideoUrl(index,sourceCodeVideoUrls);
		}
		// there are no FLV HD URLs for now, so at least try mpg, webm HD then
		index = addAllKnownMPEG_HD_UrlsToNextVideoUrl(index, sourceCodeVideoUrls);
		index = addAllKnownWEBM_HD_UrlsToNextVideoUrl(index, sourceCodeVideoUrls);
	}
	
	private void add_SD_Urls(int index,  HashMap<String, String> sourceCodeVideoUrls) {
		// try to download desired format first, if it's not available we take the other of same res 
		if (Videoformat.MPG == this.properties.getVideoformat()) {
			index=addAllKnownMPEG_SD_UrlsToNextVideoUrl(index, sourceCodeVideoUrls);
		}
		if (Videoformat.WEBM == this.properties.getVideoformat()) {
			index=addAllKnownWBEM_SD_UrlsToNextVideoUrl(index,sourceCodeVideoUrls);
		}
		if (Videoformat.FLV == this.properties.getVideoformat()) {
			index=addAllKnownFLV_SD_UrlsToNextVideoUrl(index, sourceCodeVideoUrls);
		}	
		index = addAllKnownMPEG_SD_UrlsToNextVideoUrl(index, sourceCodeVideoUrls);
		index = addAllKnownWBEM_SD_UrlsToNextVideoUrl(index, sourceCodeVideoUrls);
		index = addAllKnownFLV_SD_UrlsToNextVideoUrl(index, sourceCodeVideoUrls);
	}
	
	private void add_LD_Urls(int index,  HashMap<String, String> sourceCodeVideoUrls) {
		if (Videoformat.MPG == this.properties.getVideoformat()) {
			index = addAllKnownMPEG_LD_UrlsToNextVideoUrl(index, sourceCodeVideoUrls);
		}
		if (Videoformat.WEBM == this.properties.getVideoformat()) {
			index = addAllKnownWEBM_LD_UrlsToNextVideoUrl(index, sourceCodeVideoUrls);
		}
		if (Videoformat.FLV == this.properties.getVideoformat()) {
			index = addAllKnownFLV_LD_UrlsToNextVideoUrl(index, sourceCodeVideoUrls);
		}
		// we must ensure all (16) possible URLs get added so the list of URLs is never empty
		index = addAllKnownMPEG_LD_UrlsToNextVideoUrl(index, sourceCodeVideoUrls);
		index = addAllKnownWEBM_LD_UrlsToNextVideoUrl(index, sourceCodeVideoUrls);
		index = addAllKnownFLV_LD_UrlsToNextVideoUrl(index, sourceCodeVideoUrls);
	}
	
	private int addAllKnownWEBM_ULTRAHD_UrlsToNextVideoUrl(int index, HashMap<String, String> sourceCodeVideoUrls) {
		int newIndex = index;
		String mpegAudioUrl = sourceCodeVideoUrls.get("140");
		String webm_2160p_VideoUrl = sourceCodeVideoUrls.get("272");
		String webm_1440p_VideoUrl = sourceCodeVideoUrls.get("271");
		
		if (!this.properties.isTryToSaveDiskspace()) {
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(webm_2160p_VideoUrl, this.url, "", mpegAudioUrl));
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(webm_1440p_VideoUrl, this.url, "", mpegAudioUrl));
		} else {
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(webm_1440p_VideoUrl, this.url, "", mpegAudioUrl));
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(webm_2160p_VideoUrl, this.url, "", mpegAudioUrl));
		}
		return newIndex;
	} 

	private int addAllKnownMPEG_ULTRAHD_UrlsToNextVideoUrl(int index, HashMap<String, String> sourceCodeVideoUrls) {
		int newIndex = index;
		String mpegAudioUrl = sourceCodeVideoUrls.get("140");
		String mpeg_2304p_VideoUrl = sourceCodeVideoUrls.get("138");
		String mpeg_2160p_VideoUrl = sourceCodeVideoUrls.get("266");
		String mpeg_1440p_VideoUrl = sourceCodeVideoUrls.get("264");
		
		if (!this.properties.isTryToSaveDiskspace()) {  
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(mpeg_2304p_VideoUrl, this.url, "", mpegAudioUrl)); 
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(mpeg_2160p_VideoUrl, this.url, "", mpegAudioUrl)); 
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(mpeg_1440p_VideoUrl, this.url, "", mpegAudioUrl)); 
		} else {
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(mpeg_1440p_VideoUrl, this.url, "", mpegAudioUrl)); 
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(mpeg_2160p_VideoUrl, this.url, "", mpegAudioUrl)); 
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(mpeg_2304p_VideoUrl, this.url, "", mpegAudioUrl)); 
		}
		return newIndex;
	} 

	private int addAllKnownMPEG_HD_UrlsToNextVideoUrl(int index, HashMap<String, String> sourceCodeVideoUrls) {
		int newIndex = index;
		String mpegAudioUrl = sourceCodeVideoUrls.get("140");
		String mpeg_1080p_AVC1_640028_VideoUrl = sourceCodeVideoUrls.get("137");
		String mpeg_720p_AVC1_4d401f_VideoUrl = sourceCodeVideoUrls.get("136");
		String mpeg_1080p_AVC1_64001F_VideoUrl = sourceCodeVideoUrls.get("37");
		String mpeg_720p_AVC1_64001F_VideoUrl = sourceCodeVideoUrls.get("22");
		
		if (properties.is3DButtonState())
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(sourceCodeVideoUrls.get("84"),this.url,"3D")); // mpeg 3D full HD
		
		if (!this.properties.isTryToSaveDiskspace()) {
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(mpeg_1080p_AVC1_640028_VideoUrl, this.url, "", mpegAudioUrl));	
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(mpeg_720p_AVC1_4d401f_VideoUrl, this.url, "", mpegAudioUrl));	
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(mpeg_1080p_AVC1_64001F_VideoUrl, this.url));					
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(mpeg_720p_AVC1_64001F_VideoUrl, this.url));
		} else {
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(mpeg_720p_AVC1_4d401f_VideoUrl, this.url, "", mpegAudioUrl));	
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(mpeg_1080p_AVC1_640028_VideoUrl, this.url, "", mpegAudioUrl));	
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(mpeg_720p_AVC1_64001F_VideoUrl, this.url, "", mpegAudioUrl));	 
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(mpeg_1080p_AVC1_64001F_VideoUrl, this.url, "", mpegAudioUrl));	
		}
		return newIndex;
	} 
	
	public int addAllKnownWEBM_HD_UrlsToNextVideoUrl(int index, HashMap<String, String> sourceCodeVideoUrls) {
		int newIndex = index;
		String oggAudioUrl = sourceCodeVideoUrls.get("171");
		String webm_1080p_VP9_VideoUrl = sourceCodeVideoUrls.get("248");
		String webm_720p_VP9_VideoUrl = sourceCodeVideoUrls.get("247");
		String webm_1080p_VP8_VideoUrl = sourceCodeVideoUrls.get("46");
		String webm_720p_VP8_VideoUrl = sourceCodeVideoUrls.get("45");
		 
		if (properties.is3DButtonState())
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(sourceCodeVideoUrls.get("100"),this.url,"3D")); // webm 3D HD

		if (!this.properties.isTryToSaveDiskspace()) {
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(webm_1080p_VP9_VideoUrl, this.url,"", oggAudioUrl));
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(webm_720p_VP9_VideoUrl, this.url,"", oggAudioUrl));
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(webm_1080p_VP8_VideoUrl, this.url));
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(webm_720p_VP8_VideoUrl, this.url));
		} else {
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(webm_720p_VP8_VideoUrl, this.url));
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(webm_1080p_VP8_VideoUrl, this.url));
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(webm_720p_VP9_VideoUrl, this.url, "", oggAudioUrl));
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(webm_1080p_VP9_VideoUrl, this.url, "", oggAudioUrl));
		}
		return newIndex;
	} 
	
	private int addAllKnownWBEM_SD_UrlsToNextVideoUrl(int index, HashMap<String, String> sourceCodeVideoUrls) {
		int newIndex = index;
		String oggAudioUrl = sourceCodeVideoUrls.get("171");
		String mpegAudioUrl = sourceCodeVideoUrls.get("140");
		String webm_480p_VP9_VideoUrl = sourceCodeVideoUrls.get("244");
		String webm_360p_VP9_VideoUrl = sourceCodeVideoUrls.get("243");
		String webm_480p_VP8_VideoUrl = sourceCodeVideoUrls.get("44");
		String webm_360p_VP8_VideoUrl = sourceCodeVideoUrls.get("43");
		 
		if (properties.is3DButtonState())
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(sourceCodeVideoUrls.get("102"),this.url,"3D")); // webm 3D SD

		if (!this.properties.isTryToSaveDiskspace()) {
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(webm_480p_VP9_VideoUrl, this.url, "", oggAudioUrl));
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(webm_360p_VP9_VideoUrl, this.url, "", oggAudioUrl));
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(webm_480p_VP8_VideoUrl, this.url));
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(webm_360p_VP8_VideoUrl, this.url));
		} else {
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(webm_360p_VP8_VideoUrl, this.url));					
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(webm_480p_VP8_VideoUrl, this.url));
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(webm_360p_VP9_VideoUrl, this.url, "", mpegAudioUrl));
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(webm_480p_VP9_VideoUrl, this.url, "", mpegAudioUrl));
		}
		return newIndex;
	} 
	
	private int addAllKnownFLV_SD_UrlsToNextVideoUrl(int index, HashMap<String, String> sourceCodeVideoUrls) {
		int newIndex = index;
		String fly_480p_VideoUrl = sourceCodeVideoUrls.get("35");
		String fly_360p_VideoUrl = sourceCodeVideoUrls.get("34");
		
		if (!this.properties.isTryToSaveDiskspace()) {
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(fly_480p_VideoUrl, this.url));
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(fly_360p_VideoUrl, this.url));
		} else {
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(fly_360p_VideoUrl, this.url));
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(fly_480p_VideoUrl, this.url));
		}
		return newIndex;
	} 
	
	private int addAllKnownMPEG_SD_UrlsToNextVideoUrl(int index, HashMap<String, String> sourceCodeVideoUrls) {
		int newIndex = index;
		String mpegAudioUrl = sourceCodeVideoUrls.get("140");
		String mpeg_480p_VideoUrl = sourceCodeVideoUrls.get("135");
		String mpeg_360p_VideoUrl = sourceCodeVideoUrls.get("134");
				 
		if (properties.is3DButtonState())
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(sourceCodeVideoUrls.get("82"),this.url,"3D")); // mpeg 3D SD

		if (!this.properties.isTryToSaveDiskspace()) {
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(mpeg_480p_VideoUrl, this.url, "", mpegAudioUrl));	
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(mpeg_360p_VideoUrl, this.url, "", mpegAudioUrl));	
		} else {
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(mpeg_360p_VideoUrl, this.url, "", mpegAudioUrl));	
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(mpeg_480p_VideoUrl, this.url, "", mpegAudioUrl));	
		}		
		this.nextVideoUrls.add(newIndex++, new YoutubeUrl(sourceCodeVideoUrls.get("18"),this.url));											// mpeg SD quality=medium  type=video/mp4;+codecs="avc1.42001E%2C+mp4a.40.2"
		return newIndex;
	}  
	
	private int addAllKnownMPEG_LD_UrlsToNextVideoUrl(int index, HashMap<String, String> sourceCodeVideoUrls) {
		int newIndex = index;
		String mpegAudioUrl = sourceCodeVideoUrls.get("140");
		String mpeg_240p_VideoUrl = sourceCodeVideoUrls.get("133");
		String mpeg_144p_VideoUrl = sourceCodeVideoUrls.get("160");
		String mpeg_240p_3gpp_VideoUrl = sourceCodeVideoUrls.get("36");
		String mpeg_114p_3gpp_VideoUrl = sourceCodeVideoUrls.get("17");
		
		if (!this.properties.isTryToSaveDiskspace()) {
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(mpeg_240p_VideoUrl, this.url, "LD", mpegAudioUrl));	
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(mpeg_144p_VideoUrl, this.url, "LD", mpegAudioUrl));	
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(mpeg_240p_3gpp_VideoUrl, this.url, "LD"));				
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(mpeg_114p_3gpp_VideoUrl, this.url, "LD"));				
		} else {
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(mpeg_114p_3gpp_VideoUrl, this.url, "LD"));				
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(mpeg_240p_3gpp_VideoUrl, this.url, "LD"));			
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(mpeg_144p_VideoUrl, this.url, "LD", mpegAudioUrl));	
			this.nextVideoUrls.add(newIndex++, new YoutubeUrl(mpeg_240p_VideoUrl, this.url, "LD", mpegAudioUrl));	
		}		
		return newIndex;
	} 
	
	private int addAllKnownFLV_LD_UrlsToNextVideoUrl(int index, HashMap<String, String> sourceCodeVideoUrls) {
		int newIndex = index;
		this.nextVideoUrls.add(newIndex++, new YoutubeUrl(sourceCodeVideoUrls.get( "5"),this.url,"LD")); // flv LD quality=small type=video/x-flv
		return newIndex;
	}
	
	private int addAllKnownWEBM_LD_UrlsToNextVideoUrl(int index, HashMap<String, String> sourceCodeVideoUrls) {
		int newIndex = index;
		this.nextVideoUrls.add(newIndex++, new YoutubeUrl(sourceCodeVideoUrls.get( "242"),this.url,"LD",sourceCodeVideoUrls.get("171"))); // flv LD  size=426x240 type=video/webm;+codecs="vp9"
		return newIndex;
	} 

	private void reportHeaderInfo(CloseableHttpResponse response) {
		Vector<String> vsfnamewb = getFileNames(getTitle(),this.contentType);
		
		String fileName = String.format("\"%s.%s\"", vsfnamewb.get(0), vsfnamewb.get(1));
		
		//TODO wrong place, options are not header informations
		reportOptionSetting(this.properties);
		
		if (Properties.IS_DEBUG) {			
			//TODO	log.setLevel(Level.SEVERE);
			log.log(Level.SEVERE, "");
			log.log(Level.SEVERE,"DEBUG_MESSAGE_ALL_HEADER_FIELDS");
			printAllResponseHeaders(response);
			log.log(Level.SEVERE, Messages.getString("INFO_MESSAGE_FILENAME_WOULD_BE") +fileName); // youtube video title will be transformed to filename
		} else {
			Long fileSize = Long.parseLong(this.contentLength);
			sendMessage("");			
			sendMessage("some HTTP header fields:");
			sendMessage("content-type: " +this.contentType);
			sendMessage("content-length: " +fileSize.toString() +" Bytes ~ " +Long.toString((fileSize/1024)) +" KiB ~ " +Long.toString((fileSize/1024/1024)) +" MiB");
			if (properties.isDownloadDisabled()) {
				sendMessage(Messages.getString("INFO_MESSAGE_FILENAME_WOULD_BE") +fileName); // title contains just filename, no path
			}
		}
		this.videoUrl = null;
	} 
	
	private void printAllResponseHeaders(CloseableHttpResponse response){
		for (Header responseHeader : response.getAllHeaders()) {
			log.log(Level.SEVERE, responseHeader.getName() +" = " +responseHeader.getValue());
		}
	}
	
	private void reportOptionSetting(Properties properties){
		if (properties.isDownloadDisabled()) {
			sendMessage("NO-DOWNLOAD mode active (ndl on)");
		}
		if (properties.isAudioOnly()) {
			sendMessage("AUDIO-ONLY mode active");
		}
	}
	
	private Vector<String> getFileNames(String title, String contentType) {
		String fileName = title;		
		String fileNameAfterDot = contentType.replaceFirst("audio/|video/|application/", "").replaceAll("x-", "");
		fileNameAfterDot = fileNameAfterDot.replaceFirst("octet-stream", "mp4");
		fileNameAfterDot = fileNameAfterDot.replaceFirst("audio-stream", "mp3");
		
		Vector<String> fileNames = new Vector<String>();
		fileNames.add(fileName); 
		fileNames.add(fileNameAfterDot);
		return fileNames;
	} 
	
	private void saveBinaryData(BufferedReader page) throws IOException {

		FileOutputStream fos = null;
		
		try {
			File file; 
			Integer idUpCount = 0;
			String directoryChoosed;

			directoryChoosed=(String) properties.getSavefolder();

			do {
				// FIXME must check for special characters in filename and remove them
				Vector<String> fileNames = getFileNames(getTitle(), this.contentType);

				if(idUpCount > 0) {
					file = new File(directoryChoosed, fileNames.get(0) +idUpCount + "." +fileNames.get(1));
				}
				file = new File(directoryChoosed, fileNames.get(0) +"." +fileNames.get(1));
				
				idUpCount += 1;
			} while (file.exists());
			
			this.setFileName(file.getAbsolutePath());
			
			Long bytesMax = Long.parseLong(this.contentLength);
			fos = new FileOutputStream(file);
			
			Long bytesReadSum = writeMediaStreamToFileAndUpdateProcess(fos, bytesMax);
			
			// rename files if download was interrupted before completion of download
			if (this.isInterrupted && (bytesReadSum < bytesMax)) {

				try {
					fos.close();
					Thread.sleep(50);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
		         
				this.httpClient.close(); 
				changeFileNameWith();
				sendMessage(Messages.getString("INFO_MESSAGE_RENAME_UNFINISHED_FILE") +this.getFileName());
								
				// CANCELED filenames overwrite others as we do not test for CANCELED one, two...
				if (!file.renameTo(new File( this.getFileName()))) {
					sendMessage(Messages.getString("ERROR_MESSAGE_FAILURE_TO_RENAME_UNFINISHED_FILE") +this.getFileName());
				}
			}
		} catch (FileNotFoundException e) {
			throw(e);
		} catch (IOException e) {
			throw(e);
		} finally {
			this.videoUrl = null;
			try {
				fos.close();
			} catch (Exception e) {
				 e.printStackTrace();
			}
            try {
            	if(page!=null)
				page.close();
				this.binaryReader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	} 
	
	private Long writeMediaStreamToFileAndUpdateProcess(FileOutputStream fos, Long bytesMax) throws IOException {
		Long bytesReadSum = (long) 0;
		
		sendMessage(Messages.getString("INFO_MESSAGE_FILESIZE_OF") +getTitle() +"\" = " +bytesMax.toString() +" Bytes ~ " +Long.toString(bytesMax/1024) +" KiB ~ " +Long.toString((bytesMax/1024/1024)) +" MiB");
	    
		byte[] byteBuffer = new byte[4096];
		
		Long percentage = (long) -1;
		int bytesRead = this.binaryReader.read(byteBuffer);
		while (!this.isInterrupted && (bytesRead != -1)) {
			bytesReadSum += bytesRead;
			fos = writeBufferToFile(fos, byteBuffer, bytesRead);
			percentage = updatePercentageOfDownload(percentage, bytesMax, bytesReadSum);
			
			this.isInterrupted = JFCMainClient.getQuitRequested(); // try to get information about application shutdown
			
			bytesRead = this.binaryReader.read(byteBuffer);
		} 
		this.youtubeUrl.setDownloadingFinished();
		JFCMainClient.updateYoutubeUrlInList(this.youtubeUrl);
		
		return bytesReadSum;
	}
	
	private Long updatePercentageOfDownload(Long percentage, Long bytesMax, Long bytesReadSum ) {
		int blocks = adjustBlocksOfPercentage(bytesMax);
		// TODO check with test blocks seems useless
		Long actualpercentage = (((bytesReadSum*100/bytesMax) / blocks) * blocks);
		
		if (actualpercentage > percentage) {
			this.youtubeUrl.setPercentage(actualpercentage.intValue());
			JFCMainClient.updateYoutubeUrlInList(this.youtubeUrl);
			return actualpercentage;
		}
		return percentage;
	}
	
	private FileOutputStream writeBufferToFile(FileOutputStream fos, byte[] byteBuffer, int bytesRead) {
		try {
			fos.write(byteBuffer,0,bytesRead);
		} catch (IndexOutOfBoundsException e) {
			log.severe(e.getMessage());
		} catch (IOException e) {
			log.severe(e.getMessage());
		}
		return fos;
	}
	
	private void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	private int adjustBlocksOfPercentage(long bytesMax) {
		// adjust blocks of percentage to output - larger files are shown with smaller pieces
		if (bytesMax > 20*1024*1024) {
			return 4; 
		}
		if (bytesMax>32*1024*1024) {
			return 2; 
		}
		if (bytesMax>56*1024*1024) {
			return 1;
		}
		return 10;
	}

	private void changeFileNameWith( ) {
		String prefix = "CANCELED.";
		File file = null;
		int idUpCount = 0;
		
		String fileSeparator = System.getProperty("file.separator");
		
		if (fileSeparator.equals("\\")) {
			fileSeparator += fileSeparator; // on m$-windows we need to escape the \
		}

		String directoryChoosed="";
		String[] renFileName = this.getFileName().split(fileSeparator);
		int indexOfLastElement = renFileName.length-1;
		
		try {
			for (int i = 0; i < indexOfLastElement; i++) {
				directoryChoosed += renFileName[i].concat(fileSeparator); // constructing folder where file is saved now (could be changed in GUI already)
			}
		} catch (ArrayIndexOutOfBoundsException aioobe) {
			aioobe.printStackTrace();
		}
		
		String fileName = renFileName[indexOfLastElement];
		do {
			 // filename will be prepended with a parameter string and possibly with a duplicate counter
			file = new File(directoryChoosed, prefix.concat(idUpCount > 0 ? "(" +idUpCount +")" : "") +fileName);
			idUpCount += 1;
		} while (file.exists());
		
		this.setFileName(file.getAbsolutePath());
	}  
	
	
} 