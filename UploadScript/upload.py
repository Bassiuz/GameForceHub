import ConfigParser
import os

config = ConfigParser.ConfigParser()
config.read('config.env')
print config.get('PARSER','FILE_LOCATION')
print config.get('PARSER','BACKEND_URL')

for filename in os.listdir(config.get('PARSER','FILE_LOCATION')):
    if filename.endswith(".wer"):
        print(os.path.join(config.get('PARSER','FILE_LOCATION'), filename))
        f=open(os.path.join(config.get('PARSER','FILE_LOCATION'), filename), "r")
        if f.mode == 'r':
        	contents =f.read()
        	print(contents)

        continue
    else:
        continue