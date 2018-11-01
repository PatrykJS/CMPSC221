package zsk;

import javax.swing.DefaultListModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import zsk.helper.Properties;

public class JFCMainClientTest {

	private Properties properties;
	private JFCMainClient client;
	
	@Before
	public void setUp() {
		properties  = new Properties();
		client  = new JFCMainClient(properties);
	}
	
	@Test
	public void testSetInitialVideoFormatsToProperties() {
		Videoformat expectedVideoformat = Videoformat.MPG;
		
		properties.setVideoformat(Videoformat.FLV);
		properties.set3DButtonState(true);
		client.setInitialVideoFormatsToProperties(properties);
		
		Assert.assertEquals(expectedVideoformat, properties.getVideoformat());
		Assert.assertFalse(properties.is3DButtonState());
	}
	
	@Test
	public void testSetInitialResolutionsToProperties() {
		Resolution expectedResolution = Resolution.STANDARD_DEFINITION; 
		
		properties.setResolution(Resolution.LOW_DEFINITION);
		client.setInitialResolutionsToProperties(properties);
		
		Assert.assertEquals(expectedResolution, properties.getResolution());
	}
	
	@Test
	public void testSaveOptionsToPropertiesAndReturnParameters() {
		String expectedCommandLineArguments="-h -w parameter1 parameter2";
		String[] expectedParameters= {"parameter1", "parameter2"};
		properties.setResolution(Resolution.STANDARD_DEFINITION);
		properties.setVideoformat(Videoformat.MPG);
		
		String[] arguments = expectedCommandLineArguments.split(" ");
				
		String[] actualParameters = client.saveOptionsAndReturnParameters(arguments, properties); 
		
		Assert.assertNotNull(arguments);
		Assert.assertNotNull(actualParameters);
		Assert.assertEquals(Resolution.HIGH_DEFINITION, properties.getResolution());
		Assert.assertEquals(Videoformat.WEBM, properties.getVideoformat());
		Assert.assertArrayEquals(expectedParameters, actualParameters);
	}
	
	@Test
	public void testSaveOptionsToPropertiesAndReturnParametersFails() {
		String expectedCommandLineArguments="-H -W parameter1 parameter2";
		String[] expectedParameters= {"parameter1", "parameter2"};
		properties.setResolution(Resolution.STANDARD_DEFINITION);
		properties.setVideoformat(Videoformat.MPG);
		
		String[] arguments = expectedCommandLineArguments.split(" ");
				
		String[] actualParameters = client.saveOptionsAndReturnParameters(arguments, properties); 
		
		Assert.assertNotNull(arguments);
		Assert.assertNotNull(actualParameters);
		Assert.assertNotEquals(Resolution.HIGH_DEFINITION, properties.getResolution());
		Assert.assertNotEquals(Videoformat.WEBM, properties.getVideoformat());
		Assert.assertNotEquals(expectedParameters, actualParameters);
	}
	
	@Test
	public void testSeparateParametersFromOptions() {
		int numberOfOptions = 0;
		String argumentsWithThreeOptions="-h -w -m parameter1 parameter2";
		String argumentsWithOneOptions="-h parameter1";
		String argumentsWithFalseOptions="-h -M parameter1 parameter2";
				
		String[] expectedParameters1= {"parameter1", "parameter2"};
		String[] expectedParameters2 = {"parameter1"};
		String[] expectedParameters3 = {"-M", "parameter1", "parameter2"};
		
		String[] fiveArguments = argumentsWithThreeOptions.split(" ");
		String[] threeArguments = argumentsWithOneOptions.split(" ");
		String[] fourArguments = argumentsWithFalseOptions.split(" ");
		
		numberOfOptions=3;
		String[] actualParametersWithoutThreeOptions = client.separateParametersFromOptions(fiveArguments, numberOfOptions);
		numberOfOptions=1;
		String[] actualParametersWithoutOneOptions = client.separateParametersFromOptions(threeArguments, numberOfOptions);
		numberOfOptions=1;
		String[] actualParametersWithFalseOptions = client.separateParametersFromOptions(fourArguments, numberOfOptions);
		
		Assert.assertArrayEquals(expectedParameters1, actualParametersWithoutThreeOptions);
		Assert.assertArrayEquals(expectedParameters2, actualParametersWithoutOneOptions);
		Assert.assertArrayEquals(expectedParameters3, actualParametersWithFalseOptions);
	}
	
	@Test
	public void testIsParameterUrl() {
		String url = "http://www.youtube.com/watch?v=Cx6eaVeYXOs";
		
		Assert.assertTrue(client.isParameterUrl(url));
	}
	
