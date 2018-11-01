package zsk.http;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SourceCodeUrlTransformerTest {
	private SourceCodeUrlTransformer transformer;

	@Before
	public void setUp() throws Exception {
		transformer = new SourceCodeUrlTransformer();
	}	

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDecodeURLEncoding() {
		String encodedUrl = "%3F%2F%3B%3D%26%252C";
		String actualReplacement;
		String expectedReplacement = "?/;=&%2C";
		
		actualReplacement = transformer.decodeURLEncoding(encodedUrl);
		assertEquals(expectedReplacement, actualReplacement);
	}
	
	@Test
	public void testReplaceAbbreviationsFromUrl() {
		String orginalUrl1 = "sig=";
		String orginalUrl2 = "&s=";
		String orginalUrl3 = "?s=";
				
		assertEquals("signature=", transformer.replaceAbbreviationsFromUrl(orginalUrl1));
		assertEquals("&signature=", transformer.replaceAbbreviationsFromUrl(orginalUrl2));
		assertEquals("?signature=", transformer.replaceAbbreviationsFromUrl(orginalUrl3));
	}
	
	@Test
	public void testExtractITagNumberWithOneDigit() {
		String sourceCodeVideoUrl = "Testitag=5Test";
		String expectedITag ="5";
		String actualITag = "";

		Assert.assertNotEquals(expectedITag, actualITag);
		
		actualITag = transformer.extractITagNumber(sourceCodeVideoUrl);
		Assert.assertEquals(expectedITag, actualITag);
	}
	
	@Test
	public void testExtractITagNumberWithTwoDigits() {
		String sourceCodeVideoUrl = "Testitag=91Test";
		String expectedITag ="91";
		String actualITag = "";

		Assert.assertNotEquals(expectedITag, actualITag);
		
		actualITag = transformer.extractITagNumber(sourceCodeVideoUrl);
		Assert.assertEquals(expectedITag, actualITag);
	}
	
	@Test
	public void testExtractITagNumberWithThreeDigits() {
		String sourceCodeVideoUrl = "Testitag=909Test";
		String expectedITag ="909";
		String actualITag = "";

		Assert.assertNotEquals(expectedITag, actualITag);
		
		actualITag = transformer.extractITagNumber(sourceCodeVideoUrl);
		Assert.assertEquals(expectedITag,actualITag);
	}
	
	@Test
	public void testExtractITagNumberWithMoreThanThreeDigits() {
		String sourceCodeVideoUrl = "Testitag=09090909Test";
		String expectedITag ="090";
		String actualITag = "";

		Assert.assertNotEquals(expectedITag, actualITag);
		
		actualITag = transformer.extractITagNumber(sourceCodeVideoUrl);
		Assert.assertEquals(expectedITag,actualITag);
	}
}
