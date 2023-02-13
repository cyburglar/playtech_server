### Playtech test task "Server path" half ended :) 

Generally fully working version of server. 

Sorry, but DatagramSocket (UDP) is not implemented. 
But it should be less or more in same way (and on same port).

There is small "cardinality violation" issue with hsqldb. 
Did small workaround. In generally all operations (insert, update, responses)
should going in one query. In PgSQL it can be simply solved by using 
"with as ... insert ... update ... returning" combinations.
But unfortunately hsqldb is not supported in way what is needed.

Implemented as simple as possible. 

Don't be scared with 'volatile' ... Atomicity is not needed there as they take more time.
'ReentrantLock' is enough. 

Just look the code. Yes it wrote in knees. 

Commit is with configuration. Because was used 'preview features'.
There is used virtual threads. Less memory, less resources, faster.

How to use gradle hope You know ;)  

Sorry, spent already 5 hours ... task is too big. 

Have no time anymore. 

> 01:16:42: Executing ':Server.main()'...
> Starting Gradle Daemon...
> Gradle Daemon started in 554 ms
>> Task :compileJava
>> Task :processResources NO-SOURCE
>> Task :classes
> Note: /Users/alex/IdeaProjects/playtech_server/src/main/java/Server.java uses preview features of Java SE 19.
> Note: Recompile with -Xlint:preview for details.
> Task :Server.main()
> Total balance updates [0] min.time [0] max.time[0] avg.time [   0.00000]
> Total balance updates [0] min.time [0] max.time[0] avg.time [   0.00000]