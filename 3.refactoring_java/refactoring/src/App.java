
public class App {
    public static void findFirstStringInBracket(String str){
        String result = "String not found!";
        if(str.length() > 0)
        {
            Integer indexFirstBracketFound = str.indexOf("(");
            if (indexFirstBracketFound >= 0)
            {
                Integer indexClosingBracketFound = str.indexOf(")");
                    if(indexClosingBracketFound >=0)
                {
                       result = str.substring(str.indexOf("(")+1,str.indexOf(")"));
                }   
            } 
        }
        System.out.print(result); 
    }

    public static void main(String[] args) throws Exception {
        findFirstStringInBracket("(Halooo)");
    }
}
