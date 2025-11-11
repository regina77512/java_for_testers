package ru.stqa.mantis.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import org.openqa.selenium.io.CircularOutputStream;


public class JamesCliHelper extends HelperBase {

  public JamesCliHelper(ApplicationManager manager) {
    super(manager);
  }

  public void addUser(String email, String password) throws IOException, InterruptedException {
    ProcessBuilder processBuilder = new ProcessBuilder(
        "java", "-cp", "james-server-jpa-app.lib/*",
        "org.apache.james.cli.ServerCmd", "AddUser", email, password);
    var workingDirectory = manager.property("james.workingDir");
    processBuilder.directory(new File(workingDirectory));

    Process process = processBuilder.start();
    int exitCode = process.waitFor();
  }
}

  
