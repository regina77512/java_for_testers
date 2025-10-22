package generator;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import common.CommonFunctions;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import model.GroupData;

public class Generator {

  @Parameter(names = {"--type", "-t"})
  String type;

  @Parameter(names = {"--output", "-0"})
  String output;

  @Parameter(names = {"--format", "-f"})
  String format;

  @Parameter(names = {"--count", "-n"})
  int count;

  public static void main(String[] args) throws IOException {
    var generator = new Generator();
    JCommander.newBuilder()
        .addObject(generator)
        .build()
        .parse(args);
    generator.run();
  }

  private void run() throws IOException {
    var data = generate();
    save(data);
  }

  private Object generate() {
    if ("groups".equals(type)) {
      return generateGroups();
    } else if ("contacts".equals(type)) {
      return generateContacts();
    } else {
      throw new IllegalArgumentException("Неизвестный тип данных " + type);
    }
  }

  private Object generateGroups() {
    var result = new ArrayList<GroupData>();
    for (int i = 0; i < count; i++) {
      result.add(new GroupData() //вызывается конструктор без параметров, а потом создаются объекты
          .withName(CommonFunctions.randomString(i * 10)) // с модифицированным именем
          .withHeader(CommonFunctions.randomString(i * 10)) // хедером
          .withFooter(CommonFunctions.randomString(i * 10))); // и футером
    }
    return result;
  }

  private Object generateContacts() {
    return null;
  }

  private void save(Object data) throws IOException {
    if ("json".equals(format)) {
      ObjectMapper mapper = new ObjectMapper();
      mapper.enable(SerializationFeature.INDENT_OUTPUT);
      var json = mapper.writeValueAsString(data);

      try (var writer = new FileWriter(output)) {
        writer.write(json);
      }
    } if ("yaml".equals(format)) {
      var mapper = new YAMLMapper();
      mapper.writeValue(new File(output), data);
    } if ("xml".equals(format)) {
        var mapper = new XmlMapper();
        mapper.writeValue(new File(output), data);
    } else {
        throw new IllegalArgumentException("Неизвестный формат данных " + format);
      }
    }
  }
