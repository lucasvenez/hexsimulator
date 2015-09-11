# HexSimulator and HexClient

This project aims to provide a simulator for the [Hex Game](https://en.wikipedia.org/wiki/Hex_(board_game)). 

For using this simulator execute following command:

```{java}
java -jar HexSimulator.jar -p1 2001 -p2 2002
```

The simulator implements a simple communication protocol, that has a reference client HexClient, 
which can be started as first player using the command below:

```{java}
java -jar HexClient.jar -p 2001
```
