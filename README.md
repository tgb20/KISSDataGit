# KISSDataGit
Allows for converting the fixed width raw CDC file to a csv

## How to use:

To use KissData you need 2 files

### Data File
The first is a local copy of the CDC Mortality Dataset, which can be found [here](https://www.cdc.gov/nchs/data_access/VitalStatsOnline.htm#Mortality_Multiple). Download the Mortality Multiple Cause Files zip file for the year you are interested in and unzip the file to get a .DUSMCPUB file. This is the raw data.

### Config File
The second is a config file for the data you wish to extract. You will need the key for the data, which can be found [here](https://www.cdc.gov/nchs/nvss/mortality_public_use_data.htm), and a .txt file which will contain your configuration.

The first line in the .txt file is the ICD-10 Codes you wish to search for seperated by a space. These codes can be searched for [here](https://icdcodelookup.com/icd-10/codes).
```
CODE1 CODE2 CODE3
```

After that you need to create a line for each column you wish to create in the csv.
```
columnname startCharacter:endCharacter
```
*columnname* is the name of the column in your new csv

*startCharacter* is the Tape Location of your column in the old data file

*endCharacter* is the Last Tape Location of your column in the old data file, or if the Tape Location is only one location then the Tape Location + 1

You can also add modifiers to each column to replace the value within the old data with a new value.
This is by using the following format
```
currentValue=newValue,currentValue=newValue
```
*currentValue* is the value that the entry is normally

*newValue* is the value you wish that entry to be

**DO NOT** put spaces in the modifiers, it will crash the program.

In the end each line will look like
```
name location modifiers
```

Here is an example config.txt
```
X40 X41 X42 X43 X44
sex 69:70 
month 65:67
age 79:81 01=<1,02=1-4,03=5-14,04=15-24,05=25-34,06=35-44,07=45-54,08=55-64,09=65-74,10=75-84,11=85+,12=Unknown
education 63:64 1=<_8th_grade,2=9-12th_no_diploma,3=High_School_Graduate,4=College_Credit,5=Associates,6=Bachelors,7=Masters,9=Unknown
```
