package com.sample.manager;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.sample.page.BasePage;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GmailAPI {
  private static final String APPLICATION_NAME = "Selenium Automation";
  private static final List<String> SCOPES = Collections.singletonList(GmailScopes.MAIL_GOOGLE_COM);
  private JsonFactory JSON_FACTORY;
  private String TOKENS_DIRECTORY_PATH;
  private String CREDENTIALS_FILE_PATH;
  private NetHttpTransport HTTP_TRANSPORT;
  private Gmail service;
  private String key;

  /**
   * Opens a Gmail API interface to account.
   *
   * <p>Get credentials here: https://developers.google.com/gmail/api/quickstart/java Rename
   * credentials.json file to {user}_credentials.json and place in src/main/resources/
   *
   * <p>Tokens will be placed in ./token/{user}/StoredCredential once authenticated
   *
   * @param user The User Page associated to the account
   * @throws GeneralSecurityException
   * @throws IOException
   */
  public GmailAPI(BasePage user) throws GeneralSecurityException, IOException {
    key = user.getUserProperty("key");
    TOKENS_DIRECTORY_PATH = "tokens/" + key;
    CREDENTIALS_FILE_PATH = "/" + key + "_credentials.json";
    JSON_FACTORY = JacksonFactory.getDefaultInstance();
    HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    service =
        new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
            .setApplicationName(APPLICATION_NAME)
            .build();
  }

  /**
   * Creates an authorized Credential object.
   *
   * @param HTTP_TRANSPORT The network HTTP Transport.
   * @return An authorized Credential object.
   * @throws IOException If the credentials.json file cannot be found.
   */
  private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
    // Load client secrets.
    InputStream in = GmailAPI.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
    GoogleClientSecrets clientSecrets =
        GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

    // Build flow and trigger user authorization request.
    GoogleAuthorizationCodeFlow flow =
        new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
            .setAccessType("offline")
            .build();
    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
    return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
  }

  public void deleteMessage(String user, String threadID) throws IOException {
    service.users().threads().delete(user, threadID).execute();
  }

  public void deleteAllMessages(String user) throws IOException {
    List<Message> messages = getAllMessages(user);
    for (Message message : messages) {
      deleteMessage(user, message.getThreadId());
    }
  }

  public List<Message> getAllMessages(String user) throws IOException {
    ListMessagesResponse response = service.users().messages().list(user).execute();
    List<Message> messages = new ArrayList<Message>();
    while (response.getMessages() != null) {
      messages.addAll(response.getMessages());
      if (response.getNextPageToken() != null) {
        String pageToken = response.getNextPageToken();
        response = service.users().messages().list(user).setPageToken(pageToken).execute();
      } else {
        break;
      }
    }

    return messages;
  }
}
