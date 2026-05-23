As soon the front is loaded, the defatul period is to search the current week. Above are the reqeust front page make as is loaded:

requests sent by the frontend to the backend when the page is loaded, with the default period of the current week:
    http://localhost:8080/reports/3/customers?page=1&per_page=20
    http://localhost:8080/reports/trend?limit=50
    http://localhost:8080/reports/3/reasons
    http://localhost:8080/reports/3/carriers
    http://localhost:8080/reports/3/regions
    http://localhost:8080/reports/3
    http://localhost:8080/reports?limit=100&from=2026-05-15T23%3A17%3A56.825Z&to=2026-05-22T23%3A17%3A56.825Z

Front page allows to select a reng time as well, by week, month and year. Above is the the reqeust for each of them, that front givs to backend (this project)
 when i Click and select the ragne time:

Week:
http://localhost:8080/reports?limit=100&from=2026-05-15T23%3A21%3A06.207Z&to=2026-05-22T23%3A21%3A06.207Z

Month:
http://localhost:8080/reports?limit=100&from=2026-04-22T23%3A21%3A47.739Z&to=2026-05-22T23%3A21%3A47.739Z
    
Year:
http://localhost:8080/reports?limit=100&from=2025-05-22T23%3A22%3A09.923Z&to=2026-05-22T23%3A22%3A09.923Z

With this, i need you to implement / adapte the code to select from the database the data for the current week, month and year, and also to select the data for the trend line chart (when front page is loaded) and for the period mode (when i select the range time).

For this, you can access the 'schema.sql' to see the database structure, and the 'DeliveryReport' entity and 'DeliveryReportRepository' to see how the data is currently being accessed. You will need to add the necessary fields for delivery date range and implement the queries to filter the data based on the selected time range.