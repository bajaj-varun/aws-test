import sys
import csv
import random
import time
import shutil
from botocore.exceptions import ClientError
import boto3
import configparser

from datetime import datetime

class RandomDataGenerator:
    def readSourceFile(self, csvlocation):
        self.data=[]
        with open(csvlocation, newline='') as f:
            reader = csv.reader(f)
            self.data = list(reader)
        self.sLen = len(self.data)

    def getStringFromList(self, lst):
        return ",".join([str(val) for val in lst])+"\n"

    def generateRandomFile(self, lineCount):
        dTime = datetime.now().strftime("%Y%m%d_%H:%M:%S")
        fileName = config.get("default","seed_file_name_prefix")\
                   +dTime\
                   +config.get("default","seed_file_name_suffix")

        wr = open(config.get("default","seed_folder")+fileName,"w")
        # Print header
        wr.writelines(self.getStringFromList(self.data[0]))
        # Print rest of lines
        for i in range(0,lineCount):
            wr.writelines(self.getStringFromList(self.data[random.randint(1,self.sLen-1)]))
        wr.close()

        print("Lines generated =>"+str(lineCount)+", FileName =>"+fileName)
        return fileName

    def copyFile(self, processIndicator, fileName):
        if processIndicator:
            shutil.move(config.get("default", "seed_folder") + fileName,
                        config.get("default", "seed_folder") + "./success/" + fileName)
        else:
            shutil.move(config.get("default", "seed_folder") + fileName,
                        config.get("default", "seed_folder") + "./failed/" + fileName)

global config
config = configparser.ConfigParser()
config.read('resources/data.config')
rd = RandomDataGenerator()
rd.readSourceFile(sys.argv[1])
dTime = datetime.now().strftime("%Y%m%d_%H:%M:%S")
s3 = boto3.client(
        's3',
        region_name=config.get("aws","region_name"),
        aws_access_key_id=config.get("aws","aws_access_key_id"),
        aws_secret_access_key=config.get("aws","aws_secret_access_key")
    )

while True:
 fileName = rd.generateRandomFile(random.randint(100,150))
 try:
    s3.upload_file(
        "./seed_data/"+fileName,
        config.get("aws","bucket_name"),
        fileName
    )
    rd.copyFile(True, fileName)

 except ClientError as e:
    print(e)
    rd.copyFile(False, fileName)
 except Exception as e:
    print(e)
    rd.copyFile(False, fileName)

 time.sleep(120)
