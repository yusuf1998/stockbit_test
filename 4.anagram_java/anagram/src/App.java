import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class App {
    private static void checkAnagram(String arr[])
    {
        HashMap<String, List<String> > mapKata = new HashMap<>();
        for (int i = 0; i < arr.length; i++) { 
            String kata = arr[i];
            char[] kt = kata.toCharArray();
            Arrays.sort(kt);
            String kataBaru = new String(kt);

            if (mapKata.containsKey(kataBaru)) { 
                mapKata.get(kataBaru).add(kata);
            }
            else {
                List<String> listKata = new ArrayList<>();
                listKata.add(kata);
                mapKata.put(kataBaru, listKata);
            }
        } 
        for (String s : mapKata.keySet()) {
            List<String> values = mapKata.get(s);
            if (values.size() > 0) {
                System.out.println(values);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String tesKata[] = {"kita", "atik", "tika", "aku", "kia", "makan", "kua"}; 
        checkAnagram(tesKata);
    }
}
