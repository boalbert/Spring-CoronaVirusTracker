# SpringBoot-CoronaVirusTracker

Tracking cases per region.

- Service is pulling live data from [this CSV](https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv).
- Schedueled to pull new data @ cron (* * 1 * * *)
- Parses .CSV data with Apache Commons CSV
