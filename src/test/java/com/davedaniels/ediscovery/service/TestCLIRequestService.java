package com.davedaniels.ediscovery.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.box.sdk.BoxAPIConnection;
import com.box.sdk.BoxFile;
import com.box.sdk.BoxFolder;
import com.box.sdk.BoxItem;
import com.davedaniels.ediscovery.service.CLIRequestService;
import com.davedaniels.ediscovery.service.cloud.CloudService;

@ContextConfiguration( loader = SpringockitoContextLoader.class, locations = { "classpath:applicationContext.xml" } )
@RunWith( SpringJUnit4ClassRunner.class )
public class TestCLIRequestService {

   @Spy
   @InjectMocks
   private CLIRequestService service;

   @Mock
   private CloudService cloudService;

   @Rule
   public TemporaryFolder tempFolder = new TemporaryFolder();

   @Rule
   public ExpectedException expectedEx = ExpectedException.none();

   @Before
   public void setup() {
      MockitoAnnotations.initMocks( this );
   }

   //
   // @Test
   // public void givenAnAccountFileWhenThereAreAccountsThenLoadTheAccounts() throws IOException {
   // File tempFile = tempFolder.newFile();
   // try (FileWriter fw = new FileWriter( tempFile.getAbsoluteFile() )) {
   // try (BufferedWriter bw = new BufferedWriter( fw )) {
   // bw.write( "gayc" + System.getProperty( "line.separator" ) );
   // bw.write( "danield3" + System.getProperty( "line.separator" ) );
   // }
   // }
   //
   // Mockito.doReturn( tempFile ).when( service ).getAccountsFile();
   //
   // service.loadAccounts();
   //
   // Assert.assertEquals( 2, service.getAccounts().size() );
   // List<String> tempAc = new ArrayList<>( service.getAccounts() );
   // Assert.assertEquals( tempAc.get( 0 ), "danield3" );
   // Assert.assertEquals( tempAc.get( 1 ), "gayc" );
   //
   // Mockito.verify( service ).getAccountsFile();
   // tempFile.delete();
   // }
   //
   // @Test
   // public void givenAnAccountFileWhenThereAreNoAccountsThenDontLoad() throws IOException {
   // File tempFile = tempFolder.newFile();
   // Mockito.doReturn( tempFile ).when( service ).getAccountsFile();
   //
   // service.loadAccounts();
   //
   // Assert.assertEquals( 0, service.getAccounts().size() );
   //
   // Mockito.verify( service ).getAccountsFile();
   // tempFile.delete();
   // }
   //
   // @Test
   // public void givenAnAccountFileWhenEmptyLinesThenDontLoad() throws IOException {
   // File tempFile = tempFolder.newFile();
   // try (FileWriter fw = new FileWriter( tempFile.getAbsoluteFile() )) {
   // try (BufferedWriter bw = new BufferedWriter( fw )) {
   // bw.write( System.getProperty( "line.separator" ) );
   // bw.write( System.getProperty( "line.separator" ) );
   // bw.write( System.getProperty( "line.separator" ) );
   // bw.write( System.getProperty( "line.separator" ) );
   // }
   // }
   //
   // Mockito.doReturn( tempFile ).when( service ).getAccountsFile();
   //
   // service.loadAccounts();
   //
   // Assert.assertEquals( 0, service.getAccounts().size() );
   //
   // Mockito.verify( service ).getAccountsFile();
   // tempFile.delete();
   // }
   //
   // @Test
   // public void givenAnAccountFileWhenMixedLinesThenLoadNames() throws IOException {
   // File tempFile = tempFolder.newFile();
   // try (FileWriter fw = new FileWriter( tempFile.getAbsoluteFile() )) {
   // try (BufferedWriter bw = new BufferedWriter( fw )) {
   // bw.write( System.getProperty( "line.separator" ) );
   // bw.write( "gayc" + System.getProperty( "line.separator" ) );
   // bw.write( System.getProperty( "line.separator" ) );
   // bw.write( " " + System.getProperty( "line.separator" ) );
   // bw.write( "danield3" + System.getProperty( "line.separator" ) );
   // }
   // }
   //
   // Mockito.doReturn( tempFile ).when( service ).getAccountsFile();
   //
   // service.loadAccounts();
   //
   // Assert.assertEquals( 2, service.getAccounts().size() );
   // List<String> tempAc = new ArrayList<>( service.getAccounts() );
   // Assert.assertEquals( tempAc.get( 0 ), "danield3" );
   // Assert.assertEquals( tempAc.get( 1 ), "gayc" );
   //
   // Mockito.verify( service ).getAccountsFile();
   // tempFile.delete();
   // }
   //
   // @Test
   // public void givenNullAccountFileWhenLoadingAccountsThenThrowException() throws IOException {
   // expectedEx.expect( RuntimeException.class );
   // expectedEx.expectMessage( "The accounts.txt file could not be loaded." );
   //
   // // Will throw a null pointer (ie RuntimeException)
   // Mockito.when( service.getAccountsFile() ).thenReturn( null );
   //
   // service.loadAccounts();
   // Assert.fail( "Should never get here" );
   // }
   //
   // @Test
   // public void givenMissingAccountFileWhenGettingAccountsThenThrowException() throws IOException {
   // expectedEx.expect( RuntimeException.class );
   // expectedEx.expectMessage( "The accounts.txt file could not be loaded." );
   //
   // // Will throw an IOException
   // Mockito.when( service.getAccountsFile() ).thenReturn( new File( "file doesn't exist" ) );
   //
   // service.loadAccounts();
   // Assert.fail( "Should never get here" );
   // }
   //
   // @Test
   // public void givenAnAccountFileWhenGettingTheFileThenLoadReturnTheFile() throws IOException {
   // File tempRoot = tempFolder.newFile();
   //
   // Mockito.when( service.getProjectRoot() ).thenReturn( tempRoot );
   //
   // File actual = service.getAccountsFile();
   //
   // File expected = new File( tempRoot, "accounts.txt" );
   // Assert.assertEquals( expected, actual );
   // }
   //
   // @Test
   // public void givenAUserWhenAuthenticationSucceedsThenReturnTrue() throws IOException {
   // User user = new User( "aaa", "bbb" );
   // service.setUser( user );
   //
   // Mockito.when( cloudService.authenticateAdmin( user ) ).thenReturn( true );
   //
   // boolean actual = service.authenticate();
   //
   // Assert.assertTrue( actual );
   // Mockito.verify( cloudService ).authenticateAdmin( user );
   // }
   //
   // @Test
   // public void givenAUserWhenAuthenticationFailsThenReturnFalse() throws IOException {
   // User user = new User( "aaa", "bbb" );
   // service.setUser( user );
   //
   // Mockito.when( cloudService.authenticateAdmin( user ) ).thenReturn( false );
   //
   // boolean actual = service.authenticate();
   //
   // Assert.assertFalse( actual );
   // Mockito.verify( cloudService ).authenticateAdmin( user );
   // }
   //
   // @Test
   // public void givenBeforeNoonWhenCreatingDownloadDirectoryThenCreateTheDirectoryWith24HourName() throws IOException {
   //
   // DateTime dateTime = new DateTime().withYear( 2000 ).withMonthOfYear( 5 ).withDayOfMonth( 15 ).withHourOfDay( 5 )
   // .withMinuteOfHour( 34 ).withSecondOfMinute( 4 );
   // DateTimeUtils.setCurrentMillisFixed( dateTime.getMillis() );
   // Mockito.when( service.getProjectRoot() ).thenReturn( tempFolder.getRoot() );
   //
   // service.createDownloadDirectory();
   //
   // File[] files = tempFolder.getRoot().listFiles();
   // Assert.assertEquals( 1, files.length );
   // Assert.assertEquals( "20000515-053404", files[0].getName() );
   // }
   //
   // @Test
   // public void givenAfterNoonWhenCreatingDownloadDirectoryThenCreateTheDirectoryWith24HourName() throws IOException {
   //
   // DateTime dateTime = new DateTime().withYear( 2000 ).withMonthOfYear( 5 ).withDayOfMonth( 15 ).withHourOfDay( 15 )
   // .withMinuteOfHour( 34 ).withSecondOfMinute( 4 );
   // DateTimeUtils.setCurrentMillisFixed( dateTime.getMillis() );
   // Mockito.when( service.getProjectRoot() ).thenReturn( tempFolder.getRoot() );
   //
   // service.createDownloadDirectory();
   //
   // File[] files = tempFolder.getRoot().listFiles();
   // Assert.assertEquals( 1, files.length );
   // Assert.assertEquals( "20000515-153404", files[0].getName() );
   // }
   //
   // @Test
   // public void givenAnExistingDirectoryWhenCreatingDownloadDirectoryThenThrowException() throws IOException {
   // expectedEx.expect( RuntimeException.class );
   // expectedEx
   // .expectMessage( "The download directory could not be created: Download directory [20000515-153404] already exists." );
   //
   // DateTime dateTime = new DateTime().withYear( 2000 ).withMonthOfYear( 5 ).withDayOfMonth( 15 ).withHourOfDay( 15 )
   // .withMinuteOfHour( 34 ).withSecondOfMinute( 4 );
   // DateTimeUtils.setCurrentMillisFixed( dateTime.getMillis() );
   // Mockito.when( service.getProjectRoot() ).thenReturn( tempFolder.getRoot() );
   //
   // service.createDownloadDirectory();
   //
   // File[] files = tempFolder.getRoot().listFiles();
   // Assert.assertEquals( 1, files.length );
   // Assert.assertEquals( "20000515-153404", files[0].getName() );
   //
   // service.createDownloadDirectory();
   //
   // files = tempFolder.getRoot().listFiles();
   // Assert.assertEquals( 1, files.length );
   // Assert.assertEquals( "20000515-153404", files[0].getName() );
   // }
   //
   // @Test
   // public void givenAUserWhenAuthenticatingThenAuthenticate() throws IOException {
   // User user = new User( "Asdf", "Asdf" );
   // service.setUser( user );
   // Mockito.when( cloudService.authenticateAdmin( user ) ).thenReturn( true );
   //
   // boolean authenticated = service.authenticate();
   // Assert.assertTrue( authenticated );
   // Mockito.verify( cloudService ).authenticateAdmin( user );
   // }
   //
   // @Test
   // public void givenARequestForUserWhenUserNameAndPassEnteredThenCreateUser() throws IOException {
   // PrintStream oldOut = System.out;
   // ByteArrayOutputStream newOut = new ByteArrayOutputStream();
   //
   // System.setOut( new PrintStream( newOut ) );
   //
   // String input = "user" + System.getProperty( "line.separator" ) + "pass" + System.getProperty( "line.separator" );
   // Scanner scanner = new Scanner( input );
   // service.setScanner( scanner );
   // ReflectionTestUtils.setField(targetObj, fieldName, value)
   //
   // service.requestCredentials();
   //
   // System.setOut( oldOut );
   //
   // Assert.assertEquals( "user", service.getUser().getUserName() );
   // Assert.assertEquals( "pass", service.getUser().getPassword() );
   //
   // String expected = "Enter user: " + System.getProperty( "line.separator" ) + "Enter password: "
   // + System.getProperty( "line.separator" );
   //
   // Assert.assertEquals( expected, newOut.toString() );
   //
   // try {
   // scanner.next();
   // }
   // catch ( IllegalStateException e ) {
   // // expected, means the scanner has been closed
   // }
   // }
   //
   // @Test
   // public void downloadFiles() throws IOException {
   // User user = new User( "Asdf", "Asdf" );
   // service.setUser( user );
   // Mockito.doNothing().when( cloudService ).downloadAccountFiles();
   //
   // service.downloadAccountFiles();
   //
   // Mockito.verify( cloudService ).downloadAccountFiles();
   // }
   //
   // @Test
   // public void displayEndMessage() throws IOException {
   // PrintStream oldOut = System.out;
   // ByteArrayOutputStream newOut = new ByteArrayOutputStream();
   //
   // System.setOut( new PrintStream( newOut ) );
   //
   // service.displayEndMessage();
   //
   // System.setOut( oldOut );
   //
   // String expected = "Process finished." + System.getProperty( "line.separator" );
   // Assert.assertEquals( expected, newOut.toString() );
   // }
   //
   // @Test
   // public void displayStartMessage() throws IOException {
   // User user = new User( "name", "asdf" );
   // PrintStream oldOut = System.out;
   // ByteArrayOutputStream newOut = new ByteArrayOutputStream();
   //
   // System.setOut( new PrintStream( newOut ) );
   //
   // service.setUser( user );
   // service.displayStartMessage();
   //
   // System.setOut( oldOut );
   //
   // String expected = "User [" + user.getUserName() + "] authenticated. Beginning download."
   // + System.getProperty( "line.separator" );
   // Assert.assertEquals( expected, newOut.toString() );
   // }
   //
   // @Test
   // public void getRoot() {
   // File expected = new File( System.getProperty( "user.home" ) + File.separator + "ediscovery" );
   // File actual = service.getProjectRoot();
   // Assert.assertEquals( expected, actual );
   // }

