
# Building the seeded MongoDB instance



#### build the image
#### Disregard the pulls if you already have the latest version  
#### Disregard the hash numbers, this will be different for every build

#### from the docker folder, execute the build command

```
docker build --tag seededmongo .
Sending build context to Docker daemon  127.5kB
Step 1/7 : FROM mongo:latest
latest: Pulling from library/mongo
8ee29e426c26: Pull complete
6e83b260b73b: Pull complete
e26b65fd1143: Pull complete
40dca07f8222: Pull complete
b420ae9e10b3: Pull complete
f60a3ef68cf9: Pull complete
4a9eeb4f5b1b: Pull complete
894bdb2cb33c: Pull complete
d58ab7ee0762: Pull complete
a9aa4a79fc15: Pull complete
9313fc63e525: Pull complete
e42838071bcb: Pull complete
1c8017deb374: Pull complete
030ecc03b10b: Pull complete
Digest: sha256:31e5b22fa85586a34a917d87aea2990f6bc46d87cd208cf7f249a852b09ff673
Status: Downloaded newer image for mongo:latest
 ---> 1e2b5a85319b
Step 2/7 : RUN mkdir -p /data/db2 && mkdir /files     && echo "dbpath = /data/db2" > /etc/mongodb.conf
 ---> Running in bc5c626e6e77
Removing intermediate container bc5c626e6e77
 ---> 29d8bc4011c4
Step 3/7 : ADD . /files
 ---> 348a9d196a0e
Step 4/7 : RUN chown -R mongodb:mongodb /data/db2 && chown -R mongodb:mongodb /files
 ---> Running in 6a17bafc3fb2
Removing intermediate container 6a17bafc3fb2
 ---> b2fc0d215a47
Step 5/7 : RUN mongod --fork --logpath /var/log/mongodb.log --dbpath /data/db2 --smallfiles && sleep 20     && mongoimport --db sku --collection gsma --type csv --headerline --file /files/mock_data.csv     && mongod --dbpath /data/db2 --shutdown     && chown -R mongodb /data/db2
 ---> Running in 90382ea49949
2018-07-31T14:03:47.273+0000 I CONTROL  [main] Automatically disabling TLS 1.0, to force-enable TLS 1.0 specify --sslDisabledProtocols 'none'
about to fork child process, waiting until server is ready for connections.
forked process: 8
child process started successfully, parent exiting
2018-07-31T14:04:10.669+0000    connected to: localhost
2018-07-31T14:04:10.699+0000    imported 250 documents
2018-07-31T14:04:10.719+0000 I CONTROL  [main] Automatically disabling TLS 1.0, to force-enable TLS 1.0 specify --sslDisabledProtocols 'none'
killing process with pid: 8
Removing intermediate container 90382ea49949
 ---> fe47c7658912
Step 6/7 : VOLUME /data/db2
 ---> Running in f05d82885b4f
Removing intermediate container f05d82885b4f
 ---> 63a68a598305
Step 7/7 : CMD ["mongod", "--config", "/etc/mongodb.conf", "--smallfiles"]
 ---> Running in f6b486672442
Removing intermediate container f6b486672442
 ---> f63773bea279
Successfully built f63773bea279
Successfully tagged seededmongo:latest
```

### Start the server

#### Note that the session will stay open so you can see the log output


