package se.boalbert.coronavirustracker.services;

// Min 26:43

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import se.boalbert.coronavirustracker.models.LocationStats;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

// This class will call the .csv-data
@Service // This annotation makes it a Spring-"Service", creates an instance of Service-class
public class CoronaVirusDataService {

    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<LocationStats> allStats = new ArrayList<>();

    // Below method makes an http-call to the .csv url
    @PostConstruct // Run this method when @Service-class is constructed / app starts
    // Schedules an interval when this method should run, defined via "cron = "second, minute, hour, day, month, year"
    // Need to use annotation @EnableScheduling in main-method for scheduling to work
    @Scheduled(cron = "* * 1 * * *") // Runs the first hour of every day
    public void fetchVirusData() throws IOException, InterruptedException {
        List<LocationStats> newStats = new ArrayList<>();

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

        // Iterates all record and prints columns defined in record.get().
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            String state = record.get("Province/State");
            System.out.println(state);

        }

    }

}
