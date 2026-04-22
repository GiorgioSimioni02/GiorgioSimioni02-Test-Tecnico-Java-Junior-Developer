DESCRIZIONE

Questa applicazione conta le N parole più frequenti in un determinato file di testo.


DETTAGLI ESECUZIONE

Per eseguire l'applicazione aprire la linea di comando e posizionarsi nella cartella che contiene "src", dopo di che utilizzare il comando "java -cp src topwords.Topwords".
se si vuole specificare il percorso del file o la dimensione della classifica si specificano in questo modo "java -cp src topwords.Topwords input/hello.txt 10". 


DETTAGLI PARAMETRI

Al momento dell'esecuzione si possono dare in input:

  -N: dimensione della "classifica" visualizzata in console. (Default = 6)
  
  -il percorso a cui trovare il file.                        (Default = input/text.txt)
  
  -tali parametri possono essere inseriti in qualunque ordine:
  
    -il primo parametro contenente ".txt" viene impostato come filepath.
    
    -il primo parametro coposto solamente da cifre viene impostato come dimensione della classifica.
    
  -Se nessuno dei parametri inseriti contiene ".txt" allora verrà usato il path di default.
  
  -Se nessuno dei parametri inseriti è composto da solo cifre, allora la dimensione della classifica viene impostata a quella di default.

