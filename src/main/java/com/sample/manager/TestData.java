package com.sample.manager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.github.javafaker.Faker;

public class TestData extends Faker {
  private Map<?, ?> testData;

  public Map<String, String> getUserConfig(String user) {
    if (testData == null) {
      testData =
          readYamlFile(
              Paths.get(".").toAbsolutePath().normalize().toString() + "/config/test_data.yml");
    }

    return (Map<String, String>) testData.get(user);
  }

  /**
   * Reads a YML file.
   *
   * @param path Absolute location + file name of file to be read
   * @return Map representation of yaml file
   */
  private static Map<?, ?> readYamlFile(String path) {
    try {
      YamlReader reader = new YamlReader(new FileReader(path));
      Map<?, ?> map = (Map<?, ?>) reader.read();
      reader.close();
      return map;
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (YamlException exception) {
      exception.printStackTrace();
    } catch (IOException exception) {
      exception.printStackTrace();
    }

    // Never reached
    return null;
  }
}