   @Test
   public void xxxx() {
      /*
       * BoxAPIConnection api = new BoxAPIConnection( "IARtmxW9akGLEPWwv65hPIKMaOwfyYsf" );
       * BoxFolder rootFolder = BoxFolder.getRootFolder( api );
       * for ( BoxItem.Info itemInfo : rootFolder ) {
       * System.out.format( "[%s] %s\n", itemInfo.getID(), itemInfo.getName() );
       * }
       * BoxFile file = new BoxFile(api, "44039601210");
       * BoxFile.Info info = file.getInfo();
       * System.out.println(info.getSequenceID());
       * // BoxFolder folder = BoxFolder.getRootFolder( api );
       * // for ( BoxItem.Info itemInfo : folder ) {
       * // if ( itemInfo instanceof BoxFile.Info ) {
       * // BoxFile.Info fileInfo = (BoxFile.Info) itemInfo;
       * // System.out.println( fileInfo.getName() + " " + fileInfo.getPathCollection() );
       * // } else if ( itemInfo instanceof BoxFolder.Info ) {
       * // BoxFolder.Info folderInfo = (BoxFolder.Info) itemInfo;
       * // System.out.println( folderInfo.getName() );
       * // }
       * // }
       * // call through to api to get files
       * // Collection<BoxFile> files = new ArrayList<>();
       * // files.add( new BoxFile( api, "id" ) );
       * //
       * // for ( BoxFile boxFile : files ) {
       * // try {
       * // for ( BoxFileVersion boxFileVersion : boxFile.getVersions() ) {
       * // try (FileOutputStream stream = new FileOutputStream( new File( downloadDir, boxFileVersion.getName() ) )) {
       * // boxFileVersion.download( new FileOutputStream( new File( downloadDir, boxFileVersion.getName() ) ) );
       * // accountsDao.saveDownloadLog( boxFileVersion );
       * // }
       * // }
       * // }
       * // catch ( IOException e ) {
       * // // write error log
       * // }
       * // }
       */
   }
}