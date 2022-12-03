# CONSEGNA LAB10

### LAB 101-lambda-utilities-for-lists-and-maps

### CASA 102-stream-utilities-for-music-model

- Nel metodo **albumInYear()** non ho usato un **entrySet()** ma un **keySet** di albums
- Per **countSongs()** e **averageDurationOfSongs()** ho creato un metodo a parte che si occupasse di filtrare **songs**, controllare se la canzone avesse l'album e nel caso controllare se l'album fosse uguale all'album passato come argomento. L'ho fatto per evitare ripetizioni visto che questi procedimenti dovevano essere svolti più di una volta.
- In **countSongsInNoAlbum()** piuttosto che usare **.isEmpty()** ho scritto "**!song.getAlbumName().isPresent()**"
- In **averageDurationOfSongs()** non ho usato **mapToDouble** ma ho messo tutto lo stream dentro un **OptionalDouble.of()** e non ho notato l'esistenza di **.average()** quindi ho fatto a mano con una **reduce()**
- Anche in **longestSong()** ho messo tutto dentro un **Optional.of()** e non ho usato **collect** ma ho usato direttamente **max()** con una lambda
- In **longestAlbum()** probabilmente ho usato un metodo meno efficiente ma non sapevo che collect potesse fare tutta quella roba. Ho usato uno Stream per filtrare le canzoni in un album, poi ho usato un forEach per iterarle e inserirle in una hashmap e poi ho usato un altro stream su questa hashmap per prendere il valore massimo con **max()**

### CASA 103-stream-utilities-for-text-processing

- Nel lambda per contare i char ho usato uno stream... mi sono fatta prendere la mano
- Per contare le righe ho usato uno **split("\n")**
- Per contare ogni parola ho fatto lo stesso "errore" dell'esercizio 102 non usando collect.
