import ConfigParser
import os
import requests
import json
from datetime import datetime
from time import sleep

config = ConfigParser.ConfigParser()
config.read('UploadScript/config.env')
print config.get
print config.get('PARSER','FILE_LOCATION')
print config.get('PARSER','BACKEND_URL')
print config.get('PARSER','POST_ACTION')
loopieloopie = True;

while datetime.now().minute not in {0, 15, 30, 45}:  # Wait 1 second until we are synced up with the 'every 15 minutes' clock
    sleep(1)

def task():
    print("Update Ranking")

    # Your task goes here
    # Functionised because we need to call it twice
    filesToUpload = []

    for filename in os.listdir(config.get('PARSER','FILE_LOCATION')):
        if filename.endswith(".wer"):
            filesToUpload.append(filename)
            continue
        else:
            continue

    response = requests.post(config.get('PARSER','BACKEND_URL') + config.get('PARSER','POST_LIST_ACTION'), json=filesToUpload)

    for filename in json.loads(response.content):
        print(os.path.join(config.get('PARSER','FILE_LOCATION'), filename))
        f=open(os.path.join(config.get('PARSER','FILE_LOCATION'), filename), "r")
        if f.mode == 'r':
            contents =f.read()
            data = {
                "fileName": filename,
                "xmlValue": contents
            }
            response = requests.post(config.get('PARSER','BACKEND_URL') + config.get('PARSER','POST_ACTION'), json=data)
            print(response)
            continue
        else:
           continue


task()

while loopieloopie == True:
    sleep(60*60*1)  # Wait for 15 minutes
    task()




