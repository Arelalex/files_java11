package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.opencsv.CSVReader;
import guru.qa.model.UsersModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class ReadingFileTest {

    ClassLoader cl = ReadingFileTest.class.getClassLoader();
    Gson gson = new Gson();

    @Test
    void readingPdfFromZip() throws Exception {
        try (InputStream stream = cl.getResourceAsStream("sample.zip");
             ZipInputStream zis = new ZipInputStream(stream)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().contains("fw9.pdf")) {
                    PDF pdf = new PDF(zis);
                    Assertions.assertTrue(pdf.title.contains("Form W-9 (Rev. October 2018)"));
                    break;
                }
            }
        }
    }

    @Test
    void readingCsvFromZip() throws Exception {
        try (InputStream stream = cl.getResourceAsStream("sample.zip");
             ZipInputStream zis = new ZipInputStream(stream)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().contains("sample-csv.csv")) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> csvContent = csvReader.readAll();
                    Assertions.assertArrayEquals(new String[]{"Role 1 Emails", "Name", "Surname"}, csvContent.get(0));
                    Assertions.assertArrayEquals(new String[]{"alex_mosk@bk.ru", "Alex", "Moskotina"}, csvContent.get(1));
                    Assertions.assertArrayEquals(new String[]{"amoskotina+a@spbfiller.com", "Alexico", "Moskico"}, csvContent.get(2));
                    break;
                }
            }
        }
    }

    @Test
    void readingXlsxFromZip() throws Exception {
        try (InputStream stream = cl.getResourceAsStream("sample.zip");
             ZipInputStream zis = new ZipInputStream(stream)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().contains("sample-excel.xlsx")) {
                    XLS xls = new XLS(zis);
                    Assertions.assertEquals(xls.excel
                            .getSheetAt(0)
                            .getRow(0)
                            .getCell(0)
                            .getStringCellValue(), "Тестовые данные");
                    break;
                }
            }
        }
    }

    @Test
    void jsonTest() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream is = cl.getResourceAsStream("users.json");
             InputStreamReader isr = new InputStreamReader(is)) {
            UsersModel user = objectMapper.readValue(isr, UsersModel.class);

            Assertions.assertEquals("Alex", user.getFirstName());
            Assertions.assertEquals("M", user.getLastName());
            Assertions.assertEquals("123@gmail.com", user.getEmail());
            Assertions.assertEquals("111 8th Avenue, New York", user.getAddress());
            Assertions.assertEquals(List.of("English", "Russian", "Spanish"), user.getLanguage());
        }
    }
}










