#!/usr/bin/env bash

# Copy downloads into 'plugins'
if [ -d "/data/build/downloads/plugins" ]; then
    cp /data/build/downloads/plugins/* /opt/xlr/server/plugins
fi

# Copy to plugins
cp /data/build/libs/*.jar /opt/xlr/server/plugins

# Link `ext` folder
find /data/src/main/resources -maxdepth 1 -mindepth 1 -type d -exec ln -s -f '{}' /opt/xlr/server/ext/ \;

# Start XLR and
# Run XLR initialize
count=0
/opt/xlr/server/bin/run.sh &
while true
do
  if [ $count -le 15 ]; then

    wget --spider -q http://localhost:5516
    if [ $? -ne 0 ] ;then
      echo "waiting $count"
      tail -1 /opt/xlr/server/log/xl-release.log
      sleep 4
      count=$(( count+1 ))
    else
      echo "Website is up"
      if [ -f /data/build/resources/test/docker/initialize/initialize_data.sh ]; then
         /data/build/resources/test/docker/initialize/initialize_data.sh
         if [ $? -ne 0 ]; then
           exit $?
         fi
      fi
      sleep 4
      exit
    fi
  else
    echo "Timeout exceeded...giving up waiting for website"
    exit 1
  fi
done