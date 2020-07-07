FROM mongo:4.0.2

# Modify child mongo to use /data/db2 as dbpath (because /data/db wont persist the build)
RUN mkdir -p /data/db2 \
&& mkdir /files \
    && echo "dbpath = /data/db2" > /etc/mongodb.conf
ADD . /files

RUN chown -R mongodb:mongodb /data/db2 \
&& chown -R mongodb:mongodb /files

RUN mongod --fork --logpath /var/log/mongodb.log --dbpath /data/db2 --smallfiles && sleep 20 \
    && mongoimport --db sku --collection gsma --type csv --headerline --file /files/mock_data.csv \
    && mongod --dbpath /data/db2 --shutdown \
    && chown -R mongodb /data/db2

# Make the new dir a VOLUME to persists it
VOLUME /data/db2

CMD ["mongod", "--config", "/etc/mongodb.conf", "--smallfiles"]
