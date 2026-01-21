import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public static void main(String[] args) {
    Path path = Paths.get("./inventory1.csv");
    Path bomPath = Paths.get("./bom1.csv");
    Path swapPath = Paths.get("./swaps1.csv");
    try {
        String inventory1 = Files.readString(path);
        String bom = Files.readString(bomPath);
        String swaps = Files.readString(swapPath);
        // System.out.println(inventory1);
        
        String[] pairs = inventory1.split("\n");
        String[] bomPair = bom.split("\n");
        Map<String, Integer> inventoryMap = new HashMap<>();
        Map<String, Integer> bomMap = new HashMap<>();

        for(String pair: pairs){
            String[] keyValue = pair.split(",");
            if (keyValue.length == 2) {
                inventoryMap.put(keyValue[0].trim(), Integer.parseInt(keyValue[1].trim()));
            }
        }

          for(String pair: bomPair){
            String[] keyValue = pair.split(",");
            if (keyValue.length == 2) {
                bomMap.put(keyValue[0].trim(), Integer.parseInt(keyValue[1].trim()));
            }
        }

        Map<String, String[]> swapMap = new HashMap<>();
        String[] swappedList = swaps.split("\n");
        for(String skuList : swappedList){
            String[] skus = skuList.split(",");
            for(String sku : skus){
                swapMap.put(sku, skus);
            }
        }
        System.out.println(bomMap.toString());
        for(String newBom : bomMap.keySet()){
            String usedSku = newBom;
            boolean isValid = false;
            if(bomMap.get(newBom) <= inventoryMap.getOrDefault(newBom, 0)){
               isValid = true;
            }else{
                for(String cand : swapMap.getOrDefault(newBom, new String[]{})){
                    if(bomMap.get(newBom) <= inventoryMap.getOrDefault(cand, 0)){
                        isValid = true;
                        usedSku = cand;
                        break;
                    }  
                }
            }
            
            System.out.println(newBom + " | " + usedSku + "|" + bomMap.get(newBom) + "|" + isValid);
        }

    } catch (Exception e) {
        System.err.print(e);
    }
}

