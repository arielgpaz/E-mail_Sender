package br.com.grades.email.sender.util;

import br.com.grades.email.sender.domain.EmailInfo;
import br.com.grades.email.sender.domain.EmailModel;
import br.com.grades.email.sender.domain.Student;
import br.com.grades.email.sender.exception.CsvConverterException;
import com.opencsv.CSVWriter;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class CsvConverter {

    private static final String COMMA_DELIMITER = ";";

    public static List<EmailInfo> convertCsvToList(InputStream inputStream) {

        List<EmailInfo> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, UTF_8))) {
            String line;

            while ((line = br.readLine()) != null) {

                String[] values = line.split(COMMA_DELIMITER);

                EmailInfo emailInfo = createEmailInfo(values);

                records.add(emailInfo);
            }

            return records;

        } catch (IOException e) {
            throw new CsvConverterException("Falha ao extrair dados da planilha", e);
        }
    }

    private static EmailInfo createEmailInfo(String[] values) {

        var student = Student.builder()
                .name(values[0])
                .email(values[1])
                .build();

        List<String> grades = new ArrayList<>(Arrays.asList(values).subList(2, values.length));

        return EmailInfo.builder()
                .student(student)
                .grades(grades)
                .build();
    }

    public static byte[] convertListToCsv(List<EmailModel> emails) {

        var csvFile = "emails.csv";

        generateCsv(emails, csvFile);

        try {
            return Files.readAllBytes(Paths.get(csvFile));
        } catch (IOException e) {
            throw new CsvConverterException("Erro inesperado do sistema.", e);
        }
    }

    private static void generateCsv(List<EmailModel> emails, String csvFile) {
//        try (var writer = new CSVWriter(new FileWriter(csvFile), ';', '\"', '\\', "\n")) {
        try (
                var writer = new OutputStreamWriter(new FileOutputStream(csvFile), "windows-1252");
                var csvWriter = new CSVWriter(writer, ';', '\"', '\\', "\n")
        ) {
            var headers = getHeaders();
            csvWriter.writeNext(headers);

            emails.forEach(email -> {
                String[] data = getData(email);
                csvWriter.writeNext(data);
            });

        } catch (IOException e) {
            throw new CsvConverterException("Não foi possível transformar a lista em csv.", e);
        }
    }

    private static String[] getData(EmailModel email) {

        var dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        var data = email.getSendDateEmail().format(dateFormat);

        return new String[]{
                email.getEmailTo(),
                email.getSubject(),
                email.getText(),
                data,
                email.getStatusEmail()
        };
    }

    private static String[] getHeaders() {
        Field[] fields = EmailModel.class.getDeclaredFields();
        String[] headers = new String[fields.length - 3];

        for (int i = 3; i < fields.length; i++) {
            headers[i - 3] = fields[i].getName();
        }

        return headers;
    }
}
