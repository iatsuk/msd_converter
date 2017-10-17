# MillionSongDataset (MSD) converter to CSV
[![GitHub license](https://img.shields.io/badge/license-Apache%202-blue.svg?style=flat-square)](https://raw.githubusercontent.com/yatsukav/msd_converter/master/LICENSE)

## How to run
```
$ git clone https://github.com/yatsukav/msd_converter.git
$ mvn package
$ java -jar msd_converter.jar [path-to-msd-data]
```

## System requirements
* Windows OS (jhdf5.dll in git repo)
* Java 8
* Maven
* Git

## Useful links
* [Million Song Dataset (MSD)](https://labrosa.ee.columbia.edu/millionsong/)
* [hdf5_getters source](https://github.com/tbertinmahieux/MSongsDB)
* [ClassNotFound Solution](https://stackoverflow.com/questions/36385398/java-hdf5-library-install)
* [Last libraries](https://wiki-bsse.ethz.ch/display/JHDF5/Download+Page)