package ua.edu.ucu.autocomplete;

import org.junit.BeforeClass;
import org.junit.Test;
import ua.edu.ucu.tries.RWayTrie;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ManyWordsTest {

    private static List<String> words = new ArrayList<>(10000);

    private static Pattern wordPattern = Pattern.compile("[a-z][a-z]+");

    private PrefixMatches pm = new PrefixMatches(new RWayTrie());

    @BeforeClass
    public static void readFile() throws IOException  {
        final InputStream stream = ManyWordsTest.class.getResourceAsStream("/wiktionary.txt");
        try (final BufferedReader reader = new BufferedReader( new InputStreamReader( stream ) ) ) {
            reader.readLine();
            String line;
            while ( (line = reader.readLine()) != null ) {
                final String[] parts = line.split("\t");
                String w = parts[1];
                if ( wordPattern.matcher(w).matches() ) {
                    words.add(w);
                }
            }
        }
    }

    @Test
    public void verify_gar_1grp() {
        pm.load( words.toArray(new String[10000]) );
        Iterable<String> it = pm.wordsWithPrefix("gar",1);
        System.out.print("Found (k==1): ");
        it.forEach( s -> System.out.print(s + " ") );
        System.out.println("");
    }

    @Test
    public void verify_gar_2grp() {
        pm.load( words.toArray(new String[10000]) );
        Iterable<String> it = pm.wordsWithPrefix("gar",2);
        System.out.print("Found (k==2): ");
        it.forEach( s -> System.out.print(s + " ") );
        System.out.println("");
    }

    @Test
    public void verify_gar_3grp() {
        pm.load( words.toArray(new String[10000]) );
        Iterable<String> it = pm.wordsWithPrefix("gar",3);
        System.out.print("Found (k==3): ");
        it.forEach( s -> System.out.print(s + " ") );
        System.out.println("");
    }

}
