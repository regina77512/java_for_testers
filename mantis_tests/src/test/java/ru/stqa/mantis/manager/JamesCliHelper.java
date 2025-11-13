package ru.stqa.mantis.manager;

import java.io.File;
import java.io.IOException;

public class JamesCliHelper extends HelperBase {

  public JamesCliHelper(ApplicationManager manager) {
    super(manager);
  }

  public void addUser(String username, String password) throws IOException, InterruptedException {
    ProcessBuilder processBuilder = new ProcessBuilder(
        "java", "-cp", "james-server-jpa-app.lib/*",
        "org.apache.james.cli.ServerCmd", "AddUser", username, password);
    var workingDirectory = manager.property("james.workingDir");
    processBuilder.directory(new File(workingDirectory));

    Process process = processBuilder.start();
    int exitCode = process.waitFor();
  }
}

  