```
[devops@avid-cfv216 MongoPreLoad]$ mongo --name seeded --rm -p 27017:27017 -ti seededmongo
-bash: mongo: command not found
[devops@avid-cfv216 MongoPreLoad]$ docker run --name seeded --rm -p 27017:27017 -ti seededmongo
jq: error (at /tmp/docker-entrypoint-config.json:1): Cannot index string with string "systemLog"
jq: error (at /tmp/docker-entrypoint-config.json:1): Cannot index string with string "net"
2018-07-31T14:04:52.912+0000 I CONTROL  [main] Automatically disabling TLS 1.0, to force-enable TLS 1.0 specify --sslDisabledProtocols 'none'
2018-07-31T14:04:52.918+0000 I CONTROL  [initandlisten] MongoDB starting : pid=1 port=27017 dbpath=/data/db2 64-bit host=114d57241af8
2018-07-31T14:04:52.918+0000 I CONTROL  [initandlisten] db version v4.0.0
2018-07-31T14:04:52.918+0000 I CONTROL  [initandlisten] git version: 3b07af3d4f471ae89e8186d33bbb1d5259597d51
2018-07-31T14:04:52.918+0000 I CONTROL  [initandlisten] OpenSSL version: OpenSSL 1.0.2g  1 Mar 2016
2018-07-31T14:04:52.918+0000 I CONTROL  [initandlisten] allocator: tcmalloc
2018-07-31T14:04:52.918+0000 I CONTROL  [initandlisten] modules: none
2018-07-31T14:04:52.918+0000 I CONTROL  [initandlisten] build environment:
2018-07-31T14:04:52.918+0000 I CONTROL  [initandlisten]     distmod: ubuntu1604
2018-07-31T14:04:52.919+0000 I CONTROL  [initandlisten]     distarch: x86_64
2018-07-31T14:04:52.919+0000 I CONTROL  [initandlisten]     target_arch: x86_64
2018-07-31T14:04:52.919+0000 I CONTROL  [initandlisten] options: { config: "/etc/mongodb.conf", net: { bindIpAll: true }, storage: { dbPath: "/data/db2", mmapv1: { smallFiles: true } } }
2018-07-31T14:04:52.919+0000 I STORAGE  [initandlisten] Detected data files in /data/db2 created by the 'wiredTiger' storage engine, so setting the active storage engine to 'wiredTiger'.
2018-07-31T14:04:52.919+0000 I STORAGE  [initandlisten]
2018-07-31T14:04:52.919+0000 I STORAGE  [initandlisten] ** WARNING: Using the XFS filesystem is strongly recommended with the WiredTiger storage engine
2018-07-31T14:04:52.919+0000 I STORAGE  [initandlisten] **          See http://dochub.mongodb.org/core/prodnotes-filesystem
2018-07-31T14:04:52.919+0000 I STORAGE  [initandlisten] wiredtiger_open config: create,cache_size=3397M,session_max=20000,eviction=(threads_min=4,threads_max=4),config_base=false,statistics=(fast),log=(enabled=true,archive=true,path=journal,compressor=snappy),file_manager=(close_idle_time=100000),statistics_log=(wait=0),verbose=(recovery_progress),
2018-07-31T14:04:53.896+0000 I STORAGE  [initandlisten] WiredTiger message [1533045893:896716][1:0x7ff611009a00], txn-recover: Main recovery loop: starting at 1/43904
2018-07-31T14:04:54.017+0000 I STORAGE  [initandlisten] WiredTiger message [1533045894:17261][1:0x7ff611009a00], txn-recover: Recovering log 1 through 2
2018-07-31T14:04:54.104+0000 I STORAGE  [initandlisten] WiredTiger message [1533045894:104650][1:0x7ff611009a00], txn-recover: Recovering log 2 through 2
2018-07-31T14:04:54.162+0000 I STORAGE  [initandlisten] WiredTiger message [1533045894:162669][1:0x7ff611009a00], txn-recover: Set global recovery timestamp: 0
2018-07-31T14:04:54.198+0000 I RECOVERY [initandlisten] WiredTiger recoveryTimestamp. Ts: Timestamp(0, 0)
2018-07-31T14:04:54.222+0000 W STORAGE  [initandlisten] Detected configuration for non-active storage engine mmapv1 when current storage engine is wiredTiger
2018-07-31T14:04:54.222+0000 I CONTROL  [initandlisten]
2018-07-31T14:04:54.222+0000 I CONTROL  [initandlisten] ** WARNING: Access control is not enabled for the database.
2018-07-31T14:04:54.222+0000 I CONTROL  [initandlisten] **          Read and write access to data and configuration is unrestricted.
2018-07-31T14:04:54.222+0000 I CONTROL  [initandlisten]
2018-07-31T14:04:54.222+0000 I CONTROL  [initandlisten]
2018-07-31T14:04:54.222+0000 I CONTROL  [initandlisten] ** WARNING: /sys/kernel/mm/transparent_hugepage/enabled is 'always'.
2018-07-31T14:04:54.222+0000 I CONTROL  [initandlisten] **        We suggest setting it to 'never'
2018-07-31T14:04:54.222+0000 I CONTROL  [initandlisten]
2018-07-31T14:04:54.222+0000 I CONTROL  [initandlisten] ** WARNING: /sys/kernel/mm/transparent_hugepage/defrag is 'always'.
2018-07-31T14:04:54.222+0000 I CONTROL  [initandlisten] **        We suggest setting it to 'never'
2018-07-31T14:04:54.222+0000 I CONTROL  [initandlisten]
2018-07-31T14:04:54.247+0000 I FTDC     [initandlisten] Initializing full-time diagnostic data capture with directory '/data/db2/diagnostic.data'
2018-07-31T14:04:54.248+0000 I NETWORK  [initandlisten] waiting for connections on port 27017
```

### start the client 

```
docker run --name client --rm -ti --link seeded:seeded seededmongo /bin/bash
```

you'll see a bunch of spam here ... just wait for the command prompt to appear


#### show the databases

```
> show dbs;
admin   0.000GB  
config  0.000GB  
local   0.000GB  
sku     0.000GB  
```

#### select database
```
> use sku;
switched to db sku
```


#### show the collections
```
> show collections;
gsma

```

#### select a single random entry

```
> db.gsma.findOne();
{
        "_id" : ObjectId("5b606c5a7bc4acb65c160bab"),
        "id" : 1,
        "first_name" : "Kristoffer",
        "last_name" : "Rudgard",
        "car_model" : "1500 Club Coupe",
        "car_year" : 1995,
        "city" : "Zinder"
}
```

### This should be good to go, stop or exit out of your two mongo instance and you'll be good to go