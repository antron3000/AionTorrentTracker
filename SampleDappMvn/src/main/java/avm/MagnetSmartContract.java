package avm;

import org.aion.avm.api.ABIDecoder;
import org.aion.avm.api.BlockchainRuntime;
import org.aion.avm.userlib.AionList;
import org.aion.avm.userlib.AionMap;

public class MagnetSmartContract {

    private static AionMap<String, AionList> displayNameIndexesMap = new AionMap<>();
    private static AionMap<Integer, String> indexMagnetLinkMap = new AionMap<>();
    private static int index = 0;

    public static byte[] main() {
        return ABIDecoder.decodeAndRunWithClass(MagnetSmartContract.class, BlockchainRuntime.getData());
    }

    public static void upload(String displayName, String magnetLink) {

        indexMagnetLinkMap.put(index, magnetLink);

        if(displayNameIndexesMap.containsKey(displayName)) {
            //key exists hence append to List
            AionList list = displayNameIndexesMap.get(displayName);
            list.add(index);
            displayNameIndexesMap.put(displayName, list);
        }
        else {
            //add new List object
            AionList list = new AionList();
            list.add(index);
            displayNameIndexesMap.put(displayName, list);
        }

        index++;
    }

    /*public static String search(String displayName) {
        return map1.get(key);
    }*/

}
