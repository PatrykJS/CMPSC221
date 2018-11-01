package zsk.helper;

public final class String_Constants {

	public static final String NEWLINE = "\n";
	public static final String COMMAND_HELP = "help|h|?] :\t\t";
	public static final String COMMAND_DEBUG = "debug[ on| off] :\t\t";
	public static final String COMMAND_ID = "id[ on| off] :\t\t";
	public static final String COMMAND_NO_DOWNLOAD = "ndl[ on| off]:\t\t";
	public static final String COMMAND_PROXY = "proxy[ URL] :\t\t";
	public static final String COMMAND_QUIET = "quit|exit :\t\t";
	public static final String COMMAND_SAVE_SPACE = "sds[ on| off] :\t\t";
	public static final String COMMAND_AUDIO_ONLY = "audioonly[ on| off]:\t";
	public static final String COMMAND_VERSION = "version|v|: \t\t";
	
	public static final String PLAYLIST_REGEX = "(?i)/view_play_list\\?p=([a-z0-9]*)&playnext=[0-9]{1,2}&v=";
	// something like [http://][www.]youtube.[cc|to|pl|ev|do|ma|in]/watch?v=0123456789A   or  youtu.be/9irsg1vBmq0
	public static final String YOUTUBE_REGEX = "(?i)^(http(s)?://)?(www\\.)?(youtube\\..{2,5}/watch\\?v=|youtu\\.be/)[^&]{11}"; // http://de.wikipedia.org/wiki/CcTLD
	// RFC-1123 ? hostname [with protocol]	
	public static final String PROXY_REGEX = "(?i)(^(http(s)?://)?([a-z0-9]+:[a-z0-9]+@)?([a-z0-9\\-]{0,61}[a-z0-9])(\\.([a-z0-9\\-]{0,61}[a-z0-9]))*(:[0-90-90-90-9]{1,4})?$)|()";
	// RFC-1738 URL characters - not a regex for a real URL!!
	public static final String URL_REGEX = "(?i)^(http(s)?://)?[a-z0-9;/\\?@=&\\$\\-_\\.+!*\\'\\(\\),%]+$"; // without ":", plus "%"
	public static final String WATCH_REGEX = "/watch?.*&v=";	
	
	public static final String SHORTEST_YOUTUBE_URL = "youtube.com/watch?v=0123456789a";
}