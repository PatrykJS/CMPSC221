package zsk.http;

import org.apache.commons.lang.StringUtils;

import zsk.UTF8;

public class PageCrawler {
	private final static String TITLE = "(.*)<meta name=\"title\" content=(.*)";
	private final static String FORMATED_URLS = ".*(\"adaptive_fmts\":|\"url_encoded_fmt_stream_map\":).*"; // behind that two strings are the comma separated video URLs we use 
	private final static String START_URL_ENCODED_FORMATS = "\"url_encoded_fmt_stream_map\":\"";
	private final static String START_ADAPTIVE_FORMATS = "\"adaptive_fmts\":\"";
	private final static String CLOSURE = "\"";
	
	
	public boolean hasPagelineSourcecodeUrls(String pageLine) {
		return pageLine.matches(FORMATED_URLS);  
	}
	
	public boolean hasTitleInPageline(String pageLine) {
		return pageLine.matches(TITLE);  
	}
	
	public String extractTitleFromPageline(String pageline) {
		String temporaryTitle = pageline.replaceFirst("(.*)<meta name=\"title\" content=", "").trim();
		temporaryTitle = UTF8.changeHTMLtoUTF8(temporaryTitle);
		temporaryTitle = replaceSpecialCharacters(temporaryTitle);
		
		return temporaryTitle;
	}
	
	public String findAdaptiveFormatedUrls(String pageLine) {
		String urlEncodedFormats;
		
		pageLine = cleanupPageLine(pageLine);
		urlEncodedFormats = extractFormatedUrls(pageLine, START_ADAPTIVE_FORMATS, CLOSURE);
		
		if(urlEncodedFormats==null) {
			return "";
		}
		return urlEncodedFormats;
	}
	
	public String findUrlEncodedFormatedUrls(String pageLine) {
		String urlEncodedFormats;
		
		pageLine = cleanupPageLine(pageLine);
		urlEncodedFormats = extractFormatedUrls(pageLine, START_URL_ENCODED_FORMATS, CLOSURE);
		
		if(urlEncodedFormats==null) {
			return "";
		}
		return urlEncodedFormats;
	}
	
	private String cleanupPageLine(String pageLine) {
		pageLine = pageLine.replaceAll(" ", "");
		pageLine = pageLine.replace("%25","%");
		pageLine = pageLine.replace("\\u0026", "&");
		return pageLine;
	}
	
	private String extractFormatedUrls(String pageline, String adaptiveFormatPattern, String closure) {
		return StringUtils.substringBetween(pageline, adaptiveFormatPattern, closure);
	}
	
	//to prevent status 403 Error for get requests 
	public String removeXTags(String url) {
		return StringUtils.remove(url,"&xtags=");
	} 
	
	public String replaceSpecialCharacters(String stmp){
		stmp = stmp.replaceFirst("^\"", "");
		stmp = stmp.replaceFirst("\">$", "");
		stmp = stmp.replaceAll("<", "");
		stmp = stmp.replaceAll(">", "");
		stmp = stmp.replaceAll(":", "");
		stmp = stmp.replaceAll("/", " ");
		stmp = stmp.replaceAll("\\\\", " ");
		stmp = stmp.replaceAll("|", "");
		stmp = stmp.replaceAll("\\?", "");
		stmp = stmp.replaceAll("\\*", "");
		stmp = stmp.replaceAll("/", " ");
		stmp = stmp.replaceAll("\"", " ");
		stmp = stmp.replaceAll("%", "");
		return stmp;
	}
	
