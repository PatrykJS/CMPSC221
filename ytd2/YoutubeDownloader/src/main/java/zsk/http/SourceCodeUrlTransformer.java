package zsk.http;

import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.methods.HttpGet;

import zsk.i18n.Messages;

public class SourceCodeUrlTransformer {
	
	private HashMap<String, String> transformedUrls;
	
	Pattern iTagPattern = Pattern.compile("itag=[0-9]{1,3}");
	
	public SourceCodeUrlTransformer() {
		this.transformedUrls = new HashMap<String, String>(); 
	}
	
	public HashMap<String, String> transformUrls(HttpGet request, String[] sourceCodeUrls) {	// TODO parameter request must be removed
		for (String sourceCodeUrl : sourceCodeUrls) {
			checkForUnsupportedMedia(sourceCodeUrl);
			
			String[] splitedUrlPair = sourceCodeUrl.split("url=http(s)?",2);
			
			// TODO it's to think about to using StringBuffer 
			String formatedUrlString = "url=http"+splitedUrlPair[1]+"&"+splitedUrlPair[0];
									
			String iTagNumber = extractITagNumber(formatedUrlString);
									
			formatedUrlString = adjustProtocol(request, formatedUrlString);						
			formatedUrlString = decodeURLEncoding(formatedUrlString);
			formatedUrlString = replaceAbbreviationsFromUrl(formatedUrlString);						
			formatedUrlString = sortStringAt( formatedUrlString, "&" );
									
			try {
				this.transformedUrls.put(iTagNumber, formatedUrlString); 
			} catch (java.lang.ArrayIndexOutOfBoundsException aioobe) {
				aioobe.printStackTrace();
			}
		}
		return this.transformedUrls;
	}
	
	public String extractITagNumber(String sourceCodeVideoUrl){
		
		Matcher iTagMatcher = iTagPattern.matcher(sourceCodeVideoUrl);
		
		iTagMatcher.find();
		String iTag = iTagMatcher.group();
		iTag = iTag.replace("itag=", "");
		 	
		return iTag;
	}
	
	public String adjustProtocol(HttpGet request, String sourceCodeVideoUrl){
		if (request.getURI().toString().startsWith("https")) {
			sourceCodeVideoUrl = sourceCodeVideoUrl.replaceFirst("url=http%3A%2F%2F", "https://");
		} else {
			sourceCodeVideoUrl = sourceCodeVideoUrl.replaceFirst("url=http%3A%2F%2F", "http://");
		}
		return sourceCodeVideoUrl;
	}
	
	public String decodeURLEncoding(String urlEncodedUrl) {
		urlEncodedUrl = urlEncodedUrl.replaceAll("%3F","?");
		urlEncodedUrl = urlEncodedUrl.replaceAll("%2F", "/");
		urlEncodedUrl = urlEncodedUrl.replaceAll("%3B",";");
		urlEncodedUrl = urlEncodedUrl.replaceAll("%3D","=");
		urlEncodedUrl = urlEncodedUrl.replaceAll("%26", "&");
		urlEncodedUrl = urlEncodedUrl.replaceAll("%252C", "%2C");
		return urlEncodedUrl;
	}
	
	public String replaceAbbreviationsFromUrl(String url) {
		url = url.replaceAll("sig=", "signature=");
		url = url.replaceAll("&s=", "&signature=");
		url = url.replaceAll("\\?s=", "?signature=");
		return url;
	}
	
	/**
	 * put URL parts between "&" in a sorted array and add "range=0-1000000000"
	 * 
	 * @param source
	 * @param delimiter
	 * @return
	 */
	public String sortStringAt ( String source, String delimiter ) {
		String sortedUrl = source.replaceFirst("\\?.*", ""); 

		// FIXME range=0-999999999 lead to a maximum fraction of 1GB (not GiB !), but if the file is greater than 1GB we would have to download the whole video in more smaller parts (like the ytplayer does)
		// http://www.youtube.com/watch?v=x9WxkcUTGSY  4:14:33
		source = source.replaceFirst("http.*\\?","");
		source = source.concat("&range=0-999999999");
		String[] unsortedUrl = source.split(delimiter);
		
		Arrays.sort(unsortedUrl);
		unsortedUrl = clearDuplicateEntries(unsortedUrl);
				
		sortedUrl += Arrays.toString(unsortedUrl);
		sortedUrl = normalizeUrlParameters(sortedUrl, delimiter);
		sortedUrl = replaceFirstVideoplaybackAppearance(sortedUrl);

		return sortedUrl;
	} 
	
	private void checkForUnsupportedMedia(String sourceCodeUrl) {
		// assuming rtmpe is used for all resolutions, if found once - end download
		if (sourceCodeUrl.matches(".*conn=rtmpe.*")) {					
			throw new URLTransformerException(Messages.getString("ERROR_MESSAGE_UNABLE_DOWNLOAD_ABOUT_UNSUPPORTED_PROTOCOL") + " conn=rtmpe");
		}
	}
	
	private String[] clearDuplicateEntries(String[] unsortedUrl) {
		for (int i=0;i<unsortedUrl.length-1;i++) {
			if (unsortedUrl[i].equals(unsortedUrl[i+1]))
				unsortedUrl[i]="";
		}
		return unsortedUrl;
	}
	
	private String normalizeUrlParameters(String sortedUrl, String delimiter) {
		sortedUrl = sortedUrl.replaceAll("\\[, ", delimiter);
		sortedUrl = sortedUrl.replaceAll(", ", delimiter);
		sortedUrl = sortedUrl.replaceAll(",,", delimiter);
		sortedUrl = sortedUrl.replaceAll("]", "");
		sortedUrl = sortedUrl.replaceAll(delimiter+delimiter, delimiter);
		return sortedUrl;
	}
	
	private String replaceFirstVideoplaybackAppearance(String sortedUrl) {
		sortedUrl = sortedUrl.replaceFirst("/videoplayback\\[", "/videoplayback?");
		sortedUrl = sortedUrl.replaceFirst("/videoplayback&", "/videoplayback?");
		return sortedUrl;
	}
}