	@Test
	public void testIsParameterUrlFalse() {
		String url = "http://www.youtube.com/test?v=Cx6eaVeYXOs";
		
		Assert.assertFalse(client.isParameterUrl(url));
	}
	
	@Test
	public void testAddUrlsToDownloadList() {
		String[] parameters = {"http://www.youtube.com/watch?v=Cx6eaVeYXOs", "http://www.youtube.com/watch?v=Cx6eaVeYXOs", "test", "http://www.youtube.com/watch?v=Cx6eaVeYXOs"};
		int expectedNumberOfAddedUrls = 3;
		int actualNumberOfAddedUrls;

//		TODO there is a Problem, it can be throw a NullpointerException
		JFCMainClient.DLM = new DefaultListModel<YoutubeUrl>();

		actualNumberOfAddedUrls = client.addUrlsToDownloadList(parameters);
		Assert.assertEquals(expectedNumberOfAddedUrls, actualNumberOfAddedUrls);
	}
	
	@Test 
	public void testIsArgumentResolutionOption() {
		String argument1 = "-h";
		String argument2 = "-l";
		
		Assert.assertTrue(client.isArgumentResolutionOption(argument1));
		Assert.assertTrue(client.isArgumentResolutionOption(argument2));
	}
	
	@Test
	public void testSaveHighDefinitionResolution() {
		properties.setResolution(Resolution.STANDARD_DEFINITION);
		String argument = "-h";
		
		client.saveResolution(argument, properties);
		Assert.assertEquals(Resolution.HIGH_DEFINITION, properties.getResolution());
	}
	
	@Test
	public void testSaveLowDefinitionResolution() {
		properties.setResolution(Resolution.STANDARD_DEFINITION);
		String argument = "-l";
		
		client.saveResolution(argument, properties);
		Assert.assertEquals(Resolution.LOW_DEFINITION, properties.getResolution());
	}
	
	@Test 
	public void testIsArgumentVideoformatOption() {
		String argument1 = "-f";
		String argument2 = "-w";
		
		Assert.assertTrue(client.isArgumentVideoformatOption(argument1));
		Assert.assertTrue(client.isArgumentVideoformatOption(argument2));
	}
	
	@Test
	public void testSaveFlyVideoformat() {
		properties.setVideoformat(Videoformat.MPG);
		String argument = "-f";
		
		client.saveVideoformat(argument, properties);
		Assert.assertEquals(Videoformat.FLV, properties.getVideoformat());
	}
	
	@Test
	public void testSaveWebmVideoformat() {
		properties.setVideoformat(Videoformat.MPG);
		String argument = "-w";
		
		client.saveVideoformat(argument, properties);
		Assert.assertEquals(Videoformat.WEBM, properties.getVideoformat());
	}
	
	@Test
	public void testGetFirstYoutubeUrlFromList() {
		YoutubeUrl url1 = new YoutubeUrl("url1 test");
		YoutubeUrl url2 = new YoutubeUrl("url2 test");
		JFCMainClient.DLM = new DefaultListModel<YoutubeUrl>();
		
		JFCMainClient.DLM.addElement(url1);
		JFCMainClient.DLM.addElement(url2);
		YoutubeUrl actualUrl = JFCMainClient.getFirstDownloadableUrlFromList();
		
		Assert.assertNotNull(actualUrl);
		Assert.assertEquals(url1, actualUrl);
	}
	
	@Test (expected=DLMException.class)
	public void testGetFirstDownloadableUrlFromListWithoutUrls() {
		JFCMainClient.DLM = new DefaultListModel<YoutubeUrl>();
		
		YoutubeUrl actualUrl = JFCMainClient.getFirstDownloadableUrlFromList();
		
		Assert.assertNotNull(actualUrl);
	}
	
	@Test
	public void testGetFirstDownloadableUrlAfterDownloadingElements() {
		YoutubeUrl url1 = new YoutubeUrl("url1 test");
		YoutubeUrl url2 = new YoutubeUrl("url2 test");
		JFCMainClient.DLM = new DefaultListModel<YoutubeUrl>();
		
		url1.setDownloading();
		JFCMainClient.DLM.addElement(url1);
		JFCMainClient.DLM.addElement(url2);
		YoutubeUrl actualUrl = JFCMainClient.getFirstDownloadableUrlFromList();
		
		Assert.assertNotNull(actualUrl);
		Assert.assertEquals(url2, actualUrl);
	}
	
	@Test
	public void testCommand_NDL_DownloadIsEnabled() {
		String expectedCommand1 = "ndl off";
		String expectedCommand2 = "ndl false";
		
		client.parseAndExecuteCliInputCommands(expectedCommand1);
		Assert.assertTrue(properties.isDownloadEnabled());
		
		client.parseAndExecuteCliInputCommands(expectedCommand2);
		Assert.assertTrue(properties.isDownloadEnabled());		
	}
	