	// TODO check functionality
	// TODO change to MAP
	public String collectFormatsAsResolutions(String iTagNumber, String resolutions) {
		
		resolutions = resolutions.concat(
			iTagNumber.equals( "138" )?"2304p mpeg, ":            // 4k HD   type=video/mp4;+codecs="avc1.640033" & size=4096x2304
			iTagNumber.equals( "264" )?"1440p mpeg, ":            // <4k HD  type=video/mp4;+codecs="avc1.640032" & size=2560x1440
			iTagNumber.equals( "266" )?"2160p mpeg, ":            // <4k HD  type=video/mp4;+codecs=%22avc1.640033" & size=3840x2160&
			iTagNumber.equals( "271" )?"1440p webm, ":            // <4k HD  type=video/webm;+codecs=%22vp9" & size=2560x1440
			iTagNumber.equals( "272" )?"2160p webm, ":            // <4k HD  type=video/webm;+codecs="vp9" &	size=3840x2160									
			iTagNumber.equals( "248" )?"1080p webm, ":            // HD      type=video/webm;+codecs="vp9" & size=1920x1080
			iTagNumber.equals(  "37" )?"1080p mpeg, ":            // HD      type=video/mp4;+codecs="avc1.64001F,+mp4a.40.2"
			iTagNumber.equals(  "22" )?"720p mpeg, ":             // HD      type=video/mp4;+codecs="avc1.64001F,+mp4a.40.2"
			iTagNumber.equals( "247" )?"720p webm, ":             // HD      type=video/webm;+codecs="vp9" & size=1280x720
			iTagNumber.equals(  "84" )?"1080p 3d mpeg, ":         // HD 3D   type=video/mp4;+codecs="avc1.64001F,+mp4a.40.2"
			iTagNumber.equals(  "18" )?"360p mpeg, ":             // SD      type=video/mp4;+codecs="avc1.42001E,+mp4a.40.2"
			iTagNumber.equals(  "35" )?"480p flv, ":              // SD      type=video/x-flv
			iTagNumber.equals(  "34" )?"360p flv, ":              // SD      type=video/x-flv
			iTagNumber.equals(  "82" )?"360p 3d mpeg, ":          // SD 3D   type=video/mp4;+codecs="avc1.42001E,+mp4a.40.2"
			iTagNumber.equals(  "36" )?"240p mpeg 3gpp, ":        // LD      type=video/3gpp;+codecs="mp4v.20.3,+mp4a.40.2"
			iTagNumber.equals(  "17" )?"114p mpeg 3gpp, ":        // LD      type=video/3gpp;+codecs="mp4v.20.3,+mp4a.40.2"
			iTagNumber.equals(  "46" )?"1080p webm, ":            // HD      type=video/webm;+codecs="vp8.0,+vorbis"
			iTagNumber.equals(  "45" )?"720p webm, ":             // HD      type=video/webm;+codecs="vp8.0,+vorbis"
			iTagNumber.equals( "100" )?"1080p 3d webm, ":         // HD 3D   type=video/webm;+codecs="vp8.0,+vorbis"
			iTagNumber.equals(  "44" )?"480p webm, ":             // SD      type=video/webm;+codecs="vp8.0,+vorbis"
			iTagNumber.equals(  "43" )?"360p webm, ":             // SD      type=video/webm;+codecs="vp8.0,+vorbis"
			iTagNumber.equals( "102" )?"360p 3d webm, ":          // SD 3D   type=video/webm;+codecs="vp8.0,+vorbis"
			iTagNumber.equals( "244" )?"480p webm, ":             // SD      type=video/webm;+codecs="vp9" & size=854x480
			iTagNumber.equals(  "5"  )?"240p flv, " :             // LD      type=video/x-flv
			iTagNumber.equals( "137" )?"1080p mpeg, ":            // HD      type=video/mp4;+codecs="avc1.640028" & size=1920x1080
			iTagNumber.equals( "136" )?"720p mpeg, ":             // HD      type=video/mp4;+codecs="avc1.4d401f" & size=1280x720
			iTagNumber.equals( "135" )?"480p mpeg, ":             // SD      type=video/mp4;+codecs="avc1.4d401f" & size=854x480
			iTagNumber.equals( "134" )?"360p mpeg, ":             // SD      type=video/mp4;+codecs="avc1.4d401e" & size=640x360
			iTagNumber.equals( "133" )?"240p mpeg, ":             // LD      type=video/mp4;+codecs="avc1.4d4015" & size=426x240 
			iTagNumber.equals( "160" )?"144p mpeg, ":             // LD      type=video/mp4;+codecs="avc1.42c00c" & size=256x144
			iTagNumber.equals( "243" )?"360p webm, ":             // LD      type=video/webm;+codecs="vp9"
			iTagNumber.equals( "242" )?"240p webm, ":             // LD      type=video/webm;+codecs="vp9"
			iTagNumber.equals( "140" )?"mpeg audio only, ":       // ??      type=audio/mp4;+codecs="mp4a.40.2 & bitrate=127949
			iTagNumber.equals( "171" )?"ogg vorbis audio only, " : "unknown resolution! (".concat(iTagNumber).concat(") "));		
		return resolutions;
	}
}
