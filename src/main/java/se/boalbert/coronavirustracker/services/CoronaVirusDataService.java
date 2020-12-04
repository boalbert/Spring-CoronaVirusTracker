package se.boalbert.coronavirustracker.services;

// Min 13.13

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// This class will call the .csv-data
@Service // This annotation makes it a Spring-"Service", creates an instance of Service-class
public class CoronaVirusDataService {

    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    // Below method makes an http-call to the .csv url
    @PostConstruct // Run this method when @Service-class is constructed / app starts
    public void fetchVirusData() throws IOException, InterruptedException {
        // Creating a new client
        HttpClient client = HttpClient.newHttpClient();

        // Create a request to the URL
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();

        // Take the body of the response and return it as a String
        // "httpResponse" stores the response as a string
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString()); // This is going to give us an response

        System.out.println(httpResponse.body());

        // Instance of Reader that parses String
        StringReader csvBodyReader = new StringReader(httpResponse.body());

        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            String state = record.get("Province/State");
            System.out.println(state);

        }

    }

}
