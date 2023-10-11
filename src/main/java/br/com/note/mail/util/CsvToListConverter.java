package br.com.note.mail.util;

import br.com.note.mail.domain.EmailInfo;
import br.com.note.mail.domain.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class CsvToListConverter {

    private static final String COMMA_DELIMITER = ";";

    public static List<EmailInfo> convertCsvToList(InputStream inputStream) throws FileNotFoundException {

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
            throw new RuntimeException(e);
        }
    }

    private static EmailInfo createEmailInfo(String[] values) {
        Student student = Student.builder()
                .name(values[0])
                .email(values[1])
                .build();

        List<String> grades = new ArrayList<>(Arrays.asList(values).subList(2, values.length));

        return EmailInfo.builder()
                .student(student)
                .grades(grades)
                .build();
    }
}