	@Test
	public void testCommand_NDL_DownloadIsDisabled() {
		String expectedCommand1 = "ndl on";
		String expectedCommand2 = "ndl true";
		
		client.parseAndExecuteCliInputCommands(expectedCommand1);
		Assert.assertTrue(properties.isDownloadDisabled());
		
		client.parseAndExecuteCliInputCommands(expectedCommand2);
		Assert.assertTrue(properties.isDownloadDisabled());
	}
	
	@Test
	public void testCommand_DEBUG_isDisabled() {
		String expectedCommand1 = "debug off";
		String expectedCommand2 = "debug false";
		
		Properties.IS_DEBUG = true;
		client.parseAndExecuteCliInputCommands(expectedCommand1);
		Assert.assertFalse(Properties.IS_DEBUG);
		
		Properties.IS_DEBUG = true;
		client.parseAndExecuteCliInputCommands(expectedCommand2);
		Assert.assertFalse(Properties.IS_DEBUG);		
	}
	
	@Test
	public void testCommand_DEBUG_isEnabled() {
		String expectedCommand1 = "debug on";
		String expectedCommand2 = "debug true";
		
		Properties.IS_DEBUG = false;
		client.parseAndExecuteCliInputCommands(expectedCommand1);
		Assert.assertTrue(Properties.IS_DEBUG);
		
		Properties.IS_DEBUG = false;
		client.parseAndExecuteCliInputCommands(expectedCommand2);
		Assert.assertTrue(Properties.IS_DEBUG);		
	}
	
	@Test
	public void testCommand_SDS_isDisabled() {
		String expectedCommand1 = "sds off";
		String expectedCommand2 = "sds false";
		
		properties.setTryToSaveDiskspace(true);
		client.parseAndExecuteCliInputCommands(expectedCommand1);
		Assert.assertFalse(properties.isTryToSaveDiskspace());
		
		properties.setTryToSaveDiskspace(true);
		client.parseAndExecuteCliInputCommands(expectedCommand2);
		Assert.assertFalse(properties.isTryToSaveDiskspace());		
	}
	
	@Test
	public void testCommand_SDS_isEnabled() {
		String expectedCommand1 = "sds on";
		String expectedCommand2 = "sds true";
		
		properties.setTryToSaveDiskspace(false);
		client.parseAndExecuteCliInputCommands(expectedCommand1);
		Assert.assertTrue(properties.isTryToSaveDiskspace());
		
		properties.setTryToSaveDiskspace(false);
		client.parseAndExecuteCliInputCommands(expectedCommand2);
		Assert.assertTrue(properties.isTryToSaveDiskspace());		
	}
	
	@Test
	public void testCommand_AUDIOONLY_isDisabled() {
		String expectedCommand1 = "audio off";
		String expectedCommand2 = "audioonly false";
		String expectedCommand3 = "audio off";
		String expectedCommand4 = "audioonly false";
		
		properties.setAudioOnly(true);
		client.parseAndExecuteCliInputCommands(expectedCommand1);
		Assert.assertFalse(properties.isAudioOnly());
		
		properties.setAudioOnly(true);
		client.parseAndExecuteCliInputCommands(expectedCommand2);
		Assert.assertFalse(properties.isAudioOnly());		

		properties.setAudioOnly(true);
		client.parseAndExecuteCliInputCommands(expectedCommand3);
		Assert.assertFalse(properties.isAudioOnly());
		
		properties.setAudioOnly(true);
		client.parseAndExecuteCliInputCommands(expectedCommand4);
		Assert.assertFalse(properties.isAudioOnly());			
	}
	
	@Test
	public void testCommand_AUDIOONLY_isEnabled() {
		String expectedCommand1 = "audio on";
		String expectedCommand2 = "audioonly on";
		String expectedCommand3 = "audio true";
		String expectedCommand4 = "audioonly true";
		
		properties.setAudioOnly(false);
		client.parseAndExecuteCliInputCommands(expectedCommand1);
		Assert.assertTrue(properties.isAudioOnly());
		
		properties.setAudioOnly(false);
		client.parseAndExecuteCliInputCommands(expectedCommand2);
		Assert.assertTrue(properties.isAudioOnly());
		
		properties.setAudioOnly(false);
		client.parseAndExecuteCliInputCommands(expectedCommand3);
		Assert.assertTrue(properties.isAudioOnly());
		
		properties.setAudioOnly(false);
		client.parseAndExecuteCliInputCommands(expectedCommand4);
		Assert.assertTrue(properties.isAudioOnly());
	}
}
