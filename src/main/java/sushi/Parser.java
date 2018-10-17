package sushi;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;

public class Parser {
    private static final String SAMPLE_CSV_FILE_PATH = "C:/Users/Developer/Downloads/Sacramentorealestatetransactions.csv";

private static List<Product> parse() throws IOException {        try (
        Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
) {
    ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
    strategy.setType(Product.class);
    String[] memberFieldsToBindTo = {"street", "city", "zip", "state", "beds", "baths", "sq__ft", "type", "sale_date", "price", "latitude", "longitude"};
    strategy.setColumnMapping(memberFieldsToBindTo);

    CsvToBean<Product> csvToBean = new CsvToBeanBuilder(reader)
            .withMappingStrategy(strategy)
            .withSkipLines(1)
            .withIgnoreLeadingWhiteSpace(true)
            .build();

    Iterator<Product> myUserIterator = csvToBean.iterator();
List<Product> result  = new ArrayList();
    while (myUserIterator.hasNext()) {
        Product myUser = myUserIterator.next();
        System.out.println("Name : " + myUser.toString());
        System.out.println("street : " + myUser.getStreet());
        System.out.println("price : " + myUser.getPrice());
        System.out.println("---------------------------"); result.add(myUser);
    }
return result;
}}


private static Map<String, Type> makeHashMap(List<Product> productIterator,String city) {
Map<String , Type> result  = new HashMap<>();

    for (Product pr : productIterator) {

if (pr.getCity().equals(city)) {
Type type ;
switch(pr.getType()){
case "Residential": {type =Type.Residential; break;}
    case "Condo": {type =Type.Condo; break;}
    default:{type =Type.MIX; break;}
}

result.put(pr.getCity() +  ", " + pr.getStreet(), type);

}
    }

for(Entry<String, Type> ek : result.entrySet()){
    System.out.println("kye : " + ek.getKey());
    System.out.println("value : " +  ek.getValue());
}

return result;

}

  private static Type getRealType(boolean cashVisible, boolean cardVisible) {
if(cashVisible && cardVisible) return Type.MIX;
if (cashVisible) return Type.Residential;
if (cardVisible) return Type.Condo;

return  Type.MIX;

  }



    public static void main(String[] args) throws IOException {
        List<Product> products =  parse();
        Map<String, Type> adresses =makeHashMap(products, "SACRAMENTO");

String realAddress   = "SACRAMENTO, 7687 HOWERTON DR";
Type type =adresses.get(realAddress);
Type realType= Type.Residential;
if( !type.equals(realType) ) {
    System.out.println("alarm");

}
    }

}


