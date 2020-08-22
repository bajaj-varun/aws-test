<H1>TODO: Case study Blog and AWS services use</H1>

<h3>Use-Case: Airline on-time performance</h3>

Reference Link: <a href="http://stat-computing.org/dataexpo/2009/">http://stat-computing.org/dataexpo/2009/</a>

Have you ever been stuck in an airport because your flight was delayed or cancelled and wondered if you could have predicted it if you'd had more data? This is your chance to find out.

<h4>The data</h4>
The data consists of flight arrival and departure details for all commercial flights within the USA, from October 1987 to April 2008. This is a large dataset: there are nearly 120 million records in total, and takes up 1.6 gigabytes of space compressed and 12 gigabytes when uncompressed. 


<h4>Batch Ingestion & Processing</h4>

<b>Hadoop directory Structure to be created</b>.
<table>
<tr>
<td>Layer</td><td>Directory Path</td><td>File Format</td>		
</tr>
<tr>
<td>RAW</td><td>	/data/raw/</td><td>As is (e.g. TXT, CSV, XML, JSON, etc.,)</td>
</tr>
<tr>
<td>Decomposed</td><td>	/data/decomposed/</td><td>	Avro</td>
</tr>
<tr>
<td>Modelled</td><td>	/data/modelled/</td><td>	Parquet</td>
</tr>
<tr>
<td>Schema (Meta data)</td><td>	/data/schema/</td><td>	AVSC schema</td>
</tr>
</table>

**Source data details**
Download the stats created for year 2008 & 2007.<br/>
http://stat-computing.org/dataexpo/2009/the-data.html

**Supplemental Data**: <br/>http://stat-computing.org/dataexpo/2009/supplemental-data.html

**Data preparation**<br/>
Create a Kafka cluster
Create the following Topics in Kafka<br/>
<ul>
<li>Airports</li>
<li>Carriers</li>
<li>Planedate</li>
<li>OTP</li>
</ul>
Download the stats created for year 2008 & 2007 and load the data into a Kafka cluster under the relevant topics. Use any options of your choice to load the data to Kafka topics.
<br/><br/>

###**Batch Ingestion (HDFS)**<br/>
####**Raw layer (Store data AS-IS)**
Consume messages from Airports & Planedate Kafka Topic to HDFS Raw folder
Use Spark Streaming to consume messages from Carriers and OTP Kafka Topic to HDFS Raw folder
####**Decomposed layer (Append UUID and timestamp to the AS-IS data)**
For each message in the Airports & Planedate data from raw directory, append UUID and timestamp.
For each message in the Carriers & OTP data from raw directory, append UUID and timestamp.

####**Modelling and processing**
Cleanse the data (trim, null, removing duplicates) and load it in Parquet format as modelled using Spark/Scala	


####**Develop a solution to answer the following questions.** 
<ul>
<li>Which carrier performs better?</li>
<li>When is the best time of day/day of week/time of year to fly to minimise delays?
Do older planes suffer more delays?</li>
<li>Can you detect cascading failures as delays in one airport create delays in others?</li><li> Are there critical links in the system?</li>
<li>How well does weather predict plane delays?</li>
</ul>
