package avm;

import org.aion.avm.api.ABIDecoder;
import org.aion.avm.api.Address;
import org.aion.avm.api.BlockchainRuntime;
import org.aion.avm.userlib.AionList;
import org.aion.avm.userlib.AionMap;

import java.util.Set;

public class MagnetSmartContract {

    private static AionMap<String, AionList<Integer>> displayNameIndexesMap = new AionMap<>();
    private static AionMap<Integer, String> indexMagnetLinkMap = new AionMap<>();
    //private static AionList<Bounty> bountyList = new AionList<>();
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
/*
    public static void createBounty(String Name){
        //add bounty to bounty list
        //send Aion to the contract
        org.aion.avm.shadow.java.math.BigInteger bountyAmount = BlockchainRuntime.avm_getValue();

        //Bounty b = new Bounty(Name, bountyAmount);

        bountyList.add(new Bounty(Name, bountyAmount));
    }
    public static void submitLink(int bountyId, String magnetLink){
        //add submission to submission list for the bounty at bountyID

        Bounty b = bountyList.get(bountyId);

        b.addSubmission(new Submission(BlockchainRuntime.getCaller(), magnetLink));
    }

    public static void validateLinkAndReleaseBounty(int bountyId, int submissionId){
        //upload magnet link to the contract's magnet link map
        //send Aion to the person who submitted the correct magnet

        Bounty b = bountyList.get(bountyId);
        Submission s = b.submissions.get(submissionId);

        upload(b.name, s.magnetLink);

        // TO DO: Send bounty from Contract to submitter

        bountyList.remove(bountyId);
    }

    public static String getLinkByIndex(int index) {
        return indexMagnetLinkMap.get(index);
    }
*/
//    public static String[] getAllLinks() {
//        indexMagnetLinkMap.v
//    }


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
