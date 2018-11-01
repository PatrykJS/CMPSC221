package zsk;

import static org.mockito.Mockito.mock;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Vector;

import org.apache.http.HttpHost;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import zsk.helper.Properties;
import zsk.http.TypeOfUrl;

public class DownloadThreadTest {
	
	Properties properties;
	DownloadThread downloadThread;
	CloseableHttpResponse response;
	StatusLine statusLine;
	BufferedReader page;
		
	@Before
	public void setUp() {
		properties = new Properties();
		downloadThread = new DownloadThread(properties);
		response = mock(CloseableHttpResponse.class);
		statusLine = mock(StatusLine.class);
		page = mock(BufferedReader.class);
				
		Mockito.when(response.getStatusLine()).thenReturn(statusLine);
	}
		
	@Test
	public void testIsHttpReturnCode200() {
		String statusLine = "HTTP/1.1 201";
		
		Assert.assertFalse(downloadThread.isHttpReturnCode200(statusLine));
		
		statusLine = "HTTP/1.1 200";
		Assert.assertTrue(downloadThread.isHttpReturnCode200(statusLine));
	}
		
	@Test
	public void testIsHttpReturnCode204() {
		String statusLine = "HTTP/1.1 201";
		
		Assert.assertFalse(downloadThread.isHttpReturnCode204(statusLine));
		
		statusLine = "HTTP/1.1 204";
		Assert.assertTrue(downloadThread.isHttpReturnCode204(statusLine));
	}
	
	@Test
	public void testIsHttpReturnCode302() {
		String statusLine = "HTTP/1.1 201";
		
		Assert.assertFalse(downloadThread.isHttpReturnCode302(statusLine));
		
		statusLine = "HTTP/1.1 302";
		Assert.assertTrue(downloadThread.isHttpReturnCode302(statusLine));
	}
			
	@Test
	public void testShouldNotDownloadMore() {
		String url= "testurl";
		
		boolean shouldNotDownloadMore = downloadThread.shouldNotDownloadMore(url);
				
		Assert.assertFalse(shouldNotDownloadMore);
	}
	
	@Test
	public void testShouldNotDownloadMoreUrlIsNull() {
		String url = null;
		
		boolean shouldNotDownloadMore = downloadThread.shouldNotDownloadMore(url);
				
		Assert.assertTrue(shouldNotDownloadMore);
	}
	
	@Test
	public void testShouldNotDownloadMoreUrlIsEmpty() {
		String url = "";
		
		boolean shouldNotDownloadMore = downloadThread.shouldNotDownloadMore(url);
				
		Assert.assertTrue(shouldNotDownloadMore);
	}
	
	@Test
	public void testShouldNotDownloadMoreNotQuitRequested() {
		String url = "testurl";
		JFCMainClient.setQuitRequested(false);
		
		boolean shouldNotDownloadMore = downloadThread.shouldNotDownloadMore(url);
				
		Assert.assertFalse(shouldNotDownloadMore);
	}
	
	@Test
	public void testShouldNotDownloadMoreQuitRequested() {
		String url = "testurl";
		JFCMainClient.setQuitRequested(true);
		
		boolean shouldNotDownloadMore = downloadThread.shouldNotDownloadMore(url);
				
		Assert.assertTrue(shouldNotDownloadMore);
	}
	
	@Test
	public void testChangeStateToWEBPAGE_URL() {
		TypeOfUrl typeOfUrl = TypeOfUrl.NO_URL;
		TypeOfUrl expectedTypeOfUrl = TypeOfUrl.WEBPAGE_URL;
		
		TypeOfUrl actualTypeOfUrl = downloadThread.changeStateBetweenParseOrDownloadUrl(typeOfUrl);
		Assert.assertEquals(expectedTypeOfUrl, actualTypeOfUrl);
	}
	
	@Test
	public void testChangeStateToSOURCECODE_URL() {
		TypeOfUrl typeOfUrl = TypeOfUrl.WEBPAGE_URL;
		TypeOfUrl expectedTypeOfUrl = TypeOfUrl.SOURCECODE_URL;
		
		TypeOfUrl actualTypeOfUrl = downloadThread.changeStateBetweenParseOrDownloadUrl(typeOfUrl);
		Assert.assertEquals(expectedTypeOfUrl, actualTypeOfUrl);
	}
	
	@Test
	public void testState_SOURCECODE_URL_RemainsUnaffected() {
		TypeOfUrl typeOfUrl = TypeOfUrl.SOURCECODE_URL;
		TypeOfUrl expectedTypeOfUrl = TypeOfUrl.SOURCECODE_URL;
		
		TypeOfUrl actualTypeOfUrl = downloadThread.changeStateBetweenParseOrDownloadUrl(typeOfUrl);
		Assert.assertEquals(expectedTypeOfUrl, actualTypeOfUrl);
	}
	
	@Test
	public void testRemoveEmptyNextVideoUrls() {
		Vector<YoutubeUrl> urls = new Vector<YoutubeUrl>();
		YoutubeUrl url1 = new YoutubeUrl("notEmpty1");
		YoutubeUrl url2 = new YoutubeUrl(null);
		YoutubeUrl url3 = new YoutubeUrl("notEmpty3");
		
		urls.add(url1);
		urls.add(url2);
		urls.add(url3);
		
		Assert.assertNotEquals(2, urls.size());
		
		Vector<YoutubeUrl> actualUrls = downloadThread.removeEmptyNextVideoUrls(urls);
		Assert.assertEquals(2, actualUrls.size());
	}
	
	@Test
	public void testCreateSpecifiedProxy() {
		String proxyString = "https://www.test.de:80";
		String expectedHostname = "www.test.de";
		int expectedPort = 80;
		
		HttpHost actualProxy = downloadThread.createSpecifiedProxy(proxyString);
		
		Assert.assertEquals(expectedHostname, actualProxy.getHostName());
		Assert.assertEquals(expectedPort, actualProxy.getPort());
	}
	
	@Test (expected=ProxyException.class)
	public void testEmptyProxyString() {
		String proxyString = "";
		
		downloadThread.createSpecifiedProxy(proxyString);
	}
	
	@Test (expected=ProxyException.class)
	public void testProxyStringIsNull() {
		String proxyString = null;
		
		downloadThread.createSpecifiedProxy(proxyString);
	}

	@Ignore //TODO runs into an infinite loop
	@Test (expected=IOException.class)
	public void testSaveTextDataTrue() throws IOException {
		boolean isSaved = false;
		String pageline = "test";
		
		Mockito.when(page.readLine()).thenReturn(pageline);
		
		isSaved = downloadThread.savePageContent(page);
		Assert.assertTrue(isSaved);
	}
}
