package avm;

import org.aion.avm.api.ABIDecoder;
import org.aion.avm.api.BlockchainRuntime;
import org.aion.avm.userlib.AionList;
import org.aion.avm.userlib.AionMap;
import java.util.Set;

public class MagnetSmartContract {

    private static AionMap<String, AionList<Integer>> displayNameIndexesMap = new AionMap<>();
    private static AionMap<Integer, String> indexMagnetLinkMap = new AionMap<>();
    private static int index = 0;
    private static double MIN_MATCH = 0.5;

    public static byte[] main() {
        return ABIDecoder.decodeAndRunWithClass(MagnetSmartContract.class, BlockchainRuntime.getData());
    }

    public static void upload(String displayName, String magnetLink) {
        AionList list = null;
        indexMagnetLinkMap.put(index, magnetLink);

        if(displayNameIndexesMap.containsKey(displayName)) {
            //key exists hence append to List
            list = displayNameIndexesMap.get(displayName);
            list.add(index);
            displayNameIndexesMap.put(displayName, list);
        }
        else {
            //add new List object
            list = new AionList();
            list.add(index);
            displayNameIndexesMap.put(displayName, list);
        }

        index++;
    }

    // Return type needs to be changed later
    public static String[] search(String displayName) {
        Set<String> keys = displayNameIndexesMap.keySet();
        AionList<Integer> indexQuery = new AionList();

        double ratio;
        for(String key : keys) {
            ratio = DiffUtils.getRatio(key, displayName);

            BlockchainRuntime.println("Ratio: " + ratio);

            if(ratio >= MIN_MATCH) {
                // Found a potential match extract indicies, get the indicies this points to
                AionList<Integer> indicies = displayNameIndexesMap.get(key);

                for(int i : indicies) {
                    indexQuery.add(i);
                }
            }
        }

        String[] toReturn = new String[indexQuery.size()];

        for(int i : indexQuery) {
            toReturn[i] = "index=" + i + "," + indexMagnetLinkMap.get(i);
        }

        return toReturn;
    }

    public static String getLinkByIndex(int index) {
        return indexMagnetLinkMap.get(index);
    }


    // FOR TESTING ONLY - DOES NOT WORK
    /*public static int[] getIndexes(String displayName) {

        AionList<Integer> list = displayNameIndexesMap.get(displayName);
        int[] array = list.stream().mapToInt(i->i).toArray();
        return array;
    }*/

    // FOR TESTING ONLY
    public static int getIndex(String displayName) {

        AionList<Integer> list = displayNameIndexesMap.get(displayName);
        return  list.get(1);
    }

    // FOR TESTING ONLY
    public static String getMagnetLink(int index) {
        return indexMagnetLinkMap.get(index);
    }

}
