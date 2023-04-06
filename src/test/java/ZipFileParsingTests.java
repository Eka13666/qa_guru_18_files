import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ZipFileParsingTests {

  ClassLoader cl = ZipFileParsingTests.class.getClassLoader();

  @Test
  void zipParsePdfTest() throws Exception {
    try (
            InputStream resource = cl.getResourceAsStream("Archive.zip");
            ZipInputStream zis = new ZipInputStream(resource)
    ) {
      ZipEntry entry;
      while ((entry = zis.getNextEntry()) != null) {

        if (entry.getName().endsWith(".pdf")) {
          PDF content = new PDF(zis);
          assertThat(content.text).contains("Шпаргалка");
          return;
        }
      }
      Assertions.fail("No PDF file found in zip archive");

    }
  }

  @Test
  void zipParseXlsxTest() throws Exception {
    try (
            InputStream resource = cl.getResourceAsStream("Archive.zip");
            ZipInputStream zis = new ZipInputStream(resource)
    ) {
      ZipEntry entry;
      while ((entry = zis.getNextEntry()) != null) {

        if (entry.getName().contains(".xlsx")) {
          XLS content = new XLS(zis);
          assertThat(content.excel.getSheetAt(0).getRow(2).getCell(1).getStringCellValue()).contains("недозвон");
          return;
        }
      }
      Assertions.fail("No XLS file found in zip archive");
    }
  }

  @Test
  void zipParseCsvTest() throws Exception {
    try (
            InputStream resource = cl.getResourceAsStream("Archive.zip");
            ZipInputStream zis = new ZipInputStream(resource)
    ) {
      ZipEntry entry;
      while ((entry = zis.getNextEntry()) != null) {

        if (entry.getName().endsWith(".csv")) {
          CSVReader reader = new CSVReader(new InputStreamReader(zis));
          List<String[]> content = reader.readAll();
          assertThat(content.get(1)[0]).contains("строка3");
          return;
        }
      }
      Assertions.fail("No CSV file found in zip archive");
    }
  }
}

