package zsk.http;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import zsk.http.PageCrawler;

public class PageCrawlerTest {
	
	private PageCrawler pageCrawler;

	@Before
	public void setUp() throws Exception {
		pageCrawler = new PageCrawler();
	}

	@Test
	public void testContainsSourceCodeUrls() {
		String pageLineWithoutUrls = "testtexttesttext";
		String pageLineWithUrls = "testtext(\"adaptive_fmts\":|\"url_encoded_fmt_stream_map\":)testtext";
		boolean foundSourceCodeUrls;
		
		foundSourceCodeUrls = pageCrawler.hasPagelineSourcecodeUrls(pageLineWithoutUrls);
		Assert.assertFalse(foundSourceCodeUrls);
		
		foundSourceCodeUrls = pageCrawler.hasPagelineSourcecodeUrls(pageLineWithUrls);
		Assert.assertTrue(foundSourceCodeUrls);
	}
	
	@Test
	public void testFindAdaptiveFormats() {
		final String pageline = "test\"adaptive_fmts\":\"AdaptiveFormat1_AdaptiveFormat2\"rest";
		
		String expectedFormats = "AdaptiveFormat1_AdaptiveFormat2";
		String actualFormats;
		
		actualFormats = pageCrawler.findAdaptiveFormatedUrls(pageline);
		
		Assert.assertNotNull(actualFormats);
		Assert.assertEquals(expectedFormats, actualFormats);
	}
	
	@Test
	public void testFindEmptyAdaptiveFormats() {
		final String pageline = "test\"adaptive_fmts\":\"";
		String expectedFormats = "";
		String actualFormats;
		
		actualFormats = pageCrawler.findAdaptiveFormatedUrls(pageline);
		
		Assert.assertNotNull(actualFormats);
		Assert.assertEquals(expectedFormats, actualFormats);
	} 
	
	@Test
	public void testFindUrlEncodedFormats() {
		final String pageline = "test\"url_encoded_fmt_stream_map\":\"UrlEncodedFormat1_UrlEncodedFormat2\"rest";
		String expectedFormats = "UrlEncodedFormat1_UrlEncodedFormat2";
		String actualFormats;
		
		actualFormats = pageCrawler.findUrlEncodedFormatedUrls(pageline);
		
		Assert.assertNotNull(actualFormats);
		Assert.assertEquals(expectedFormats, actualFormats);
	}
	
	@Test
	public void testFindEmptyUrlEncodedFormats() {
		final String pageline = "test\"url_encoded_fmt_stream_map\":\"";
		String expectedFormats = "";
		String actualFormats;
		
		actualFormats = pageCrawler.findUrlEncodedFormatedUrls(pageline);
		
		Assert.assertNotNull(actualFormats);
		Assert.assertEquals(expectedFormats, actualFormats);
	} 
	
	@Test
	public void testRemoveXTags() {
		String line = "&xtags=&itag=313&lmt=1472646182092146&type=video%2Fwebm%3B+codecs";
		String expectedLine = "&itag=313&lmt=1472646182092146&type=video%2Fwebm%3B+codecs";
		String actualLine = "";
		
		actualLine = pageCrawler.removeXTags(line);
		
		Assert.assertEquals(expectedLine, actualLine);
	}
	
	@Test
	public void testRemoveXTagsFails() {
		String line = "xtags=&itag=313&lmt=1472646182092146&type=video%2Fwebm%3B+codecs";
		String expectedLine = "&itag=313&lmt=1472646182092146&type=video%2Fwebm%3B+codecs";
		String actualLine = "";
		
		actualLine = pageCrawler.removeXTags(line);
		
		Assert.assertNotEquals(expectedLine, actualLine);
	}
		
	@Test
	public void testCollectResolutionsFromUrl() {
		String resolutions = "";
		String sourceCodeVideoUrl_1 = "5";
		String sourceCodeVideoUrl_2 = "44";
		String sourceCodeVideoUrl_3 = "137";
		
		resolutions = pageCrawler.collectFormatsAsResolutions(sourceCodeVideoUrl_1, resolutions);
		resolutions = pageCrawler.collectFormatsAsResolutions(sourceCodeVideoUrl_2, resolutions);
		resolutions = pageCrawler.collectFormatsAsResolutions(sourceCodeVideoUrl_3, resolutions);
		
		assertEquals("240p flv, 480p webm, 1080p mpeg, ", resolutions);
	}
}
